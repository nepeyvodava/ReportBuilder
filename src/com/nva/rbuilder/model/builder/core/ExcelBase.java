package com.nva.rbuilder.model.builder.core;

import com.nva.rbuilder.utils.Assets;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;

public class ExcelBase {

    FileInputStream inputStream;
    HSSFWorkbook wb;
    HSSFCellStyle cellStyle1;
    HSSFCellStyle cellStyle2;
    HSSFCellStyle cellStyle3;
    HSSFCellStyle cellStyle4;
    protected ReportThreadData reportThreadData;

    public ExcelBase(ReportThreadData reportThreadData) {
        this.reportThreadData = reportThreadData;
        wb = new HSSFWorkbook();
        cellStyle1 = wb.createCellStyle();
        cellStyle2 = wb.createCellStyle();
        cellStyle3 = wb.createCellStyle();
        cellStyle4 = wb.createCellStyle();
        Assets.println("---------------------------", reportThreadData);
        Assets.println(reportThreadData.getGameName() + " Report Building..." , reportThreadData);
        Assets.println("---------------------------", reportThreadData);
        Assets.print("Fonts Loading...", reportThreadData);

        try {
            //Cell Style #1
            HSSFFont font1 = wb.createFont();
            font1.setFontHeightInPoints((short) 13);
            font1.setFontName("Calibri");
            font1.setItalic(true);
            font1.setBold(true);
            cellStyle1.setFont(font1);
            cellStyle1.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
//        cellStyle1.setFillForegroundColor(IndexedColors.GREEN.getIndex());
            cellStyle1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cellStyle1.setBorderBottom(BorderStyle.THIN);
            cellStyle1.setBorderTop(BorderStyle.THIN);
            cellStyle1.setBorderRight(BorderStyle.THIN);
            cellStyle1.setBorderLeft(BorderStyle.THIN);
            cellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
            cellStyle1.setAlignment(HorizontalAlignment.CENTER);

            //Cell Style #2
            HSSFFont font2 = wb.createFont();
            font2.setFontHeightInPoints((short) 13);
            font2.setFontName("Calibri");
            font2.setBold(true);
            cellStyle2.setFont(font2);
            cellStyle2.setBorderBottom(BorderStyle.THIN);
            cellStyle2.setBorderTop(BorderStyle.THIN);
            cellStyle2.setBorderRight(BorderStyle.THIN);
            cellStyle2.setBorderLeft(BorderStyle.THIN);
            cellStyle2.setVerticalAlignment(VerticalAlignment.CENTER);
            cellStyle2.setAlignment(HorizontalAlignment.CENTER);

            //Cell Style #3
            HSSFFont font3 = wb.createFont();
            font3.setFontHeightInPoints((short) 12);
            font3.setFontName("Calibri");
            cellStyle3.setFont(font3);
            cellStyle3.setWrapText(true);
            cellStyle3.setBorderBottom(BorderStyle.THIN);
            cellStyle3.setBorderTop(BorderStyle.THIN);
            cellStyle3.setBorderRight(BorderStyle.THIN);
            cellStyle3.setBorderLeft(BorderStyle.THIN);
            cellStyle3.setVerticalAlignment(VerticalAlignment.CENTER);
            cellStyle3.setAlignment(HorizontalAlignment.CENTER);

            //Cell Style #4
            HSSFFont font4 = wb.createFont();
            font4.setFontHeightInPoints((short) 13);
            font4.setFontName("Calibri");
            font4.setBold(true);
            font4.setColor(IndexedColors.RED.index);
            cellStyle4.setFont(font4);
            cellStyle4.setVerticalAlignment(VerticalAlignment.CENTER);
            cellStyle4.setAlignment(HorizontalAlignment.CENTER);
            Assets.printrep("Fonts Loading...Done!", reportThreadData);
            Assets.print("\n", reportThreadData);
        }catch (Exception ex){
            Assets.printrep("Fonts Loading...Filed!", reportThreadData);
            Assets.print("\n", reportThreadData);

        }
    }

}
