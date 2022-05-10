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
package me.zhengjie.anomalydetection.repository;

import me.zhengjie.anomalydetection.domain.NtsAnomalyDetectionTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.sql.Timestamp;

/**
* @website https://el-admin.vip
* @author Peng Zhan
* @date 2021-03-10
**/
public interface NtsAnomalyDetectionTaskRepository extends JpaRepository<NtsAnomalyDetectionTask, Long>, JpaSpecificationExecutor<NtsAnomalyDetectionTask> {
}