package com.ssdifall2016.communityhealthindicator.api;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.NetworkError;
import com.ssdifall2016.communityhealthindicator.models.County;
import com.ssdifall2016.communityhealthindicator.models.CountyList;
import com.ssdifall2016.communityhealthindicator.models.Disease;
import com.ssdifall2016.communityhealthindicator.parsers.CountyListParser;
import com.ssdifall2016.communityhealthindicator.parsers.DiseaseListParser;
import com.ssdifall2016.communityhealthindicator.utils.APIConstants;

import java.nio.charset.Charset;

/**
 * Created by viseshprasad on 11/18/16.
 */

public class getCountyListApi extends AppRequest<CountyList> {

    public getCountyListApi(String mapped_disease_id, Response.Listener<CountyList> listener, Response.ErrorListener errorListener) {
        super(Method.GET, "http://172.73.154.11:8080/disease/" + mapped_disease_id + "/", listener, errorListener);
        setShouldCache(false);
        setPriority(Priority.IMMEDIATE);

        setHttpParams("mapped_disease", mapped_disease_id);
    }

    @Override
    protected Response<CountyList> parseNetworkResponse(NetworkResponse response) {
        if (response.statusCode == 200) {
            CountyList countyList = new CountyListParser(new String(response.data, Charset.forName("UTF-8"))).getParserResponse();
            return Response.success(countyList, null);
        } else if (response.statusCode == 401) {
            return Response.error(new AuthFailureError());
        } else {
            return Response.error(new NetworkError());
        }
    }
}