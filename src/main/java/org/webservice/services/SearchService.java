package org.webservice.services;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.ru.RussianAnalyzer;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.document.*;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.gagauz.utils.C;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webservice.database.dao.HqlEntityFilter;
import org.webservice.database.dao.RepetitorDao;
import org.webservice.database.model.*;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

@Service("SearchService")
public class SearchService {

    private final static Logger LOG = LoggerFactory.getLogger(SearchService.class);

    private static final CharArraySet STOP_WORDS_SET = new CharArraySet(Arrays.asList("по", "репетитор", "репетиторы", "и", "или", "для", "при", "на", "под",
            "с", "без", "от", "в"),
            true);

    private static final Analyzer ANALYZER = new RussianAnalyzer(STOP_WORDS_SET);
    private static Directory directory;
    private static final String INDEX_PATH = "/dev/shm/search_index.idx";
    private static final String ALL_LATIN = "`qwertyuiop[]asdfghjkl;'zxcvbnm,.~QWERTYUIOP{}ASDFGHJKL:\"ZXCVBNM<>";
    private static final String ALL_CYRILLIC = "ёйцукенгшщзхъфывапролджэячсмитьбюЁЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮ";
    private static final String[] FIELDS = {"id", "subject", "repetitor", "section_extra", "region", "education"};
    private static final Map<String, Float> BOOST = new HashMap<String, Float>();
    private QueryParser parser;
    static {
        BOOST.put("id", 5.0f);
        BOOST.put("subject", 4.0f);
        BOOST.put("repetitor", 3.0f);
        BOOST.put("region", 2.0f);
        BOOST.put("section_extra", 2.0f);
        BOOST.put("education", 1.0f);
    }

    private static ReentrantLock LOCK = new ReentrantLock();

    @Autowired
    private RepetitorDao repetitorDao;

    public List<Repetitor> searchRepetitors(String searchWord) {
        if (null != searchWord) {
            searchWord = searchWord.toLowerCase();
        }

        if (null == directory) {
            invoke();
        }

        List<Repetitor> result = searchProductsInternal(searchWord);

        // начало: если ничего не найдено, то слово введено не в той раскладке клавиатуры?
        if (result.size() == 0
                && !StringUtils.isEmpty(searchWord)) {
            int latinCount = 0;
            int cyrillicCount = 0;
            StringBuilder sbCyrillic = new StringBuilder();
            StringBuilder sbLatin = new StringBuilder();
            for (int i = 0; i < searchWord.length(); ++i) {
                int latinIndex = ALL_LATIN.indexOf(searchWord.charAt(i));
                int cyrIndex = ALL_CYRILLIC.indexOf(searchWord.charAt(i));
                if (latinIndex >= 0) {
                    sbCyrillic.append(ALL_CYRILLIC.charAt(latinIndex));
                    ++latinCount;
                } else {
                    sbCyrillic.append(searchWord.charAt(i));
                    if (ALL_CYRILLIC.contains(Character.toString(searchWord.charAt(i)))) {
                        ++cyrillicCount;
                    }
                }
                if (cyrIndex >= 0) {
                    sbLatin.append(ALL_LATIN.charAt(cyrIndex));
                    ++cyrillicCount;
                } else {
                    sbLatin.append(searchWord.charAt(i));
                    if (ALL_LATIN.contains(Character.toString(searchWord.charAt(i)))) {
                        ++latinCount;
                    }
                }
            }

            if (latinCount > 0 && cyrillicCount == 0) {
                result = searchProductsInternal(searchWord);//поиск на латинице почему-то находит только по словам в верхнем регистре
                if (result.size() == 0) {
                    result = searchProductsInternal(sbCyrillic.toString());
                }
            }
        }

        return result;
    }

    private List<Repetitor> searchProductsInternal(String searchWord) {

        String queryLine = buildQuery(searchWord);

        Set<Integer> inIds = C.newHashSet();
        int id = NumberUtils.toInt(searchWord);
        if (id > 0) {
            inIds.add(id);
        } else if (queryLine != null) {

            final long now = System.currentTimeMillis();

            QueryParser parser = buildQueryParser(queryLine);
            IndexReader reader = null;
            try {
                LOG.info("Query line: {}", queryLine);
                reader = DirectoryReader.open(directory);
                IndexSearcher searcher = new IndexSearcher(reader);
                TopScoreDocCollector collector = TopScoreDocCollector.create(500);
                Query query = parser.parse(queryLine);
                LOG.info("Searching for: {}", query.toString());
                searcher.search(query, collector);
                ScoreDoc[] hits = collector.topDocs().scoreDocs;
                int numTotalHits = collector.getTotalHits();

                System.out.println(numTotalHits);
                for (ScoreDoc hit : hits) {
                    Document doc = searcher.doc(hit.doc);
                    id = (Integer) doc.getField("id").numericValue();
                    inIds.add(id);
                }

                LOG.info("Results found : {}", numTotalHits);
                LOG.info("Search took : {} ms", System.currentTimeMillis() - now);
            } catch (Exception e) {
                LOG.error("", e);
            } finally {
                IOUtils.closeQuietly(reader);
            }
        }

        if (!inIds.isEmpty()) {
            HqlEntityFilter filter = new HqlEntityFilter();
            filter.in("r.id", inIds);
            return repetitorDao.findAllWithData(filter);
        }

        return Collections.emptyList();
    }

    private void indexRepetitor(IndexWriter writer, RepetitorSubject repetitorSubject) throws CorruptIndexException, IOException {

        Document doc = new Document();

        Repetitor repetitor = repetitorSubject.getRepetitor();
        Subject subject = repetitorSubject.getSubject();

        doc.add(new IntField("id", repetitor.getId(), Field.Store.YES));
        doc.add(new StringField("subject", subject.getName() + subject.getName2(), Store.NO));
        doc.add(new TextField("repetitor", repetitor.getName() + " " + repetitor.getPatronymic(), Store.NO));

        StringBuilder sb = new StringBuilder();
        for (SubjectSection section : repetitorSubject.getSubjectSections()) {
            sb.append(section.getName()).append(", ");
        }
        for (SubjectExtra extra : repetitorSubject.getSubjectExtras()) {
            sb.append(extra.getName()).append(", ");
        }
        doc.add(new TextField("section_extra", sb.toString().toLowerCase(), Store.NO));
        doc.add(new TextField("education", repetitor.getEducation(), Store.NO));

        if (null != repetitor.getPlaceRepetitorRegion()) {
            doc.add(new TextField("region", repetitor.getPlaceRepetitorRegion().getName().toLowerCase(), Field.Store.NO));
        }

        writer.addDocument(doc);
    }

    private String buildQuery(String searchWord) {

        StringBuilder sb = new StringBuilder();
        String searchQuery = searchWord.trim()
                .replace('\\', ' ').replace('/', ' ').replace('"', ' ')
                .replaceAll("(\\s*(AND|[\\+\\-/&\\|!\\(\\)%\\[\\]\\{\\}\\^~\\*\\?:]|\\s{2})\\s*)+", " ")
                .trim();

        if (searchQuery.length() < 2) {
            return null;
        }

        sb.append(searchQuery);

        //        if (!sharp) {
        //            sb.append('*');
        //        }

        return sb.toString();
    }

    public void invoke() {
        if (LOCK.tryLock()) {
            final IndexWriterConfig indexWriterConfig = new IndexWriterConfig(ANALYZER);

            int i = 0;
            Directory directoryIndex = null;
            IndexWriter indexWriter = null;
            try {

                directoryIndex = FSDirectory.open(Paths.get(INDEX_PATH));
                indexWriter = new IndexWriter(directoryIndex, indexWriterConfig);

                indexWriter.deleteAll();
                indexWriter.commit();

                HqlEntityFilter filter = new HqlEntityFilter();

                for (Repetitor repetitor : repetitorDao.findAllWithData(filter)) {
                    for (RepetitorSubject repetitorSubject : repetitor.getSubjects()) {
                        indexRepetitor(indexWriter, repetitorSubject);
                    }
                    i++;
                }
                indexWriter.commit();
                LOG.info("Indexed {} products in total", i);
            } catch (Exception e) {
                LOG.error("Error while creating index", e);
            } finally {
                LOCK.unlock();
                IOUtils.closeQuietly(indexWriter);
                if (indexWriter != null) {
                    directory = directoryIndex;
                }
            }
        }
    }

    private QueryParser buildQueryParser(String query) {
        if (null == parser) {
            parser = new MultiFieldQueryParser(FIELDS, ANALYZER, BOOST);
            parser.setDefaultOperator(QueryParser.Operator.AND);
            parser.setAllowLeadingWildcard(false);
            parser.setPhraseSlop(0);
        }
        return parser;
    }
}
