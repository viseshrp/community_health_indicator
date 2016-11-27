package com.ssdifall2016.communityhealthindicator.parsers;

import com.google.gson.Gson;
import com.ssdifall2016.communityhealthindicator.models.DiseaseNameList;
import com.ssdifall2016.communityhealthindicator.utils.BaseApiParser;

/**
 * Created by viseshprasad on 11/26/16.
 */

public class DiseaseNameListParser extends BaseApiParser {
    public DiseaseNameListParser(String response) {
        super(response);
    }

    @Override
    public DiseaseNameList getParserResponse() {
        Gson gson = new Gson();
        return gson.fromJson(mResponse, DiseaseNameList.class);
    }

}
