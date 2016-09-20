package com.leoman.bus.util;

import com.leoman.common.core.bean.Response;
import com.leoman.utils.HttpRequestUtil;
import com.leoman.utils.JsonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取gpx数据工具类
 * Created by Daisy on 2016/9/20.
 */
public class GpxUtil {

	private static String basicUrl= "http://221.234.42.20:89/gpsonline/GPSAPI";

	private static String username = "gdtqtest";

	private static String password = "000000";

	private static String version = "1";

	public static void main(String[] args) {
		List list = getGroupsBus();
		System.out.println(list);
	}

	/**
	 * 用户登录
	 * @return
     */
	public static Map userLogin() {
		Map map = new HashMap<>();
		map.put("version",version);
		map.put("method","loginSystem");
		map.put("name",username);
		map.put("pwd",password);
		Response result = HttpRequestUtil.sendPost(basicUrl,map);
		if(result.getStatus()){
			String body = result.getBody();
			Map resMap = JsonUtil.jsontoMap(body);
			return resMap;
		}
		return null;
	}

	/**
	 * 获取所有车辆信息
	 * @return
     */
	public static List<Map> getGroupsBus() {
		Map user = userLogin();
		Map map = new HashMap<>();
		map.put("version",version);
		map.put("method","loadVehicles");
		map.put("uid",String.valueOf(user.get("uid")));
		map.put("uKey",user.get("uKey"));
		Response result = HttpRequestUtil.sendPost(basicUrl,map);
		if(result.getStatus()){
			String body = result.getBody();
			Map resMap = JsonUtil.jsontoMap(body);
			List<Map> groups = (List<Map>)resMap.get("groups");
			return groups;
		}
		return null;
	}

	/**
	 * 获取车辆当前最新位置
	 * @param vid
	 * @param vKey
     * @return
     */
	public static List<Map> getCurrentLoc(String vid, String vKey) {
		Map map = new HashMap<>();
		map.put("version",version);
		map.put("method","loadLocation");
		map.put("vid",vid);
		map.put("vKey",vKey);
		Response result = HttpRequestUtil.sendPost(basicUrl,map);
		if(result.getStatus()){
			String body = result.getBody();
			Map resMap = JsonUtil.jsontoMap(body);
			List<Map> locs = (List<Map>)resMap.get("locs");
			return locs;
		}
		return null;
	}

}
