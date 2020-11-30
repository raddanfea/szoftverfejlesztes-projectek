package hu.szoftverprojekt.holdemfree.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

/**
 * You can save and get data from the default shared preferences file (which you can access anywhere in the app)
 * in 2 ways:
 * 1) By creating an instance, os you don't have to add the context all the time  or
 * 2) By calling the static methods
 */
public class AppData {
  
  private Context context;
  private SharedPreferences pref;
  private Editor editor;
  
  private int defIntValue;
  private String defStringValue;
  private boolean defBooleanValue;
  private float defFloatValue;
  
  public Context getContext() {
    return context;
  }
  
  public void setContext(Context context) {
    this.context = context;
  }
  
  public SharedPreferences getSharedPreferences() {
    return pref;
  }
  
  public void setDefIntValue(int defIntValue) {
    this.defIntValue = defIntValue;
  }
  
  public void setDefStringValue(String defStringValue) {
    this.defStringValue = defStringValue;
  }
  
  public void setDefBooleanValue(boolean defBooleanValue) {
    this.defBooleanValue = defBooleanValue;
  }
  
  public void setDefFloatValue(float defFloatValue) {
    this.defFloatValue = defFloatValue;
  }
  
  
  public AppData(Context context) {
    this.context = context;
    pref = PreferenceManager.getDefaultSharedPreferences(context);
    editor = pref.edit();
    
    defIntValue = 0;
    defStringValue = "Default string value";
    defBooleanValue = false;
    defFloatValue = 0.0f;
  }
  
  // ----------------------------------- THE INSTANCED WAY ---------------------------------
  
  public void save(String key, int value) {
    editor.putInt(key, value)
        .apply();
  }
  
  public void save(String key, String value) {
    editor.putString(key, value)
        .apply();
  }
  
  public void save(String key, boolean value) {
    editor.putBoolean(key, value)
        .apply();
  }
  
  public void save(String key, float value) {
    editor.putFloat(key, value)
        .apply();
  }
  
  public int getInt(String key) {
    return pref.getInt(key, defIntValue);
  }
  
  public String getString(String key) {
    return pref.getString(key, defStringValue);
  }
  
  public boolean getBoolean(String key) {
    return pref.getBoolean(key, defBooleanValue);
  }
  
  public float getFloat(String key) {
    return pref.getFloat(key, defFloatValue);
  }
  
  // ------------------------------ THE STATIC WAY -------------------------------
  
  public static void save(Context context, String key, int value) {
    PreferenceManager.getDefaultSharedPreferences(context)
        .edit()
        .putInt(key, value)
        .apply();
  }
  
  public static void save(Context context, String key, String value) {
    PreferenceManager.getDefaultSharedPreferences(context)
        .edit()
        .putString(key, value)
        .apply();
  }
  
  public static void save(Context context, String key, boolean value) {
    PreferenceManager.getDefaultSharedPreferences(context)
        .edit()
        .putBoolean(key, value)
        .apply();
  }
  
  public static void save(Context context, String key, float value) {
    PreferenceManager.getDefaultSharedPreferences(context)
        .edit()
        .putFloat(key, value)
        .apply();
  }
  
  public static int get(Context context, String key, int defValue) {
    return PreferenceManager.getDefaultSharedPreferences(context)
        .getInt(key, defValue);
  }
  
  public static String get(Context context, String key, String defValue) {
    return PreferenceManager.getDefaultSharedPreferences(context)
        .getString(key, defValue);
  }
  
  public static boolean get(Context context, String key, boolean defValue) {
    return PreferenceManager.getDefaultSharedPreferences(context)
        .getBoolean(key, defValue);
  }
  
  public static float get(Context context, String key, float defValue) {
    return PreferenceManager.getDefaultSharedPreferences(context)
        .getFloat(key, defValue);
  }
}
