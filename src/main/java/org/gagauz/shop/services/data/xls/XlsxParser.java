package org.gagauz.shop.services.data.xls;

import static javax.xml.stream.XMLStreamConstants.END_ELEMENT;
import static javax.xml.stream.XMLStreamConstants.START_ELEMENT;

import java.io.File;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class XlsxParser {

    private static final DateFormat DTF = new SimpleDateFormat("dd.MM.yyyy");

    private XlsxParser() {
    }

    private static class Styles {
        final String[] styles;
        final Map<String, String> numFmts;

        Styles(String[] styles, Map<String, String> numFmts) {
            this.styles = styles;
            this.numFmts = numFmts;
        }

        boolean isDateStyle(String style) {
            String s = styles[Integer.valueOf(style)];
            if (s.equals("14"))
                return true;
            String numFmt = numFmts.get(s);
            if (numFmt != null) {
                return numFmt.contains("d") && numFmt.contains("m") && numFmt.contains("y");
            }
            return false;
        }
    }

    public static Map<String, String[][]> parse(File file) throws Exception {
        ZipFile zipFile = new ZipFile(file);
        XMLInputFactory factory = XMLInputFactory.newInstance();
        Map<String, String> sheets = parseWorkbook(factory, zipFile.getInputStream(zipFile.getEntry("xl/workbook.xml")));
        ZipEntry sharedStringsEntry = zipFile.getEntry("xl/sharedStrings.xml");
        String[] sharedStrings = null;
        if (sharedStringsEntry != null) {
            sharedStrings = parseSharedStrings(factory, zipFile.getInputStream(sharedStringsEntry));
        }
        Styles styles = parseStyles(factory, zipFile.getInputStream(zipFile.getEntry("xl/styles.xml")));
        Map<String, String[][]> result = new LinkedHashMap<>();
        for (Map.Entry<String, String> e : sheets.entrySet()) {
            InputStream entr = zipFile.getInputStream(zipFile.getEntry("xl/worksheets/sheet" + e.getValue() + ".xml"));
            String[][] data = parseSheet(factory, entr, sharedStrings, styles);
            result.put(e.getKey(), data);
        }
        zipFile.close();
        return result;
    }

    private static int[][] range2num(String r) {
        String[] rs = r.split("[:]");
        int[][] ret = new int[2][];
        ret[0] = ref2num(rs[0]);
        if (rs.length > 1) {
            ret[1] = ref2num(rs[1]);
        } else
            ret[1] = ret[0];
        return ret;
    }

    private static int[] ref2num(String r) {
        char c0 = r.charAt(0);
        char c1 = r.charAt(1);
        if (Character.isDigit(c1))
            return new int[] {c0 - 'A', Integer.parseInt(r.substring(1)) - 1};
        else
            return new int[] {(c0 - 'A' + 1) * 26 + (c1 - 'A'), Integer.parseInt(r.substring(2)) - 1};
    }

    private static Map<String, String> parseWorkbook(XMLInputFactory factory, InputStream inputStream) throws XMLStreamException {
        final Map<String, String> sheets = new LinkedHashMap<String, String>();
        final XMLStreamReader reader = factory.createXMLStreamReader(inputStream);

        while (reader.hasNext()) {
            reader.next();
            if (reader.getEventType() == START_ELEMENT) {
                if (reader.getLocalName().equals("sheet")) {
                    sheets.put(
                            reader.getAttributeValue(null, "name"),
                            reader.getAttributeValue("http://schemas.openxmlformats.org/officeDocument/2006/relationships",
                                    "id").substring(3));
                }
            }
        }

        return sheets;
    }

    private static Styles parseStyles(XMLInputFactory factory, InputStream inputStream) throws Exception {
        XMLStreamReader r = factory.createXMLStreamReader(inputStream);
        String[] styles = null;
        Map<String, String> numFmts = new HashMap<String, String>();
        while (r.hasNext()) {
            r.next();
            if (r.getEventType() == START_ELEMENT) {
                if (r.getLocalName().equals("numFmts")) {
                    while (r.hasNext()) {
                        r.next();
                        if (r.getEventType() == START_ELEMENT && r.getLocalName().equals("numFmt"))
                            numFmts.put(r.getAttributeValue(null, "numFmtId"), r.getAttributeValue(null, "formatCode"));
                        else if (r.getEventType() == END_ELEMENT
                                && r.getLocalName().equals("numFmts")) {
                            break;
                        }
                    }
                } else if (r.getLocalName().equals("cellXfs")) {
                    styles = new String[Integer.parseInt(r.getAttributeValue(null, "count"))];
                    int i = 0;
                    while (r.hasNext()) {
                        r.next();
                        if (r.getEventType() == START_ELEMENT && r.getLocalName().equals("xf"))
                            styles[i++] = r.getAttributeValue(null, "numFmtId");
                        else if (r.getEventType() == XMLStreamConstants.END_ELEMENT
                                && r.getLocalName().equals("cellXfs")) {
                            break;
                        }
                    }
                }
            }
        }
        r.close();
        return new Styles(styles, numFmts);
    }

    private static String[][] parseSheet(XMLInputFactory factory, InputStream is, String[] sharedStrings, Styles styles) throws Exception {
        XMLStreamReader reader = factory.createXMLStreamReader(is);
        String[][] data = null;
        while (reader.hasNext()) {
            reader.nextTag();
            if (reader.getLocalName().equals("dimension")) {
                int[][] range = range2num(reader.getAttributeValue(null, "ref"));
                data = new String[range[1][1] + 1][range[1][0] + 1];
                break;
            }
        }
        Calendar c = Calendar.getInstance();
        while (reader.hasNext()) {
            reader.next();
            if (reader.getEventType() == START_ELEMENT) {
                if (reader.getLocalName().equals("c")) {
                    int[] ref = ref2num(reader.getAttributeValue(null, "r"));
                    String type = reader.getAttributeValue(null, "t");
                    String style = reader.getAttributeValue(null, "s");
                    while (true) {
                        reader.next();
                        if (reader.getEventType() == END_ELEMENT && reader.getLocalName().equals("c")) {
                            break;
                        } else if (reader.getEventType() == START_ELEMENT) {
                            if (reader.getLocalName().equals("v")) {
                                String text = reader.getElementText();
                                if (type != null) {
                                    if (type.equals("s")) {
                                        data[ref[1]][ref[0]] = sharedStrings[Integer.parseInt(text)];
                                    } else if (type.equals("str")) {
                                        data[ref[1]][ref[0]] = text;
                                    }
                                } else if (style != null) {
                                    if (styles.isDateStyle(style)) {
                                        // TODO 1900-02-29
                                        c.set(1900, 0, Integer.parseInt(text) - 1, 0, 0, 0);
                                        data[ref[1]][ref[0]] = DTF.format(c.getTime());
                                    } else {
                                        data[ref[1]][ref[0]] = text;
                                    }
                                } else {
                                    data[ref[1]][ref[0]] = text;
                                }
                            }
                        }
                    }
                }
            }
        }
        reader.close();
        return data;
    }

    private static String[] parseSharedStrings(XMLInputFactory factory, InputStream inputStream) throws Exception {
        XMLStreamReader reader = factory.createXMLStreamReader(inputStream);
        int index = 0;
        reader.nextTag();
        if (!reader.getLocalName().equals("sst")) {
            throw new AssertionError();
        }
        final String[] sharedStrings = new String[Integer.parseInt(reader.getAttributeValue(null, "count"))];
        while (reader.hasNext()) {
            reader.next();
            if (reader.getEventType() == START_ELEMENT) {
                if (reader.getLocalName().equals("si")) {
                    StringBuilder sb = new StringBuilder();
                    while (true) {
                        reader.next();
                        if (reader.getEventType() == END_ELEMENT && reader.getLocalName().equals("si"))
                            break;
                        else if (reader.getEventType() == XMLStreamConstants.CHARACTERS) {
                            sb.append(reader.getText());
                        }
                    }
                    sharedStrings[index++] = sb.toString();
                }
            }
        }
        reader.close();

        return sharedStrings;
    }

}
