package com.elvis.optimizationtask.export;

import com.elvis.optimizationtask.algorithm.maxcut.MaxCut;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by User: el
 * Date: 08.10.12
 * Time: 0:24
 */
public class ExcelExporter {
    List<MaxCut> maxCuts;

    public ExcelExporter(List<MaxCut> list) {
        maxCuts = list;
    }

    public void writeData(OutputStream outputStream) {
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet();


        Row headerRow = sheet.createRow(0);

        Map<String, DataForWriting> mapID_Data = new HashMap<String, DataForWriting>();
        Map<Integer, Row> r = new HashMap<Integer, Row>();

        int algIndex = 0;
        for (MaxCut maxCut : maxCuts) {
            DataForWriting dataForWriting = mapID_Data.get(maxCut.getID());
            if (dataForWriting == null) {
                mapID_Data.put(maxCut.getID(), dataForWriting = new DataForWriting(algIndex++, 0));
                headerRow.createCell(dataForWriting.algorithmIndex * 2 + 1).setCellValue(maxCut.getHumanID());
            }
            dataForWriting.lastRow++;
            Row row = r.get(dataForWriting.lastRow);
            if (row == null) {
                r.put(dataForWriting.lastRow, row = sheet.createRow(dataForWriting.lastRow));
                row.createCell(0).setCellValue(maxCut.getMask().length);

            }
            row.createCell(dataForWriting.algorithmIndex * 2 + 1).setCellValue(maxCut.getTimeExec());
            row.createCell(dataForWriting.algorithmIndex * 2 + 2).setCellValue(maxCut.getMaxCut());
        }
        try {
            wb.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class DataForWriting {

        private DataForWriting(int algorithmIndex, int lastRow) {
            this.algorithmIndex = algorithmIndex;
            this.lastRow = lastRow;
        }

        public int algorithmIndex;
        public int lastRow;
    }


}
