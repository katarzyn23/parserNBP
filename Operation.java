import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by katarzyna on 02.05.15.
 */
public class Operation {



    public float average(String rate, Date beginDate, Date endDate, Boolean bought, Parser ps ) throws IOException, ParseException, ParserConfigurationException, SAXException {
        float sum = 0;
        float avg = 0;
        //Parser ps = new Parser();
        List<String> XMLs = ps.getXMLs(beginDate, endDate);
        ListIterator<String> XMLiterator = XMLs.listIterator();
        while (XMLiterator.hasNext()) {
            sum += ps.getRateFromXML("http://www.nbp.pl/kursy/xml/" + XMLiterator.next() + ".xml", rate, bought);
            //System.out.println(sum);
        }
        avg = sum / XMLs.size();
        return avg;
    }

    public double standardDeviation(String rate, Date beginDate, Date endDate, Parser ps) throws IOException, SAXException, ParserConfigurationException, ParseException {
        double stDev =0;
        double x = 0;
        float avg = average(rate, beginDate, endDate, false, ps);
      //  Parser ps = new Parser();
        List<String> XMLs = ps.getXMLs(beginDate, endDate);
        ListIterator<String> XMLiterator = XMLs.listIterator();
        while (XMLiterator.hasNext()) {
            x = ps.getRateFromXML("http://www.nbp.pl/kursy/xml/" + XMLiterator.next() + ".xml", rate, false);
            x = (x-avg);
            x = x*x;
            stDev +=x;
        }
        stDev = stDev / XMLs.size();
        stDev = Math.sqrt(stDev);
        return stDev;
    }
}
