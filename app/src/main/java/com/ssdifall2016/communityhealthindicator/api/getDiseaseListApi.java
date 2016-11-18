package com.ssdifall2016.communityhealthindicator.api;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.NetworkError;
import com.ssdifall2016.communityhealthindicator.models.Disease;
import com.ssdifall2016.communityhealthindicator.models.HealthOfficial;
import com.ssdifall2016.communityhealthindicator.parsers.DiseaseListParser;
import com.ssdifall2016.communityhealthindicator.parsers.UserLoginParser;
import com.ssdifall2016.communityhealthindicator.utils.APIConstants;

import java.nio.charset.Charset;

/**
 * Created by viseshprasad on 11/18/16.
 */

public class getDiseaseListApi extends AppRequest<Disease> {

    public getDiseaseListApi(String email, String mapped_county, Response.Listener<Disease> listener, Response.ErrorListener errorListener) {
        super(Method.POST, APIConstants.GET_DISEASE_LIST_URL, listener, errorListener);
        setShouldCache(false);
        setPriority(Priority.IMMEDIATE);

        setHttpParams("email", email);
        setHttpParams("mapped_county", mapped_county);
    }

    @Override
    protected Response<Disease> parseNetworkResponse(NetworkResponse response) {
        if (response.statusCode == 200) {
            Disease disease = new DiseaseListParser(new String(response.data, Charset.forName("UTF-8"))).getParserResponse();
            return Response.success(disease, null);
        } else if (response.statusCode == 401) {
            return Response.error(new AuthFailureError());
        } else {
            return Response.error(new NetworkError());
        }
    }
}