import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Created by katarzyna on 29.04.15.
 */
public class MainClass {



    public static void main(String args[]) throws IOException, SAXException, ParserConfigurationException, ParseException {

      if(args.length != 3) {
          System.err.println("Usage: <rate> <begin date> <end date>");
          return;
      }
       // boolean d1 = Pattern.matches("(19|20)\\\\d\\\\d(?:,)[-](0[1-9]|1[012])[-](0[1-9]|[12][0-9]|3[01])", args[1]);
       // boolean d2 = Pattern.matches("(19|20)\\\\d\\\\d(?:,)[-](0[1-9]|1[012])[-](0[1-9]|[12][0-9]|3[01])", args[2]);
        boolean d1 = true;
        boolean d2 = true;
        if(args[0].length() == 3 && d1 && d2){
            Parser ps = new Parser();
            Operation op = new Operation();
            try{
                String rate = "EUR";
                String date1string = "2013-01-28";
                String date2string = "2013-01-31";
                Date date1 = ps.string2Date(args[1]);
                Date date2 = ps.string2Date(args[2]);
                float avg = op.average(args[0],date1, date2, true, ps);
                double std = op.standardDeviation(args[0], date1, date2, ps);
                DecimalFormat df = new DecimalFormat("#.####");
                System.out.println(df.format(avg));
                System.out.println(df.format(std));


            }catch(NumberFormatException ex){

            }
        }else
        {
            System.err.println("Invalid arguments");
        }



    }
}
