package me.zhengjie.machineroom.service.mapstruct;

import me.zhengjie.base.BaseMapper;
import me.zhengjie.machineroom.domain.NtsMachineroom;
import me.zhengjie.machineroom.service.dto.NtsSmallMachineroomDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NtsSmallMachineroomMapper extends BaseMapper<NtsSmallMachineroomDto, NtsMachineroom> {
}
