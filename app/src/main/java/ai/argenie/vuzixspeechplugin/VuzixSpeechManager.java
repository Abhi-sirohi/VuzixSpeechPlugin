package ai.argenie.vuzixspeechplugin;

import android.app.Activity;
import android.content.IntentFilter;
import android.util.Log;

import com.vuzix.sdk.speechrecognitionservice.VuzixSpeechClient;

public class VuzixSpeechManager {

    private static final String TAG = VuzixSpeechManager.class.getSimpleName();
    private static VuzixSpeechManager instance;
    private VuzixSpeechClient speechClient;
    private VoiceCmdReceiver voiceCmdReceiver;
    private Activity activity;

    public VuzixSpeechManager(Activity activity) {
        this.activity = activity;
        try {
            speechClient = new VuzixSpeechClient(activity);
        } catch (Exception e) {
            Log.e(TAG, "Error initializing VuzixSpeechClient: " + e);
        }
        voiceCmdReceiver = new VoiceCmdReceiver();
    }

    public static VuzixSpeechManager getInstance(Activity activity) {
        if (instance == null) {
            instance = new VuzixSpeechManager(activity);
        }
        return instance;
    }

    public void initialize() {
        if (speechClient != null) {
            Log.d(TAG, "Initializing Vuzix Speech Client...");
            // Insert wake words
            speechClient.insertWakeWordPhrase("hello ar genie");
            speechClient.insertWakeWordPhrase("hello argenie");

            // Insert custom phrases
            speechClient.insertPhrase("chat");
            speechClient.insertPhrase("join meeting");
            speechClient.insertPhrase("open chat");

            // Register the broadcast receiver
            if (activity != null && voiceCmdReceiver != null) {
                IntentFilter filter = new IntentFilter(VuzixSpeechClient.ACTION_VOICE_COMMAND);
                activity.registerReceiver(voiceCmdReceiver, filter);
                Log.i(TAG, "Phrases registered and BroadcastReceiver initialized.");
            } else {
                Log.e(TAG, "Activity or VoiceCmdReceiver is null!");
            }
        } else {
            Log.e(TAG, "Vuzix Speech Client is null!");
        }
    }

    public void stop() {
        if (activity != null && voiceCmdReceiver != null) {
            activity.unregisterReceiver(voiceCmdReceiver);
            Log.d(TAG, "BroadcastReceiver unregistered.");
        } else {
            Log.e(TAG, "Failed to unregister BroadcastReceiver: Activity or VoiceCmdReceiver is null!");
        }
    }
}