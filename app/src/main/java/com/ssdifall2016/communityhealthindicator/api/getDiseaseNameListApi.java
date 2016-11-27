package com.ssdifall2016.communityhealthindicator.api;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.NetworkError;
import com.ssdifall2016.communityhealthindicator.models.DiseaseNameList;
import com.ssdifall2016.communityhealthindicator.parsers.DiseaseNameListParser;

import java.nio.charset.Charset;

/**
 * Created by viseshprasad on 11/26/16.
 */

public class getDiseaseNameListApi extends AppRequest<DiseaseNameList> {

    public getDiseaseNameListApi(String mapped_county_id, Response.Listener<DiseaseNameList> listener, Response.ErrorListener errorListener) {
        super(Method.GET, "http://172.73.154.11:8080/countyDistinctDisease/" + mapped_county_id + "/", listener, errorListener);
        setShouldCache(false);
        setPriority(Priority.IMMEDIATE);

        setHttpParams("mapped_county", mapped_county_id);
    }

    @Override
    protected Response<DiseaseNameList> parseNetworkResponse(NetworkResponse response) {
        if (response.statusCode == 200) {
            Log.e("response.data", new String(response.data, Charset.forName("UTF-8")));
            DiseaseNameList diseaseNameList = new DiseaseNameListParser(new String(response.data, Charset.forName("UTF-8"))).getParserResponse();
            return Response.success(diseaseNameList, null);
        } else if (response.statusCode == 401) {
            return Response.error(new AuthFailureError());
        } else {
            return Response.error(new NetworkError());
        }
    }
}
