package com.zhu.domain.dto.userdto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 用户查询DTO
 * 用于接收多条件查询的请求参数
 *
 * @author xiaozhu
 */
@Data
@Schema(description = "用户查询条件对象")
public class UserQueryDTO {

    @Schema(description = "用户名（模糊查询）", example = "zhang")
    private String userName;

    @Schema(description = "昵称（模糊查询）", example = "张")
    private String nickName;

    @Schema(description = "邮箱（精确查询）", example = "zhangsan@example.com")
    private String email;

    @Schema(description = "手机号（精确查询）", example = "13800138000")
    private String phonenumber;

    @Schema(description = "账号状态（0正常，1停用）", example = "0")
    private String status;

    @Schema(description = "用户类型（0普通用户，1管理员）", example = "0")
    private String type;

    @Schema(description = "性别（0男，1女，2未知）", example = "0")
    private String sex;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "创建开始时间", example = "2024-01-01")
    private Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "创建结束时间", example = "2024-12-31")
    private Date endTime;

    @Schema(description = "页码", example = "1")
    private Integer pageNum = 1;

    @Schema(description = "每页数量", example = "10")
    private Integer pageSize = 10;

}
