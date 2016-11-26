package com.ssdifall2016.communityhealthindicator.api;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.NetworkError;
import com.ssdifall2016.communityhealthindicator.models.Disease;
import com.ssdifall2016.communityhealthindicator.models.DiseaseList;
import com.ssdifall2016.communityhealthindicator.models.HealthOfficial;
import com.ssdifall2016.communityhealthindicator.parsers.DiseaseListParser;
import com.ssdifall2016.communityhealthindicator.parsers.UserLoginParser;
import com.ssdifall2016.communityhealthindicator.utils.APIConstants;

import java.nio.charset.Charset;

/**
 * Created by viseshprasad on 11/18/16.
 */

public class getDiseaseListApi extends AppRequest<DiseaseList> {

    public getDiseaseListApi(String mapped_county, Response.Listener<DiseaseList> listener, Response.ErrorListener errorListener) {
        super(Method.GET, "http://172.73.154.11:8080/countyname/" + mapped_county + "/", listener, errorListener);
        setShouldCache(false);
        setPriority(Priority.IMMEDIATE);

        setHttpParams("mapped_county", mapped_county);
    }

    @Override
    protected Response<DiseaseList> parseNetworkResponse(NetworkResponse response) {
        if (response.statusCode == 200) {
            DiseaseList diseaseList = new DiseaseListParser(new String(response.data, Charset.forName("UTF-8"))).getParserResponse();
            return Response.success(diseaseList, null);
        } else if (response.statusCode == 401) {
            return Response.error(new AuthFailureError());
        } else {
            return Response.error(new NetworkError());
        }
    }
}