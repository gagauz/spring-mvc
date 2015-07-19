package org.repetitor.test.scenarios;

import org.gagauz.utils.C;
import org.repetitor.database.dao.SubjectDao;
import org.repetitor.database.dao.SubjectExtraDao;
import org.repetitor.database.dao.SubjectSectionDao;
import org.repetitor.database.model.Subject;
import org.repetitor.database.model.SubjectExtra;
import org.repetitor.database.model.SubjectSection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ScenarioSubject extends DataBaseScenario {
    @Autowired
    private SubjectDao subjectDao;

    @Autowired
    private SubjectSectionDao subjectSectionDao;

    @Autowired
    private SubjectExtraDao subjectExtraDao;

    private int counterE = 0;
    private int counterEe = 0;
    private int counterSs = 0;
    private int counterS = 0;

    private List<Subject> save = C.newArrayList();

    private List<SubjectExtra> saveE = C.newArrayList();
    private List<SubjectSection> saveS = C.newArrayList();

    @Override
    @Transactional
    protected void execute() {
        Subject mat = createCategory("Математика");
        createExtra("ЕГЭ", mat);
        createExtra("ОГЭ (ГИА)", mat);
        createExtra("подготовка к олимпиадам", mat);
        createSection("школьный курс", mat);
        createSection("актуарная математика", mat);
        createSection("алгебра", mat);
        createSection("алгебра логики", mat);
        createSection("аналитическая геометрия", mat);
        createSection("вариационное исчисление", mat);
        createSection("векторный анализ", mat);
        createSection("высшая математика", mat);
        createSection("вычислимые функции", mat);
        createSection("геометрия", mat);
        createSection("дискретная математика", mat);
        createSection("дифференциальная геометрия", mat);
        createSection("дифференциальные уравнения", mat);
        createSection("интегральные уравнения", mat);
        createSection("комбинаторика", mat);
        createSection("линейная алгебра", mat);
        createSection("линейная геометрия", mat);
        createSection("линейное программирование", mat);
        createSection("математи логика", mat);
        createSection("математическая статистика", mat);
        createSection("математическая физика", mat);
        createSection("математические модели", mat);
        createSection("математический анализ", mat);
        createSection("методы оптимальных решений", mat);
        createSection("методы оптимизации", mat);
        createSection("на английском языке", mat);
        createSection("оптимальное управление", mat);
        createSection("прикладная математика", mat);
        createSection("сопромат", mat);
        createSection("тензорный анализ", mat);
        createSection("теоретическая механика", mat);
        createSection("теория вероятностей", mat);
        createSection("теория графов", mat);
        createSection("теория игр", mat);
        createSection("теория оптимизации", mat);
        createSection("теория приближений", mat);
        createSection("теория чисел", mat);
        createSection("топология", mat);
        createSection("тригоном-ия", mat);
        createSection("ТФКП", mat);
        createSection("уравнения в частных производных", mat);
        createSection("уравнения математической физики", mat);
        createSection("финансовая математика", mat);
        createSection("функциональный анализ", mat);
        createSection("численные методы", mat);
        createSection("эконометрика", mat);

        mat = createCategory("Русский язык", "Репетиторы по русскому языку");
        createExtra("ЕГЭ", mat);
        createExtra("ОГЭ (ГИА)", mat);
        createExtra("подготовка к олимпиадам", mat);
        createSection("школьный курс", mat);
        createSection("вузовский курс", mat);

        Subject parent = createCategory("Иностранные языки", true);
        mat = createCategory("Английский язык", "Репетиторы по английскому языку", parent);
        createExtra("A-Level", mat);
        createExtra("BEC", mat);
        createExtra("CAE", mat);
        createExtra("CPE", mat);
        createExtra("ESOL Pitman", mat);
        createExtra("FCE", mat);
        createExtra("GMAT", mat);
        createExtra("GRE", mat);
        createExtra("IELTS", mat);
        createExtra("ILEC", mat);
        createExtra("KET", mat);
        createExtra("PET", mat);
        createExtra("SAT", mat);
        createExtra("TKT", mat);
        createExtra("TOEFL", mat);
        createExtra("TOEIC", mat);
        createExtra("YLE", mat);
        createExtra("бизнес-курс", mat);
        createExtra("ЕГЭ", mat);
        createExtra("ОГЭ (ГИА)", mat);
        createExtra("подготовка к олимпиадам", mat);

        mat = createCategory("Немецкий язык", "Репетиторы по немецкому языку", parent);
        createExtra("DSD", mat);
        createExtra("DSH", mat);
        createExtra("Goethe-Zertifikat C2", mat);
        createExtra("KDS", mat);
        createExtra("TestDAF", mat);
        createExtra("ZDaF", mat);
        createExtra("ZMP", mat);
        createExtra("бизнес-курс", mat);
        createExtra("ЕГЭ", mat);
        createExtra("ОГЭ (ГИА)", mat);

        mat = createCategory("Французский язык", "Репетиторы по французскому языку", parent);
        createExtra("DALF", mat);
        createExtra("DELF", mat);
        createExtra("TCF", mat);
        createExtra("TEF", mat);
        createExtra("TEFAQ", mat);
        createExtra("бизнес-курс", mat);
        createExtra("ЕГЭ", mat);
        createExtra("ОГЭ (ГИА)", mat);

        mat = createCategory("Итальянский язык", "Репетиторы по итальянскому языку", parent);
        createExtra("CELI", mat);
        createExtra("CILS", mat);
        createExtra("бизнес-курс", mat);

        mat = createCategory("Испанский язык", "Репетиторы по испанскому языку", parent);
        createExtra("D.E.L.E.", mat);
        createExtra("ЕГЭ", mat);
        createExtra("ОГЭ (ГИА)", mat);
        createExtra("бизнес-курс", mat);

        mat = createCategory("Китайский язык", "Репетиторы по китайскому языку", parent);
        createExtra("HSK", mat);
        createExtra("бизнес-курс", mat);

        mat = createCategory("Арабский язык", "Репетиторы по арабскому языку", parent);
        mat = createCategory("Японский язык", "Репетиторы по японскому языку", parent);
        createSection("общий курс", mat);
        createSection("иероглифика", mat);
        createSection("каллиграфия", mat);
        createExtra("сертификат Нихонго норёку сикэн", mat);
        createExtra("бизнес-курс", mat);

        mat = createCategory("Редкие иностранные языки", "Репетиторы по редким иностранным языкам", parent);
        mat = createCategory("Русский как иностранный", "Репетиторы по русскому языку для иностранцев", parent);
        createSection("язык-посредник: азербайджанский", mat);
        createSection("язык-посредник: английский", mat);
        createSection("язык-посредник: арабский", mat);
        createSection("язык-посредник: армянский", mat);
        createSection("язык-посредник: болгарский", mat);
        createSection("язык-посредник: венгерский", mat);
        createSection("язык-посредник: вьетнамский", mat);
        createSection("язык-посредник: греческий", mat);
        createSection("язык-посредник: грузинский", mat);
        createSection("язык-посредник: иврит", mat);
        createSection("язык-посредник: испанский", mat);
        createSection("язык-посредник: итальянский", mat);
        createSection(" язык-посредник: китайский", mat);
        createSection("язык-посредник: корейский", mat);
        createSection("язык-посредник: любой", mat);
        createSection("язык-посредник: македонский", mat);
        createSection("язык-посредник: монгольский", mat);
        createSection("язык-посредник: немецкий", mat);
        createSection("язык-посредник: осетинский", mat);
        createSection("язык-посредник: персидский", mat);
        createSection("язык-посредник: польский", mat);
        createSection("язык-посредник: португальский", mat);
        createSection("язык-посредник: сербский", mat);
        createSection("язык-посредник: таджикский", mat);
        createSection("язык-посредник: турецкий", mat);
        createSection("язык-посредник: узбекский", mat);
        createSection("язык-посредник: финский", mat);
        createSection("язык-посредник: французский", mat);
        createSection("язык-посредник: хинди", mat);
        createSection("язык-посредник: хорватский", mat);
        createSection("язык-посредник: чешский", mat);
        createSection("язык-посредник: шведский", mat);
        createSection("язык-посредник: японский", mat);
        createExtra("ТРКИ", mat);
        createExtra("ТЭУ", mat);

        mat = createCategory("Физика");
        createSection("вузовский курс", mat);
        createSection("школьный курс", mat);
        createSection("атомная физика", mat);
        createSection("волновая оптика", mat);
        createSection("геометрическая оптика", mat);
        createSection("детали машин", mat);
        createSection("динамика", mat);
        createSection("квантовая механика", mat);
        createSection("кинематика", mat);
        createSection("магнетизм", mat);
        createSection("механика", mat);
        createSection("механика жидкости и газа", mat);
        createSection("молекулярная физика", mat);
        createSection("на английском языке", mat);
        createSection("оптика", mat);
        createSection("основы электроники", mat);
        createSection("релейная защита и автоматика", mat);
        createSection("релятивистская механика", mat);
        createSection("сопромат", mat);
        createSection("статика", mat);
        createSection("строительная механика", mat);
        createSection("теоретическая механика", mat);
        createSection("теоретические основы электротехники", mat);
        createSection("теория относительности", mat);
        createSection("теория электрических цепей", mat);
        createSection("теплота", mat);
        createSection("термодинамика", mat);
        createSection("физика твердого тела", mat);
        createSection("электричество", mat);
        createSection("электромеханика", mat);
        createSection("электротехника", mat);
        createSection("ядерная физика", mat);
        createExtra("ЕГЭ", mat);
        createExtra("ОГЭ (ГИА)", mat);
        createExtra("подготовка к олимпиадам", mat);

        mat = createCategory("Информатика/программирование", "Репетиторы по информатике");
        createSection("ruby", mat);
        createSection("SEO (search engine optimization)", mat);
        createSection("SolidWorks", mat);
        createSection("SQL", mat);
        createSection("t-sql", mat);
        createSection("TurboPascal", mat);
        createSection("Unix", mat);
        createSection("VB Pro", mat);
        createSection("VBA", mat);
        createSection("visual basic", mat);
        createSection("Windows", mat);
        createSection("Word", mat);
        createSection("Wordpress", mat);
        createSection("xml", mat);
        createSection("алгоритмы", mat);
        createSection("анимация", mat);
        createSection("выпуклое программирование", mat);
        createSection("дизайн веб-сайтов", mat);
        createSection("компьютерная грамотность", mat);
        createSection("компьютерная графика", mat);
        createSection("линейное программирование", mat);
        createSection("объемное моделирование", mat);
        createSection("операционные системы", mat);
        createSection("программирование", mat);
        createSection("разработка веб-сайтов", mat);
        createSection("РЕФАЛ", mat);
        createSection("системное администрирование", mat);
        createExtra("ЕГЭ", mat);
        createExtra("ОГЭ (ГИА)", mat);
        createExtra("подготовка к олимпиадам", mat);

        mat = createCategory("Химия");
        createSection("вузовский курс", mat);
        createSection("школьный курс", mat);
        createSection("аналитическая химия", mat);
        createSection("бионеорганическая химия", mat);
        createSection("биоорганическая химия", mat);
        createSection("биотехнология", mat);
        createSection("биохимия", mat);
        createSection("гетерогенный катализ", mat);
        createSection("коллоидная химия", mat);
        createSection("кристаллохимия", mat);
        createSection("на английском языке", mat);
        createSection("неорганическая химия", mat);
        createSection("органическая химия", mat);
        createSection("радиационная химия", mat);
        createSection("фармакологическая химия", mat);
        createSection("физическая химия", mat);
        createSection("хиимия окружающей среды", mat);
        createSection("химическая кинетика", mat);
        createSection("химическая термодинамика", mat);
        createSection("химическая технология", mat);
        createSection("химия твердого тела", mat);
        createSection("электрохимия", mat);
        createSection("ядерная химия", mat);
        createExtra("ЕГЭ", mat);
        createExtra("ОГЭ (ГИА)", mat);
        createExtra("подготовка к олимпиадам", mat);

        mat = createCategory("Биология");
        createSection("вузовский курс", mat);
        createSection("школьный курс", mat);
        createSection("анатомия", mat);
        createSection("биохимия", mat);
        createSection("ботаника", mat);
        createSection("генетика", mat);
        createSection("гистология", mat);
        createSection("зоология", mat);
        createSection("микробиология", mat);
        createSection("молекулярная биология", mat);
        createSection("морфология животных", mat);
        createSection("патологическая физиология", mat);
        createSection("топографическая анатомия", mat);
        createSection("топографическая хирургия", mat);
        createSection("фармакология", mat);
        createSection("физиология", mat);
        createSection("физиология растений", mat);
        createSection("цитология", mat);
        createSection("эмбриология", mat);
        createExtra("ЕГЭ", mat);
        createExtra("ОГЭ (ГИА)", mat);
        createExtra("подготовка к олимпиадам", mat);

        mat = createCategory("История");
        createExtra("ЕГЭ", mat);
        createExtra("ОГЭ (ГИА)", mat);
        createExtra("подготовка к олимпиадам", mat);
        createSection("школьный курс", mat);
        createSection("вузовский курс", mat);

        mat = createCategory("Обществознание", "Репетиторы по обществознанию");
        createExtra("ЕГЭ", mat);
        createExtra("ОГЭ (ГИА)", mat);
        createExtra("подготовка к олимпиадам", mat);
        createSection("школьный курс", mat);
        createSection("вузовский курс", mat);

        mat = createCategory("Литература");
        createExtra("ЕГЭ", mat);
        createExtra("ОГЭ (ГИА)", mat);
        createExtra("подготовка к олимпиадам", mat);
        createSection("школьный курс", mat);
        createSection("вузовский курс", mat);

        mat = createCategory("География");
        createExtra("ЕГЭ", mat);
        createExtra("ОГЭ (ГИА)", mat);
        createExtra("подготовка к олимпиадам", mat);
        createSection("школьный курс", mat);
        createSection("вузовский курс", mat);

        mat = createCategory("Экономика");
        createSection("школьный курс", mat);
        createSection("1С", mat);
        createSection("аудит", mat);
        createSection("АФХД", mat);
        createSection("банковское дело", mat);
        createSection("бухучет", mat);
        createSection("деловое администрирование", mat);
        createSection("инвестиционный анализ", mat);
        createSection("институциональная экономика", mat);
        createSection("информационные технологии в экономике и управлении", mat);
        createSection("история экономики", mat);
        createSection("коммерческое право", mat);
        createSection("кредит", mat);
        createSection("макростатистика", mat);
        createSection("макроэкономика", mat);
        createSection("маркетинг", mat);
        createSection("международные экономические отношения", mat);
        createSection("менеджмент", mat);
        createSection("микроэкономика", mat);
        createSection("МСФО", mat);
        createSection("налогообложение", mat);
        createSection("оптимизация сетевых моделей", mat);
        createSection("опционы", mat);
        createSection("отраслевые рынки", mat);
        createSection("политэкономия", mat);
        createSection("построение сетевых моделей", mat);
        createSection("рынок ценных бумаг", mat);
        createSection("стратегический менеджмент", mat);
        createSection("управление персоналом", mat);
        createSection("управленческий учет", mat);
        createSection("финансовый анализ", mat);
        createSection("финансовый менеджмент", mat);
        createSection("финансовый учет", mat);
        createSection("финансы", mat);
        createSection("фондовые рынки", mat);
        createSection("эконометрика", mat);
        createSection("экономика предприятия", mat);
        createSection("экономическая статистика", mat);
        createSection("экономическая теория", mat);
        createExtra("подготовка к олимпиадам", mat);

        mat = createCategory("Подготовка к школе", "Репетиторы по подготовке к школе");
        createSection("общий курс", mat);
        createSection("аутичные дети", mat);
        createSection("гиперактивные дети", mat);
        createSection("дети с ЗПР", mat);
        createSection("лепка", mat);
        createSection("математика", mat);
        createSection("мелкая моторика", mat);
        createSection("обучение грамоте", mat);
        createSection("окружающий мир", mat);
        createSection("письмо", mat);
        createSection("психодиагностика", mat);
        createSection("развитие внимания", mat);
        createSection("развитие познавательных процессов", mat);
        createSection("развитие речи", mat);
        createSection("раннее развитие", mat);
        createSection("рисование", mat);
        createSection("счет", mat);
        createSection("чтение", mat);
        createExtra("английский для малышей", mat);
        createExtra("немецкий для малышей", mat);

        mat = createCategory("Начальная школа", "Репетиторы по предметам начальной школы");
        mat = createCategory("Логопед", "Логопеды");
        mat = createCategory("Изобразительное искусство", "Репетиторы по изобразительному искусству");
        mat = createCategory("Музыка");
        mat = createCategory("Другой", "Репетиторы по Другим предметам");

        subjectDao.save(save);
        subjectExtraDao.save(saveE);
        subjectSectionDao.save(saveS);
    }

    private Subject createCategory(String name) {
        Subject c = new Subject();
        c.setName(name);
        if (name.endsWith("а")) {
            String name2 = "Репетиторы по " + name.substring(0, name.length() - 1).toLowerCase() + "е";
            c.setName2(name2);
        } else if (name.endsWith("я")) {
            String name2 = "Репетиторы по " + name.substring(0, name.length() - 1).toLowerCase() + "и";
            c.setName2(name2);
        } else {
            c.setName2(name);
        }

        save.add(c);
        counterE = 0;
        counterS = 0;
        return c;
    }

    private Subject createCategory(String name, String name2) {
        Subject c = new Subject();
        c.setName(name);
        c.setName2(name2);
        save.add(c);
        counterE = 0;
        counterS = 0;
        return c;
    }

    private Subject createCategory(String name, boolean container) {
        Subject c = createCategory(name);
        c.setContainer(container);
        return c;
    }

    private Subject createCategory(String name, String name2, Subject parent) {
        Subject c = new Subject();
        c.setName(name);
        c.setName2(name2);
        c.setParent(parent);
        save.add(c);
        counterE = 0;
        counterS = 0;
        return c;
    }

    private void createExtra(String name, Subject parent) {
        SubjectExtra c = new SubjectExtra();
        c.setName(name);
        c.setSubject(parent);
        c.setIdx(counterE++);
        parent.getExtras().add(c);
        saveE.add(c);
    }

    private void createSection(String name, Subject parent) {
        SubjectSection c = new SubjectSection();
        c.setName(name);
        c.setSubject(parent);
        c.setIdx(counterS++);
        parent.getSections().add(c);
        saveS.add(c);
    }
}
