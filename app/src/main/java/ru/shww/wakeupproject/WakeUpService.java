package ru.shww.wakeupproject;

import android.app.IntentService;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.PowerManager;
import android.util.Log;
import android.view.WindowManager;

/**
 * Created by shww-big on 01.03.2015.
 */
public class WakeUpService extends IntentService {

    private static String LOG_TAG = WakeUpService.class.getSimpleName();



    public WakeUpService() {
        super("WakeUpService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.d(LOG_TAG, "alarm works");


        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK
                | PowerManager.ACQUIRE_CAUSES_WAKEUP, "MyWakelockTag");
        wakeLock.acquire();

        KeyguardManager keyguardManager = (KeyguardManager) getApplicationContext().getSystemService(Context.KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock keyguardLock =  keyguardManager.newKeyguardLock("TAG");
        keyguardLock.disableKeyguard();

        final String encodedHash = Uri.encode("#");
        String ussd = "*100" + encodedHash;
        Intent intent1 = new Intent(Intent.ACTION_CALL);
        intent1.setData(Uri.parse("tel:" + ussd));
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent1.addFlags(Intent.FLAG_FROM_BACKGROUND);
        startActivity(intent1);


        wakeLock.release();

    }


}
