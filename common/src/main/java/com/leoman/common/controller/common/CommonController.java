package com.leoman.common.controller.common;

import com.leoman.common.controller.editor.*;
import com.leoman.entity.Constant;
import com.leoman.user.entity.UserInfo;
import com.leoman.utils.DateUtils;
import com.leoman.utils.HttpRequestUtil;
import com.leoman.utils.ehcache.EhcacheUtil;
import me.chanjar.weixin.mp.api.WxMpService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * Created by wangbin on 2014/12/10.
 */
public class CommonController {


    public static Map<String,Object> emptyData =null;

    @Autowired
    private WxMpService wxMpService;

    public final static String weixin_ticket_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";

    static {
        emptyData = new HashMap<String, Object>();
        emptyData.put("data",new ArrayList());
        emptyData.put("iTotalRecords",0);
        emptyData.put("iTotalDisplayRecords",0);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new CustomStringEditor());
        binder.registerCustomEditor(MultipartFile.class, new CustomFileEditor());
        binder.registerCustomEditor(Double.class, new CustomDoubleEditor());
        binder.registerCustomEditor(Float.class, new CustomFloatEditor());
        binder.registerCustomEditor(Integer.class, new CustomIntegerEditor());
        binder.registerCustomEditor(Long.class, new CustomLongEditor());
        binder.registerCustomEditor(Date.class, new CustomDateEditor());
    }


    public Integer getPageNum(Integer start,Integer length){
        if(start==null){
            start = 0;
        }
        if(length == null){
            length = 10;
        }

        int pageNum = (start/length)+1;
        return pageNum;
    }

    /**
     * 获取指定天数的旧天数
     *
     * @return
     */
    public static Long getOldDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 7 * -1);

        return calendar.getTimeInMillis();
    }

    public static Date getYesterday() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 0);
        Date finallyDate = null;
        try {
            String yesterday = DateUtils.dateToStringWithFormat(calendar.getTime(), "yyyy-MM-dd hh:mm:ss");
            String days = yesterday.substring(0, 10);

            finallyDate = DateUtils.stringToDateWithFormat(days + " 00:00:00", "yyyy-MM-dd hh:mm:ss");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return finallyDate;
    }

    public static Date getToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        Date finallyDate = null;
        try {
            String yesterday = DateUtils.dateToStringWithFormat(calendar.getTime(), "yyyy-MM-dd hh:mm:ss");
            String days = yesterday.substring(0, 10);

            finallyDate = DateUtils.stringToDateWithFormat(days + " 00:00:00", "yyyy-MM-dd hh:mm:ss");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return finallyDate;
    }

    public Map<String,Object> createMap(String key, Object obj){
        Map<String,Object> map = new HashMap<>();
        map.put(key,obj);
        return map;
    }

    public UserInfo getSessionUser(HttpServletRequest request){
        UserInfo user = (UserInfo) request.getSession().getAttribute(Constant.SESSION_MEMBER_USER);
        return user;
    }

    /**
     * 获取access_token
     *
     * @return
     * @throws Exception
     * @updateDate 2015年8月3日23:22:39 增加对缓存的处理
     */
    public String getAccessToken() throws Exception {
        String accessToken = (String) EhcacheUtil.get("wxCache", "accessToken");
        System.out.println("cache accessToken : "+accessToken);
        if (StringUtils.isBlank(accessToken)
                || StringUtils.isEmpty(accessToken)) {
//            accessToken = wxMpConfigStorage.getAccessToken();
            accessToken = wxMpService.getAccessToken();
            System.out.println("api accessToken : "+accessToken);
            EhcacheUtil.put("wxCache", "accessToken", accessToken);
        }
        return accessToken;
    }

    /**
     * 获得jsapi_ticket（有效期7200秒)
     *
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @throws IOException
     * @throws NoSuchProviderException
     * @updateDate 2015年8月4日00:00:46 z 增加对缓存的
     */
    public String getTicket() throws Exception {
        String jsapi_ticket = null;
        String accessToken = getAccessToken();
        if(StringUtils.isNotBlank(accessToken)){
            jsapi_ticket = (String) EhcacheUtil.get("wxCache", "jsapi_ticket");

            if (StringUtils.isBlank(jsapi_ticket) || StringUtils.isEmpty(jsapi_ticket)) {
                String urljson = weixin_ticket_url.replace("ACCESS_TOKEN", accessToken);
                String result = HttpRequestUtil.sendGet(urljson);
                JSONObject str = JSONObject.fromObject(result);
                Integer errcode = (Integer)str.get("errcode");
                //如果接口调用失败，则重新获取accessToken
                if(errcode != 0){
                    accessToken = wxMpService.getAccessToken();
                    urljson = weixin_ticket_url.replace("ACCESS_TOKEN", accessToken);
                    result = HttpRequestUtil.sendGet(urljson);
                    str = JSONObject.fromObject(result);
                }
                jsapi_ticket = (String)str.get("ticket");
                System.out.println("api ticket : "+jsapi_ticket);
                EhcacheUtil.put("wxCache", "jsapi_ticket", jsapi_ticket);
            }
        }

        return jsapi_ticket;

    }

}
