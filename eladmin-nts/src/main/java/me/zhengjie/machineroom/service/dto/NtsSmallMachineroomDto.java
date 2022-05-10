package me.zhengjie.machineroom.service.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class NtsSmallMachineroomDto implements Serializable {
    /** 主键 */
    private Long id;

    /** 机房名称 */
    private String roomName;
}
