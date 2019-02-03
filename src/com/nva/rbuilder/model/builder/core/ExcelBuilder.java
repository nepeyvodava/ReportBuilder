package com.nva.rbuilder.model.builder.core;

//import junit.framework.Assert;

import com.nva.rbuilder.utils.Assets;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.hssf.usermodel.HSSFHyperlink;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellUtil;

import java.io.File;
import java.io.FileOutputStream;

public class ExcelBuilder extends ExcelBase {

    public ExcelBuilder(ReportThreadData reportThreadData) {super(reportThreadData);}
    int attempt = 0;
    public void buildTable() {
        Sheet sh = wb.createSheet(this.reportThreadData.getGameName());
        Assets.print("Create Table...", reportThreadData);
        //create table
        Row row;
        int sectionNum = 0;

        //create Bugs Quantity row
        row = sh.createRow(sh.getLastRowNum());
        Cell lastcell = row.createCell(0);
        lastcell.setCellValue("All Open Issues: " + this.reportThreadData.getBugQuantity());
        lastcell.setCellStyle(cellStyle4);
        CellUtil.setAlignment(lastcell, HorizontalAlignment.LEFT);
        row = sh.createRow(sh.getLastRowNum()+ 1);

        for(String section:this.reportThreadData.getSectionMap().keySet()) {
            //create section
            sectionNum++;
            //create first row
            row = sh.createRow(sh.getLastRowNum());
            for(int cellNum = 0; cellNum < this.reportThreadData.getColumnNameList().length; cellNum++){
                Cell cell = row.createCell(cellNum);
                cell.setCellValue("");
                cell.setCellStyle(cellStyle1);
                if (cellNum == 0) {CellUtil.setAlignment(cell, HorizontalAlignment.LEFT);}
            }
            //create second row
            if(!this.reportThreadData.isConferenceMode()) {
                row = sh.createRow(sh.getLastRowNum() + 1);
                for (int cellNum = 0; cellNum < this.reportThreadData.getColumnNameList().length; cellNum++) {
                    Cell cell = row.createCell(cellNum);
                    cell.setCellValue("");
                    if (cellNum == 0) {
                        cell.setCellValue(sectionNum + ". " + Assets.cut(section, 0));
                    }
                    cell.setCellStyle(cellStyle2);
                    if (cellNum == 0) {
                        CellUtil.setAlignment(cell, HorizontalAlignment.LEFT);
                    }
                }
            }
            //create items
            for (String unit : this.reportThreadData.getSectionMap().get(section)) {
                row = sh.createRow(sh.getLastRowNum() + 1);
//                for (int cellNum = 0; cellNum < Assets.getColumnNameList().length; cellNum++) {
                int cellNum = 0;
                for (String data : this.reportThreadData.getUnitMap().get(Assets.cut(section,1) + Assets.SPECIALSYMBOL + unit)) {
                    Cell cell = row.createCell(cellNum);
                    cell.setCellValue(data);
                    if ((cellNum == 0)&& (!this.reportThreadData.isConferenceMode())){cell.setCellValue("• " + data);}
                    cell.setCellStyle(cellStyle3);
                    if (cellNum == 0) {CellUtil.setAlignment(cell, HorizontalAlignment.LEFT);}
                    if ((cellNum == 2)&&(this.reportThreadData.isConferenceMode())) {CellUtil.setAlignment(cell, HorizontalAlignment.LEFT);}
                    if ((cellNum == 3)&&(!this.reportThreadData.isConferenceMode())) {CellUtil.setAlignment(cell, HorizontalAlignment.LEFT);}
                    try {
                        if (cellNum == 1) {cell.setCellValue(Float.parseFloat(data));}
                    }catch (Exception ex){}
                    cellNum++;
                }
            }
            row = sh.createRow(sh.getLastRowNum() + 1);
        }

        //add column names
        Row rowFirst = sh.createRow(1);
        for(int cellNum = 0; cellNum < this.reportThreadData.getColumnNameList().length; cellNum++){
            Cell cell = rowFirst.createCell(cellNum);
            cell.setCellValue(this.reportThreadData.getColumnNameList()[cellNum]);
            if(cellNum == 0){
                HSSFHyperlink hyperlink = wb.getCreationHelper().createHyperlink(HyperlinkType.URL);
                hyperlink.setAddress(this.reportThreadData.getIssueURL(this.reportThreadData.getIssueCode()));
                cell.setHyperlink(hyperlink);
            }
            cell.setCellStyle(cellStyle1);
            if(cellNum == 0){CellUtil.setAlignment(cell, HorizontalAlignment.LEFT);}
        }
        //set auto size for sheet
        for(int i = 0; i < this.reportThreadData.getColumnNameList().length; i++){
            sh.autoSizeColumn(i);
            if(this.reportThreadData.isConferenceMode()&& (i == 2)){sh.setColumnWidth(i, 20000);}
        }
        Assets.println("Done!", reportThreadData);

        // write file
        writeBook();

        //close sheet
        try {
            wb.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void writeBook(){
        try {
            createFolder();
            Assets.print("File writing...", reportThreadData);
            FileOutputStream out = new FileOutputStream(
                    this.reportThreadData.getGameDir() + this.reportThreadData.getReportsName() + ".xls");
            wb.write(out);
            out.close();
            Assets.printrep("File writing...Done!", reportThreadData);
            Assets.print("\n", reportThreadData);
            Assets.println("---------------------------", reportThreadData);
            Assets.println("REPORT COMPLETE!", reportThreadData);
            Assets.println("---------------------------", reportThreadData);
        }catch (Exception ex){
            if(++attempt < 3) {
                Assets.printrep("File writing...Failed!", reportThreadData);
                Assets.print("\n", reportThreadData);
                Assets.println("FILE NAME WAS CHANGED!", reportThreadData);
                this.reportThreadData.addError("Xls file is open!");
                this.reportThreadData.addError("File name was changed!");
                this.reportThreadData.setGameName("_" + this.reportThreadData.getGameName());
                this.writeBook();
            }else{
                Assets.println("Please check permissions and available disc space.", reportThreadData);
                Assets.println("---------------------------", reportThreadData);
                Assets.println("REPORT NOT COMPILED!", reportThreadData);
                Assets.println("---------------------------", reportThreadData);
                this.reportThreadData.addError("Please check permissions and available disc space.");
            }
        }
    }

    private void createFolder(){
        new File(this.reportThreadData.getGameDir()).mkdir();
    }

}
