package me.zhengjie.carbinet.service.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class NtsSmallCarbinetDto  implements Serializable {
    /** 主键 */
    private Long id;

    /** 机柜名称 */
    private String jgName;
}
