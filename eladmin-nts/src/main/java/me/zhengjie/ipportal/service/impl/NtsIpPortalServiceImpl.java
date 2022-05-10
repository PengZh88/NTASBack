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
package me.zhengjie.ipportal.service.impl;

import me.zhengjie.ipportal.domain.NtsIpPortal;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.ipportal.repository.NtsIpPortalRepository;
import me.zhengjie.ipportal.service.NtsIpPortalService;
import me.zhengjie.ipportal.service.dto.NtsIpPortalDto;
import me.zhengjie.ipportal.service.dto.NtsIpPortalQueryCriteria;
import me.zhengjie.ipportal.service.mapstruct.NtsIpPortalMapper;
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
* @date 2021-01-07
**/
@Service
@RequiredArgsConstructor
public class NtsIpPortalServiceImpl implements NtsIpPortalService {

    private final NtsIpPortalRepository ntsIpPortalRepository;
    private final NtsIpPortalMapper ntsIpPortalMapper;

    @Override
    public Map<String,Object> queryAll(NtsIpPortalQueryCriteria criteria, Pageable pageable){
        Page<NtsIpPortal> page = ntsIpPortalRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(ntsIpPortalMapper::toDto));
    }

    @Override
    public List<NtsIpPortalDto> queryAll(NtsIpPortalQueryCriteria criteria){
        return ntsIpPortalMapper.toDto(ntsIpPortalRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public NtsIpPortalDto findById(Long id) {
        NtsIpPortal ntsIpPortal = ntsIpPortalRepository.findById(id).orElseGet(NtsIpPortal::new);
        ValidationUtil.isNull(ntsIpPortal.getId(),"NtsIpPortal","id",id);
        return ntsIpPortalMapper.toDto(ntsIpPortal);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public NtsIpPortalDto create(NtsIpPortal resources) {
        return ntsIpPortalMapper.toDto(ntsIpPortalRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(NtsIpPortal resources) {
        NtsIpPortal ntsIpPortal = ntsIpPortalRepository.findById(resources.getId()).orElseGet(NtsIpPortal::new);
        ValidationUtil.isNull( ntsIpPortal.getId(),"NtsIpPortal","id",resources.getId());
        ntsIpPortal.copy(resources);
        ntsIpPortalRepository.save(ntsIpPortal);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            ntsIpPortalRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<NtsIpPortalDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (NtsIpPortalDto ntsIpPortal : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("协议号", ntsIpPortal.getIptnum());
            map.put("协议名称", ntsIpPortal.getIptname());
            map.put("说明", ntsIpPortal.getIptremark());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}