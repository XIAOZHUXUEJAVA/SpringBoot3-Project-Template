package com.zhu.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhu.domain.dto.userdto.UserAddDTO;
import com.zhu.domain.dto.userdto.UserQueryDTO;
import com.zhu.domain.dto.userdto.UserUpdateDTO;
import com.zhu.domain.entity.User;
import com.zhu.domain.vo.uservo.*;
import com.zhu.mapper.UserMapper;
import com.zhu.service.UserService;
import com.zhu.utils.DictUtils;
import com.zhu.common.result.ResponseResult;
import com.zhu.converter.UserConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * 用户表(User)表服务实现类
 *
 * 使用MyBatis Plus最佳实践：
 * 1. 简单查询使用Lambda Query Wrapper
 * 2. 复杂查询使用XML配置
 * 3. 使用DTO接收请求参数，使用VO返回响应数据
 * 4. 使用Converter进行对象转换
 *
 * @author xiaozhu
 * @since 2022-10-04 00:06:26
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    // ==================== 基础CRUD（使用Lambda Query） ====================

    /**
     * 获取所有用户
     * 使用LambdaQueryWrapper进行条件查询，返回UserVO
     */
    @Override
    public ResponseResult getAllUsers() {
        // Lambda查询：查询未删除的用户，按创建时间降序
        List<User> users = lambdaQuery()
                .eq(User::getDelFlag, 0)
                .orderByDesc(User::getCreateTime)
                .list();
        // 转换为VO
        List<UserVO> userVOList = UserConverter.toVOList(users);
        return ResponseResult.okResult(userVOList);
    }

    /**
     * 根据ID获取用户详情
     * 使用getById方法（MyBatis Plus提供），返回UserDetailVO
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
        // 转换为DetailVO
        UserDetailVO userDetailVO = UserConverter.toDetailVO(user);
        return ResponseResult.okResult(userDetailVO);
    }

    /**
     * 根据用户名查询用户
     * Lambda查询示例：单条件查询，返回UserVO
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
        // 转换为VO
        UserVO userVO = UserConverter.toVO(user);
        return ResponseResult.okResult(userVO);
    }

    /**
     * 根据邮箱查询用户
     * Lambda查询示例：单条件查询，返回UserVO
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
        // 转换为VO
        UserVO userVO = UserConverter.toVO(user);
        return ResponseResult.okResult(userVO);
    }

    /**
     * 分页查询用户
     * 使用MyBatis Plus的Page分页，返回UserVO
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

        // 转换为VO分页对象
        Page<UserVO> voPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        voPage.setRecords(UserConverter.toVOList(page.getRecords()));

        return ResponseResult.okResult(voPage);
    }

    /**
     * 根据状态查询用户
     * Lambda查询示例：条件查询+排序，返回UserVO
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

        // 转换为VO
        List<UserVO> userVOList = UserConverter.toVOList(users);
        return ResponseResult.okResult(userVOList);
    }

    /**
     * 根据类型查询用户
     * Lambda查询示例：多条件查询，返回UserVO
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

        // 转换为VO
        List<UserVO> userVOList = UserConverter.toVOList(users);
        return ResponseResult.okResult(userVOList);
    }

    /**
     * 添加用户
     * 使用DTO接收参数，转换为Entity保存
     */
    @Override
    public ResponseResult addUser(UserAddDTO dto) {
        if (dto == null) {
            return ResponseResult.errorResult(400, "用户信息不能为空");
        }

        // 检查用户名是否已存在
        Long count = lambdaQuery()
                .eq(User::getUserName, dto.getUserName())
                .eq(User::getDelFlag, 0)
                .count();

        if (count > 0) {
            return ResponseResult.errorResult(400, "用户名已存在");
        }

        // DTO转Entity
        User user = UserConverter.toEntity(dto);

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
     * 使用DTO接收参数，转换为Entity更新
     */
    @Override
    public ResponseResult updateUser(UserUpdateDTO dto) {
        if (dto == null || dto.getId() == null) {
            return ResponseResult.errorResult(400, "用户ID不能为空");
        }

        // 检查用户是否存在
        User existUser = getById(dto.getId());
        if (existUser == null || existUser.getDelFlag() == 1) {
            return ResponseResult.errorResult(404, "用户不存在");
        }

        // DTO转Entity
        User user = UserConverter.toEntity(dto);
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
     * 使用XML配置的复杂查询，返回UserVO
     */
    @Override
    public ResponseResult searchUsersByCondition(UserQueryDTO dto) {
        if (dto == null) {
            dto = new UserQueryDTO();
        }
        List<User> users = baseMapper.selectUsersByCondition(
                dto.getUserName(), dto.getNickName(), dto.getEmail(),
                dto.getPhonenumber(), dto.getStatus(), dto.getType(),
                dto.getSex(), dto.getStartTime(), dto.getEndTime()
        );
        // 转换为VO
        List<UserVO> userVOList = UserConverter.toVOList(users);
        return ResponseResult.okResult(userVOList);
    }

    /**
     * 统计各类型用户数量
     * Mapper 直接返回 VO，无需转换
     */
    @Override
    public ResponseResult countUsersByType() {
        List<UserTypeStatisticsVO> voList = baseMapper.countUsersByType();
        // 设置描述字段
        voList.forEach(vo -> vo.setUserTypeDesc(DictUtils.getTypeDesc(vo.getUserType())));
        return ResponseResult.okResult(voList);
    }

    /**
     * 查询用户统计信息
     * Mapper 直接返回 VO，无需转换
     */
    @Override
    public ResponseResult getUserStatistics(Date startTime, Date endTime) {
        UserStatisticsVO vo = baseMapper.getUserStatistics(startTime, endTime);
        return ResponseResult.okResult(vo);
    }

    /**
     * 批量查询用户（通过ID列表）
     * 返回UserVO
     */
    @Override
    public ResponseResult getUsersByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return ResponseResult.errorResult(400, "ID列表不能为空");
        }
        List<User> users = baseMapper.selectUsersByIds(ids);
        // 转换为VO
        List<UserVO> userVOList = UserConverter.toVOList(users);
        return ResponseResult.okResult(userVOList);
    }

    /**
     * 查询最近注册的用户
     * 返回UserVO
     */
    @Override
    public ResponseResult getRecentUsers(Integer days, Integer limit) {
        days = days == null || days < 1 ? 7 : days;
        limit = limit == null || limit < 1 ? 10 : limit;

        List<User> users = baseMapper.selectRecentUsers(days, limit);
        // 转换为VO
        List<UserVO> userVOList = UserConverter.toVOList(users);
        return ResponseResult.okResult(userVOList);
    }

    /**
     * 模糊搜索用户
     * 返回UserVO
     */
    @Override
    public ResponseResult searchUsers(String keyword) {
        if (!StringUtils.hasText(keyword)) {
            return ResponseResult.errorResult(400, "搜索关键词不能为空");
        }
        List<User> users = baseMapper.searchUsers(keyword);
        // 转换为VO
        List<UserVO> userVOList = UserConverter.toVOList(users);
        return ResponseResult.okResult(userVOList);
    }

    /**
     * 查询用户及其创建者信息
     * Mapper 直接返回 VO，无需转换
     */
    @Override
    public ResponseResult getUsersWithCreator(String status) {
        List<UserDetailVO> voList = baseMapper.selectUsersWithCreator(status);
        // 设置描述字段
        voList.forEach(vo -> vo.setStatusDesc(DictUtils.getStatusDesc(vo.getStatus())));
        return ResponseResult.okResult(voList);
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
     * Mapper 直接返回 VO，无需转换
     */
    @Override
    public ResponseResult countUsersByMonth(Integer year) {
        List<UserMonthStatisticsVO> voList = baseMapper.countUsersByMonth(year);
        return ResponseResult.okResult(voList);
    }

    /**
     * 动态更新用户信息
     * 使用DTO接收参数
     */
    @Override
    public ResponseResult updateUserSelective(UserUpdateDTO dto) {
        if (dto == null || dto.getId() == null) {
            return ResponseResult.errorResult(400, "用户ID不能为空");
        }

        // DTO转Entity
        User user = UserConverter.toEntity(dto);
        user.setUpdateTime(new Date());

        int count = baseMapper.updateUserSelective(user);
        return count > 0 ? ResponseResult.okResult() : ResponseResult.errorResult(500, "更新失败");
    }

}
