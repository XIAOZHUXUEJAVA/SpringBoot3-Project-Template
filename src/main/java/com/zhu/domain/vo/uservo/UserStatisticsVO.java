package com.zhu.domain.vo.uservo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 用户统计VO
 * 用于返回用户统计信息
 *
 * @author xiaozhu
 */
@Data
@Schema(description = "用户统计信息视图对象")
public class UserStatisticsVO {

    @Schema(description = "总用户数", example = "1000")
    private Long totalUsers;

    @Schema(description = "管理员数量", example = "10")
    private Long adminCount;

    @Schema(description = "普通用户数量", example = "990")
    private Long normalCount;

    @Schema(description = "正常状态用户数", example = "950")
    private Long activeUsers;

    @Schema(description = "停用状态用户数", example = "50")
    private Long inactiveUsers;

    @Schema(description = "男性用户数", example = "600")
    private Long maleCount;

    @Schema(description = "女性用户数", example = "400")
    private Long femaleCount;

    @Schema(description = "最早注册日期", example = "2020-01-01")
    private String earliestRegisterDate;

    @Schema(description = "最近注册日期", example = "2024-12-31")
    private String latestRegisterDate;

}
