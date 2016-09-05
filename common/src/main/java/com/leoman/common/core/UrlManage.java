package com.leoman.common.core;

import com.leoman.entity.Configue;

/**
 * Created by wangbin on 2015/7/31.
 */
public class UrlManage {

    public static String getProUrl(String uri){

        String url = Configue.getBaseUrl()+uri;
        return url;
    }


}
