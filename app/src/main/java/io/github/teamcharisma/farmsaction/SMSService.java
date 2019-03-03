package io.github.teamcharisma.farmsaction;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.telephony.SmsMessage;
import android.util.Log;

import androidx.annotation.Nullable;

public class SMSService extends Service {
    private SMSListener mSMSListener;
    private final String TAG = this.getClass().getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        //SMS event receiver
        Log.v(TAG, "onCreate service");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        mSMSListener = new SMSListener();
        IntentFilter mIntentFilter = new IntentFilter();
        mIntentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(mSMSListener, mIntentFilter);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.v(TAG, "onDestroy service");
        super.onDestroy();

        // Unregister the SMS receiver
        unregisterReceiver(mSMSListener);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public class SMSListener extends BroadcastReceiver {
        private final String TAG = this.getClass().getSimpleName();

        private int substringOccurences(String str, String findStr){
            int lastIndex = 0;
            int count = 0;

            while (lastIndex != -1) {

                lastIndex = str.indexOf(findStr, lastIndex);

                if (lastIndex != -1) {
                    count++;
                    lastIndex += findStr.length();
                }
            }
            return count;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.v("TAG", "onReceive service");
            Bundle extras = intent.getExtras();
            StringBuilder strMessages = new StringBuilder();

            if (extras != null) {
                Object[] smsextras = (Object[]) extras.get("pdus");
                if (smsextras != null)
                    for (Object smsextra : smsextras) {
                        SmsMessage smsmsg = SmsMessage.createFromPdu((byte[]) smsextra);

                        String strMsgBody = smsmsg.getMessageBody();
                        String strMsgSrc = smsmsg.getOriginatingAddress();
                        String strMessage;
                        strMessage = strMsgSrc + "|~|" + strMsgBody + "|~~|";
                        if (strMessage.toUpperCase().contains("UPI")) {
                            strMessages.append(strMessage);
                        }
                        Log.i(TAG, strMessage);
                    }

            }
            if (strMessages.length() > 0) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                String old = preferences.getString("SMSAPP", "");
                String neww = old + strMessages;
                preferences.edit()
                        .putString("SMSAPP", neww)
                        .apply();
                Log.i(TAG, "OMG:" + neww);
                notification(context, substringOccurences(neww, "|~~|"));
            }
        }

        public void notification(Context context, int n) {
            // Open NotificationView Class on Notification Click
            Intent intent = new Intent(context, UPIActivity.class);
            // Open NotificationView.java Activity
            PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancelAll();
            Notification mNotification = new Notification.Builder(context)
                    .setContentTitle("Add your UPI transactions!")
                    .setContentText(n + " transactions require your attention")
                    .setAutoCancel(true)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentIntent(pIntent)
                    .build();

            // Build Notification with Notification Manager
            notificationManager.notify(0, mNotification);
        }

    }
}
