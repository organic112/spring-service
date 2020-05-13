package com.potato112.springservice.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DBQueryMapUtil {


    private static Map<String, String> queryMap = new HashMap<>();

    private static final String QUERY_TAG = "query";
    private static final String ID_ATTRIBUTE = "id";

    public static void readQueries() throws SQLException {

        if (!queryMap.isEmpty()) {
            return;
        }

        try {
            StringBuilder path = new StringBuilder();
            path.append("/").append("sql").append("/").append("CustomManagerQueries").append(".xml");

            // C:\workspace\github\demo-crud\src\main\resources\sql\CustomManagerQueries.xml

            InputStream queryXML = DBQueryMapUtil.class.getResourceAsStream(path.toString());

            if (null == queryXML) {

                throw new IllegalArgumentException("Query not found");
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            Document document = documentBuilder.parse(queryXML);

            NodeList nodeList = document.getElementsByTagName(QUERY_TAG);

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element eElement = (Element) nodeList.item(i);

                String sqlID = eElement.getAttribute(ID_ATTRIBUTE);
                String query = eElement.getTextContent();
                queryMap.put(sqlID, query);
            }

        } catch (Exception e) {
            throw new SQLException(e);
        }
    }

    public static String getSqlQueryVo(String sqlID) {
        return queryMap.get(sqlID);
    }

}
