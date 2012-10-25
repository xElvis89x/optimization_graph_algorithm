package com.elvis.optimizationtask.export.plugins;

import com.elvis.optimizationtask.algorithm.maxcut.MaxCut;
import com.elvis.optimizationtask.export.AdditionColInfo;
import com.elvis.optimizationtask.export.AdditionalRowInfo;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

/**
 * Created by User: el
 * Date: 18.10.12
 * Time: 10:23
 */
public interface DataHandler {
    public void start();

    public void setWorkbook(Workbook workbook);

    public void setMaxCuts(List<MaxCut> maxCuts);

    public void setAdditionRowInfo(List<AdditionalRowInfo> additionRowInfo);

    public void setAdditionColInfo(AdditionColInfo additionColInfo);

}
