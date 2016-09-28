package shashib.taskhelper;

/**
 * Created by Soft on 07-Sep-16.
 */
public class taskClass {
    String Task;   // Holds Task and time, date of deadline
    int dte;
    int mnth;
    int yr;
    int hr;
    int min;
    public taskClass(){}
    public  taskClass(String task, int d,int mn ,int y, int h, int m  ) {
        Task=task;
        dte=d;
        mnth=mn;
        yr=y;
        hr=h;
        min=m;

    }
    public void settask(String task){
        Task=task;
    }
    public void settime(int h,int m){ hr= h; min=m;
    }
    public void setdate(int d, int m, int y ){
       dte=d; mnth = m; yr=y;
    }
    public String getTask() {
        return Task;
    }
    public int getDate() {
        return dte;
    } // Currently  returns only date and hour ...
    public int getTime() {
        return hr;
    }

}


