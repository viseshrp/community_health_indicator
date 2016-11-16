package com.ssdifall2016.communityhealthindicator.utils;

import android.content.Context;

import com.ssdifall2016.communityhealthindicator.R;

/**
 * Created by viseshprasad on 11/4/16.
 */

public class NetworkErrorHandler {
    public static void handleLoginError(Context mContext, int errorCode) {
        if (errorCode == 401) {
            MsgUtils.displayToast(mContext, R.string.error_authentication);
        } else {
            MsgUtils.displayToast(mContext, R.string.error_generic);
        }
    }

}
