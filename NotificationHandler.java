package shashib.taskhelper;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Soft on 14-Sep-16.
 */
public class NotificationHandler {
    public void NotifyUser(String message, Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent viewmoreintent = PendingIntent.getActivity(context,1234,intent,PendingIntent.FLAG_UPDATE_CURRENT);

//Log.d("DARU",context.toString());
   //     Log.d("DARU",context.getApplicationContext().toString());

        NotificationCompat.Builder nbuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Today's work :")
                .setContentIntent(viewmoreintent);
     NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
                int i=0;

        if (message.contains("\n")) {
            String[] message1 = message.split("\n");
            do {
              inboxStyle.addLine(message1[i++]);
            }while(i<message1.length);
        }
        else;
        inboxStyle.addLine(message);
       nbuilder.setStyle(inboxStyle);


               NotificationManager mnotify = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        mnotify.notify(99,nbuilder.build());
    }
}
