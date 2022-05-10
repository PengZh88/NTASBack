package me.zhengjie.machineroom.domain.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BuildingMachineroomVo implements Serializable {
    private String bmId;
    private String name;
    private List<BuildingMachineroomVo> children;
}
