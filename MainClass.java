import java.text.DecimalFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Created by katarzyna on 29.04.15.
 */
public class MainClass {

    public static void main(String args[])  {

      if(args.length != 3) {
          System.err.println("Usage: <rate> <begin date> <end date>");
          return;
      }
        boolean d1 = Pattern.matches("(\\d{4})-(\\d{2})-(\\d{2})", args[1]);
        boolean d2 = Pattern.matches("(\\d{4})-(\\d{2})-(\\d{2})", args[2]);
        if(args[0].length() == 3 && d1 && d2){

            try{

                Date date1 = Parser.string2Date(args[1]);
                Date date2 = Parser.string2Date(args[2]);
                float avg = Operation.average(args[0],date1, date2, true);
                double std = Operation.standardDeviation(args[0], date1, date2);
                DecimalFormat df = new DecimalFormat("#.####");
                System.out.println(df.format(avg));
                System.out.println(df.format(std));


            }catch(NumberFormatException e){
                e.printStackTrace();
            }

        }else {
            System.err.println("Invalid arguments");
        }



    }
}
