package shashib.taskhelper;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Soft on 21-Sep-16.
 */
public class MyBroadcasrReciever extends BroadcastReceiver {
@Override
    public void onReceive(Context context, Intent intent)
{
     int a = validatetime(context); // This funciton ensures running of alarm in day time only
    int b = validatezerorecords(context);
    if(a==1 && b!=0)
    {
        checkformsg(context);

    }

}

    public void checkformsg (Context cont)
    {
        Context c =cont.getApplicationContext();

        DatabaseHandlr db = new DatabaseHandlr(c);
        SQLiteDatabase hnd = db.getReadableDatabase();
        SimpleDateFormat sf = new SimpleDateFormat(("ddMMyy HHmm"));
        Calendar calendar = Calendar.getInstance();
        String currdt = sf.format(calendar.getTime());
        int datetodo = Integer.parseInt(currdt.substring(0, 2)); /* Extract date,month,year from today's date */
        int monthtodo = Integer.parseInt(currdt.substring(2, 4));
        int yeartodo = Integer.parseInt(currdt.substring(4, 6));
        int hourtodo = Integer.parseInt(currdt.substring(7, 9));
        int mintodo = Integer.parseInt(currdt.substring(9, 11));
        String searchstring = "SELECT  * FROM " + YashistantContractDB.TABLE_NAME + " WHERE "+ YashistantContractDB.COL_DATE+ " = " + datetodo + " AND " + YashistantContractDB.COL_MONTH + " = " + monthtodo + " AND " + YashistantContractDB.COL_YEAR + " = " + yeartodo + " AND " + YashistantContractDB.COL_TASKSTATUS + " = 0 ";
         Log.d("TAGO", searchstring);
        Cursor cursor2 = hnd.rawQuery(searchstring,null);
        if (cursor2!=null) {
            cursor2.moveToFirst();
            String msgtoshow = "";
            do {
                msgtoshow = msgtoshow + cursor2.getString(7) + "\n";
            } while (cursor2.moveToNext());
            Log.d("TAGO", msgtoshow);

            hnd.close();
            Uri sound = Uri.parse("android.resource://" + cont.getPackageName() + "/raw/voice.mp3");
            NotificationCompat.Builder nbuilder = new NotificationCompat.Builder(cont);
            nbuilder.setStyle(new android.support.v4.app.NotificationCompat.BigTextStyle().bigText(msgtoshow));
            nbuilder.setContentText("Tasks for today !");
            nbuilder.setSmallIcon(R.drawable.addtasksmall);
            nbuilder.setSound(sound);
            NotificationManager nm = (NotificationManager) cont.getSystemService(Context.NOTIFICATION_SERVICE);
            nm.notify(12345, nbuilder.build());
        }
    }

    public int validatetime (Context context) {
        SimpleDateFormat sf = new SimpleDateFormat(("ddMMyy HHmm"));
        Calendar calendar = Calendar.getInstance();
        String currdt = sf.format(calendar.getTime());
        //int datetodo = Integer.parseInt(currdt.substring(0, 2)); /* Extract date,month,year from today's date */
       // int monthtodo = Integer.parseInt(currdt.substring(2, 4));
       // int yeartodo = Integer.parseInt(currdt.substring(4, 6));
        int hourtodo = Integer.parseInt(currdt.substring(7, 9));
       // int mintodo = Integer.parseInt(currdt.substring(9, 11));
        if(hourtodo>20 || hourtodo <8) {
            return 0;
        }
        else return 1;

    }

    public int validatezerorecords(Context cont) {  // Returns 1 if no records for today
        Context c =cont.getApplicationContext();
        DatabaseHandlr db = new DatabaseHandlr(c);
        SQLiteDatabase hnd = db.getReadableDatabase();
        SimpleDateFormat sf = new SimpleDateFormat(("ddMMyy HHmm"));
        Calendar calendar = Calendar.getInstance();
        String currdt = sf.format(calendar.getTime());
        int datetodo = Integer.parseInt(currdt.substring(0, 2)); /* Extract date,month,year from today's date */
        int monthtodo = Integer.parseInt(currdt.substring(2, 4));
        int yeartodo = Integer.parseInt(currdt.substring(4, 6));
        int hourtodo = Integer.parseInt(currdt.substring(7, 9));
        int mintodo = Integer.parseInt(currdt.substring(9, 11));
        String searchstring = "SELECT  * FROM " + YashistantContractDB.TABLE_NAME + " WHERE "+ YashistantContractDB.COL_DATE+ " = " + datetodo + " AND " + YashistantContractDB.COL_MONTH + " = " + monthtodo + " AND " + YashistantContractDB.COL_YEAR + " = " + yeartodo + " AND " + YashistantContractDB.COL_TASKSTATUS + " = 0 ";
        Log.d("TAGO", searchstring);
        Cursor cursor2 = hnd.rawQuery(searchstring,null);
        int i=0;
        if(cursor2!=null) {
            i = cursor2.getCount();
        }
        cursor2.close();
        db.close();
        Log.d("COUNT OF CURSOR ", Integer.toString(i));
        return i;
    }
}