package androidsamples.java.dicegames;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

public class DiceGamesPrefs {
    private static final String TAG = "DiceGamesPrefs";
    private static final String PREFS_KEY_BALANCE = "PREFS_KEY_BALANCE";
    private static final String SHARED_PREF_FILE
            = "androidsamples.java.dicegames.SHARED_PREF_FILE";

    private static SharedPreferences getSharedPrefs(@NotNull Context context) {
        return context
                .getSharedPreferences(SHARED_PREF_FILE,
                        Context.MODE_PRIVATE);
    }
    static int balance(@NotNull Context context) {
        SharedPreferences prefs = getSharedPrefs(context);
        int balance = prefs.getInt(PREFS_KEY_BALANCE, 0);
        Log.d(TAG, "Retrieving balance: " + balance);
        return balance;
    }
    static void setBalance(@NotNull Context context, int balance) {
        SharedPreferences prefs = getSharedPrefs(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(PREFS_KEY_BALANCE, balance);
        editor.apply();
        Log.d(TAG, "Storing balance: " + balance);
    }

}
