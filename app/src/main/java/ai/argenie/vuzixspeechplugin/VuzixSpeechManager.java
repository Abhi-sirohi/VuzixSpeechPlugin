package ai.argenie.vuzixspeechplugin;

import android.app.Activity;
import android.content.IntentFilter;
import android.util.Log;
import com.vuzix.sdk.speechrecognitionservice.VuzixSpeechClient;

public class VuzixSpeechManager {

    private static final String TAG = VuzixSpeechManager.class.getSimpleName();
    private VuzixSpeechClient speechClient;
    private Activity activity;
    private VoiceCmdReceiver voiceCmdReceiver;

    public VuzixSpeechManager(Activity activity) {
        this.activity = activity;
        try {
            this.speechClient = new VuzixSpeechClient(activity);
        }catch (Exception e)
        {
            Log.e(TAG, e.toString());
        }

        this.voiceCmdReceiver = new VoiceCmdReceiver();
    }

    public void initialize() {
        if (speechClient != null) {
            Log.d(TAG, "Initializing Vuzix Speech Client...");

            // Register phrases
            speechClient.insertPhrase("chat");
            speechClient.insertPhrase("join meeting");
            speechClient.insertPhrase("open chat");

            // Register the broadcast receiver
            IntentFilter filter = new IntentFilter(VuzixSpeechClient.ACTION_VOICE_COMMAND);
            activity.registerReceiver(voiceCmdReceiver, filter);

            Log.d(TAG, "Phrases registered and BroadcastReceiver initialized.");
        } else {
            Log.e(TAG, "Vuzix Speech Client is null!");
        }
    }

    public void stop() {
        if (activity != null && voiceCmdReceiver != null) {
            activity.unregisterReceiver(voiceCmdReceiver);
            Log.d(TAG, "BroadcastReceiver unregistered.");
        }
    }
}