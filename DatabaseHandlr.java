package shashib.taskhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Soft on 07-Sep-16.
 */
public class DatabaseHandlr extends SQLiteOpenHelper {
    public static final int DB_VERSION = 5;// Version 5 , with additional column STATUS
    public static final String DB_NAME = "worksdb.db";
public DatabaseHandlr(Context context) {
    super(context,DB_NAME,null,DB_VERSION);
}
public void onCreate(SQLiteDatabase sqLiteDatabase) {
final String CREATE_TABLE_STRING="CREATE TABLE "+YashistantContractDB.TABLE_NAME+" (" +
        YashistantContractDB.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
        YashistantContractDB.COL_DATE + " INTEGER NOT NULL, " +
        YashistantContractDB.COL_MONTH + " INTEGER NOT NULL, " +
        YashistantContractDB.COL_YEAR + " INTEGER NOT NULL, " +
        YashistantContractDB.COL_HOUR + " INTEGER NOT NULL, " +
        YashistantContractDB.COL_MINUTES + " INTEGER NOT NULL, " +
        YashistantContractDB.COL_TASKSTATUS + " INTEGER NOT NULL DEFAULT 0 , " +  // ZEro means incomplete task, 1 means complete
        YashistantContractDB.COL_TASK +" TEXT NOT NULL" + ")" ;
    sqLiteDatabase.execSQL(CREATE_TABLE_STRING);
    Log.d("TAG","CREATED TABLE" + CREATE_TABLE_STRING);
}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + YashistantContractDB.TABLE_NAME );
        // Create tables again
        onCreate(db);
    }

 public void AddTask(taskClass tsk){

     SQLiteDatabase db = getWritableDatabase();
     ContentValues values = new ContentValues();
     values.put(YashistantContractDB.COL_DATE,tsk.dte);
     values.put(YashistantContractDB.COL_MONTH,tsk.mnth);
     values.put(YashistantContractDB.COL_YEAR,tsk.yr);
     values.put(YashistantContractDB.COL_HOUR,tsk.hr);
     values.put(YashistantContractDB.COL_MINUTES,tsk.min);
     values.put(YashistantContractDB.COL_TASK,tsk.Task);
     db.insert(YashistantContractDB.TABLE_NAME,null,values);
     Log.d("TAG", "AddTask: Added");
     db.close();
      }
 /*   public List<taskClass> getAllTasks() {
        List<taskClass> alltasks = new ArrayList<taskClass>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * FROM " + YashistantContractDB.TABLE_NAME,null);
        Log.d("TAG","Entered Getall");
        if(cursor.moveToFirst()){
            do {
                taskClass ts=new taskClass();
                ts.setdate(Integer.parseInt(cursor.getString(1)),Integer.parseInt(cursor.getString(2)), Integer.parseInt(cursor.getString(3)));
                ts.settime(Integer.parseInt(cursor.getString(4)),Integer.parseInt(cursor.getString(5)));
                ts.settask((cursor.getString(6)));
                alltasks.add(ts);
            }while(cursor.moveToNext());
        }
     return alltasks;
  }
} */

 }