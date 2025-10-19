package com.zhu.domain.dto.userdto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/**
 * 用户密码更新DTO
 * 用于接收修改密码的请求参数
 *
 * @author xiaozhu
 */
@Data
@Schema(description = "用户密码更新请求对象")
public class UserPasswordUpdateDTO {

    @NotNull(message = "用户ID不能为空")
    @Schema(description = "用户ID", example = "1")
    private Long id;

    @NotBlank(message = "旧密码不能为空")
    @Schema(description = "旧密码", example = "OldPassword123")
    private String oldPassword;

    @NotBlank(message = "新密码不能为空")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d@$!%*?&]{8,20}$",
             message = "密码必须包含大小写字母和数字，长度8-20位")
    @Schema(description = "新密码", example = "NewPassword123")
    private String newPassword;

    @NotBlank(message = "确认密码不能为空")
    @Schema(description = "确认密码", example = "NewPassword123")
    private String confirmPassword;

}
