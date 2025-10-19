package com.zhu.domain.dto.userdto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/**
 * 用户更新DTO
 * 用于接收更新用户的请求参数
 *
 * @author xiaozhu
 */
@Data
@Schema(description = "用户更新请求对象")
public class UserUpdateDTO {

    @NotNull(message = "用户ID不能为空")
    @Schema(description = "用户ID", example = "1")
    private Long id;

    @Schema(description = "昵称", example = "张三")
    private String nickName;

    @Email(message = "邮箱格式不正确")
    @Schema(description = "邮箱", example = "zhangsan@example.com")
    private String email;

    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    @Schema(description = "手机号", example = "13800138000")
    private String phonenumber;

    @Pattern(regexp = "^[012]$", message = "性别只能是0(男)、1(女)、2(未知)")
    @Schema(description = "性别（0男，1女，2未知）", example = "0")
    private String sex;

    @Schema(description = "头像URL", example = "https://example.com/avatar.jpg")
    private String avatar;

    @Pattern(regexp = "^[01]$", message = "状态只能是0(正常)或1(停用)")
    @Schema(description = "账号状态（0正常，1停用）", example = "0")
    private String status;

}
