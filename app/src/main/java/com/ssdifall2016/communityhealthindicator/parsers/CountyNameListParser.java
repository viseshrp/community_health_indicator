package com.ssdifall2016.communityhealthindicator.parsers;

import com.google.gson.Gson;
import com.ssdifall2016.communityhealthindicator.models.CountyNameList;
import com.ssdifall2016.communityhealthindicator.utils.BaseApiParser;

/**
 * Created by viseshprasad on 11/26/16.
 */

public class CountyNameListParser extends BaseApiParser {
    public CountyNameListParser(String response) {
        super(response);
    }

    @Override
    public CountyNameList getParserResponse() {
        Gson gson = new Gson();
        return gson.fromJson(mResponse, CountyNameList.class);
    }

}
