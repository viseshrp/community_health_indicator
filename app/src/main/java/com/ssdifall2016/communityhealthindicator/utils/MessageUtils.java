package com.ssdifall2016.communityhealthindicator.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by viseshprasad on 11/4/16.
 */

public class MessageUtils {
    public static void displayToast(Context context, String message) {
        if (context != null && context.getResources() != null) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }

    public static void displayToast(Context context, int messageStringReference) {
        if (context != null && context.getResources() != null) {
            Toast.makeText(context, messageStringReference, Toast.LENGTH_SHORT).show();
        }
    }
}
