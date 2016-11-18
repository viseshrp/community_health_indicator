package com.ssdifall2016.communityhealthindicator.api;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.NetworkError;
import com.ssdifall2016.communityhealthindicator.models.County;
import com.ssdifall2016.communityhealthindicator.models.Disease;
import com.ssdifall2016.communityhealthindicator.parsers.CountyListParser;
import com.ssdifall2016.communityhealthindicator.parsers.DiseaseListParser;
import com.ssdifall2016.communityhealthindicator.utils.APIConstants;

import java.nio.charset.Charset;

/**
 * Created by viseshprasad on 11/18/16.
 */

public class getCountyListApi extends AppRequest<County> {

    public getCountyListApi(String email, String mapped_disease, Response.Listener<County> listener, Response.ErrorListener errorListener) {
        super(Method.POST, APIConstants.GET_COUNTY_LIST_URL, listener, errorListener);
        setShouldCache(false);
        setPriority(Priority.IMMEDIATE);

        setHttpParams("email", email);
        setHttpParams("mapped_disease", mapped_disease);
    }

    @Override
    protected Response<County> parseNetworkResponse(NetworkResponse response) {
        if (response.statusCode == 200) {
            County county = new CountyListParser(new String(response.data, Charset.forName("UTF-8"))).getParserResponse();
            return Response.success(county, null);
        } else if (response.statusCode == 401) {
            return Response.error(new AuthFailureError());
        } else {
            return Response.error(new NetworkError());
        }
    }
}