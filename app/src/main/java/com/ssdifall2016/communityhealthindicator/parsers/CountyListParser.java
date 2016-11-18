package com.ssdifall2016.communityhealthindicator.parsers;

import com.google.gson.Gson;
import com.ssdifall2016.communityhealthindicator.models.County;
import com.ssdifall2016.communityhealthindicator.utils.BaseApiParser;

/**
 * Created by viseshprasad on 11/18/16.
 */

public class CountyListParser extends BaseApiParser {

    public CountyListParser(String response) {
        super(response);
    }

    @Override
    public County getParserResponse() {
        Gson gson = new Gson();
        return gson.fromJson(mResponse, County.class);
    }

}
