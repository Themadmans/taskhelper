package shashib.taskhelper;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Soft on 17-Sep-16.
 */
public class utility {

    public static class util {  // This class contains some generic functions...


        public void populatelistview(int code, View view) { // This function populates listview using cursor adapter
            Context c =
                    view.getContext();
            DatabaseHandlr db = new DatabaseHandlr(c); // CODE :0 for today , -1 for yesterday, 1 for tommorow
            SQLiteDatabase hnd = db.getReadableDatabase();

            String[] COLUMNS_TO_BOUND = new String[]{
                    YashistantContractDB.COL_TASK,
                    YashistantContractDB.COL_DATE,
                    YashistantContractDB.COL_MONTH,
                    YashistantContractDB.COL_YEAR,
                    YashistantContractDB.COL_HOUR,
                    YashistantContractDB.COL_MINUTES,
                    YashistantContractDB.COL_ID,
                    YashistantContractDB.COL_TASKSTATUS
            };
            int[] LAYOUT_ITEMS_TO_FILL = new int[]{
                    R.id.text11,
                    R.id.text22,
                    R.id.text3,
                    R.id.text4,
                    R.id.text5,
                    R.id.text6,
                    R.id.idtext,
                    R.id.statustext
            };

            SimpleDateFormat sf = new SimpleDateFormat(("ddMMyy HHmm"));
            Calendar calendar = Calendar.getInstance();
            String currdt = sf.format(calendar.getTime());
            int datetodo = Integer.parseInt(currdt.substring(0, 2)); /* Extract date,month,year from today's date */
            int monthtodo = Integer.parseInt(currdt.substring(2, 4));
            int yeartodo = Integer.parseInt(currdt.substring(4, 6));
            int hourtodo = Integer.parseInt(currdt.substring(7, 9));
            int mintodo = Integer.parseInt(currdt.substring(9, 11));
            String querystring = "";
            String querystring2 = "";

            if (code == 1) {
                querystring = "SELECT * FROM " + YashistantContractDB.TABLE_NAME + " WHERE DATE = " + datetodo + " AND MONTH = " + monthtodo + " AND  YEAR = " + yeartodo + " AND STATUS = " + 0;
                querystring2 = "SELECT * FROM " + YashistantContractDB.TABLE_NAME + " WHERE DATE = " + datetodo + " AND MONTH = " + monthtodo + " AND  YEAR = " + yeartodo + " AND STATUS = " + 1;
                Cursor cursor2 = hnd.rawQuery(querystring2, null);
                if (cursor2 != null) {
                    cursor2.moveToFirst();
                    Log.d("TAG", "CURSOR2 IS NOT NULL");
                } else {
                    Log.d("TAG", "CURSOR2 is NULL");
                }
                SimpleCursorAdapter simpleCursorAdapter2 = new SimpleCursorAdapter(c,
                        R.layout.mylayout,
                        cursor2,
                        COLUMNS_TO_BOUND,
                        LAYOUT_ITEMS_TO_FILL);
                ListView lv2 = (ListView) view.findViewById(R.id.listView2);
                lv2.setAdapter(simpleCursorAdapter2);
            } else if (code == 0) {
                querystring = "SELECT * FROM " + YashistantContractDB.TABLE_NAME + " WHERE  ( DATE < " + datetodo + " AND  MONTH = " + monthtodo + " ) OR  ( DATE >= " + datetodo + " AND MONTH < " + monthtodo + " ) AND  YEAR <= " + yeartodo;
            } else if (code == 2) {
                querystring = "SELECT * FROM " + YashistantContractDB.TABLE_NAME + " WHERE  ( DATE >  " + datetodo + " AND  MONTH = " + monthtodo + " ) OR  ( DATE <= " + datetodo + " AND MONTH > " + monthtodo + " ) AND  YEAR >= " + yeartodo;
            }
            Log.d("TAG", querystring);
            Cursor cursor = hnd.rawQuery(querystring, null);
            if (cursor != null && cursor.getCount()!=0) {
                cursor.moveToFirst();
                Log.d("TAG", "CURSOR IS NOT NULL");
                SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(c,
                        R.layout.mylayout,
                        cursor,
                        COLUMNS_TO_BOUND,
                        LAYOUT_ITEMS_TO_FILL
                );
                ListView lv = (ListView) view.findViewById(R.id.listView);
                lv.setAdapter(simpleCursorAdapter);
            }
            else
        {
            Log.d("TAG", "CURSOR is NULL");
        }

            if(cursor.getCount()==0)
            {
                ListView listView = (ListView) view.findViewById(R.id.listView);
                listView.setBackgroundResource(R.drawable.noworktoday);
                Log.d(" HERE COMES ", Integer.toString(code));
            }
            else {
                ListView listView = (ListView) view.findViewById(R.id.listView);
                listView.setBackgroundResource(0);
                Log.d(" HERE COMESYY ", Integer.toString(code));

            }
        //       cursor.close();  // MUST CLOSE CURSOR - CREATING PROBLEMS IN DISPLAY - hence not closing
        db.close(); // must close database
    }

        public void deleteitem(int item, View view) {
            Context c =
                    view.getContext();
            DatabaseHandlr db = new DatabaseHandlr(c);
            SQLiteDatabase hnd = db.getWritableDatabase();
            String searchstring = YashistantContractDB.COL_ID + "=" + item ;
            Log.d("TAGO", searchstring);
            hnd.delete(YashistantContractDB.TABLE_NAME,searchstring, null);
            hnd.close();
            db.close();
            MainActivity.updatelist();
        }

        public void postponeitem(int item,View view ) {
            Context c =
                    view.getContext();
            DatabaseHandlr db = new DatabaseHandlr(c);
            SQLiteDatabase hnd = db.getWritableDatabase();
            ContentValues cv = new ContentValues();
            SimpleDateFormat sf = new SimpleDateFormat(("ddMMyy HHmm"));
            Calendar calendar = Calendar.getInstance();
            String currdt = sf.format(calendar.getTime());
            int datetodo = Integer.parseInt(currdt.substring(0, 2)); /* Extract date,month,year from today's date */
            cv.put(YashistantContractDB.COL_DATE,datetodo+1);
            String searchstring = YashistantContractDB.COL_ID + "=" + item ;
            hnd.update(YashistantContractDB.TABLE_NAME,cv,searchstring,null);
            Toast.makeText(view.getContext()," Task Postponed by a day", Toast.LENGTH_SHORT).show();
   hnd.close();
            db.close();
            MainActivity.updatelist();
        }

        public void OnclickforListviews(final View view, final int FRAGNO) { // 0 for today (FRAGNO) , -1 for yesterday
            final int item =Integer.parseInt( ((TextView) view.findViewById(R.id.idtext)).getText().toString());
            final View view1 = view;
            final int status = Integer.parseInt(((TextView) view.findViewById(R.id.statustext)).getText().toString());
            final String buttontext, buttontext2;
            if(status==0) {
                buttontext = "Mark Completed";
            }
            else
            {
                buttontext = "Mark Incomplete";
            }
            if(FRAGNO == 0 || FRAGNO == -1)
            {
                buttontext2 = "Postpone";
            }
            else {
                buttontext2 = "Cancel";
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setPositiveButton(buttontext, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    changestatus(view1, item ,status);
                }
            })
                    .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                             deleteitem(item, view1);
                            Toast.makeText(view.getContext(), " Deleted !  ", Toast.LENGTH_SHORT).show();

                        }
                    })
                    .setNeutralButton(buttontext2, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(FRAGNO==0 || FRAGNO == -1)
                            {
                                postponeitem(item,view1);
                            }

                        }
                    })

                    .show();

        }

        public void changestatus (View view, int item, int status) {
            Context c =
                    view.getContext();
            DatabaseHandlr db = new DatabaseHandlr(c);
            SQLiteDatabase hnd = db.getWritableDatabase();
            String searchstring = YashistantContractDB.COL_ID + "=" + item ;
            String setstring = YashistantContractDB.COL_TASKSTATUS + "=";
            Log.d("TAGO", searchstring);
            ContentValues cv = new ContentValues();
            if (status == 0 )
            {
                cv.put(YashistantContractDB.COL_TASKSTATUS,1);

            }
            else {
                cv.put(YashistantContractDB.COL_TASKSTATUS,0);
            }

            hnd.update(YashistantContractDB.TABLE_NAME, cv, searchstring,null );
            MainActivity.updatelist();
           hnd.close();
            db.close();
        }

    }

}
