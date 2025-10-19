package com.zhu.domain.vo.uservo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * 用户详情VO
 * 用于返回用户详细信息（包含创建人等关联信息）
 *
 * @author xiaozhu
 */
@Data
@Schema(description = "用户详情视图对象")
public class UserDetailVO {

    @Schema(description = "用户ID", example = "1")
    private Long id;

    @Schema(description = "用户名", example = "zhangsan")
    private String userName;

    @Schema(description = "昵称", example = "张三")
    private String nickName;

    @Schema(description = "用户类型（0普通用户，1管理员）", example = "0")
    private String type;

    @Schema(description = "用户类型描述", example = "普通用户")
    private String typeDesc;

    @Schema(description = "账号状态（0正常，1停用）", example = "0")
    private String status;

    @Schema(description = "账号状态描述", example = "正常")
    private String statusDesc;

    @Schema(description = "邮箱", example = "zhangsan@example.com")
    private String email;

    @Schema(description = "手机号", example = "13800138000")
    private String phonenumber;

    @Schema(description = "性别（0男，1女，2未知）", example = "0")
    private String sex;

    @Schema(description = "性别描述", example = "男")
    private String sexDesc;

    @Schema(description = "头像URL", example = "https://example.com/avatar.jpg")
    private String avatar;

    @Schema(description = "创建人ID", example = "1")
    private Long createBy;

    @Schema(description = "创建人用户名", example = "admin")
    private String creatorName;

    @Schema(description = "创建人昵称", example = "管理员")
    private String creatorNickName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "创建时间", example = "2024-01-01 12:00:00")
    private Date createTime;

    @Schema(description = "更新人ID", example = "1")
    private Long updateBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "更新时间", example = "2024-01-01 12:00:00")
    private Date updateTime;

}
