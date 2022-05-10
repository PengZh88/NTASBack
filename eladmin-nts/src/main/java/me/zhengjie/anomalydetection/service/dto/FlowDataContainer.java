package me.zhengjie.anomalydetection.service.dto;

import lombok.Data;
import me.zhengjie.ntsprepdata.service.dto.NtsPrepFlowDataDto;

import java.io.Serializable;
import java.util.List;

@Data
public class FlowDataContainer implements Serializable {
    private List<NtsPrepFlowDataDto> flowDatas;

    public double[] getDoubleArray() {
        return null;
    }

    public void normalizeFlowDataValue() {

    }
}
