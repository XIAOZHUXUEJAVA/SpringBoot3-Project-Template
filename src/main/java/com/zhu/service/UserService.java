package com.zhu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhu.domain.entity.User;
import com.zhu.utils.ResponseResult;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 用户表(User)表服务接口
 *
 * @author xiaozhu
 * @since 2022-10-04 00:05:56
 */
public interface UserService extends IService<User> {

    // ==================== 基础CRUD（使用Lambda Query） ====================
    
    /**
     * 获取所有用户
     */
    ResponseResult getAllUsers();

    /**
     * 根据ID获取用户
     */
    ResponseResult getUserById(Long id);

    /**
     * 根据用户名查询用户
     */
    ResponseResult getUserByUsername(String username);

    /**
     * 根据邮箱查询用户
     */
    ResponseResult getUserByEmail(String email);

    /**
     * 分页查询用户
     */
    ResponseResult getUsersByPage(Integer pageNum, Integer pageSize);

    /**
     * 根据状态查询用户
     */
    ResponseResult getUsersByStatus(String status);

    /**
     * 根据类型查询用户
     */
    ResponseResult getUsersByType(String type);

    /**
     * 添加用户
     */
    ResponseResult addUser(User user);

    /**
     * 更新用户
     */
    ResponseResult updateUser(User user);

    /**
     * 删除用户（逻辑删除）
     */
    ResponseResult deleteUser(Long id);

    /**
     * 批量删除用户
     */
    ResponseResult batchDeleteUsers(List<Long> ids);

    // ==================== 复杂查询（使用XML） ====================

    /**
     * 多条件动态查询用户
     */
    ResponseResult searchUsersByCondition(String userName, String nickName, String email,
                                         String phonenumber, String status, String type,
                                         String sex, Date startTime, Date endTime);

    /**
     * 统计各类型用户数量
     */
    ResponseResult countUsersByType();

    /**
     * 查询用户统计信息
     */
    ResponseResult getUserStatistics(Date startTime, Date endTime);

    /**
     * 批量查询用户（通过ID列表）
     */
    ResponseResult getUsersByIds(List<Long> ids);

    /**
     * 查询最近注册的用户
     */
    ResponseResult getRecentUsers(Integer days, Integer limit);

    /**
     * 模糊搜索用户
     */
    ResponseResult searchUsers(String keyword);

    /**
     * 查询用户及其创建者信息
     */
    ResponseResult getUsersWithCreator(String status);

    /**
     * 批量更新用户状态
     */
    ResponseResult batchUpdateStatus(List<Long> ids, String status, Long updateBy);

    /**
     * 按月统计用户注册数量
     */
    ResponseResult countUsersByMonth(Integer year);

    /**
     * 动态更新用户信息
     */
    ResponseResult updateUserSelective(User user);

}
