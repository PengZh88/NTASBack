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
package me.zhengjie.carbinet.service.impl;

import me.zhengjie.carbinet.domain.NtsCarbinet;
import me.zhengjie.carbinet.domain.vo.MachineroomCarbinetVo;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.carbinet.repository.NtsCarbinetRepository;
import me.zhengjie.carbinet.service.NtsCarbinetService;
import me.zhengjie.carbinet.service.dto.NtsCarbinetDto;
import me.zhengjie.carbinet.service.dto.NtsCarbinetQueryCriteria;
import me.zhengjie.carbinet.service.mapstruct.NtsCarbinetMapper;
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
* @date 2020-08-18
**/
@Service
@RequiredArgsConstructor
public class NtsCarbinetServiceImpl implements NtsCarbinetService {

    private final NtsCarbinetRepository ntsCarbinetRepository;
    private final NtsCarbinetMapper ntsCarbinetMapper;

    @Override
    public Map<String,Object> queryAll(NtsCarbinetQueryCriteria criteria, Pageable pageable){
        Page<NtsCarbinet> page = ntsCarbinetRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(ntsCarbinetMapper::toDto));
    }

    @Override
    public List<NtsCarbinetDto> queryAll(NtsCarbinetQueryCriteria criteria){
        return ntsCarbinetMapper.toDto(ntsCarbinetRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public NtsCarbinetDto findById(Long id) {
        NtsCarbinet ntsCarbinet = ntsCarbinetRepository.findById(id).orElseGet(NtsCarbinet::new);
        ValidationUtil.isNull(ntsCarbinet.getId(),"NtsCarbinet","id",id);
        return ntsCarbinetMapper.toDto(ntsCarbinet);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public NtsCarbinetDto create(NtsCarbinet resources) {
        return ntsCarbinetMapper.toDto(ntsCarbinetRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(NtsCarbinet resources) {
        NtsCarbinet ntsCarbinet = ntsCarbinetRepository.findById(resources.getId()).orElseGet(NtsCarbinet::new);
        ValidationUtil.isNull( ntsCarbinet.getId(),"NtsCarbinet","id",resources.getId());
        ntsCarbinet.copy(resources);
        ntsCarbinetRepository.save(ntsCarbinet);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            ntsCarbinetRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<NtsCarbinetDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (NtsCarbinetDto ntsCarbinet : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("机柜编号", ntsCarbinet.getJgNum());
            map.put("机柜名称", ntsCarbinet.getJgName());
            map.put("备注", ntsCarbinet.getJgRemark());
            map.put("机房主键", ntsCarbinet.getMachineroom().getId());
            map.put("创建者", ntsCarbinet.getCreateBy());
            map.put("更新者", ntsCarbinet.getUpdatedBy());
            map.put("创建日期", ntsCarbinet.getCreateTime());
            map.put("更新日期", ntsCarbinet.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}