package com.ssdifall2016.communityhealthindicator;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.RequestTickle;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.VolleyTickle;
import com.ssdifall2016.communityhealthindicator.api.CHIApi;
import com.ssdifall2016.communityhealthindicator.utils.HttpManager;

/**
 * Created by viseshprasad on 11/14/16.
 */

public class CHIApp extends Application {

    private static CHIApp mChiApp;
    private RequestQueue mRequestQueue;
    private RequestTickle mRequestTickle;
    private Context mContext;

    private CHIApi mChiApi;

    public static CHIApp get() {
        if (mChiApp == null) {
            System.exit(0);
        }
        return mChiApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        mChiApp = this;

        setupNetwork();
        setupApi();
    }

    public CHIApi getmChiApi() {
        return mChiApi;
    }

    private void setupNetwork() {
        HttpManager httpManager = new HttpManager(this);
        HttpStack httpStack = httpManager.getHttpStack();

        mRequestQueue = Volley.newRequestQueue(this);
        mRequestTickle = VolleyTickle.newRequestTickle(this, httpStack);
    }

    private void setupApi() {
        mChiApi = new CHIApi(mRequestQueue);
    }

}
