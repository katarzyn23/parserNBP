import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by katarzyna on 29.04.15.
 */
public class Parser {

    //getting rate from xml
    public static float getRateFromXML(String address, String foreignCurrency, String lastTagName) {
        float result = 0;
        try {
            String rate = null;
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(address);
            doc.getDocumentElement().normalize();
            NodeList nodeLst = doc.getElementsByTagName("pozycja");
            for (int s = 0; s < nodeLst.getLength(); s++) {
                Node node = nodeLst.item(s);
                Element eElement = (Element) node;
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    if (eElement.getElementsByTagName("kod_waluty").item(0).getTextContent().contains(foreignCurrency)) {
                        rate = eElement.getElementsByTagName(lastTagName).item(0).getTextContent();
                        rate = rate.replace(",", ".");
                        result = Float.parseFloat(rate);
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    //parsing string into format xxxx-xx-xx
    private static String parseString(String s) {
        String parsed = null;
        if (s.length() == 11) {
            parsed = s.substring(5);
            parsed = parsed.substring(0, 2) + "-" + parsed.substring(2, 4) + "-" + parsed.substring(4, 6);
        }
        return parsed;
    }

    //getting list of xml files
    public static List<String> getXMLs(Date beginDate, Date endDate) {
        List<String> currentXMLs = new LinkedList<String>();
        URL url = null;
        try {
            url = new URL("http://www.nbp.pl/kursy/xml/dir.txt");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()))){
            String inputLine;
            String tmp;
            while ((inputLine = in.readLine()) != null) {
                if (inputLine.startsWith("c")) {
                    tmp = parseString(inputLine);
                    if (string2Date(tmp).after(beginDate) && string2Date(tmp).before(endDate) || string2Date(tmp).compareTo(beginDate) == 00 || string2Date(tmp).compareTo(endDate) == 0) {
                        currentXMLs.add(inputLine);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return currentXMLs;
    }


    public static Date string2Date(String s)  {
        SimpleDateFormat sdf = new SimpleDateFormat("yy-M-dd");
        Date date = null;
        try {
            date = sdf.parse(s);
        } catch (ParseException e) {
            System.err.println("Invalid date, try YYYY-MM-DD");
            e.printStackTrace();

        }
        return date;
    }


}
