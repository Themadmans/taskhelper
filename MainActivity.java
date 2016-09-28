package shashib.taskhelper;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends FragmentActivity {
    public static FragmentPagerAdapter fpa;
public static int STATUS_MAIN=0; // 0  for stop , 1 for active

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        STATUS_MAIN=1;
        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int runvalue = sharedPreferences.getInt("RUN",0);
        if (runvalue==0 ) {
            editor.putInt("RUN", 1); //For first Run
            AddShortcut(); // Create Shortcut only first time
            ShowTutorial();
            editor.commit();
       //     Log.d(" TAG ", " RUNVALUE CHANGED from 0 ");
        }
//ShowTutorial();
        setContentView(R.layout.activity_main);
        SimplePagerAdapter adapter = new SimplePagerAdapter(getSupportFragmentManager());
        ViewPager pager = (ViewPager) findViewById(R.id.fragmentPager);
        fpa = new MyPagerAdapter(getSupportFragmentManager());
       // AddShortcut();
        pager.setAdapter(fpa);
        pager.setCurrentItem(1);
        PackageManager pm = getPackageManager();
        ImageButton speakButton = (ImageButton) findViewById(R.id.imageButton);
        List<ResolveInfo> activities = pm.queryIntentActivities(
                new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() == 0) {
            Toast.makeText(this," Voice Recognizer Issue", Toast.LENGTH_SHORT).show();
        }
        speakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displaySpeechRecognizer1();
            }
        });
        if (ShortcutActivity.STATUS_SC == 1 && runvalue!=0 )
        {
            displaySpeechRecognizer1();
        }
        startalert(1); // Check if we need do this at Oncreate everytime ?
    }

    @Override
    public void onStart()
    {
        super.onStart();
        STATUS_MAIN=1;

    }
    public void onDestroy()
    {
        super.onDestroy();
        STATUS_MAIN=0;
    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 3;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return new YesterFragment();
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return new TodayFragment();
                case 2: // Fragment # 1 - This will show SecondFragment
                    return new TomrFragment();
                default:
                    return null;
            }

        }

    }

    private static final int SPEECH_REQUEST_CODE = 12334;

    // Create an intent that can start the Speech Recognizer activity
    private void displaySpeechRecognizer1() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getClass()
                .getPackage().getName());

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
// Start the activity, the intent will be populated with the speech text
        startActivityForResult(intent, SPEECH_REQUEST_CODE);
    }

    // This callback is invoked when the Speech Recognizer returns.
// This is where you process the intent and extract the speech text from the intent.
    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            final String spokenText = results.get(0);

            final taskClass tc; // new taskClass(spokenText,0,0);
            Deciphersaid dp = new Deciphersaid();
            tc = dp.Deciphersaid(spokenText); // SENDING SPOKEN TEXT FOR INTERPRETATION to this class method

            String string = "Save ? \n TASK : " + tc.getTask() + "\n" + " Date : " + tc.dte + "/" + tc.mnth + "/" + tc.yr + "\n" + " Time : " + tc.hr + ":" + tc.min;
       /*     final Dialog dialog = new Dialog(this);
         //   LayoutInflater inflater = getLayoutInflater();
         //   View customviw = inflater.inflate(R.layout.dialoglayout, null);
            dialog.setContentView(R.layout.dialoglayout);
            dialog.setTitle(" Task Addition : ");
            final EditText tasktxt = (EditText) dialog.findViewById(R.id.taskText);
            EditText datetxt = (EditText) dialog.findViewById(R.id.dateTExt);
            EditText timetxt = (EditText) dialog.findViewById(R.id.timeText);
            Button savebutton = (Button) dialog.findViewById(R.id.button);
            Button cancelbutton = (Button) dialog.findViewById(R.id.button2);
            cancelbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            tasktxt.setText(tc.getTask());
         timetxt.setText(Integer.toString(tc.getTime()));
          datetxt.setText(Integer.toString(tc.getDate()));

            savebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "SKIDNS ", Toast.LENGTH_SHORT).show();
                }
            });
            dialog.show(); */

            new AlertDialog.Builder(this).setTitle("Confirm ?").setMessage(string).setPositiveButton("Save ", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    DatabaseHandlr db = new DatabaseHandlr(getApplicationContext());
                    db.AddTask(tc);
                    updatelist();
                     // Function to call notify dataset change

//                        Fragment fm21 =  getSupportFragmentManager().findFragmentById(R.id.fragmnettoday);
                    //                      getSupportFragmentManager().beginTransaction().replace(R.id.fragmentPager,fm21).commit();
                    // /getSupportFragmentManager().beginTransaction().detach(fm21).commit();
                    //getSupportFragmentManager().beginTransaction().attach(fm21).commit();
                    Toast.makeText(getApplicationContext(), "Saved ! ", Toast.LENGTH_SHORT).show();
                }
            })
                    .setNegativeButton("Don't Save", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(), "Canelling", Toast.LENGTH_SHORT).show();
                        }
                    }).show();
        } else {
            Toast.makeText(getApplicationContext(), "Recording Failed ! ", Toast.LENGTH_SHORT).show();
        }

        }


    public static class SimplePagerAdapter extends FragmentPagerAdapter {
        public SimplePagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position)

            {
                case 0:
                    return new YesterFragment();
                case 1:
                    return new TodayFragment();
                case 2:
                    return new TomrFragment();
                default:
                    return null;

            }
        }
    }

public static void updatelist()
{
    fpa.notifyDataSetChanged();
}



    public void startalert(int code) {  // This method starts Alarm for frequent check of tasks

        Intent intent = new Intent(this, MyBroadcasrReciever.class);
        PendingIntent pendant = PendingIntent.getBroadcast(this.getApplicationContext(),123,intent,0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        if(code==1) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),AlarmManager.INTERVAL_HOUR,pendant);
//            Toast.makeText(MainActivity.this, "STARTED ALARM ! ", Toast.LENGTH_SHORT).show();
        }
        else {
            alarmManager.cancel(pendant);
  //          Toast.makeText(MainActivity.this, "ALARM STOPPED", Toast.LENGTH_SHORT).show();
    //        Log.d("ALARM SOUND", "SET NOW");
        }
    }


public void AddShortcut() {
    Intent shortcutIntent = new Intent(getApplicationContext(),
            ShortcutActivity.class);

 //   shortcutIntent.setAction("com.shashib.myreciever");

    Intent addIntent = new Intent();
    addIntent
            .putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
    addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "Add A Task");
    addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
            Intent.ShortcutIconResource.fromContext(getApplicationContext(),
                    R.drawable.addatask));
    addIntent.putExtra("shortcut","AddATask");
//Log.d("TAG", "SHORTCUT ADDED");
    addIntent
            .setAction("com.android.launcher.action.INSTALL_SHORTCUT");
    getApplicationContext().sendBroadcast(addIntent);
}
public void ShowTutorial() {
    final Dialog d = new Dialog(this);
    d.setContentView(R.layout.tutorial);
    Button button = (Button) d.findViewById(R.id.tutbutton);
    TextView tv = (TextView) d.findViewById(R.id.tuttext);
    tv.setMovementMethod(new ScrollingMovementMethod());
    button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            d.dismiss();
        }
    });
    d.show();
}


}