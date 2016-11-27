package com.ssdifall2016.communityhealthindicator.api;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.NetworkError;
import com.ssdifall2016.communityhealthindicator.models.CountyNameList;
import com.ssdifall2016.communityhealthindicator.parsers.CountyNameListParser;

import java.nio.charset.Charset;

public class getCountyNameListApi extends AppRequest<CountyNameList> {

    public getCountyNameListApi(String mapped_county_id, Response.Listener<CountyNameList> listener, Response.ErrorListener errorListener) {
        super(Method.GET, "http://172.73.154.11:8080/diseaseDistinctCounty/" + mapped_county_id + "/", listener, errorListener);
        setShouldCache(false);
        setPriority(Priority.IMMEDIATE);

        setHttpParams("mapped_county", mapped_county_id);
    }

    @Override
    protected Response<CountyNameList> parseNetworkResponse(NetworkResponse response) {
        if (response.statusCode == 200) {
            CountyNameList countyNameList = new CountyNameListParser(new String(response.data, Charset.forName("UTF-8"))).getParserResponse();
            return Response.success(countyNameList, null);
        } else if (response.statusCode == 401) {
            return Response.error(new AuthFailureError());
        } else {
            return Response.error(new NetworkError());
        }
    }
}