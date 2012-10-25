package com.elvis.optimizationtask.export.plugins;

import com.elvis.optimizationtask.algorithm.maxcut.MaxCut;
import com.elvis.optimizationtask.export.AdditionalRowInfo;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellReference;

/**
 * Created by User: el
 * Date: 18.10.12
 * Time: 10:57
 */
public class ExcelAVGDeflectionDataHandler extends AbstractDataHandler {
    public void start() {
        Sheet sheet2 = workbook.createSheet("AVGDeflection");
        int methodsCount = createHeader(sheet2);

        int rowIndex = 2;
        for (AdditionalRowInfo addRowInfo : additionalRowInfos) {
            Row row = sheet2.createRow(rowIndex);

            row.createCell(0).setCellFormula("AVERAGE(Sheet0!" + new CellReference(addRowInfo.rowStart, 0).formatAsString() +
                    ":" + new CellReference(addRowInfo.rowEnd, 0).formatAsString() + ")");
            for (int i = 1; i < methodsCount; i++) {
                String startCell = new CellReference(addRowInfo.rowStart, additionColInfo.deflectionCol + i - 1).formatAsString();
                String endCell = new CellReference(addRowInfo.rowEnd, additionColInfo.deflectionCol + i - 1).formatAsString();
                row.createCell(i).setCellFormula("AVERAGE(Sheet0!" + startCell + ":" + endCell + ")");
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
