package com.hhh.core.util;



import java.io.IOException;  
import java.io.InputStream;  
import java.util.ArrayList;  
import java.util.List;  
import org.apache.poi.hssf.usermodel.HSSFCell;  
import org.apache.poi.hssf.usermodel.HSSFWorkbook;  
import org.apache.poi.ss.usermodel.Cell;  
import org.apache.poi.ss.usermodel.Row;  
import org.apache.poi.ss.usermodel.Sheet;  
import org.apache.poi.ss.usermodel.Workbook;  
import org.apache.poi.xssf.usermodel.XSSFWorkbook;  
  
/** 
 *  
 *  
 */  
  
public class ImportExcelUtil  {  
  
    /** 总行数 */  
  
    private int totalRows = 0;  
  
    /** 总列数 */  
  
    private int totalCells = 0;  
  
    /** 错误信息 */  
  
    private String errorInfo;  
  
    /** 构造方法 */  
  
    public ImportExcelUtil(){}  
  
    /** 
     * 得到总行数 
     * @返回值：int 
     */  
  
    public int getTotalRows(){  
        return totalRows;  
    }  
  
    /** 
     * @描述：得到总列数 
     * @返回值：int 
     */  
  
    public int getTotalCells(){  
        return totalCells;  
    }  
  
    /** 
     * @描述：得到错误信息 
     * @返回值：String 
     */  
  
    public String getErrorInfo(){  
        return errorInfo;  
    }  
  
  
    /** 
     * @描述：根据流读取Excel文件 
     * @参数：@param inputStream 
     * @参数：@param isExcel2003 
     * @返回值：List 
     */  
  
    public List<List<String>> read(InputStream inputStream, boolean isExcel2003){  
        List<List<String>> dataLst = null;  
  
        try{  
            /** 根据版本选择创建Workbook的方式 */  
            Workbook wb = null;  
            if (isExcel2003){  
                wb = new HSSFWorkbook(inputStream);  
            }else{  
                wb = new XSSFWorkbook(inputStream);  
            }  
            dataLst = read(wb);  
        }  
        catch (IOException e){  
            e.printStackTrace();  
        }  
        return dataLst;  
    }  
  
    /** 
     * @描述：读取数据 
     * @参数：@param Workbook 
     * @返回值：List<List<String>> 
     */  
  
    private List<List<String>> read(Workbook wb){  
        List<List<String>> dataLst = new ArrayList<List<String>>();  
        /** 得到第一个shell */  
        Sheet sheet = wb.getSheetAt(0);  
        /** 得到Excel的行数 */  
        this.totalRows = sheet.getPhysicalNumberOfRows();  
        /** 得到Excel的列数 */  
        if (this.totalRows >= 1 && sheet.getRow(0) != null){  
            this.totalCells = sheet.getRow(0).getPhysicalNumberOfCells();  
        }  
        /** 循环Excel的行 */  
        for (int r = 0; r < this.totalRows; r++){  
            Row row = sheet.getRow(r);  
            if (row == null){  
                continue;  
            }  
            List<String> rowLst = new ArrayList<String>();  
            /** 循环Excel的列 */  
            for (int c = 0; c < this.getTotalCells(); c++){  
                Cell cell = row.getCell(c);  
                String cellValue = "";  
                if (null != cell){  
                    // 以下是判断数据的类型  
                    switch (cell.getCellType()){  
                    case HSSFCell.CELL_TYPE_NUMERIC: // 数字  
                        cellValue = cell.getNumericCellValue() + "";  
                        break;  
                    case HSSFCell.CELL_TYPE_STRING: // 字符串  
                        cellValue = cell.getStringCellValue();  
                        break;  
                    case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean  
                        cellValue = cell.getBooleanCellValue() + "";  
                        break;  
                    case HSSFCell.CELL_TYPE_FORMULA: // 公式  
                        cellValue = cell.getCellFormula() + "";  
                        break;  
                    case HSSFCell.CELL_TYPE_BLANK: // 空值  
                        cellValue = "";  
                        break;  
                    case HSSFCell.CELL_TYPE_ERROR: // 故障  
                        cellValue = "非法字符";  
                        break;  
                    default:  
                        cellValue = "未知类型";  
                        break;  
                    }  
                }  
                rowLst.add(cellValue);  
            }  
            /** 保存第r行的第c列 */  
            dataLst.add(rowLst);  
        }  
        return dataLst;  
    }  
  
}  
  
