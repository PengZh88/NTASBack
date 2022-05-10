package me.zhengjie.carbinet.service.mapstruct;

import me.zhengjie.base.BaseMapper;
import me.zhengjie.building.domain.NtsBuilding;
import me.zhengjie.building.service.dto.NtsSmallBuildingDto;
import me.zhengjie.carbinet.domain.NtsCarbinet;
import me.zhengjie.carbinet.service.dto.NtsSmallCarbinetDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NtsSmallCarbinetMapper extends BaseMapper<NtsSmallCarbinetDto, NtsCarbinet> {

}