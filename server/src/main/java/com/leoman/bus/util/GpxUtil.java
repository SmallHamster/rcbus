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
			if(resMap.get("success").equals(true)){
				return resMap;
			}
		}
		return null;
	}

	/**
	 * 获取所有车辆信息
	 * @return
     */
	public static List<Map> getGroupsBus() {
		Map user = userLogin();
		if(user != null){
			Map map = new HashMap<>();
			map.put("version",version);
			map.put("method","loadVehicles");
			map.put("uid",String.valueOf(user.get("uid")));
			map.put("uKey",user.get("uKey"));
			Response result = HttpRequestUtil.sendPost(basicUrl,map);
			if(result.getStatus()){
				String body = result.getBody();
				Map resMap = JsonUtil.jsontoMap(body);
				if(resMap.get("success").equals(true)){
					List<Map> groups = (List<Map>)resMap.get("groups");
					return groups;
				}
			}
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
			if(resMap.get("success").equals(true)){
				List<Map> locs = (List<Map>)resMap.get("locs");
				return locs;
			}
		}
		return null;
	}

	/**
	 * 获取两点间的距离
	 * @param long1
	 * @param lat1
	 * @param long2
	 * @param lat2
     * @return
     */
	public static double getDistance(double long1, double lat1, double long2,
								  double lat2) {
		double a, b, R;
		R = 6378137; // 地球半径
		lat1 = lat1 * Math.PI / 180.0;
		lat2 = lat2 * Math.PI / 180.0;
		a = lat1 - lat2;
		b = (long1 - long2) * Math.PI / 180.0;
		double d;
		double sa2, sb2;
		sa2 = Math.sin(a / 2.0);
		sb2 = Math.sin(b / 2.0);
		d = 2
				* R
				* Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1)
				* Math.cos(lat2) * sb2 * sb2));
		return d;
	}

}
