package com.zhu.domain.vo.uservo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 用户类型统计VO
 * 用于返回各类型用户的统计信息
 *
 * @author xiaozhu
 */
@Data
@Schema(description = "用户类型统计视图对象")
public class UserTypeStatisticsVO {

    @Schema(description = "用户类型（0普通用户，1管理员）", example = "0")
    private String userType;

    @Schema(description = "用户类型描述", example = "普通用户")
    private String userTypeDesc;

    @Schema(description = "该类型用户总数", example = "990")
    private Long count;

    @Schema(description = "该类型正常状态用户数", example = "950")
    private Long activeCount;

    @Schema(description = "该类型停用状态用户数", example = "40")
    private Long inactiveCount;

}
