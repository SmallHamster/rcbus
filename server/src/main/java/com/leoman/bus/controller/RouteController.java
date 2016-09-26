package com.leoman.bus.controller;

import com.leoman.bus.dao.RouteStationDao;
import com.leoman.bus.entity.Route;
import com.leoman.bus.entity.RouteOrder;
import com.leoman.bus.entity.RouteStation;
import com.leoman.bus.entity.RouteTime;
import com.leoman.bus.service.RouteOrderService;
import com.leoman.bus.service.RouteService;
import com.leoman.bus.service.RouteStationService;
import com.leoman.bus.service.RouteTimeService;
import com.leoman.bus.service.impl.RouteServiceImpl;
import com.leoman.bussend.entity.BusSend;
import com.leoman.bussend.service.BusSendService;
import com.leoman.common.controller.common.GenericEntityController;
import com.leoman.common.core.Result;
import com.leoman.common.factory.DataTableFactory;
import com.leoman.common.service.Query;
import com.leoman.order.entity.Order;
import com.leoman.order.service.OrderService;
import com.leoman.permissions.admin.entity.Admin;
import com.leoman.system.enterprise.entity.Enterprise;
import com.leoman.system.enterprise.service.EnterpriseService;
import com.leoman.user.entity.UserInfo;
import com.leoman.user.service.UserService;
import com.leoman.utils.ClassUtil;
import com.leoman.utils.JsonUtil;
import org.jdom.JDOMException;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 路线
 * Created by Daisy on 2016/9/7.
 */
@Controller
@RequestMapping(value = "/admin/route")
public class RouteController extends GenericEntityController<Route, Route, RouteServiceImpl> {

    @Autowired
    private RouteService routeService;

    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private BusSendService busSendService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private RouteOrderService routeOrderService;

    @Autowired
    private RouteStationService routeStationService;

    @Autowired
    private RouteTimeService routeTimeService;

    /**
     * 列表页面
     */
    @RequestMapping(value = "/index")
    public String index(Model model) {
        //获取所有企业
        List<Enterprise> enterpriseList = enterpriseService.queryAll();

        model.addAttribute("enterpriseList", enterpriseList);
        return "route/route_list";
    }

    /**
     * 获取列表
     * @param route
     * @param draw
     * @param start
     * @param length
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> list(HttpServletRequest request,Route route, Long enterpriseId, Integer draw, Integer start, Integer length) {
        int pagenum = getPageNum(start, length);
        Query query = Query.forClass(Route.class, routeService);
        query.setPagenum(pagenum);
        query.setPagesize(length);
        query.like("startStation",route.getStartStation());
        query.like("endStation",route.getEndStation());
        if(enterpriseId != null && enterpriseId != 0){
            Enterprise enterprise = new Enterprise();
            enterprise.setId(enterpriseId);
            query.eq("enterprise",enterprise);
        }
        Admin admin = getSessionAdmin(request);
        if(admin != null && admin.getEnterprise() != null){
            query.eq("enterprise.id",admin.getEnterprise().getId());
        }

        Page<Route> page = routeService.queryPage(query);
        return DataTableFactory.fitting(draw, page);
    }

    /**
     * 添加车辆
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Long id, Model model) {
        if (id != null) {
            Route route = routeService.queryByPK(id);
            List<RouteTime> times = routeTimeService.findByRouteId(route.getId());

            //路线时间
            model.addAttribute("route", route);//路线
            model.addAttribute("timeJson", JsonUtil.obj2Json(times));

            //已派遣的班车ids
            String busIdsStr = "";
            StringBuffer busIds = new StringBuffer();
            List<BusSend> bsList = busSendService.findBus(route.getId(),1);
            for (BusSend bs:bsList) {
                Long busId = bs.getBus().getId();
                busIds.append(busId+",");
                busIdsStr = busIds.toString().substring(0,busIds.toString().length()-1);
            }
            model.addAttribute("busIds",busIdsStr);

            //显示已有的路线
            StringBuffer stationSb = new StringBuffer();
            List<RouteStation> stationList = routeStationService.findByRouteId(id);
            for (RouteStation rs:stationList) {
                stationSb.append(rs.getStationName()+"-->");
            }
            String stationStr = "";
            if(stationSb.length()>0){
                stationStr = stationSb.toString().substring(0,stationSb.toString().length()-3);
            }
            model.addAttribute("stationStr",stationStr);

        }
        //获取所有企业
        List<Enterprise> enterpriseList = enterpriseService.queryAll();
        model.addAttribute("enterpriseList", enterpriseList);
        return "route/route_add";
    }

    /**
     * 保存
     * @param route
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Result save(Route route, String departTimes, String backTimes, String busIds, Integer isRoundTrip, MultipartFile file) {
        try {
            List<Map> list = null;
            if(file != null){
                list = this.doXMLParse(file);//解析xml
            }
            routeService.saveRoute(route,departTimes,backTimes,busIds,isRoundTrip, list);
        } catch (Exception e) {
            e.printStackTrace();
            Result.failure();
        }
        return Result.success();
    }

    /**
     * 详情
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public String info(Long id, Model model) {
        if (id != null) {
            Route route = routeService.queryByPK(id);
            model.addAttribute("route", route);//路线
            model.addAttribute("timeJson", JsonUtil.obj2Json(route.getTimes()));//路线时间

            //已派遣的班车ids
            String busIdsStr = "";
            StringBuffer busIds = new StringBuffer();
            List<BusSend> bsList = busSendService.findBus(route.getId(),1);
            for (BusSend bs:bsList) {
                Long busId = bs.getBus().getId();
                busIds.append(busId+",");
                busIdsStr = busIds.toString().substring(0,busIds.toString().length()-1);
            }
            model.addAttribute("busIds",busIdsStr);

            //显示已有的路线
            StringBuffer stationSb = new StringBuffer();
            List<RouteStation> stationList = routeStationService.findByRouteId(id);
            for (RouteStation rs:stationList) {
                stationSb.append(rs.getStationName()+"-->");
            }
            String stationStr = "";
            if(stationSb.length()>0){
                stationStr = stationSb.toString().substring(0,stationSb.toString().length()-3);
            }
            model.addAttribute("stationStr",stationStr);
        }
        return "route/route_info";
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result delete(String ids) {
        try {
            String[] idArr = JsonUtil.json2Obj(ids, String[].class);
            for (String id:idArr) {
                if(!StringUtils.isEmpty(id)){
                    routeService.deleteRoute(Long.valueOf(id));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Result.failure();
        }
        return Result.success();
    }

    /**
     * 派遣
     * @param ids
     * @return
     */
    @RequestMapping(value = "/dispatch", method = RequestMethod.POST)
    @ResponseBody
    public Result dispatch(String ids) {
        try {
            String [] idArr = ids.split("\\,");
            for (String id:idArr) {
                Integer routeId = Integer.valueOf(id);
                routeService.deleteByPK(routeId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Result.failure();
        }
        return Result.success();
    }

    /**
     * 修改路线的显示状态
     * @param route
     * @return
     */
    @RequestMapping(value = "/updateIsShow", method = RequestMethod.POST)
    @ResponseBody
    public Result updateIsShow(Route route) {
        try {
            Route orgRoute = routeService.queryByPK(route.getId());
            ClassUtil.copyProperties(orgRoute,route);
            routeService.update(orgRoute);
        } catch (Exception e) {
            e.printStackTrace();
            Result.failure();
        }
        return Result.success();
    }

    /**
     * 通勤巴士历史记录
     * @param model
     * @return
     */
    @RequestMapping(value = "/order/index")
    public String orderIndex(Model model) {
        //获取所有企业
        List<Enterprise> enterpriseList = enterpriseService.queryAll();

        model.addAttribute("enterpriseList", enterpriseList);
        return "route/route_order_list";
    }

    /**
     * 获取通勤巴士历史记录，即订单列表
     * @param draw
     * @param start
     * @param length
     * @return
     */
    @RequestMapping(value = "/order/list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> list(String routeName,Long enterpriseId, String startDate, String endDate, Integer draw, Integer start, Integer length) {
        int pagenum = getPageNum(start, length);
        StringBuffer sql = new StringBuffer("SELECT \n" +
                "  CONCAT(FROM_UNIXTIME(ro.`create_date` / 1000,'%Y-%m-%d'),' ',ro.`depart_time`) AS rideTime,\n" +//0
                "  ro.`start_station` AS startStation,\n" +//1
                "  ro.`end_station` AS endStation,\n" +//2
                "  ro.`depart_time` AS departTime,\n" +//3
                "  e.`name` AS enterpriseName,\n" +//4
                "  COUNT(o.`user_id`) AS peopleNum, \n" +//5
                "  ro.`route_id` AS routeId\n" +//6
                "FROM\n" +
                "  t_route_order ro \n" +
                "  LEFT JOIN t_order o \n" +
                "    ON o.`id` = ro.`order_id` \n" +
                "  LEFT JOIN t_route r \n" +
                "    ON r.`id` = ro.`route_id` \n" +
                "  LEFT JOIN t_enterprise e \n" +
                "    ON e.`id` = r.`enterprise_id` \n" +
                "  where 1=1 ");
        if(!StringUtils.isEmpty(routeName)){
            sql.append(" AND (ro.`start_station` LIKE '%"+routeName+"%' OR ro.`end_station` LIKE '%"+routeName+"%') ");
        }
        if(enterpriseId != null){
            sql.append(" and e.`id` = '"+enterpriseId+"'");
        }
        if(!StringUtils.isEmpty(startDate)){
            sql.append(" AND FROM_UNIXTIME(ro.`create_date` / 1000,'%Y-%m-%d') >= DATE_FORMAT('"+startDate+"','%Y-%m-%d') ");
        }
        if(!StringUtils.isEmpty(endDate)){
            sql.append(" AND FROM_UNIXTIME(ro.`create_date` / 1000,'%Y-%m-%d') <= DATE_FORMAT('"+endDate+"','%Y-%m-%d') ");
        }
        sql.append(" GROUP BY CONCAT(FROM_UNIXTIME(ro.`create_date` / 1000,'%Y-%m-%d'),' ',ro.`depart_time`) ");
        Page page = orderService.queryPageBySql(sql.toString(), pagenum, length);
        return DataTableFactory.fitting(draw, page);
    }

    /**
     * 保存订单
     * @param routeId
     * @param departTime
     * @param userId
     * @return
     */
    @RequestMapping(value = "/saveOrder", method = RequestMethod.POST)
    @ResponseBody
    public Result saveOrder(Long routeId, String departTime, Long userId) {
        try {
            UserInfo user = userService.queryByPK(userId);
            routeService.saveOrder(routeId, departTime, user);
        } catch (Exception e) {
            e.printStackTrace();
            Result.failure();
        }
        return Result.success();
    }

    /**
     * 进入司机评价页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/order/comment")
    public String orderComment(Model model, Long routeId, String departTime) {
        model.addAttribute("routeId",routeId);
        model.addAttribute("departTime",departTime);
        Route route = routeService.queryByPK(routeId);
        model.addAttribute("route",route);
        return "route/route_order_comment";
    }

    /**
     * 获取路线对应的所有评价
     * @param draw
     * @param start
     * @param length
     * @return
     */
    @RequestMapping(value = "/comment/list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> commentList(RouteOrder routeOrder, Long routeId,String mobile,Integer draw, Integer start, Integer length) {
        int pagenum = getPageNum(start, length);
        Query query = Query.forClass(RouteOrder.class, routeOrderService);
        query.setPagenum(pagenum);
        query.setPagesize(length);
        query.eq("order.isComment",1);//已评价
        query.eq("route.id", routeId);
        String [] departTimeArr = routeOrder.getDepartTime().split("\\ ");
        query.eq("departTime", departTimeArr.length==2?departTimeArr[1]:null);
        query.like("userInfo.mobile",mobile);
        Page<RouteOrder> page = routeOrderService.queryPage(query);
        return DataTableFactory.fitting(draw, page);
    }

    /**
     * 解析xml
     * @param file
     * @return
     * @throws JDOMException
     * @throws IOException
     */
    private List<Map> doXMLParse(MultipartFile file) throws JDOMException, IOException, ParserConfigurationException, SAXException {
        List<Map> list = new ArrayList<>();
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        InputStream in = file.getInputStream();
        Document doc = builder.parse(in);
        doc.getDocumentElement().normalize();
        NodeList nList = doc.getElementsByTagName("wpt");


        for(int i = 0 ; i<nList.getLength();i++) {
            Node node = nList.item(i);
            Element ele = (Element) node;
            if (node.getNodeType() == Element.ELEMENT_NODE) {
                Map map = new HashMap();
                map.put("lat",ele.getAttribute("lat"));
                map.put("lon",ele.getAttribute("lon"));
                map.put("name",ele.getElementsByTagName("name").item(0).getTextContent());
                list.add(map);
            }
        }
        in.close();
        return list;
    }

}
