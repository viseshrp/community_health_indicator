package com.ssdifall2016.communityhealthindicator.api;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.ssdifall2016.communityhealthindicator.interfaces.RequestInterface;
import com.ssdifall2016.communityhealthindicator.models.BaseModel;
import com.ssdifall2016.communityhealthindicator.utils.LoggerUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by viseshprasad on 11/14/16.
 */

public abstract class AppRequest<T extends BaseModel> extends Request<T>
        implements RequestInterface {

    private static int SOCKET_TIMEOUT_MS = 20000;
    private Response.ErrorListener mErrorReference;
    private Response.Listener<T> mResponseReference;
    private HashMap<String, String> mParams = new HashMap<>();

    public AppRequest(int method, String url, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        mErrorReference = errorListener;
        mResponseReference = listener;
        setRetryPolicy(new DefaultRetryPolicy(SOCKET_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public void setHttpParams(String key, String value) {
        mParams.put(key, value);
    }

    public void setHttpParams(HashMap<String, String> params) {
        mParams = params;
    }

    public HashMap<String, String> getHttpParams() {
        return mParams;
    }

    public void authenticateIfFailure() {
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mParams;
    }

    @Override
    public byte[] getJSONByteArray(HashMap<String, String> params) {
        try {
            JSONObject jObject = new JSONObject();
            Set<String> set = params.keySet();

            for (String key : set) {
                String value = params.get(key);
                jObject.accumulate(key, value == null ? "" : value);
            }
            LoggerUtils.d("HTTP: json pose", jObject.toString());
            return jObject.toString().getBytes(Charset.forName("UTF-8"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deliverError(VolleyError error) {
        Response.ErrorListener mErrorListener = mErrorReference;
        if (mErrorListener != null) {
            mErrorListener.onErrorResponse(error);
        }
        authenticateIfFailure();
    }

    @Override
    protected void deliverResponse(T response) {
        Response.Listener<T> listener = mResponseReference;
        if (listener != null) {
            listener.onResponse(response);
        }
    }
}
