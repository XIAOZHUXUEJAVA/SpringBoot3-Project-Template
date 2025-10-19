package com.zhu.domain.vo.uservo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 用户月度统计VO
 * 用于返回按月统计的用户注册数据
 *
 * @author xiaozhu
 */
@Data
@Schema(description = "用户月度统计视图对象")
public class UserMonthStatisticsVO {

    @Schema(description = "月份", example = "2024-01")
    private String month;

    @Schema(description = "该月注册用户数", example = "120")
    private Long count;

}
