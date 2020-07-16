package eslam.emad.bloodbank.data;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class SharedPreferencesManger {

    private SharedPreferences sharedPreferences = null;
    private static SharedPreferencesManger INSTANCE = null;

    private SharedPreferencesManger(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(
                    "BloodBankSharedPreferences", MODE_PRIVATE);
        }
    }

    public static SharedPreferencesManger getINSTANCE(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new SharedPreferencesManger(context);
        }
        return INSTANCE;
    }

    public void saveStringValue(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void saveIntegerValue(String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public void saveBooleanValue(String key, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public String restoreStringValue(String key) {
        return sharedPreferences.getString(key,"Empty");
    }

    public int restoreIntegerValue(String key) {
        return sharedPreferences.getInt(key,0);
    }

    public boolean restoreBooleanValue(String key) {
        return sharedPreferences.getBoolean(key,false);
    }
}
