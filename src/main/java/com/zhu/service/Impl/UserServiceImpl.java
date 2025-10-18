package com.zhu.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhu.domain.entity.User;
import com.zhu.mapper.UserMapper;
import com.zhu.service.UserService;
import com.zhu.utils.ResponseResult;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 用户表(User)表服务实现类
 * 
 * 使用MyBatis Plus最佳实践：
 * 1. 简单查询使用Lambda Query Wrapper
 * 2. 复杂查询使用XML配置
 * 3. 避免使用字符串拼接字段名
 *
 * @author xiaozhu
 * @since 2022-10-04 00:06:26
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    // ==================== 基础CRUD（使用Lambda Query） ====================

    /**
     * 获取所有用户
     * 使用LambdaQueryWrapper进行条件查询
     */
    @Override
    public ResponseResult getAllUsers() {
        // Lambda查询：查询未删除的用户，按创建时间降序
        List<User> users = lambdaQuery()
                .eq(User::getDelFlag, 0)
                .orderByDesc(User::getCreateTime)
                .list();
        return ResponseResult.okResult(users);
    }

    /**
     * 根据ID获取用户
     * 使用getById方法（MyBatis Plus提供）
     */
    @Override
    public ResponseResult getUserById(Long id) {
        if (id == null) {
            return ResponseResult.errorResult(400, "用户ID不能为空");
        }
        User user = getById(id);
        if (user == null || user.getDelFlag() == 1) {
            return ResponseResult.errorResult(404, "用户不存在");
        }
        return ResponseResult.okResult(user);
    }

    /**
     * 根据用户名查询用户
     * Lambda查询示例：单条件查询
     */
    @Override
    public ResponseResult getUserByUsername(String username) {
        if (!StringUtils.hasText(username)) {
            return ResponseResult.errorResult(400, "用户名不能为空");
        }
        
        // Lambda查询：eq表示等于
        User user = lambdaQuery()
                .eq(User::getUserName, username)
                .eq(User::getDelFlag, 0)
                .one();
        
        if (user == null) {
            return ResponseResult.errorResult(404, "用户不存在");
        }
        return ResponseResult.okResult(user);
    }

    /**
     * 根据邮箱查询用户
     * Lambda查询示例：单条件查询
     */
    @Override
    public ResponseResult getUserByEmail(String email) {
        if (!StringUtils.hasText(email)) {
            return ResponseResult.errorResult(400, "邮箱不能为空");
        }
        
        User user = lambdaQuery()
                .eq(User::getEmail, email)
                .eq(User::getDelFlag, 0)
                .one();
        
        if (user == null) {
            return ResponseResult.errorResult(404, "用户不存在");
        }
        return ResponseResult.okResult(user);
    }

    /**
     * 分页查询用户
     * 使用MyBatis Plus的Page分页
     */
    @Override
    public ResponseResult getUsersByPage(Integer pageNum, Integer pageSize) {
        pageNum = pageNum == null || pageNum < 1 ? 1 : pageNum;
        pageSize = pageSize == null || pageSize < 1 ? 10 : pageSize;
        
        // Lambda分页查询
        Page<User> page = lambdaQuery()
                .eq(User::getDelFlag, 0)
                .orderByDesc(User::getCreateTime)
                .page(new Page<>(pageNum, pageSize));
        
        return ResponseResult.okResult(page);
    }

    /**
     * 根据状态查询用户
     * Lambda查询示例：条件查询+排序
     */
    @Override
    public ResponseResult getUsersByStatus(String status) {
        if (!StringUtils.hasText(status)) {
            return ResponseResult.errorResult(400, "状态不能为空");
        }
        
        List<User> users = lambdaQuery()
                .eq(User::getStatus, status)
                .eq(User::getDelFlag, 0)
                .orderByDesc(User::getCreateTime)
                .list();
        
        return ResponseResult.okResult(users);
    }

    /**
     * 根据类型查询用户
     * Lambda查询示例：多条件查询
     */
    @Override
    public ResponseResult getUsersByType(String type) {
        if (!StringUtils.hasText(type)) {
            return ResponseResult.errorResult(400, "类型不能为空");
        }
        
        List<User> users = lambdaQuery()
                .eq(User::getType, type)
                .eq(User::getDelFlag, 0)
                .eq(User::getStatus, "0") // 只查询正常状态的用户
                .orderByDesc(User::getCreateTime)
                .list();
        
        return ResponseResult.okResult(users);
    }

    /**
     * 添加用户
     * 使用save方法（MyBatis Plus提供）
     */
    @Override
    public ResponseResult addUser(User user) {
        if (user == null) {
            return ResponseResult.errorResult(400, "用户信息不能为空");
        }
        
        // 检查用户名是否已存在
        Long count = lambdaQuery()
                .eq(User::getUserName, user.getUserName())
                .eq(User::getDelFlag, 0)
                .count();
        
        if (count > 0) {
            return ResponseResult.errorResult(400, "用户名已存在");
        }
        
        // 设置默认值
        user.setCreateTime(new Date());
        user.setDelFlag(0);
        if (user.getStatus() == null) {
            user.setStatus("0"); // 默认正常状态
        }
        if (user.getType() == null) {
            user.setType("0"); // 默认普通用户
        }
        
        boolean saved = save(user);
        return saved ? ResponseResult.okResult() : ResponseResult.errorResult(500, "添加失败");
    }

    /**
     * 更新用户
     * 使用updateById方法（MyBatis Plus提供）
     */
    @Override
    public ResponseResult updateUser(User user) {
        if (user == null || user.getId() == null) {
            return ResponseResult.errorResult(400, "用户ID不能为空");
        }
        
        // 检查用户是否存在
        User existUser = getById(user.getId());
        if (existUser == null || existUser.getDelFlag() == 1) {
            return ResponseResult.errorResult(404, "用户不存在");
        }
        
        user.setUpdateTime(new Date());
        boolean updated = updateById(user);
        return updated ? ResponseResult.okResult() : ResponseResult.errorResult(500, "更新失败");
    }

    /**
     * 删除用户（逻辑删除）
     * Lambda更新示例：使用LambdaUpdateWrapper
     */
    @Override
    public ResponseResult deleteUser(Long id) {
        if (id == null) {
            return ResponseResult.errorResult(400, "用户ID不能为空");
        }
        
        // 检查用户是否存在
        User user = getById(id);
        if (user == null || user.getDelFlag() == 1) {
            return ResponseResult.errorResult(404, "用户不存在");
        }
        
        // Lambda更新：逻辑删除
        boolean updated = lambdaUpdate()
                .set(User::getDelFlag, 1)
                .set(User::getUpdateTime, new Date())
                .eq(User::getId, id)
                .update();
        
        return updated ? ResponseResult.okResult() : ResponseResult.errorResult(500, "删除失败");
    }

    /**
     * 批量删除用户
     * Lambda更新示例：批量更新
     */
    @Override
    public ResponseResult batchDeleteUsers(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return ResponseResult.errorResult(400, "用户ID列表不能为空");
        }
        
        // Lambda批量更新
        boolean updated = lambdaUpdate()
                .set(User::getDelFlag, 1)
                .set(User::getUpdateTime, new Date())
                .in(User::getId, ids)
                .eq(User::getDelFlag, 0)
                .update();
        
        return updated ? ResponseResult.okResult() : ResponseResult.errorResult(500, "批量删除失败");
    }

    // ==================== 复杂查询（使用XML） ====================

    /**
     * 多条件动态查询用户
     * 使用XML配置的复杂查询
     */
    @Override
    public ResponseResult searchUsersByCondition(String userName, String nickName, String email,
                                                String phonenumber, String status, String type,
                                                String sex, Date startTime, Date endTime) {
        List<User> users = baseMapper.selectUsersByCondition(
                userName, nickName, email, phonenumber, status, type, sex, startTime, endTime
        );
        return ResponseResult.okResult(users);
    }

    /**
     * 统计各类型用户数量
     */
    @Override
    public ResponseResult countUsersByType() {
        List<Map<String, Object>> statistics = baseMapper.countUsersByType();
        return ResponseResult.okResult(statistics);
    }

    /**
     * 查询用户统计信息
     */
    @Override
    public ResponseResult getUserStatistics(Date startTime, Date endTime) {
        Map<String, Object> statistics = baseMapper.getUserStatistics(startTime, endTime);
        return ResponseResult.okResult(statistics);
    }

    /**
     * 批量查询用户（通过ID列表）
     */
    @Override
    public ResponseResult getUsersByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return ResponseResult.errorResult(400, "ID列表不能为空");
        }
        List<User> users = baseMapper.selectUsersByIds(ids);
        return ResponseResult.okResult(users);
    }

    /**
     * 查询最近注册的用户
     */
    @Override
    public ResponseResult getRecentUsers(Integer days, Integer limit) {
        days = days == null || days < 1 ? 7 : days;
        limit = limit == null || limit < 1 ? 10 : limit;
        
        List<User> users = baseMapper.selectRecentUsers(days, limit);
        return ResponseResult.okResult(users);
    }

    /**
     * 模糊搜索用户
     */
    @Override
    public ResponseResult searchUsers(String keyword) {
        if (!StringUtils.hasText(keyword)) {
            return ResponseResult.errorResult(400, "搜索关键词不能为空");
        }
        List<User> users = baseMapper.searchUsers(keyword);
        return ResponseResult.okResult(users);
    }

    /**
     * 查询用户及其创建者信息
     */
    @Override
    public ResponseResult getUsersWithCreator(String status) {
        List<Map<String, Object>> users = baseMapper.selectUsersWithCreator(status);
        return ResponseResult.okResult(users);
    }

    /**
     * 批量更新用户状态
     */
    @Override
    public ResponseResult batchUpdateStatus(List<Long> ids, String status, Long updateBy) {
        if (ids == null || ids.isEmpty()) {
            return ResponseResult.errorResult(400, "用户ID列表不能为空");
        }
        if (!StringUtils.hasText(status)) {
            return ResponseResult.errorResult(400, "状态不能为空");
        }
        
        int count = baseMapper.batchUpdateStatus(ids, status, updateBy);
        return count > 0 ? ResponseResult.okResult(count) : ResponseResult.errorResult(500, "更新失败");
    }

    /**
     * 按月统计用户注册数量
     */
    @Override
    public ResponseResult countUsersByMonth(Integer year) {
        List<Map<String, Object>> statistics = baseMapper.countUsersByMonth(year);
        return ResponseResult.okResult(statistics);
    }

    /**
     * 动态更新用户信息
     */
    @Override
    public ResponseResult updateUserSelective(User user) {
        if (user == null || user.getId() == null) {
            return ResponseResult.errorResult(400, "用户ID不能为空");
        }
        
        user.setUpdateTime(new Date());
        int count = baseMapper.updateUserSelective(user);
        return count > 0 ? ResponseResult.okResult() : ResponseResult.errorResult(500, "更新失败");
    }

}
