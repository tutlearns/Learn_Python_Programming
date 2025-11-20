package com.meet.learnpython;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * MyAdManager: A helper class to manage the "ads disabled" state using SharedPreferences.
 * This class provides methods to check if ads should be shown and to update the status
 * after a successful in-app purchase.
 */
public class MyAdManager {

    // Name for the SharedPreferences file
    private static final String PREFS_NAME = "MyAdPrefs";
    // Key for storing the boolean status of ads being disabled
    private static final String KEY_ADS_DISABLED = "ads_disabled";

    private SharedPreferences sharedPreferences;

    /**
     * Constructor for MyAdManager.
     * Initializes SharedPreferences with the application context.
     *
     * @param context The application context.
     */
    public MyAdManager(Context context) {
        // Get a SharedPreferences instance in private mode
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    /**
     * Checks if ads should be disabled based on the stored preference.
     *
     * @return true if ads are disabled (user has purchased "remove ads"), false otherwise.
     */
    public boolean hasPurchasedRemoveAds() {
        // Retrieve the boolean value. Default is false (ads are enabled) if not found.
        return sharedPreferences.getBoolean(KEY_ADS_DISABLED, false);
    }

    /**
     * Sets the status of ads being disabled.
     * Call this method after a successful in-app purchase to remove ads.
     *
     * @param disabled true to disable ads, false to enable them.
     */
    public void setAdsDisabled(boolean disabled) {
        // Get an editor to modify SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // Put the new boolean value
        editor.putBoolean(KEY_ADS_DISABLED, disabled);
        // Apply the changes asynchronously
        editor.apply();
    }
}