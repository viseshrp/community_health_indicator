package com.ssdifall2016.communityhealthindicator.api;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.ssdifall2016.communityhealthindicator.models.HealthOfficial;

/**
 * Created by viseshprasad on 11/14/16.
 */

public class CHIApi {
    private final RequestQueue mRequestQueue;

    public CHIApi(RequestQueue mRequestQueue) {
        this.mRequestQueue = mRequestQueue;
    }

    public Request<?> login(String email, String password, Response.Listener<HealthOfficial> listener,
                            Response.ErrorListener errorListener) {
        return mRequestQueue.add(new OfficialLoginApi(email, password, listener, errorListener));
    }

}