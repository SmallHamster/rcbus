//package com.leoman.utils;
//
//import org.apache.poi.hssf.usermodel.*;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.OutputStream;
//import java.util.List;
//import java.util.Map;
//
//public class ExcelUtil {
//
//    /**
//     * 生成excel
//     *
//     * @param templatePath   目标文件路径
//     * @param titles         字段数据，中间用逗号隔开，例如：name,mobile,address
//     * @param list           待导出的列表
//     * @param exportFilePath 生成文件路径
//     * @author gaolei
//     */
//    public static void createExcel(String templatePath, String titles, List<Map<String, Object>> list, String exportFilePath)
//            throws Exception {
//        // 读取导出模板
//        FileInputStream fis = new FileInputStream(new File(templatePath));
//        HSSFWorkbook workbook = new HSSFWorkbook(fis);
//        HSSFCellStyle style = workbook.createCellStyle();
//        style.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
//        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//        // 输出流
//        OutputStream out = new FileOutputStream(exportFilePath);
//        // 模板数据要放在Sheet1
//        HSSFSheet sheet = workbook.getSheet("Y2016项目");
//        // 标题字段数据
//        String[] titlesArr = titles.split(",");
//
//        HSSFRow row = null;
//        for (int i = 0; i < list.size(); i++) {
//            Map<String, Object> map = list.get(i);
//            row = sheet.createRow(2 + i);
//
//            for (int j = 0; j < titlesArr.length; j++) {
//                HSSFCell cell = row.createCell(j);
//                String value = (String) (map.get(titlesArr[j].toString()) + "");
//                HSSFRichTextString text = new HSSFRichTextString(value);
//                cell.setCellValue(text);
//            }
//
//        }
//        workbook.write(out);
//    }
//}