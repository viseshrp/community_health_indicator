package com.ssdifall2016.communityhealthindicator.interfaces;

import java.util.HashMap;

/**
 * Created by viseshprasad on 11/14/16.
 */

public interface RequestInterface {

    /**
     * Converts the url params to be converted into a byteArrray
     */

    byte[] getJSONByteArray(HashMap<String, String> params);

}
