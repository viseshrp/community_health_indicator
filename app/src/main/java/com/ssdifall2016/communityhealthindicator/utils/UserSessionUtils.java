package com.ssdifall2016.communityhealthindicator.utils;

import android.content.Context;

import com.ssdifall2016.communityhealthindicator.models.HealthOfficial;

/**
 * Created by viseshprasad on 11/4/16.
 */

public class UserSessionUtils {
    public static void saveStudentLoginData(Context mContext, HealthOfficial response) {
        PreferencesUtils.setString(mContext, AppConstants.EMAIL, response.getEmail());
        PreferencesUtils.setString(mContext, AppConstants.USER_FIRST_NAME, response.getFirst_name());
        PreferencesUtils.setString(mContext, AppConstants.USER_LAST_NAME, response.getLast_name());
        PreferencesUtils.setString(mContext, AppConstants.MAPPED_COUNTY, response.getMappedCounty());
        PreferencesUtils.setString(mContext, AppConstants.MAPPED_DISEASE, response.getMappedDisease());
        PreferencesUtils.setBoolean(mContext, AppConstants.IS_LOGGED_IN, true);
    }
}