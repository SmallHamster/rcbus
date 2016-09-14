package com.leoman.utils;

import com.leoman.user.entity.UserInfo;
import com.leoman.user.entity.vo.UserExportVo;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/1 0001.
 */
public class ReadExcelUtil {

//    public static void readXls() {
//        try {
//            File file = new File("E:/导入新增高校.xlsx");
//            POIFSFileSystem poifsFileSystem = new POIFSFileSystem(new FileInputStream(file));
//            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(poifsFileSystem);
//            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
//
//            int rowstart = hssfSheet.getFirstRowNum();
//            int rowEnd = hssfSheet.getLastRowNum();
//            for (int i = rowstart; i <= rowEnd; i++) {
//                HSSFRow row = hssfSheet.getRow(i);
//                if (null == row) continue;
//                int cellStart = row.getFirstCellNum();
//                int cellEnd = row.getLastCellNum();
//
//                for (int k = cellStart; k <= cellEnd; k++) {
//                    HSSFCell cell = row.getCell(k);
//                    if (null == cell) continue;
//                    System.out.println("" + k + "  ");
//                    System.out.println("type:" + cell.getCellType());
//
//                    chooseValue(cell.getCellType(), null, cell, 1);
//
//                }
//                System.out.print("\n");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void readXlsx() {
//        try {
//            File file = new File("E:/导入新增高校.xlsx");
//            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream(file));
//            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
//
//            int rowstart = xssfSheet.getFirstRowNum();
//            int rowEnd = xssfSheet.getLastRowNum();
//            for (int i = rowstart; i <= rowEnd; i++) {
//                XSSFRow row = xssfSheet.getRow(i);
//                if (null == row) continue;
//                int cellStart = row.getFirstCellNum();
//                int cellEnd = row.getLastCellNum();
//
//                for (int k = cellStart; k <= cellEnd; k++) {
//                    XSSFCell cell = row.getCell(k);
//                    if (null == cell) continue;
//
//                    chooseValue(cell.getCellType(), cell, null, 0);
//                }
//                System.out.print("\n");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static void chooseValue(Integer cellType, XSSFCell xssfCell, HSSFCell hssfCell, Integer type) {
//        switch (cellType) {
//            case HSSFCell.CELL_TYPE_NUMERIC: // 数字
//                if (type == 1) {
//                    System.out.print(hssfCell.getNumericCellValue() + "   ");
//                } else {
//                    System.out.print(xssfCell.getNumericCellValue() + "   ");
//                }
//                break;
//            case HSSFCell.CELL_TYPE_STRING: // 字符串
//                if (type == 1) {
//                    System.out.print(hssfCell.getStringCellValue() + "   ");
//                } else {
//                    System.out.print(xssfCell.getStringCellValue() + "   ");
//                }
//                break;
//            case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
//                if (type == 1) {
//                    System.out.print(hssfCell.getBooleanCellValue() + "   ");
//                } else {
//                    System.out.print(xssfCell.getBooleanCellValue() + "   ");
//                }
//                break;
//            case HSSFCell.CELL_TYPE_FORMULA: // 公式
//                if (type == 1) {
//                    System.out.print(hssfCell.getCellFormula() + "   ");
//                } else {
//                    System.out.print(xssfCell.getCellFormula() + "   ");
//                }
//                break;
//            case HSSFCell.CELL_TYPE_BLANK: // 空值
//                System.out.println(" ");
//                break;
//            case HSSFCell.CELL_TYPE_ERROR: // 故障
//                System.out.println(" ");
//                break;
//            default:
//                System.out.print("未知类型   ");
//                break;
//        }
//    }

    public static List<UserExportVo> readExcel(MultipartFile multipartFile) {
        List<UserExportVo> list = new ArrayList<UserExportVo>();
        UserExportVo userExportVo = null;

        try {
            CommonsMultipartFile cf = (CommonsMultipartFile) multipartFile;
            DiskFileItem fi = (DiskFileItem) cf.getFileItem();
            File file = fi.getStoreLocation();
            POIFSFileSystem poifsFileSystem = new POIFSFileSystem(new FileInputStream(file));
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(poifsFileSystem);

            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);

            int rowEnd = hssfSheet.getLastRowNum();
            for (int i = 1; i <= rowEnd; i++) {
                HSSFRow row = hssfSheet.getRow(i);
                if (null == row) continue;

                if (null == row.getCell(1)) continue;

                // 将数据写入map
                userExportVo = new UserExportVo();
                //修改格式
                row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                if(StringUtils.isNotBlank(row.getCell(0).getStringCellValue().trim())){
                    userExportVo.setMobile(row.getCell(0).getStringCellValue().trim());
                }
                if(StringUtils.isNotBlank(row.getCell(1).getStringCellValue().trim())){
                    userExportVo.setEnterprise(row.getCell(1).getStringCellValue().trim());
                }
                list.add(userExportVo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
