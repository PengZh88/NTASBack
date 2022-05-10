package me.zhengjie.ntsprepdata.repository;

import me.zhengjie.ntsprepdata.domain.NtsPrepFlowData;
import me.zhengjie.ntsprepdata.service.dto.NtsPersistenceAnomalyI;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface PreFlowDataAnomalyRepository extends JpaRepository<NtsPrepFlowData, Integer> {
    @Query(value = "select DATE_FORMAT(fd_date_time,'%Y-%m-%d') as paDate,SUM(fd_value) as paSumValue from nts_prep_flow_data where device_id=?1 and fd_protocol=?2 and fd_date_time between ?3 and ?4 group by paDate order by paDate ASC", nativeQuery = true)
    List<NtsPersistenceAnomalyI> findNtsPrepDataByDay(Long deviceId, String protocol, Timestamp startTime, Timestamp endTime);
    @Query(value = "select DATE_FORMAT(fd_date_time,'%Y-%m-%d %H:00:00') as paDate,SUM(fd_value) as paSumValue from nts_prep_flow_data where device_id=?1 and fd_protocol=?2 and fd_date_time between ?3 and ?4 group by paDate order by paDate ASC", nativeQuery = true)
    List<NtsPersistenceAnomalyI> findNtsPrepDataByHour(Long deviceId, String protocol, Timestamp startTime, Timestamp endTime);
    @Query(value = "select DATE_FORMAT(fd_date_time,'%Y-%m-%d %H:%i:00') as paDate,SUM(fd_value) as paSumValue from nts_prep_flow_data where device_id=?1 and fd_protocol=?2 and fd_date_time between ?3 and ?4 group by paDate order by paDate ASC", nativeQuery = true)
    List<NtsPersistenceAnomalyI> findNtsPrepDataByMinute(Long deviceId, String protocol, Timestamp startTime, Timestamp endTime);
    @Query(value = "select DATE_FORMAT(fd_date_time,'%Y-%m-%d %H:%i:%s') as paDate,SUM(fd_value) as paSumValue from nts_prep_flow_data where device_id=?1 and fd_protocol=?2 and fd_date_time between ?3 and ?4 group by paDate order by paDate ASC", nativeQuery = true)
    List<NtsPersistenceAnomalyI> findNtsPrepDataBySecond(Long deviceId, String protocol, Timestamp startTime, Timestamp endTime);

}
