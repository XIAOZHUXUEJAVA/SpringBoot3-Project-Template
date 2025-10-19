package com.zhu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhu.domain.dto.userdto.UserAddDTO;
import com.zhu.domain.dto.userdto.UserQueryDTO;
import com.zhu.domain.dto.userdto.UserUpdateDTO;
import com.zhu.domain.entity.User;
import com.zhu.common.result.ResponseResult;

import java.util.Date;
import java.util.List;


/**
 * 用户表(User)表服务接口
 * 使用 DTO 接收请求参数，使用 VO 返回响应数据
 *
 * @author xiaozhu
 * @since 2022-10-04 00:05:56
 */
public interface UserService extends IService<User> {

    // ==================== 基础CRUD（使用Lambda Query + DTO/VO） ====================

    /**
     * 获取所有用户
     * @return ResponseResult<List<UserVO>>
     */
    ResponseResult getAllUsers();

    /**
     * 根据ID获取用户详情
     * @param id 用户ID
     * @return ResponseResult<UserDetailVO>
     */
    ResponseResult getUserById(Long id);

    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return ResponseResult<UserVO>
     */
    ResponseResult getUserByUsername(String username);

    /**
     * 根据邮箱查询用户
     * @param email 邮箱
     * @return ResponseResult<UserVO>
     */
    ResponseResult getUserByEmail(String email);

    /**
     * 分页查询用户
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @return ResponseResult<Page<UserVO>>
     */
    ResponseResult getUsersByPage(Integer pageNum, Integer pageSize);

    /**
     * 根据状态查询用户
     * @param status 状态
     * @return ResponseResult<List<UserVO>>
     */
    ResponseResult getUsersByStatus(String status);

    /**
     * 根据类型查询用户
     * @param type 类型
     * @return ResponseResult<List<UserVO>>
     */
    ResponseResult getUsersByType(String type);

    /**
     * 添加用户
     * @param dto 用户添加DTO
     * @return ResponseResult
     */
    ResponseResult addUser(UserAddDTO dto);

    /**
     * 更新用户
     * @param dto 用户更新DTO
     * @return ResponseResult
     */
    ResponseResult updateUser(UserUpdateDTO dto);

    /**
     * 删除用户（逻辑删除）
     * @param id 用户ID
     * @return ResponseResult
     */
    ResponseResult deleteUser(Long id);

    /**
     * 批量删除用户
     * @param ids 用户ID列表
     * @return ResponseResult
     */
    ResponseResult batchDeleteUsers(List<Long> ids);

    // ==================== 复杂查询（使用XML + DTO/VO） ====================

    /**
     * 多条件动态查询用户
     * @param dto 查询条件DTO
     * @return ResponseResult<List<UserVO>>
     */
    ResponseResult searchUsersByCondition(UserQueryDTO dto);

    /**
     * 统计各类型用户数量
     * @return ResponseResult<List<UserTypeStatisticsVO>>
     */
    ResponseResult countUsersByType();

    /**
     * 查询用户统计信息
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return ResponseResult<UserStatisticsVO>
     */
    ResponseResult getUserStatistics(Date startTime, Date endTime);

    /**
     * 批量查询用户（通过ID列表）
     * @param ids ID列表
     * @return ResponseResult<List<UserVO>>
     */
    ResponseResult getUsersByIds(List<Long> ids);

    /**
     * 查询最近注册的用户
     * @param days 最近天数
     * @param limit 限制数量
     * @return ResponseResult<List<UserVO>>
     */
    ResponseResult getRecentUsers(Integer days, Integer limit);

    /**
     * 模糊搜索用户
     * @param keyword 关键词
     * @return ResponseResult<List<UserVO>>
     */
    ResponseResult searchUsers(String keyword);

    /**
     * 查询用户及其创建者信息
     * @param status 状态
     * @return ResponseResult<List<UserDetailVO>>
     */
    ResponseResult getUsersWithCreator(String status);

    /**
     * 批量更新用户状态
     * @param ids ID列表
     * @param status 状态
     * @param updateBy 更新人
     * @return ResponseResult
     */
    ResponseResult batchUpdateStatus(List<Long> ids, String status, Long updateBy);

    /**
     * 按月统计用户注册数量
     * @param year 年份
     * @return ResponseResult<List<UserMonthStatisticsVO>>
     */
    ResponseResult countUsersByMonth(Integer year);

    /**
     * 动态更新用户信息
     * @param dto 用户更新DTO
     * @return ResponseResult
     */
    ResponseResult updateUserSelective(UserUpdateDTO dto);

}
