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
package me.zhengjie.device.service.impl;

import me.zhengjie.device.domain.NtsDevice;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.device.repository.NtsDeviceRepository;
import me.zhengjie.device.service.NtsDeviceService;
import me.zhengjie.device.service.dto.NtsDeviceDto;
import me.zhengjie.device.service.dto.NtsDeviceQueryCriteria;
import me.zhengjie.device.service.mapstruct.NtsDeviceMapper;
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
* @date 2020-12-19
**/
@Service
@RequiredArgsConstructor
public class NtsDeviceServiceImpl implements NtsDeviceService {

    private final NtsDeviceRepository ntsDeviceRepository;
    private final NtsDeviceMapper ntsDeviceMapper;

    @Override
    public Map<String,Object> queryAll(NtsDeviceQueryCriteria criteria, Pageable pageable){
        Page<NtsDevice> page = ntsDeviceRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(ntsDeviceMapper::toDto));
    }

    @Override
    public List<NtsDeviceDto> queryAll(NtsDeviceQueryCriteria criteria){
        return ntsDeviceMapper.toDto(ntsDeviceRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public NtsDeviceDto findById(Long id) {
        NtsDevice ntsDevice = ntsDeviceRepository.findById(id).orElseGet(NtsDevice::new);
        ValidationUtil.isNull(ntsDevice.getId(),"NtsDevice","id",id);
        return ntsDeviceMapper.toDto(ntsDevice);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public NtsDeviceDto create(NtsDevice resources) {
        return ntsDeviceMapper.toDto(ntsDeviceRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(NtsDevice resources) {
        NtsDevice ntsDevice = ntsDeviceRepository.findById(resources.getId()).orElseGet(NtsDevice::new);
        ValidationUtil.isNull( ntsDevice.getId(),"NtsDevice","id",resources.getId());
        ntsDevice.copy(resources);
        ntsDeviceRepository.save(ntsDevice);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            ntsDeviceRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<NtsDeviceDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (NtsDeviceDto ntsDevice : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("设备名称", ntsDevice.getDevName());
            map.put("设备编号", ntsDevice.getDevNum());
            map.put("设备负责人", ntsDevice.getDevChargePerson());
            map.put("设备联系人", ntsDevice.getDevContactPerson());
            map.put("IP地址", ntsDevice.getDevIpAddr());
            map.put("MAC地址", ntsDevice.getDevMacAddr());
            map.put("设备品牌", ntsDevice.getDevBrand());
            map.put("设备型号", ntsDevice.getDevModel());
            map.put("所在机柜", ntsDevice.getCarbinet().getJgName());
            map.put("创建者", ntsDevice.getCreateBy());
            map.put("更新者", ntsDevice.getUpdatedBy());
            map.put("创建时间", ntsDevice.getCreateTime());
            map.put("更新时间", ntsDevice.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}