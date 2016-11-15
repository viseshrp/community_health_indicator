package com.ssdifall2016.communityhealthindicator.parsers;

import com.google.gson.Gson;
import com.ssdifall2016.communityhealthindicator.models.BaseModel;
import com.ssdifall2016.communityhealthindicator.models.HealthOfficial;
import com.ssdifall2016.communityhealthindicator.utils.BaseApiParser;

/**
 * Created by viseshprasad on 11/14/16.
 */

public class UserLoginParser extends BaseApiParser {
    public UserLoginParser(String response) {
        super(response);
    }

    @Override
    public HealthOfficial getParserResponse() {
        Gson gson = new Gson();
        return gson.fromJson(mResponse, HealthOfficial.class);
    }
}
