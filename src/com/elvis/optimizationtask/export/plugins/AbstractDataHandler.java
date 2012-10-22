package com.elvis.optimizationtask.export.plugins;

import com.elvis.optimizationtask.algorithm.maxcut.MaxCut;
import com.elvis.optimizationtask.export.AdditionalInfo;
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
    protected List<AdditionalInfo> additionalInfos;

    public void setWorkbook(Workbook workbook) {
        this.workbook = workbook;
    }

    public void setMaxCuts(List<MaxCut> maxCuts) {
        this.maxCuts = maxCuts;
    }

    @Override
    public void setAdditionInfo(List<AdditionalInfo> additionInfo) {
        additionalInfos = additionInfo;
    }
}
