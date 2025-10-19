package com.zhu.domain.dto.userdto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * 用户添加DTO
 * 用于接收创建用户的请求参数
 *
 * @author xiaozhu
 */
@Data
@Schema(description = "用户添加请求对象")
public class UserAddDTO {

    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9_]{4,20}$", message = "用户名必须是4-20位字母、数字或下划线")
    @Schema(description = "用户名", example = "zhangsan")
    private String userName;

    @NotBlank(message = "昵称不能为空")
    @Schema(description = "昵称", example = "张三")
    private String nickName;

    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d@$!%*?&]{8,20}$",
             message = "密码必须包含大小写字母和数字，长度8-20位")
    @Schema(description = "密码", example = "Password123")
    private String password;

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

    @Schema(description = "用户类型（0普通用户，1管理员）", example = "0")
    private String type;

}
