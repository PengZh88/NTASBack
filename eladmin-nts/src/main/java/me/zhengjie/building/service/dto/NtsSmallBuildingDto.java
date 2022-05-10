package me.zhengjie.building.service.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class NtsSmallBuildingDto implements Serializable {
    /** 主键 */
    private Long id;

    /** 楼宇名称 */
    private String buildingName;
}
