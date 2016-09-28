package shashib.taskhelper;

import android.provider.BaseColumns;

/**
 * Created by Soft on 07-Sep-16.
 */
public final class YashistantContractDB implements BaseColumns {
public static final String TABLE_NAME = "works"; // Changng name to 2
    public static final String COL_DATE = "date";//Date of completion of task
    public static final String COL_MONTH = "month";
    public static final String COL_YEAR = "year";
     public static final String COL_HOUR = "hour"; // TIme of Completion of task
    public static final String COL_MINUTES="minutes";
    public static final String COL_TASK = "task";
    public static final String COL_ID = "_id";
    public static final String COL_TASKSTATUS = "status"; // 0 = incomplete 1 = complete


}
