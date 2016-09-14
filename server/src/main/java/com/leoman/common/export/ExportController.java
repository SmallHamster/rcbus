package com.leoman.common.export;

import com.leoman.carrental.controller.CarRentalController;
import com.leoman.carrental.entity.CarRental;
import com.leoman.carrental.service.CarRentalService;
import com.leoman.user.entity.UserInfo;
import com.leoman.user.service.UserService;
import com.leoman.utils.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/export")
public class ExportController {
    // webapp路径
    private static String webpath = (ExportController.class.getResource("/").getPath().substring(0, ExportController.class.getResource("/").getPath().indexOf("WEB-INF")));
    // 临时文件名
    private static String tempName = "excelTempFile.xls";
    // 临时文件路径,不需要修改
    private static String tempFilePath = webpath + tempName;

    // 导出模版
    // 收入明细
    private static String USER_TEMPLATE = webpath + "excelTemplate" + "/" + "feedback.xls";

    // 导出字段排序，按模板的顺序列出数据库对应字段，中间用逗号隔开
    // 收入明细
    public static String USER_FIELD = "orderNo,startDate,userName,mobile,totalAmount,income,refund,busNum";

    // 导出文件名
    // 收入明细
    public static String USER_EXCEL_NAME = "收入明细" + getNowTime() + ".xls";

    @Autowired
    private CarRentalService carRentalService;

    @Autowired
    private UserService userService;

    /**
     * 导出反馈问题信息
     *
     * @author 涂奕恒
     */
    @RequestMapping(value = {"/exportFeedback"})
    public void exportFeedback(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        List<CarRental> carRentals = (List<CarRental>)session.getAttribute("carRentals");
        List<Map<String, Object>> list = carRentalService.pageToExcel(carRentals);
        ExcelUtil.createExcel(USER_TEMPLATE, USER_FIELD, list, tempFilePath);
        download(request, response, USER_EXCEL_NAME);
    }

    /**
     * 导入反馈问题信息
     *
     * @author 涂奕恒
     */
    @RequestMapping(value = {"/importFeedback"})
    @ResponseBody
    public Integer importFeedback(MultipartRequest multipartRequest) throws Exception {
        return userService.readExcelInfo(multipartRequest);
    }

    /**
     * 文件下载
     *
     * @author gaolei
     */
    public static void download(HttpServletRequest request, HttpServletResponse response, String exportName) throws Exception {
        // 解决文件名乱码问题
        if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) { // IE浏览器
            exportName = URLEncoder.encode(exportName, "UTF-8");
        } else { // 其他浏览器
            exportName = new String(exportName.getBytes("UTF-8"), "ISO8859-1");
        }

        InputStream inStream;
        inStream = new FileInputStream(tempFilePath);
        // 设置输出的格式
        response.setContentType("application/x-download");// 设置为下载application/x-download
        response.addHeader("content-type ", "application/x-msdownload");
        response.setContentType("application/octet-stream");
        // 设置输出的文件名
        response.setHeader("Content-Disposition", "attachment; filename=\"" + exportName + "\"");
        // 循环取出流中的数据
        byte[] b = new byte[100];
        int len;
        OutputStream outStream = response.getOutputStream();
        PrintStream out = new PrintStream(outStream);

        while ((len = inStream.read(b)) > 0) {
            out.write(b, 0, len);
            out.flush();
        }
        out.close();
        inStream.close();

        // 导出完成，删除文件
        File file = new File(tempFilePath);
        file.delete();
    }

    /**
     * 文件下载
     *
     * @author gaolei
     */
    public static void downloadZip(HttpServletRequest request, HttpServletResponse response, String exportName) throws Exception {
        // 解决文件名乱码问题
        if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) { // IE浏览器
            exportName = URLEncoder.encode(exportName, "UTF-8");
        } else { // 其他浏览器
            exportName = new String(exportName.getBytes("UTF-8"), "ISO8859-1");
        }

        InputStream inStream;
        inStream = new FileInputStream(webpath + "/excelTemplate/teamSales.7z");
        // 设置输出的格式
        response.setContentType("application/x-download");// 设置为下载application/x-download
        response.addHeader("content-type ", "application/x-msdownload");
        response.setContentType("application/octet-stream");
        // 设置输出的文件名
        response.setHeader("Content-Disposition", "attachment; filename=\"" + exportName + "\"");
        // 循环取出流中的数据
        byte[] b = new byte[100];
        int len;
        OutputStream outStream = response.getOutputStream();
        PrintStream out = new PrintStream(outStream);

        while ((len = inStream.read(b)) > 0) {
            out.write(b, 0, len);
            out.flush();
        }
        out.close();
        inStream.close();
    }

    /**
     * 获取当前时间
     *
     * @author gaolei
     */
    public static String getNowTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        return sdf.format(date);
    }
}
