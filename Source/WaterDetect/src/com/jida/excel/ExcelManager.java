package com.jida.excel;

import android.content.Context;
import android.util.Log;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenyuye on 17/12/4.
 */

public class ExcelManager {

    private static final String TAG = ExcelManager.class.getSimpleName();

    private static ExcelManager mInstance = null;


    public static ExcelManager getInstance(){
        if(mInstance == null)
            mInstance = new ExcelManager();
        return mInstance;
    }


    String[] str = new String[]{"one","two","three","four","five","six","seven","eight","nine","ten"};
    String filename = "/mnt/sdcard/textExcel.xls";

    public void exportExcelFile(Context context){
        Log.d(TAG, "exportExcelFile: executed");
        int size = 10;
        Workbook wb = new HSSFWorkbook();
        Sheet sh = wb.createSheet();

        for(int rownum=0;rownum<size;rownum++){
            Row row = sh.createRow(rownum);
            for(int cellnum=0;cellnum<10;cellnum++){
                Cell cell = row.createCell(cellnum);
                cell.setCellValue(str[cellnum]);
            }
        }

        try {
            FileOutputStream fos = new FileOutputStream(filename);
            wb.write(fos);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void importExcelFile(Context context, String filepath) throws Exception{

        String fileType = filepath.substring(filepath.lastIndexOf(".") + 1, filepath.length());
        InputStream is = null;
        Workbook wb = null;
        try {
            is = new FileInputStream(filepath);

            if (fileType.equals("xls")) {
                wb = new HSSFWorkbook(is);
//            } else if (fileType.equals("xlsx")) {
//                wb = new XSSFWorkbook(is);
            } else {
                throw new Exception("读取的不是excel文件");
            }

            List<List<Map<String, String>>> result = new ArrayList<List<Map<String,String>>>();//对应excel文件

            int sheetSize = wb.getNumberOfSheets();
            for (int i = 0; i < sheetSize; i++){//遍历sheet页
                Sheet sheet = wb.getSheetAt(i);
                List<Map<String, String>> sheetList = new ArrayList<Map<String, String>>();//对应sheet页

                List<String> titles = new ArrayList<String>();//放置所有的标题

                int rowSize = sheet.getLastRowNum() + 1;
                for (int j = 0; j < rowSize; j++) {//遍历行
                    Row row = sheet.getRow(j);
                    if (row == null) {//略过空行
                        continue;
                    }
                    int cellSize = row.getLastCellNum();//行中有多少个单元格，也就是有多少列
                    if (j == 0) {//第一行是标题行
                        for (int k = 0; k < cellSize; k++) {
                            Cell cell = row.getCell(k);
                            titles.add(cell.toString());
                        }
                    } else {//其他行是数据行
                        Map<String, String> rowMap = new HashMap<String, String>();//对应一个数据行
                        for (int k = 0; k < titles.size(); k++) {
                            Cell cell = row.getCell(k);
                            String key = titles.get(k);
                            String value = null;
                            if (cell != null) {
                                value = cell.toString();
                            }
                            rowMap.put(key, value);
                        }
                        sheetList.add(rowMap);
                    }
                }
                result.add(sheetList);
            }
        } catch (FileNotFoundException e) {
            throw e;
        } finally {
            if (wb != null) {
                wb.close();
            }
            if (is != null) {
                is.close();
            }
        }
    }

}
