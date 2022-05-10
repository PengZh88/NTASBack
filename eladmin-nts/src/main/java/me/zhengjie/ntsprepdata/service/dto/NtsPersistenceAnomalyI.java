package me.zhengjie.ntsprepdata.service.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

public interface NtsPersistenceAnomalyI {
    Timestamp getPaDate();
    BigDecimal getPaSumValue();
}
