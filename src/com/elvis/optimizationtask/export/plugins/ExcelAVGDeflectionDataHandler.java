package com.elvis.optimizationtask.export.plugins;

import com.elvis.optimizationtask.algorithm.maxcut.MaxCut;
import com.elvis.optimizationtask.export.AdditionalInfo;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * Created by User: el
 * Date: 18.10.12
 * Time: 10:57
 */
public class ExcelAVGDeflectionDataHandler extends AbstractDataHandler {
    public void start() {
        Sheet sheet2 = workbook.createSheet("AVGTime");
        int methodsCount = createHeader(sheet2);

        int rowIndex = 2;
        for (AdditionalInfo addInfo : additionalInfos) {
            Row row = sheet2.createRow(rowIndex);

            row.createCell(0).setCellFormula("AVERAGE(Sheet0!A" + addInfo.rowStart + ":A" + addInfo.rowEnd + ")");
            for (int i = 1; i < methodsCount; i++) {
                char col = (char) ('A' + (char) i * 2 - 1);
                row.createCell(i).setCellFormula("AVERAGE(Sheet0!" + col + addInfo.rowStart + ":" + col + addInfo.rowEnd + ")");
            }
            rowIndex++;
        }
    }

    private int createHeader(Sheet sheet2) {
        Row row = sheet2.createRow(0);
        row.createCell(0).setCellValue("Среднее отклонение");
        row = sheet2.createRow(1);
        int methodsCount = 1;
        for (MaxCut maxCut : maxCuts) {
            String methodID = maxCut.getHumanID();
            if (methodID.equals(maxCuts.get(0).getHumanID()) && methodsCount > 1) {
                break;
            }
            row.createCell(methodsCount++).setCellValue(methodID);
        }
        return methodsCount;
    }
}
