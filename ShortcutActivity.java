package shashib.taskhelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ShortcutActivity extends AppCompatActivity {
    public static int STATUS_SC = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shortcut);
  //      if (MainActivity.STATUS_MAIN == 0) {
    //        Toast.makeText(ShortcutActivity.this, "Main Application must be running for me to record a task ! ", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            STATUS_SC = 1;  // To tell main activity that starting you from this activity
    }
}




