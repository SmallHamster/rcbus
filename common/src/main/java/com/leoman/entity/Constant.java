package com.leoman.entity;

/**
 * Created by wangbin on 2015/6/24.
 */
public interface Constant {
    String ENCODING = "UTF-8";

    Integer DEFAULT_COIN = 10;

    int PAGE_DEF_SIZE = 10;

    String SESSION_MEMBER_GLOBLE = "session_globle_member";
    String SESSION_MEMBER_BUSINESS = "session_business_member";
    String SESSION_MEMBER_USER = "session_user_member";

    String SESSION_WEIXIN_WXUSER = "session_weixin_wxUser";

    String MEMBER_TYPE_GLOBLE = "GLOBLE";

    String jsApi_ticket = "JSAPI_TICKET";
    String WEIXIN_STATE = "weixin_state";

    // 当前登录人id
    String CURRENT_USER_ID = "0";

    // 当前登录人姓名
    String CURRENT_USER_NAME = "session_member_name";

    String EVENT_DEF_SUBSCRIBE_TEXT = "感谢关注!";
}
