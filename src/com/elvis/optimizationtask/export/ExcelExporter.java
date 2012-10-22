package com.elvis.optimizationtask.export;

import com.elvis.optimizationtask.algorithm.maxcut.MaxCut;
import com.elvis.optimizationtask.export.plugins.DataHandler;
import com.elvis.optimizationtask.export.plugins.ExcelAVGTimeDataHandler;
import com.elvis.optimizationtask.export.plugins.ExcelMaxTimeDataHandler;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * Created by User: el
 * Date: 08.10.12
 * Time: 0:24
 */
public class ExcelExporter {
    private List<MaxCut> maxCuts;

    public ExcelExporter(List<MaxCut> list) {
        maxCuts = list;
    }

    static final int countColumn = 2;

    public void writeData(OutputStream outputStream) {
        Workbook wb = new HSSFWorkbook();
        writeDataToSheet(wb.createSheet());
        pluginsStart(wb);
        writeToFile(outputStream, wb);
    }

    private List<DataHandler> handlerList = new ArrayList<DataHandler>() {
        {
            add(new ExcelAVGTimeDataHandler());
            add(new ExcelMaxTimeDataHandler());
        }
    };

    void pluginsStart(Workbook wb) {
        for (DataHandler dataHandler : handlerList) {
            dataHandler.setMaxCuts(maxCuts);
            dataHandler.setWorkbook(wb);
            dataHandler.setAdditionInfo(additionalData);
            dataHandler.start();
        }
    }


    private void writeDataToSheet(Sheet sheet) {
        Collections.sort(maxCuts, new Comparator<MaxCut>() {
            @Override
            public int compare(MaxCut o1, MaxCut o2) {
                return o1.getMask().length - o2.getMask().length;
            }
        });

        Row headerRow = sheet.createRow(0);

        Map<String, DataForWriting> mapID_Data = new HashMap<String, DataForWriting>();
        Map<Integer, Row> r = new HashMap<Integer, Row>();

        int algIndex = 0;
        int lastRow = 1;
        for (MaxCut maxCut : maxCuts) {
            DataForWriting dataForWriting = mapID_Data.get(maxCut.getID());
            if (dataForWriting == null) {
                mapID_Data.put(maxCut.getID(), dataForWriting = new DataForWriting(algIndex++, 0));
                headerRow.createCell(dataForWriting.algorithmIndex * countColumn + 1).setCellValue(maxCut.getHumanID());
            }
            dataForWriting.lastRow++;
            Row row = r.get(dataForWriting.lastRow);
            if (row == null) {
                r.put(dataForWriting.lastRow, row = sheet.createRow(dataForWriting.lastRow));
                row.createCell(0).setCellValue(maxCut.getMask().length);
            }
            row.createCell(dataForWriting.algorithmIndex * countColumn + 1).setCellValue(maxCut.getTimeExec());
            row.createCell(dataForWriting.algorithmIndex * countColumn + 2).setCellValue(maxCut.getMaxCut());

            if (additionalData.size() == 0 || additionalData.getLast().nodeCount != maxCut.getMask().length) {
                if (additionalData.size() != 0) {
                    additionalData.getLast().rowEnd = dataForWriting.lastRow;
                }
                AdditionalInfo data = new AdditionalInfo();
                data.nodeCount = maxCut.getMask().length;
                data.rowStart = dataForWriting.lastRow + 1;
                additionalData.add(data);
            }
            lastRow = dataForWriting.lastRow;
        }
        additionalData.getLast().rowEnd = lastRow + 1;
    }

    private LinkedList<AdditionalInfo> additionalData = new LinkedList<AdditionalInfo>();


    private void writeToFile(OutputStream outputStream, Workbook wb) {
        try {
            wb.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
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

