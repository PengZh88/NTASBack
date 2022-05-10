package me.zhengjie.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.service.LogService;
import me.zhengjie.service.dto.LogQueryCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/otherlogs")
@Api(tags = "系统：日志管理(其他需求)")
public class OtherLogController {
    private final LogService logService;

    @GetMapping("/tasklogs")
    @ApiOperation("数据同步日志查询")
    @PreAuthorize("@el.check()")
    public ResponseEntity<Object> query(LogQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(logService.queryAll(criteria,pageable), HttpStatus.OK);
    }
}
