package com.leoman.bus.util;

import com.leoman.common.core.bean.Response;
import com.leoman.utils.HttpRequestUtil;
import com.leoman.utils.JsonUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取gpx数据工具类
 * Created by Daisy on 2016/9/20.
 */
public class GpxUtil {

	private static String basicUrl= "http://120.77.65.158:89/gpsonline/GPSAPI";//120.77.65.158  221.234.42.20

	private static String username = "whgdtq01";

	private static String password = "000000";

	private static String version = "1";

	public static void main(String[] args) {

//		List<Map> list = getGroupsBus();
//		System.out.println(list);
		List list = getCurrentLoc("8365837", "f4cd02362491a44b2a2138840d0ab1c1");
		System.out.println(list);
		Map loc = (Map)list.get(0);
		Double lat = (Double) loc.get("lat");
		Double lng = (Double) loc.get("lng");
		Double curLatXZ = (Double) loc.get("lat_xz");//纬度修正值
        Double curLngXZ = (Double)loc.get("lng_xz");//经度修正值
		Double baidu_lat_xz = (Double) loc.get("baidu_lat_xz");//纬度修正值
		Double baidu_lng_xz = (Double)loc.get("baidu_lng_xz");//经度修正值
		System.out.println(lat );
		System.out.println(lng );
		System.out.println("------------------");
		double[] position = MathUtil.wgs2bd(lat ,lng);
		System.out.println(position[0]);
		System.out.println(position[1]);
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
