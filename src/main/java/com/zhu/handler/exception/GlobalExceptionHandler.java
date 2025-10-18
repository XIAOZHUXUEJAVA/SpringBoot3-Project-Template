package com.zhu.handler.exception;

import com.zhu.enums.AppHttpCodeEnum;
import com.zhu.utils.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;


/**
 * 全局捕获异常
 *
 * @author xiaozhu
 * @date 2022年10月03日 23:40                          $
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseResult noResourceFoundExceptionHandler(NoResourceFoundException e) {
        // 忽略 favicon.ico 等静态资源请求，不打印错误日志
        String resourcePath = e.getResourcePath();
        if (resourcePath != null && (resourcePath.contains("favicon.ico") || resourcePath.endsWith(".ico"))) {
            log.debug("忽略静态资源请求: {}", resourcePath);
            return null; // 返回 null，Spring 会返回 404 状态码
        }
        // 其他资源未找到的情况记录日志
        log.warn("资源未找到: {}", resourcePath);
        return ResponseResult.errorResult(HttpStatus.NOT_FOUND.value(), "资源未找到");
    }

    @ExceptionHandler(SystemException.class)
    public ResponseResult systemExceptionHandler(SystemException e) {
        //打印异常信息
        log.error("出现了异常！ : {}", e);
        //从异常对象中获取提示信息封装返回
        return ResponseResult.errorResult(e.getCode(), e.getMsg());
    }


    @ExceptionHandler(Exception.class)
    public ResponseResult exceptionHandler(Exception e) {
        //打印异常信息
        log.error("出现了异常！ : {}", e);
        //从异常对象中获取提示信息封装返回
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(), e.getMessage());
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseResult parameterExceptionHandler(MethodArgumentNotValidException e) {
        log.error("参数出现了异常！ : {}", e);
        // 获取异常信息
        BindingResult exceptions = e.getBindingResult();
        // 判断异常中是否有错误信息，如果存在就使用异常中的消息，否则使用默认消息
        if (exceptions.hasErrors()) {
            List<ObjectError> errors = exceptions.getAllErrors();
            if (!errors.isEmpty()) {
                // 这里列出了全部错误参数，按正常逻辑，只需要第一条错误即可
                FieldError fieldError = (FieldError) errors.get(0);
                return ResponseResult.errorResult(AppHttpCodeEnum.PARAMETER_ERROR.getCode(), fieldError.getDefaultMessage());
            }
        }
        return ResponseResult.okResult();
    }

}
