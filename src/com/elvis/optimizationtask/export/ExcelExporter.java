package com.elvis.optimizationtask.export;

import com.elvis.optimizationtask.algorithm.maxcut.MaxCut;
import com.elvis.optimizationtask.export.plugins.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;

import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * Created by User: el
 * Date: 08.10.12
 * Time: 0:24
 */
public class ExcelExporter {
    public static final int shiftAdditionCalc = 15;
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
            add(new ExcelAVGDeflectionDataHandler());
            add(new ExcelMaxDeflectionDataHandler());
        }
    };

    void pluginsStart(Workbook wb) {
        for (DataHandler dataHandler : handlerList) {
            try {
                dataHandler.setMaxCuts(maxCuts);
                dataHandler.setWorkbook(wb);
                dataHandler.setAdditionRowInfo(additionalRowData);
                dataHandler.setAdditionColInfo(additionColInfo);
                dataHandler.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void writeDataToSheet(Sheet sheet) {
        Collections.sort(maxCuts, new Comparator<MaxCut>() {
            @Override
            public int compare(MaxCut o1, MaxCut o2) {
                return o1.getResult().length - o2.getResult().length;
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
                row.createCell(0).setCellValue(maxCut.getResult().length);
            }
            Cell timeCell = row.createCell(dataForWriting.algorithmIndex * countColumn + 1);
            timeCell.setCellValue(maxCut.getTimeExec());

            Cell cutCell = row.createCell(dataForWriting.algorithmIndex * countColumn + 2);
            cutCell.setCellValue(maxCut.getMaxCutValue());


            Cell c = row.createCell(dataForWriting.algorithmIndex + shiftAdditionCalc);
            c.setCellFormula(new CellReference(dataForWriting.lastRow, 2).formatAsString()
                    + "-"
                    + new CellReference(cutCell).formatAsString());

            if (additionalRowData.size() == 0 || additionalRowData.getLast().nodeCount != maxCut.getResult().length) {
                if (additionalRowData.size() != 0) {
                    additionalRowData.getLast().rowEnd = dataForWriting.lastRow - 1;
                }
                AdditionalRowInfo data = new AdditionalRowInfo();
                data.nodeCount = maxCut.getResult().length;
                data.rowStart = dataForWriting.lastRow;
                additionalRowData.add(data);
            }
            lastRow = dataForWriting.lastRow;
        }
        additionColInfo.deflectionCol = shiftAdditionCalc;
        additionalRowData.getLast().rowEnd = lastRow;
    }

    private LinkedList<AdditionalRowInfo> additionalRowData = new LinkedList<AdditionalRowInfo>();
    private AdditionColInfo additionColInfo = new AdditionColInfo();


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

