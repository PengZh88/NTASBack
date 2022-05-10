package me.zhengjie.building.service.mapstruct;

import me.zhengjie.base.BaseMapper;
import me.zhengjie.building.domain.NtsBuilding;
import me.zhengjie.building.service.dto.NtsSmallBuildingDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NtsSmallBuildingMapper extends BaseMapper<NtsSmallBuildingDto, NtsBuilding> {

}