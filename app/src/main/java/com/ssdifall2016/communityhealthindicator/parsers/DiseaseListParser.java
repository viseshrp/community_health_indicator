package com.ssdifall2016.communityhealthindicator.parsers;

import com.google.gson.Gson;
import com.ssdifall2016.communityhealthindicator.models.County;
import com.ssdifall2016.communityhealthindicator.models.Disease;
import com.ssdifall2016.communityhealthindicator.utils.BaseApiParser;

/**
 * Created by viseshprasad on 11/18/16.
 */

public class DiseaseListParser extends BaseApiParser {

    public DiseaseListParser(String response) {
        super(response);
    }

    @Override
    public Disease getParserResponse() {
        Gson gson = new Gson();
        return gson.fromJson(mResponse, Disease.class);
    }

}
