package com.zhu.controller;

import com.zhu.annoation.SystemLog;
import com.zhu.domain.dto.BatchOperationDTO;
import com.zhu.domain.dto.userdto.UserAddDTO;
import com.zhu.domain.dto.userdto.UserQueryDTO;
import com.zhu.domain.dto.userdto.UserUpdateDTO;
import com.zhu.service.UserService;
import com.zhu.common.result.ResponseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * 用户控制器
 *
 * 使用MyBatis Plus最佳实践：
 * 1. 简单查询使用Lambda Query（Service层实现）
 * 2. 复杂查询使用XML配置（Mapper层实现）
 * 3. 使用DTO接收请求参数，使用VO返回响应数据
 * 4. RESTful API设计规范
 *
 * @author xiaozhu
 * @date 2022年10月04日 0:23
 */
@RestController
@RequestMapping("/user")
@Tag(name = "用户接口", description = "用户管理相关接口")
public class UserController {

    @Autowired
    private UserService userService;

    // ==================== 基础CRUD接口（使用Lambda Query） ====================

    /**
     * 获取所有用户
     */
    @GetMapping("/all")
    @SystemLog(businessName = "获取所有用户")
    @Operation(summary = "获取所有用户", description = "查询所有未删除的用户列表")
    public ResponseResult getAllUsers() {
        return userService.getAllUsers();
    }

    /**
     * 根据ID获取用户
     */
    @GetMapping("/{id}")
    @SystemLog(businessName = "根据ID获取用户")
    @Operation(summary = "根据ID获取用户", description = "通过用户ID查询用户详情")
    public ResponseResult getUserById(@PathVariable("id") @Parameter(description = "用户ID") Long id) {
        return userService.getUserById(id);
    }

    /**
     * 根据用户名查询用户
     */
    @GetMapping("/username/{username}")
    @SystemLog(businessName = "根据用户名查询用户")
    @Operation(summary = "根据用户名查询用户")
    public ResponseResult getUserByUsername(@PathVariable("username") @Parameter(description = "用户名") String username) {
        return userService.getUserByUsername(username);
    }

    /**
     * 根据邮箱查询用户
     */
    @GetMapping("/email/{email}")
    @SystemLog(businessName = "根据邮箱查询用户")
    @Operation(summary = "根据邮箱查询用户")
    public ResponseResult getUserByEmail(@PathVariable("email") @Parameter(description = "邮箱") String email) {
        return userService.getUserByEmail(email);
    }

    /**
     * 分页查询用户
     */
    @GetMapping("/page")
    @SystemLog(businessName = "分页查询用户")
    @Operation(summary = "分页查询用户", description = "支持分页的用户列表查询")
    public ResponseResult getUsersByPage(
            @RequestParam(value = "pageNum", defaultValue = "1") @Parameter(description = "页码") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") @Parameter(description = "每页数量") Integer pageSize) {
        return userService.getUsersByPage(pageNum, pageSize);
    }

    /**
     * 根据状态查询用户
     */
    @GetMapping("/status/{status}")
    @SystemLog(businessName = "根据状态查询用户")
    @Operation(summary = "根据状态查询用户", description = "0-正常，1-停用")
    public ResponseResult getUsersByStatus(@PathVariable("status") @Parameter(description = "用户状态") String status) {
        return userService.getUsersByStatus(status);
    }

    /**
     * 根据类型查询用户
     */
    @GetMapping("/type/{type}")
    @SystemLog(businessName = "根据类型查询用户")
    @Operation(summary = "根据类型查询用户", description = "0-普通用户，1-管理员")
    public ResponseResult getUsersByType(@PathVariable("type") @Parameter(description = "用户类型") String type) {
        return userService.getUsersByType(type);
    }

    /**
     * 添加用户
     */
    @PostMapping("/add")
    @SystemLog(businessName = "添加用户")
    @Operation(summary = "添加用户", description = "创建新用户")
    public ResponseResult addUser(@RequestBody @Valid UserAddDTO dto) {
        return userService.addUser(dto);
    }

    /**
     * 更新用户
     */
    @PutMapping("/update")
    @SystemLog(businessName = "更新用户")
    @Operation(summary = "更新用户", description = "更新用户信息")
    public ResponseResult updateUser(@RequestBody @Valid UserUpdateDTO dto) {
        return userService.updateUser(dto);
    }

    /**
     * 删除用户（逻辑删除）
     */
    @DeleteMapping("/{id}")
    @SystemLog(businessName = "删除用户")
    @Operation(summary = "删除用户", description = "逻辑删除用户")
    public ResponseResult deleteUser(@PathVariable("id") @Parameter(description = "用户ID") Long id) {
        return userService.deleteUser(id);
    }

    /**
     * 批量删除用户
     */
    @DeleteMapping("/batch")
    @SystemLog(businessName = "批量删除用户")
    @Operation(summary = "批量删除用户", description = "批量逻辑删除用户")
    public ResponseResult batchDeleteUsers(@RequestBody @Valid BatchOperationDTO dto) {
        return userService.batchDeleteUsers(dto.getIds());
    }

    // ==================== 复杂查询接口（使用XML） ====================

    /**
     * 多条件动态查询用户
     */
    @GetMapping("/search/condition")
    @SystemLog(businessName = "多条件查询用户")
    @Operation(summary = "多条件动态查询用户", description = "支持多个条件组合查询用户")
    public ResponseResult searchUsersByCondition(UserQueryDTO dto) {
        return userService.searchUsersByCondition(dto);
    }

    /**
     * 统计各类型用户数量
     */
    @GetMapping("/statistics/type")
    @SystemLog(businessName = "统计各类型用户数量")
    @Operation(summary = "统计各类型用户数量", description = "统计不同类型用户的数量及状态分布")
    public ResponseResult countUsersByType() {
        return userService.countUsersByType();
    }

    /**
     * 查询用户统计信息
     */
    @GetMapping("/statistics/overview")
    @SystemLog(businessName = "查询用户统计信息")
    @Operation(summary = "查询用户统计信息", description = "获取用户的综合统计数据")
    public ResponseResult getUserStatistics(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") @Parameter(description = "开始时间") Date startTime,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") @Parameter(description = "结束时间") Date endTime) {
        return userService.getUserStatistics(startTime, endTime);
    }

    /**
     * 批量查询用户（通过ID列表）
     */
    @PostMapping("/batch/query")
    @SystemLog(businessName = "批量查询用户")
    @Operation(summary = "批量查询用户", description = "通过ID列表批量查询用户")
    public ResponseResult getUsersByIds(@RequestBody @Valid BatchOperationDTO dto) {
        return userService.getUsersByIds(dto.getIds());
    }

    /**
     * 查询最近注册的用户
     */
    @GetMapping("/recent")
    @SystemLog(businessName = "查询最近注册用户")
    @Operation(summary = "查询最近注册的用户", description = "查询指定天数内注册的用户")
    public ResponseResult getRecentUsers(
            @RequestParam(value = "days", defaultValue = "7") @Parameter(description = "最近天数") Integer days,
            @RequestParam(value = "limit", defaultValue = "10") @Parameter(description = "限制数量") Integer limit) {
        return userService.getRecentUsers(days, limit);
    }

    /**
     * 模糊搜索用户
     */
    @GetMapping("/search")
    @SystemLog(businessName = "模糊搜索用户")
    @Operation(summary = "模糊搜索用户", description = "在用户名、昵称、邮箱、手机号中模糊搜索")
    public ResponseResult searchUsers(@RequestParam("keyword") @Parameter(description = "搜索关键词") String keyword) {
        return userService.searchUsers(keyword);
    }

    /**
     * 查询用户及其创建者信息
     */
    @GetMapping("/with-creator")
    @SystemLog(businessName = "查询用户及创建者信息")
    @Operation(summary = "查询用户及其创建者信息", description = "查询用户列表及其创建者的详细信息")
    public ResponseResult getUsersWithCreator(
            @RequestParam(required = false) @Parameter(description = "状态") String status) {
        return userService.getUsersWithCreator(status);
    }

    /**
     * 批量更新用户状态
     */
    @PutMapping("/batch/status")
    @SystemLog(businessName = "批量更新用户状态")
    @Operation(summary = "批量更新用户状态", description = "批量修改用户的状态")
    public ResponseResult batchUpdateStatus(
            @RequestParam("ids") @Parameter(description = "用户ID列表") List<Long> ids,
            @RequestParam("status") @Parameter(description = "新状态") String status,
            @RequestParam(required = false) @Parameter(description = "更新人ID") Long updateBy) {
        return userService.batchUpdateStatus(ids, status, updateBy);
    }

    /**
     * 按月统计用户注册数量
     */
    @GetMapping("/statistics/month")
    @SystemLog(businessName = "按月统计用户注册数量")
    @Operation(summary = "按月统计用户注册数量", description = "统计指定年份或全部年份的月度注册数据")
    public ResponseResult countUsersByMonth(
            @RequestParam(required = false) @Parameter(description = "年份") Integer year) {
        return userService.countUsersByMonth(year);
    }

    /**
     * 动态更新用户信息（只更新非空字段）
     */
    @PatchMapping("/update/selective")
    @SystemLog(businessName = "动态更新用户信息")
    @Operation(summary = "动态更新用户信息", description = "只更新提交的非空字段")
    public ResponseResult updateUserSelective(@RequestBody @Valid UserUpdateDTO dto) {
        return userService.updateUserSelective(dto);
    }

}
