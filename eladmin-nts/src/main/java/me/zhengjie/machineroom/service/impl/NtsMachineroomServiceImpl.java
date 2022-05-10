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
package me.zhengjie.machineroom.service.impl;

import me.zhengjie.building.service.NtsBuildingService;
import me.zhengjie.building.service.dto.NtsBuildingDto;
import me.zhengjie.building.service.dto.NtsBuildingQueryCriteria;
import me.zhengjie.machineroom.domain.NtsMachineroom;
import me.zhengjie.exception.EntityExistException;
import me.zhengjie.machineroom.domain.vo.BuildingMachineroomVo;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.machineroom.repository.NtsMachineroomRepository;
import me.zhengjie.machineroom.service.NtsMachineroomService;
import me.zhengjie.machineroom.service.dto.NtsMachineroomDto;
import me.zhengjie.machineroom.service.dto.NtsMachineroomQueryCriteria;
import me.zhengjie.machineroom.service.mapstruct.NtsMachineroomMapper;
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
 * @author Peng Zhan
 * @website https://el-admin.vip
 * @description 服务实现
 * @date 2020-08-06
 **/
@Service
@RequiredArgsConstructor
public class NtsMachineroomServiceImpl implements NtsMachineroomService {

    private final NtsMachineroomRepository ntsMachineroomRepository;
    private final NtsMachineroomMapper ntsMachineroomMapper;
    private final NtsBuildingService ntsBuildingService;

    @Override
    public Map<String, Object> queryAll(NtsMachineroomQueryCriteria criteria, Pageable pageable) {
        Page<NtsMachineroom> page = ntsMachineroomRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
        return PageUtil.toPage(page.map(ntsMachineroomMapper::toDto));
    }

    @Override
    public List<NtsMachineroomDto> queryAll(NtsMachineroomQueryCriteria criteria) {
        return ntsMachineroomMapper.toDto(ntsMachineroomRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    private List<BuildingMachineroomVo> findBMVoByBuildingIds(Long buildingId) {
        List<NtsMachineroomDto> dtos = ntsMachineroomMapper.toDto(ntsMachineroomRepository.findByBuildingId(buildingId));
        List<BuildingMachineroomVo> bmvos = new ArrayList<BuildingMachineroomVo>();
        if (dtos != null && !dtos.isEmpty()) {
            for(NtsMachineroomDto dto : dtos) {
                BuildingMachineroomVo bmv = new BuildingMachineroomVo();
                bmv.setBmId("Machineroom_" + dto.getId());
                bmv.setName(dto.getRoomName());
                bmvos.add(bmv);
            }
        }
        return bmvos;
    }

    @Override
    public List<BuildingMachineroomVo> queryBMVo() {
        List<NtsBuildingDto> bdtos = ntsBuildingService.queryAll(new NtsBuildingQueryCriteria());
        List<BuildingMachineroomVo> bmvos = new ArrayList<BuildingMachineroomVo>();
        if (bdtos != null && !bdtos.isEmpty()) {
            for (NtsBuildingDto nbd : bdtos) {
                BuildingMachineroomVo bmv = new BuildingMachineroomVo();
                bmv.setName(nbd.getBuildingName());
                bmv.setBmId("Building_" + nbd.getId());
                bmv.setChildren(this.findBMVoByBuildingIds(nbd.getId()));
                bmvos.add(bmv);
            }
        }
        return bmvos;
    }

    @Override
    @Transactional
    public NtsMachineroomDto findById(Long id) {
        NtsMachineroom ntsMachineroom = ntsMachineroomRepository.findById(id).orElseGet(NtsMachineroom::new);
        ValidationUtil.isNull(ntsMachineroom.getId(), "NtsMachineroom", "id", id);
        return ntsMachineroomMapper.toDto(ntsMachineroom);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public NtsMachineroomDto create(NtsMachineroom resources) {
        if (ntsMachineroomRepository.findByRoomName(resources.getRoomName()) != null) {
            throw new EntityExistException(NtsMachineroom.class, "room_name", resources.getRoomName());
        }
        return ntsMachineroomMapper.toDto(ntsMachineroomRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(NtsMachineroom resources) {
        NtsMachineroom ntsMachineroom = ntsMachineroomRepository.findById(resources.getId()).orElseGet(NtsMachineroom::new);
        ValidationUtil.isNull(ntsMachineroom.getId(), "NtsMachineroom", "id", resources.getId());
        NtsMachineroom ntsMachineroom1 = null;
        ntsMachineroom1 = ntsMachineroomRepository.findByRoomName(resources.getRoomName());
        if (ntsMachineroom1 != null && !ntsMachineroom1.getId().equals(ntsMachineroom.getId())) {
            throw new EntityExistException(NtsMachineroom.class, "room_name", resources.getRoomName());
        }
        ntsMachineroom.copy(resources);
        ntsMachineroomRepository.save(ntsMachineroom);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            ntsMachineroomRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<NtsMachineroomDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (NtsMachineroomDto ntsMachineroom : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("机房名称", ntsMachineroom.getRoomName());
            map.put("楼宇名称", ntsMachineroom.getBuilding().getBuildingName());
            map.put("楼层", ntsMachineroom.getFloorNumber());
            map.put("机柜数", ntsMachineroom.getCabinetNumber());
            map.put("管理员", ntsMachineroom.getChargePerson());
            map.put("联系电话", ntsMachineroom.getTelephone());
            map.put("备注", ntsMachineroom.getRemark());
            map.put("创建者", ntsMachineroom.getCreateBy());
            map.put("更新者", ntsMachineroom.getUpdatedBy());
            map.put("创建日期", ntsMachineroom.getCreateTime());
            map.put("更新日期", ntsMachineroom.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public Integer countByBuildingId(Long buildindId) {
        return ntsMachineroomRepository.countByBuildingId(buildindId);
    }
}