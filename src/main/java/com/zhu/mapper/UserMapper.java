package com.zhu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhu.domain.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 用户表(User)表数据库访问层
 *
 * @author xiaozhu
 * @since 2022-10-04 00:03:08
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 多条件动态查询用户
     *
     * @param userName 用户名
     * @param nickName 昵称
     * @param email 邮箱
     * @param phonenumber 手机号
     * @param status 状态
     * @param type 用户类型
     * @param sex 性别
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 用户列表
     */
    List<User> selectUsersByCondition(@Param("userName") String userName,
                                      @Param("nickName") String nickName,
                                      @Param("email") String email,
                                      @Param("phonenumber") String phonenumber,
                                      @Param("status") String status,
                                      @Param("type") String type,
                                      @Param("sex") String sex,
                                      @Param("startTime") Date startTime,
                                      @Param("endTime") Date endTime);

    /**
     * 统计各类型用户数量
     *
     * @return 统计结果
     */
    List<Map<String, Object>> countUsersByType();

    /**
     * 查询用户统计信息
     *
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 统计信息
     */
    Map<String, Object> getUserStatistics(@Param("startTime") Date startTime,
                                          @Param("endTime") Date endTime);

    /**
     * 批量查询用户（通过ID列表）
     *
     * @param ids ID列表
     * @return 用户列表
     */
    List<User> selectUsersByIds(@Param("ids") List<Long> ids);

    /**
     * 查询最近注册的用户
     *
     * @param days 最近天数
     * @param limit 限制数量
     * @return 用户列表
     */
    List<User> selectRecentUsers(@Param("days") Integer days,
                                  @Param("limit") Integer limit);

    /**
     * 模糊搜索用户（多字段OR查询）
     *
     * @param keyword 关键词
     * @return 用户列表
     */
    List<User> searchUsers(@Param("keyword") String keyword);

    /**
     * 查询用户及其创建者信息
     *
     * @param status 状态
     * @return 用户及创建者信息
     */
    List<Map<String, Object>> selectUsersWithCreator(@Param("status") String status);

    /**
     * 批量更新用户状态
     *
     * @param ids ID列表
     * @param status 状态
     * @param updateBy 更新人
     * @return 更新数量
     */
    int batchUpdateStatus(@Param("ids") List<Long> ids,
                          @Param("status") String status,
                          @Param("updateBy") Long updateBy);

    /**
     * 按月统计用户注册数量
     *
     * @param year 年份
     * @return 统计结果
     */
    List<Map<String, Object>> countUsersByMonth(@Param("year") Integer year);

    /**
     * 动态更新用户信息（只更新非空字段）
     *
     * @param user 用户信息
     * @return 更新数量
     */
    int updateUserSelective(User user);

}
