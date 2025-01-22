package ai.argenie.vuzixspeechplugin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.unity3d.player.UnityPlayer;
import com.vuzix.sdk.speechrecognitionservice.VuzixSpeechClient;

import java.util.Objects;

public class VoiceCmdReceiver extends BroadcastReceiver {

    private static final String TAG = "VoiceCmdReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Objects.equals(intent.getAction(), VuzixSpeechClient.ACTION_VOICE_COMMAND)) {
            String phrase = intent.getStringExtra(VuzixSpeechClient.PHRASE_STRING_EXTRA);
            if (phrase != null) {
                Log.d(TAG, "Recognized phrase: " + phrase);
                UnityPlayer.UnitySendMessage("VuzixSpeechManager", "OnKeywordRecognized", phrase);
            }
        }
    }
}