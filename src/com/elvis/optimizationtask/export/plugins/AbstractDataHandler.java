package com.elvis.optimizationtask.export.plugins;

import com.elvis.optimizationtask.algorithm.maxcut.MaxCut;
import com.elvis.optimizationtask.export.AdditionColInfo;
import com.elvis.optimizationtask.export.AdditionalRowInfo;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

/**
 * Created by User: el
 * Date: 18.10.12
 * Time: 10:58
 */
public abstract class AbstractDataHandler implements DataHandler {

    protected Workbook workbook;
    protected List<MaxCut> maxCuts;
    protected List<AdditionalRowInfo> additionalRowInfos;
    protected AdditionColInfo additionColInfo;

    public void setWorkbook(Workbook workbook) {
        this.workbook = workbook;
    }

    public void setMaxCuts(List<MaxCut> maxCuts) {
        this.maxCuts = maxCuts;
    }

    @Override
    public void setAdditionRowInfo(List<AdditionalRowInfo> additionRowInfo) {
        additionalRowInfos = additionRowInfo;
    }

    @Override
    public void setAdditionColInfo(AdditionColInfo additionColInfo) {
        this.additionColInfo = additionColInfo;
    }
}
