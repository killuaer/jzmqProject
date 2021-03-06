package com.hhh.core.util;


import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;


public class ExportExcel <T> {
    private static HSSFWorkbook wb;

    private static CellStyle titleStyle; // 标题行样式
    private static Font titleFont; // 标题行字体
    private static CellStyle dateStyle; // 日期行样式
    private static Font dateFont; // 日期行字体
    private static CellStyle headStyle; // 表头行样式
    private static Font headFont; // 表头行字体
    private static CellStyle contentStyle; // 内容行样式
    private static Font contentFont; // 内容行字体

    /**
     * 导出文件
     * 
     * @param setInfo
     * @param outputExcelFileName
     * @return
     * @throws IOException
     */
   /* public static boolean export2File(ExcelExportData setInfo,
            String outputExcelFileName) throws Exception {
        return FileUtil.write(outputExcelFileName, export2ByteArray(setInfo),
                true, true);
    }*/

    /**
     * 导出到byte数组
     * 
     * @param setInfo
     * @return
     * @throws Exception
     */
  /*  public static byte[] export2ByteArray(ExcelExportData setInfo)
            throws Exception {
        return export2Stream(setInfo).toByteArray();
    }
*/
    
public void exportExcel(LinkedHashMap<String, List<T>> dataMap, String[] titles,List<String[]> columnNames,OutputStream out) throws Exception{
		
		exportExcel(dataMap, titles, columnNames, out, "yyyy-MM-dd");
	}
    
public void exportExcel1(List<T> dataMap, String titles,String sheet,String[] columnNames,OutputStream out) throws Exception{
	
	exportExcel1(dataMap, sheet,titles, columnNames, out, "yyyy-MM-dd");
}
	private void exportExcel1(List<T> dataMap, String sheet,String title, String[] columnName,OutputStream out, String pattern) throws Exception {
		LinkedHashMap<String, List<T>> map = new LinkedHashMap<String, List<T>>();
		map.put(sheet, dataMap);
		List<String[]> columNames = new ArrayList<String[]>();
		columNames.add(columnName);
		String[] titles=new String[]{title};
		exportExcel(map, titles, columNames, out,pattern);
	
}

	/**
     * 导出到流
     * 
     * @param setInfo
     * @return
     * @throws Exception
     */
    public  void exportExcel(LinkedHashMap<String, List<T>> dataMap, String[] titles,List<String[]> columnNames,OutputStream out,String pattern)
            throws Exception {
        init();

       // OutputStream outputStream = new ByteArrayOutputStream();

        Set<Entry<String, List<T>>> set = dataMap.entrySet();
       // Set<Entry<String, List<?>>> set = dataMap.entrySet();
        String[] sheetNames = new String[dataMap.size()];
        int sheetNameNum = 0;
        for (Entry<String, List<T>> entry : set) {
            sheetNames[sheetNameNum] = entry.getKey();
            sheetNameNum++;
        }
        HSSFSheet[] sheets = getSheets(dataMap.size(), sheetNames);
        int sheetNum = 0;

        for (Entry<String, List<T>> entry : set) {
            // Sheet
            List<?> objs = entry.getValue();

            // 标题行
            createTableTitleRow(columnNames,titles, sheets, sheetNum);

            // 日期行
            createTableDateRow(columnNames, sheets, sheetNum);

            // 表头
            creatTableHeadRow(columnNames, sheets, sheetNum);

            // 表体
            String[] fieldNames = columnNames.get(sheetNum);

            int rowNum = 3;
            Iterator<T> it = (Iterator<T>) objs.iterator();
            while (it.hasNext()){
            	 HSSFRow contentRow = sheets[sheetNum].createRow(rowNum);
                 contentRow.setHeight((short) 300);
                 HSSFCell[] cells = getCells(contentRow, columnNames.get(sheetNum).length);
                 int cellNum = 1; // 去掉一列序号，因此从1开始
                 T t = (T) it.next();
              // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
     			Field[] fields = t.getClass().getDeclaredFields();
     			for (short i = 0; i < fields.length; i++){
     				Field field = fields[i];
    				String fieldName = field.getName();
    				String getMethodName = "get"
    						+ fieldName.substring(0, 1).toUpperCase()
    						+ fieldName.substring(1);
    				try{
    					Class tCls = t.getClass();
    					Method getMethod = tCls.getMethod(getMethodName,new Class[]{});
    					Object value = getMethod.invoke(t, new Object[]{});
    					// 判断值的类型后进行强制类型转换
    					String textValue = null;
    					if(value != null){
    					if (value instanceof Date)
    					{
    						Date date = (Date) value;
    						SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    						textValue = sdf.format(date);
    					} else{  
                            // 其它数据类型都当作字符串简单处理  
                            textValue = value.toString();  
                        }  
    					}
    					if (textValue != null)
    					{
    						Pattern p = Pattern.compile("^//d+(//.//d+)?$");
    						Matcher matcher = p.matcher(textValue);
    						if (matcher.matches())
    						{
    							// 是数字当作double处理
    							cells[cellNum].setCellValue(Double.parseDouble(textValue));
    						}else if(textValue.equals("×")){
    							HSSFRichTextString richString = new HSSFRichTextString(
    									textValue);
    							HSSFFont font3 = wb.createFont();
    							font3.setColor(HSSFColor.RED.index);
    							richString.applyFont(font3);
    							cells[cellNum].setCellValue(richString);
    						}else if(textValue.equals("√")){
    							HSSFRichTextString richString = new HSSFRichTextString(
    									textValue);
    							HSSFFont font3 = wb.createFont();
    							font3.setColor(HSSFColor.BLUE.index);
    							richString.applyFont(font3);
    							cells[cellNum].setCellValue(richString);
    						}
    					}
    					cells[cellNum].setCellValue(textValue == null ? "" : textValue
    							.toString());
    					cellNum++;
    				}catch(Exception e){
    					e.printStackTrace();
    				}
     			}
     			rowNum++;
    		}
            adjustColumnSize(sheets, sheetNum, fieldNames); // 自动调整列宽
            sheetNum++;
        }
        wb.write(out);
        out.close();
        //return outputStream;
    }



	




	/**
     * @Description: 初始化
     */
    private static void init() {
        wb = new HSSFWorkbook();

        titleFont = wb.createFont();
        titleStyle = wb.createCellStyle();
        dateStyle = wb.createCellStyle();
        dateFont = wb.createFont();
        headStyle = wb.createCellStyle();
        headFont = wb.createFont();
        contentStyle = wb.createCellStyle();
        contentFont = wb.createFont();

        initTitleCellStyle();
        initTitleFont();
        initDateCellStyle();
        initDateFont();
        initHeadCellStyle();
        initHeadFont();
        initContentCellStyle();
        initContentFont();
    }

    /**
     * @Description: 自动调整列宽
     */
    private static void adjustColumnSize(HSSFSheet[] sheets, int sheetNum,
            String[] fieldNames) {
        for (int i = 0; i < fieldNames.length + 1; i++) {
            sheets[sheetNum].autoSizeColumn(i, true);
        }
    }

    /**
     * @Description: 创建标题行(需合并单元格)
     */


    private void createTableTitleRow(List<String[]> columnNames,
			String[] titles, HSSFSheet[] sheets, int sheetNum) {
    	CellRangeAddress titleRange = new CellRangeAddress(0, 0, 0, columnNames.get(sheetNum).length);
        sheets[sheetNum].addMergedRegion(titleRange);
        HSSFRow titleRow = sheets[sheetNum].createRow(0);
        titleRow.setHeight((short) 800);
        HSSFCell titleCell = titleRow.createCell(0);
        titleCell.setCellStyle(titleStyle);
        titleCell.setCellValue(titles[sheetNum]);
	}
    
    /**
     * @Description: 创建日期行(需合并单元格)
     */

    private void createTableDateRow(List<String[]> columnNames,
			HSSFSheet[] sheets, int sheetNum) {
    	 CellRangeAddress dateRange = new CellRangeAddress(1, 1, 0, columnNames.get(sheetNum).length);
         sheets[sheetNum].addMergedRegion(dateRange);
         HSSFRow dateRow = sheets[sheetNum].createRow(1);
         dateRow.setHeight((short) 0);
         HSSFCell dateCell = dateRow.createCell(0);
         dateCell.setCellStyle(dateStyle);
         dateCell.setCellValue(new SimpleDateFormat("yyyy-MM-dd")
                 .format(new Date()));
		
	}

    
    /**
     * @Description: 创建表头行(需合并单元格)
     */
	private void creatTableHeadRow(List<String[]> columnNames,
			HSSFSheet[] sheets, int sheetNum) {
		 // 表头
        HSSFRow headRow = sheets[sheetNum].createRow(2);
        headRow.setHeight((short) 350);
        // 序号列
        HSSFCell snCell = headRow.createCell(0);
        snCell.setCellStyle(headStyle);
        snCell.setCellValue("序号");
        // 列头名称
        for (int num = 1, len = columnNames.get(sheetNum).length; num <= len; num++) {
            HSSFCell headCell = headRow.createCell(num);
            headCell.setCellStyle(headStyle);
            headCell.setCellValue(columnNames.get(sheetNum)[num - 1]);
        }
		
	}
    /**
     * @Description: 创建所有的Sheet
     */
    private static HSSFSheet[] getSheets(int num, String[] names) {
        HSSFSheet[] sheets = new HSSFSheet[num];
        for (int i = 0; i < num; i++) {
            sheets[i] = wb.createSheet(names[i]);
        }
        return sheets;
    }

    /**
     * @Description: 创建内容行的每一列(附加一列序号)
     */
    private static HSSFCell[] getCells(HSSFRow contentRow, int num) {
        HSSFCell[] cells = new HSSFCell[num + 1];

        for (int i = 0, len = cells.length; i < len; i++) {
            cells[i] = contentRow.createCell(i);
            cells[i].setCellStyle(contentStyle);
        }

        // 设置序号列值，因为出去标题行和日期行，所有-2
        cells[0].setCellValue(contentRow.getRowNum() - 2);

        return cells;
    }

    /**
     * @Description: 初始化标题行样式
     */
    private static void initTitleCellStyle() {
        titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
        titleStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        titleStyle.setFont(titleFont);
        titleStyle.setFillBackgroundColor(IndexedColors.SKY_BLUE.index);
    }

    /**
     * @Description: 初始化日期行样式
     */
    private static void initDateCellStyle() {
        dateStyle.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);
        dateStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        dateStyle.setFont(dateFont);
        dateStyle.setFillBackgroundColor(IndexedColors.SKY_BLUE.index);
    }

    /**
     * @Description: 初始化表头行样式
     */
    private static void initHeadCellStyle() {
        headStyle.setAlignment(CellStyle.ALIGN_CENTER);
        headStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        headStyle.setFont(headFont);
        headStyle.setFillBackgroundColor(IndexedColors.YELLOW.index);
        headStyle.setBorderTop(CellStyle.BORDER_MEDIUM);
        headStyle.setBorderBottom(CellStyle.BORDER_THIN);
        headStyle.setBorderLeft(CellStyle.BORDER_THIN);
        headStyle.setBorderRight(CellStyle.BORDER_THIN);
        headStyle.setTopBorderColor(IndexedColors.BLUE.index);
        headStyle.setBottomBorderColor(IndexedColors.BLUE.index);
        headStyle.setLeftBorderColor(IndexedColors.BLUE.index);
        headStyle.setRightBorderColor(IndexedColors.BLUE.index);
    }

    /**
     * @Description: 初始化内容行样式
     */
    private static void initContentCellStyle() {
        contentStyle.setAlignment(CellStyle.ALIGN_CENTER);
        contentStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        contentStyle.setFont(contentFont);
        contentStyle.setBorderTop(CellStyle.BORDER_THIN);
        contentStyle.setBorderBottom(CellStyle.BORDER_THIN);
        contentStyle.setBorderLeft(CellStyle.BORDER_THIN);
        contentStyle.setBorderRight(CellStyle.BORDER_THIN);
        contentStyle.setTopBorderColor(IndexedColors.BLUE.index);
        contentStyle.setBottomBorderColor(IndexedColors.BLUE.index);
        contentStyle.setLeftBorderColor(IndexedColors.BLUE.index);
        contentStyle.setRightBorderColor(IndexedColors.BLUE.index);
        contentStyle.setWrapText(true); // 字段换行
    }

    /**
     * @Description: 初始化标题行字体
     */
    private static void initTitleFont() {
        titleFont.setFontName("华文楷体");
        titleFont.setFontHeightInPoints((short) 18);
        titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        titleFont.setCharSet(Font.DEFAULT_CHARSET);
        titleFont.setColor(IndexedColors.BLUE_GREY.index);
    }

    /**
     * @Description: 初始化日期行字体
     */
    private static void initDateFont() {
        dateFont.setFontName("隶书");
        dateFont.setFontHeightInPoints((short) 10);
        dateFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        dateFont.setCharSet(Font.DEFAULT_CHARSET);
        dateFont.setColor(IndexedColors.BLUE_GREY.index);
    }

    /**
     * @Description: 初始化表头行字体
     */
    private static void initHeadFont() {
        headFont.setFontName("宋体");
        headFont.setFontHeightInPoints((short) 10);
        headFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        headFont.setCharSet(Font.DEFAULT_CHARSET);
        headFont.setColor(IndexedColors.BLUE_GREY.index);
    }

    /**
     * @Description: 初始化内容行字体
     */
    private static void initContentFont() {
        contentFont.setFontName("宋体");
        contentFont.setFontHeightInPoints((short) 10);
        contentFont.setBoldweight(Font.BOLDWEIGHT_NORMAL);
        contentFont.setCharSet(Font.DEFAULT_CHARSET);
        contentFont.setColor(IndexedColors.GREY_80_PERCENT.index);
    }

    /**
     * Excel导出数据类
     * 
     * @author zgm
     *
     */
    public static class ExcelExportData {
        /**
         * 导出数据 key:String 表示每个Sheet的名称 value:List<?> 表示每个Sheet里的所有数据行
         */
        private LinkedHashMap<String, List<?>> dataMap;

        /**
         * 每个Sheet里的顶部大标题
         */
        private String[] titles;

        /**
         * 单个sheet里的数据列标题
         */
        private List<String[]> columnNames;

        /**
         * 单个sheet里每行数据的列对应的对象属性名称
         */
        private List<String[]> fieldNames;

        public List<String[]> getFieldNames() {
            return fieldNames;
        }

        public void setFieldNames(List<String[]> fieldNames) {
            this.fieldNames = fieldNames;
        }

        public String[] getTitles() {
            return titles;
        }

        public void setTitles(String[] titles) {
            this.titles = titles;
        }

        public List<String[]> getColumnNames() {
            return columnNames;
        }

        public void setColumnNames(List<String[]> columnNames) {
            this.columnNames = columnNames;
        }

        public LinkedHashMap<String, List<?>> getDataMap() {
            return dataMap;
        }

        public void setDataMap(LinkedHashMap<String, List<?>> dataMap) {
            this.dataMap = dataMap;
        }

    }
}