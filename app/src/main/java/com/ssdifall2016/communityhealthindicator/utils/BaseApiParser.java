package com.ssdifall2016.communityhealthindicator.utils;

import com.ssdifall2016.communityhealthindicator.models.BaseModel;

/**
 * Created by viseshprasad on 11/14/16.
 */

public abstract class BaseApiParser {

    protected String mResponse;

    public BaseApiParser(String response) {
        mResponse = response;
    }

    public abstract <T extends BaseModel> BaseModel getParserResponse();


}
