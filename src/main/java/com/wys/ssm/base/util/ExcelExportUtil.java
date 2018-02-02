package com.wys.ssm.base.util;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.springframework.util.StringUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelExportUtil {

    public static void exportFile(Map<String, List<List<String>>> fileMap, String filePath,
                                  HttpServletResponse response, HttpServletRequest request, String fileName) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        Set<String> sheetSet = fileMap.keySet();
        Object[] sheetName = sheetSet.toArray();
        HSSFSheet sheet = null;
        for (int k = 0; k < sheetName.length; k++) {
            sheet = workbook.createSheet();
            workbook.setSheetName(0, sheetName[k].toString());
            List<List<String>> fileData = fileMap.get(sheetName[k]);
            for (int i = 0; i < fileData.size(); i++) {
                HSSFRow row = sheet.createRow(i);
                sheet.setDefaultColumnWidth(18);
                List<String> fileBody = fileData.get(i);
                for (int j = 0; j < fileBody.size(); j++) {
                    HSSFCell cell = row.createCell(j);
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    HSSFRichTextString testString = new HSSFRichTextString(fileBody.get(j));
                    cell.setCellValue(testString);
                }
            }
        }

        try {
            String name = fileName + ".xls";
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;" + encodingFileName(name, request));
            ServletOutputStream fos = response.getOutputStream();
            workbook.write(fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String encodingFileName(String filename, HttpServletRequest request)
            throws UnsupportedEncodingException {

        String userAgent = request.getHeader("User-Agent");
        if (StringUtils.isEmpty(userAgent)) {
            return "filename=" + URLEncoder.encode(filename, "UTF8");
        } else if (userAgent.indexOf("Trident") != -1) {
            return "filename=" + URLEncoder.encode(filename, "UTF8");
        } else if (userAgent.indexOf("MSIE") != -1) {
            return "filename=" + URLEncoder.encode(filename, "UTF8");
        } else if (userAgent.indexOf("Opera") != -1) {
            return "filename*=UTF-8''" + URLEncoder.encode(filename, "UTF8");
        } else {
            return "filename=\"" + new String(filename.getBytes("UTF-8"), "ISO8859-1") + "\"";
        }
    }

    /**
     * 生成excel文件下载
     * 
     * @param response
     * @param fileName
     *            文件名
     * @param records
     *            数据列表
     * @param headers
     *            excel头信息
     * @param exportProperties
     *            导出数据字段
     */
    public static <T> void downExcelStream(HttpServletResponse response, String fileName, Collection<T> records,
                                           String[] headers, String[] exportProperties) {
        Workbook workbook = createWorkbook(records, headers, exportProperties);

        BufferedOutputStream bos = null;
        try {
            // 设置response参数，可以打开下载页面
            response.reset();
            response.setContentType("application/octet-stream;charset=utf-8");
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + new String((fileName + ".xls").getBytes(), "iso-8859-1"));
            ServletOutputStream out = response.getOutputStream();

            bos = new BufferedOutputStream(out);
            workbook.write(bos);
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workbook = null;
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                workbook = null;
            }
        }
    }

    public static <T> Workbook createWorkbook(Collection<T> records, String[] headers, String[] exportProperties) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        // 设置样式
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setBorderLeft((short) 1);
        cellStyle.setBorderTop((short) 1);
        cellStyle.setBorderRight((short) 1);
        cellStyle.setBorderBottom((short) 1);
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
        headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

        for (int i = 0; i < headers.length; i++) {
            sheet.setColumnWidth(i, 380 * 13);
        }
        int rowIndex = 0;
        addRow(sheet, headers, rowIndex++, null, headerStyle, cellStyle);
        if (records != null) {
        	int i=1;
            for (T record : records) {
                List<Object> values = new ArrayList<Object>();
                for (String exportProperty : exportProperties) {
                    Object value = null;
                    try {
                        value = PropertyUtils.getProperty(record, exportProperty);
                    } catch (Exception e) {
                        // logger.error(e.getMessage());
                    }
                    if (value == null) {
                        values.add("");
                    } else {
                        values.add(value);
                    }
                }
                //大于60000条，分页
                if(i++%60000==0){
                	sheet = workbook.createSheet();
                	 rowIndex = 0;
                	 for (int j = 0; j < headers.length; j++) {
                         sheet.setColumnWidth(i, 380 * 13);
                     }
                     addRow(sheet, headers, rowIndex++, null, headerStyle, cellStyle);
                }
                addRow(sheet, values.toArray(new Object[values.size()]), rowIndex++, record, headerStyle, cellStyle);
                values = null;
            }
        }
        return workbook;
    }

    public static <T> Row addRow(Sheet sheet, Object[] values, int rowIndex, T record, CellStyle headerStyle,
            CellStyle cellStyle) {

        if (values == null) {
            return null;
        }

        Row row = sheet.createRow(rowIndex);
        int i = 0;

        CellStyle style = cellStyle;
        if (rowIndex == 0) {
            style = headerStyle;
        } else {
            style = cellStyle;
        }
        for (Object value : values) {
            addCell(row, i++, value, style);
        }
        return row;
    }

    public static Cell addCell(Row row, int index, Object value, CellStyle cellStyle) {
        Cell cell = row.createCell(index);
        if (value instanceof java.util.Date) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            cell.setCellValue(sdf.format(value));
        } else {
            cell.setCellType(Cell.CELL_TYPE_STRING);
            cell.setCellValue(value != null ? value.toString() : "");
        }
        cellStyle.setBorderLeft((short) 1);
        cellStyle.setBorderTop((short) 1);
        cellStyle.setBorderRight((short) 1);
        cellStyle.setBorderBottom((short) 1);
        cell.setCellStyle(cellStyle);

        return cell;
    }
}
