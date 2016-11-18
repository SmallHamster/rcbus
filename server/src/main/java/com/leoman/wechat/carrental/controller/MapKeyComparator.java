package com.leoman.wechat.carrental.controller;

import java.util.*;

/**
 * Created by Administrator on 2016/9/18.
 */
class MapKeyComparator implements Comparator<String>{

    @Override
    public int compare(String str1, String str2) {

        return str1.compareTo(str2);
    }

}