import java.text.DecimalFormat;
import java.util.Date;

/**
 * Created by katarzyna on 29.04.15.
 */
public class MainClass {

    public static void main(String args[])  {
        if(args.length != 3) {
            System.err.println("Usage: <rate> <begin date> <end date>");
            return;
        }

        if(args[0].length() == 3 ){

            Date date1 = Parser.string2Date(args[1]);
            Date date2 = Parser.string2Date(args[2]);
            if (!date1.after(date2)){
                float avg = Operation.average(args[0], date1, date2, "kurs_kupna");
                double std = Operation.standardDeviation(args[0], date1, date2, "kurs_sprzedazy");
                DecimalFormat df = new DecimalFormat("#.####");
                System.out.println(df.format(avg));
                System.out.println(df.format(std));
            }else{
                System.err.println("First date is later than the second. ");
            }


        }else {

            System.err.println("Invalid rate argument, try USD, EUR, CHF or GBP");
        }
    }
}
