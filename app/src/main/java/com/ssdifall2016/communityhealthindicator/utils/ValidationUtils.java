package com.ssdifall2016.communityhealthindicator.utils;

import android.content.Context;

import com.ssdifall2016.communityhealthindicator.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by viseshprasad on 11/4/16.
 */

public class ValidationUtils {
    public static boolean checkValidity(String data, int dataType, Context mContext) {

        boolean isDataValid = false;
        data = data.trim();
        /* Validating empty field */
        if (data.isEmpty()) {

            MsgUtils.displayToast(mContext, R.string.error_enter_all_details);
            isDataValid = false;

            /* Validating Email */
        } else {
            if (dataType == AppConstants.DATA_TYPE_EMAIL) {
                if (checkEmailValidity(data)) {
                    isDataValid = true;
                } else {
                    MsgUtils.displayToast(mContext, mContext.getResources().getString(R.string.error_invalid_email));
                    isDataValid = false;
                }
            /* Validating Password */
            } else if (dataType == AppConstants.DATA_TYPE_PASSWORD) {
                if (data.length() < AppConstants.PASSWORD_MIN_CHARACTER_COUNT) {
                    MsgUtils.displayToast(mContext, mContext.getResources().getString(R.string.error_invalid_password) + " Enter at least " + String.valueOf(AppConstants.PASSWORD_MIN_CHARACTER_COUNT) + " characters.");
                    isDataValid = false;
                } else {
                    isDataValid = true;
                }
            } else if (dataType == AppConstants.DATA_TYPE_GENERAL_TEXT) {
                /* Data is not empty */
                isDataValid = true;
            }
        }
        return isDataValid;
    }

    public static boolean checkEmailValidity(String userEmail) {
        boolean isEmailValid = false;
        Matcher emailMatcher;
        Pattern normalEmailPattern = Pattern.compile("\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
        emailMatcher = normalEmailPattern.matcher(userEmail);

        if (emailMatcher.matches()) {
            isEmailValid = true;
        }

        return isEmailValid;
    }
}
