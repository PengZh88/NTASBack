/*
*  Copyright 2019-2020 Zheng Jie
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*  http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*/
package me.zhengjie.building.service.impl;

import me.zhengjie.building.domain.NtsBuilding;
import me.zhengjie.building.repository.NtsBuildingRepository;
import me.zhengjie.building.service.NtsBuildingService;
import me.zhengjie.building.service.dto.NtsBuildingDto;
import me.zhengjie.building.service.dto.NtsBuildingQueryCriteria;
import me.zhengjie.exception.EntityExistException;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.building.service.mapstruct.NtsBuildingMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
* @website https://el-admin.vip
* @description 服务实现
* @author Peng Zhan
* @date 2020-08-05
**/
@Service
@RequiredArgsConstructor
public class NtsBuildingServiceImpl implements NtsBuildingService {

    private final NtsBuildingRepository ntsBuildingRepository;
    private final NtsBuildingMapper ntsBuildingMapper;

    @Override
    public Map<String,Object> queryAll(NtsBuildingQueryCriteria criteria, Pageable pageable){
        Page<NtsBuilding> page = ntsBuildingRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(ntsBuildingMapper::toDto));
    }

    @Override
    public List<NtsBuildingDto> queryAll(NtsBuildingQueryCriteria criteria){
        return ntsBuildingMapper.toDto(ntsBuildingRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public NtsBuildingDto findById(Long buildingId) {
        NtsBuilding ntsBuilding = ntsBuildingRepository.findById(buildingId).orElseGet(NtsBuilding::new);
        ValidationUtil.isNull(ntsBuilding.getId(),"NtsBuilding","buildingId",buildingId);
        return ntsBuildingMapper.toDto(ntsBuilding);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public NtsBuildingDto create(NtsBuilding resources) {
        if(ntsBuildingRepository.findByBuildingName(resources.getBuildingName()) != null){
            throw new EntityExistException(NtsBuilding.class,"building_name",resources.getBuildingName());
        }
        return ntsBuildingMapper.toDto(ntsBuildingRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(NtsBuilding resources) {
        NtsBuilding ntsBuilding = ntsBuildingRepository.findById(resources.getId()).orElseGet(NtsBuilding::new);
        ValidationUtil.isNull( ntsBuilding.getId(),"NtsBuilding","id",resources.getId());
        NtsBuilding ntsBuilding1 = null;
        ntsBuilding1 = ntsBuildingRepository.findByBuildingName(resources.getBuildingName());
        if(ntsBuilding1 != null && !ntsBuilding1.getId().equals(ntsBuilding.getId())){
            throw new EntityExistException(NtsBuilding.class,"building_name",resources.getBuildingName());
        }
        ntsBuilding.copy(resources);
        ntsBuildingRepository.save(ntsBuilding);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long buildingId : ids) {
            ntsBuildingRepository.deleteById(buildingId);
        }
    }

    @Override
    public void download(List<NtsBuildingDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (NtsBuildingDto ntsBuilding : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("楼宇名称", ntsBuilding.getBuildingName());
            map.put("楼宇编号", ntsBuilding.getBuildingNum());
            map.put("地址", ntsBuilding.getBuildingAddress());
            map.put("邮编", ntsBuilding.getBuildingPostal());
            map.put("联系电话", ntsBuilding.getBuildingContact());
            map.put("创建者", ntsBuilding.getCreateBy());
            map.put("更新者", ntsBuilding.getUpdatedBy());
            map.put("创建时间", ntsBuilding.getCreateTime());
            map.put("更新时间", ntsBuilding.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}