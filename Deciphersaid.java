package shashib.taskhelper;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Soft on 09-Sep-16.
 */
public class Deciphersaid {
    public taskClass Deciphersaid(String spokentxt) {
        SimpleDateFormat sf = new SimpleDateFormat(("ddMMyy HHmm"));
        Calendar calendar = Calendar.getInstance();
        taskClass tt = new taskClass();   /* the object to write and then return */
        String currdt = sf.format(calendar.getTime());
        int datetodo = Integer.parseInt(currdt.substring(0, 2)); /* Extract date,month,year from today's date */
        int monthtodo = Integer.parseInt(currdt.substring(2, 4));
        int yeartodo = Integer.parseInt(currdt.substring(4, 6));
        int hourtodo = Integer.parseInt(currdt.substring(7, 9));
        int mintodo = Integer.parseInt(currdt.substring(9, 11));

        String taskstring=spokentxt;
        if(spokentxt.contains("today")) {
            taskstring=spokentxt.replaceAll("today","");
        }

        else if (spokentxt.contains("tomorrow") || spokentxt.contains("in one day")|| spokentxt.contains("in 24 hours"))/* These are possible voice time commands , add here for more */
        {

            datetodo++;
            taskstring = spokentxt.replaceAll("tomorrow", "");

        }
        else if (spokentxt.contains("by next week") || spokentxt.contains("in a week")) {
            datetodo += 7;
            taskstring = spokentxt.replaceAll("by next week", "");
        }
        else if (spokentxt.contains("in 2 days"))
        {
            taskstring = spokentxt.replaceAll("in 2 days", "");
            datetodo += 2;

        }
        else if (spokentxt.contains("in 3 days")) {
            datetodo += 3;
            taskstring = spokentxt.replaceAll("in 3 days", "");

        }
        else if (spokentxt.contains("by next month")|| (spokentxt.contains("in a month"))) {
            if ( monthtodo!= 12)
                monthtodo++;
            else
                monthtodo = 1;
            taskstring = spokentxt.replaceAll("by next month", "");
        }
        else if (spokentxt.contains(("someday")) || (spokentxt.contains("soon"))) {
            if ( monthtodo!= 12)
                monthtodo++;
            else
                monthtodo = 1;
            taskstring = spokentxt.replaceAll("someday","");
            taskstring = spokentxt.replaceAll("soon","");
        }
        if (datetodo > 30)
            datetodo = datetodo - 30;
        String deb = " Date is : " + datetodo + " Hour is " + hourtodo + "min is "+ mintodo + " task is " +taskstring;
        Log.d("TAG", deb);

        tt.setdate(datetodo,monthtodo,yeartodo);
        tt.settime(hourtodo,mintodo);
        tt.settask(taskstring);
        return tt;
    }

    Deciphersaid() {

    }
}
