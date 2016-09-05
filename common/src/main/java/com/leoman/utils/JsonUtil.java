package com.leoman.utils;

import com.google.gson.ExclusionStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.leoman.utils.gson.DmsExclusionStrategy;
import net.sf.json.JSONObject;
import org.springframework.data.domain.Page;

import java.lang.reflect.Type;
import java.util.*;

/**
 * Created by wangbin on 14-10-17.
 */
public class JsonUtil {

    /**
     * json字符串转换成对象
     *
     * @param str  json字符串
     * @param type 对象类型
     * @param <T>
     * @return
     */
    public static <T> T json2Obj(String str, Type type) {
        Gson gson = new GsonBuilder().
                serializeNulls().
                setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        return gson.fromJson(str, type);
    }


    public static String obj2Json(Object obj) {
        Gson gson = new GsonBuilder()
                .serializeNulls()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        return gson.toJson(obj);
    }

    /**
     * java对象转换成json字符串
     *
     * @param obj      java对象
     * @param excludes 过滤字段
     * @return
     */
    public static String obj2Json(Object obj, String... excludes) {
        ExclusionStrategy strategy = new DmsExclusionStrategy(excludes);
        Gson gson = new GsonBuilder()
                .serializeNulls()
                .setExclusionStrategies(strategy)
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        return gson.toJson(obj);
    }


    /**
     * java对象转换成json字符串
     *
     * @param obj
     * @param classes  类别数组
     * @param excludes 字符串数组
     * @return
     */
    public static String obj2Json(Object obj, Class[] classes, String... excludes) {
        ExclusionStrategy strategy = new DmsExclusionStrategy(excludes, classes);
        Gson gson = new GsonBuilder()
                .serializeNulls()
                .setExclusionStrategies(strategy)
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        return gson.toJson(obj);
    }


    /**
     * java对象转换成json字符串,过滤成api需要的字段值
     *
     * @param obj
     * @param excludes
     * @return
     */
    public static String obj2ApiJson(Object obj, String... excludes) {
        String json = obj2Json(obj, excludes);
        return filterJson(json);
    }

    /**
     * 过滤字符串 -> true转换成0，false转换成1,null转换成""或者{}
     *
     * @param json
     * @return
     */
    public static String filterJson(String json) {
        //"image":null
        String lastStr = json.replaceAll(":true", ":\"0\"")
                .replaceAll(":false", ":\"1\"")
                // .replaceAll("\"image\":null", "\"image\":{}")
                .replaceAll(":null", ":\"\"");

        return lastStr;
    }

    /**
     * 将json格式的字符串解析成Map对象 <li>
     * json格式：{"name":"admin","retries":"3fff","testname" :"ddd","testretries":"fffffffff"}
     */
    @SuppressWarnings("rawtypes")
    public static Map<String, Object> jsontoMap(Object object) {
        Map<String, Object> data = new HashMap<String, Object>();
        // 将json字符串转换成jsonObject
        JSONObject jsonObject = JSONObject.fromObject(object);
        Iterator it = jsonObject.keys();
        // 遍历jsonObject数据，添加到Map对象
        while (it.hasNext()) {
            String key = String.valueOf(it.next());
            Object value = (Object) jsonObject.get(key);
            data.put(key, value);
        }
        return data;
    }

    public static void main(String[] args) {

    }


    /**
     * json转换公共类
     *
     * @param page 分页数据
     * @return
     */
    public static Map<String, Object> fitting(Page<?> page) {
        List<?> list = page.getContent();

        // data中的page
        Map<String, Object> pageMap = new HashMap<String, Object>();
        pageMap.put("totalNum", page.getTotalElements());
        pageMap.put("totalPage", page.getTotalPages());
        pageMap.put("currentPage", page.getNumber() + 1);

        // data中的list
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("list", list);
        dataMap.put("page", pageMap);
        return dataMap;
    }

    /**
     * json转换公共类，附加新的list
     *
     * @param page
     * @param newList
     * @return
     */
    public static Map<String, Object> fittingPlus(Page<?> page, List<?> newList) {
        // data中的page
        Map<String, Object> pageMap = new HashMap<String, Object>();
        pageMap.put("totalNum", page.getTotalElements());
        pageMap.put("totalPage", page.getTotalPages());
        pageMap.put("currentPage", page.getNumber() + 1);

        // data中的list
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("list", newList);
        dataMap.put("page", pageMap);
        return dataMap;
    }

    /**
     * json字符串转换成集合
     *
     * @param str
     * @param type
     * @param <T>
     * @return
     */
    public static <T> List<T> json2List(String str, Class<T[]> type) {
        T[] list = new GsonBuilder().create().fromJson(str, type);
        return Arrays.asList(list);
    }

}
