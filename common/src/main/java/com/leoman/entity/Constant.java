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

    String MEMBER_TYPE_GLOBLE = "GLOBLE";

    // 用户积分/馒头变动类型
    Integer USER_INTEGRAL_CHANGE_TYPE_001 = 1;  // 积分
    Integer USER_INTEGRAL_CHANGE_TYPE_002 = 2;  // 馒头

    // 举报消息状态
    Integer REPORT_STATUS_000 = 0;   // 未处理
    Integer REPORT_STATUS_001 = 1;   // 已删帖
    Integer REPORT_STATUS_002 = 2;   // 已锁帖
    Integer REPORT_STATUS_003 = 3;   // 已忽略

    // 帖子类型
    Integer POST_TYPE_000 = 0;   // 普通帖
    Integer POST_TYPE_001 = 1;   // 直播帖
    Integer POST_TYPE_002 = 2;   // 投票帖
    Integer POST_TYPE_003 = 3;   // 用户帖
    Integer POST_TYPE_004 = 4;   // 帖子评论（回复）
    Integer POST_TYPE_005 = 5;   // 作品评论
    Integer POST_TYPE_006 = 6;   // 作品回复

    // 用户操作类别
    Integer USER_CHANGE_CATEGORY_001 = 1;   // 帖子

    // 用户操作类型
    Integer USER_CHANGE_TYPE_001 = 1;   // 点赞
    Integer USER_CHANGE_TYPE_002 = 2;   // 收藏

    // 分类类别
    Integer CATEGORY_TYPE_001 = 1;    // 帖子
    Integer CATEGORY_TYPE_002 = 2;    // 剧照
    Integer CATEGORY_TYPE_003 = 3;    // 音乐
    Integer CATEGORY_TYPE_004 = 4;    // 视频
    Integer CATEGORY_TYPE_005 = 5;    // 小说
    Integer CATEGORY_TYPE_006 = 6;    // 漫画

    // 评论回复类型
    Integer COMMENT_TYPE_DEFAULT = 0; // 评论
    Integer COMMENT_TYPE_REPLY = 1;   // 回复

    // 是否主创
    Integer IF_CREATOR_YES = 1;  // 是
    Integer IF_CREATOR_NO = 0;   // 不是

    // 收藏类型
    Integer COLLECT_TYPE_001 = 1;    // 视频
    Integer COLLECT_TYPE_002 = 2;    // 小说
    Integer COLLECT_TYPE_003 = 3;    // 漫画
    Integer COLLECT_TYPE_004 = 4;    // 帖子

    // 福利类型
    Integer WELFARE_TYPE_000 = 0;    // 铃声
    Integer WELFARE_TYPE_001 = 1;    // 经验值
    Integer WELFARE_TYPE_002 = 2;    // 实物
    Integer WELFARE_TYPE_003 = 3;    // 表情包
    Integer WELFARE_TYPE_004 = 4;    // 商城购买
    Integer WELFARE_TYPE_005 = 5;    // 游戏兑换码

    // 主创动态类型
    Integer CREATER_DYNAMIC_TYPE_001 = 1;  // 正在看帖子
    Integer CREATER_DYNAMIC_TYPE_002 = 2;  // 正在回帖子
    Integer CREATER_DYNAMIC_TYPE_003 = 3;  // 正在发帖子
    Integer CREATER_DYNAMIC_TYPE_004 = 4;  // 正在看小说
    Integer CREATER_DYNAMIC_TYPE_005 = 5;  // 正在看漫画
    Integer CREATER_DYNAMIC_TYPE_006 = 6;  // 正在看网剧
    Integer CREATER_DYNAMIC_TYPE_007 = 7;  // 正在发弹幕

    // 馒头获取路径
    Integer COIN_GET_TYPE_001 = 3;   // 邀请好友成功注册，获得3个馒头

    // 当前登录人id
    String CURRENT_USER_ID = "0";

    // 当前登录人姓名
    String CURRENT_USER_NAME = "session_member_name";
}
