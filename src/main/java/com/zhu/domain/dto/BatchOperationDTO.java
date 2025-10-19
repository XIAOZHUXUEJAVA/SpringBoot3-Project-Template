package com.zhu.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 * 批量操作DTO
 * 用于接收批量操作的请求参数
 *
 * @author xiaozhu
 */
@Data
@Schema(description = "批量操作请求对象")
public class BatchOperationDTO {

    @NotEmpty(message = "用户ID列表不能为空")
    @Schema(description = "用户ID列表", example = "[1, 2, 3]")
    private List<Long> ids;

    @Schema(description = "操作类型（用于批量更新状态等）", example = "0")
    private String operationType;

    @Schema(description = "操作人ID", example = "1")
    private Long operatorId;

}
