package com.ssdifall2016.communityhealthindicator.api;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.NetworkError;
import com.ssdifall2016.communityhealthindicator.models.HealthOfficial;
import com.ssdifall2016.communityhealthindicator.parsers.UserLoginParser;
import com.ssdifall2016.communityhealthindicator.utils.APIConstants;

import java.nio.charset.Charset;

/**
 * Created by viseshprasad on 11/14/16.
 */

public class OfficialLoginApi extends AppRequest<HealthOfficial> {

    public OfficialLoginApi(String userName, String password, Response.Listener<HealthOfficial> listener, Response.ErrorListener errorListener) {
        super(Method.GET, "http://172.73.154.11:8080/login/" + userName + "/" + password + "/", listener, errorListener);
        setShouldCache(false);
        setPriority(Priority.IMMEDIATE);

        setHttpParams("username", userName);
        setHttpParams("password", password);
    }

    @Override
    protected Response<HealthOfficial> parseNetworkResponse(NetworkResponse response) {
        if (response.statusCode == 200) {
            HealthOfficial healthOfficial = new UserLoginParser(new String(response.data, Charset.forName("UTF-8"))).getParserResponse();
            return Response.success(healthOfficial, null);
        } else if (response.statusCode == 401) {
            return Response.error(new AuthFailureError());
        } else {
            return Response.error(new NetworkError());
        }
    }
}
