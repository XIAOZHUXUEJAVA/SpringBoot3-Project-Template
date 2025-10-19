package com.zhu.converter;

import com.zhu.domain.dto.userdto.UserAddDTO;
import com.zhu.domain.dto.userdto.UserUpdateDTO;
import com.zhu.domain.entity.User;
import com.zhu.domain.vo.uservo.UserDetailVO;
import com.zhu.domain.vo.uservo.UserVO;
import com.zhu.utils.BeanCopyUtils;
import com.zhu.utils.DictUtils;

import java.util.List;

/**
 * 用户对象转换工具类
 * 负责 User 模块特有的业务转换逻辑
 * Mapper 直接返回 VO，无需 Map 转换
 *
 * @author xiaozhu
 */
public class UserConverter {

    private UserConverter() {
    }

    // ==================== DTO -> Entity ====================

    /**
     * UserAddDTO 转 User
     */
    public static User toEntity(UserAddDTO dto) {
        return BeanCopyUtils.copyBean(dto, User.class);
    }

    /**
     * UserUpdateDTO 转 User
     */
    public static User toEntity(UserUpdateDTO dto) {
        return BeanCopyUtils.copyBean(dto, User.class);
    }

    // ==================== Entity -> VO ====================

    /**
     * User 转 UserVO（带业务描述字段）
     */
    public static UserVO toVO(User user) {
        if (user == null) {
            return null;
        }
        UserVO vo = BeanCopyUtils.copyBean(user, UserVO.class);
        enrichUserVO(vo, user);
        return vo;
    }

    /**
     * User 列表转 UserVO 列表
     */
    public static List<UserVO> toVOList(List<User> users) {
        return BeanCopyUtils.copyBeanList(users, UserVO.class);
    }

    /**
     * User 转 UserDetailVO（带业务描述字段）
     */
    public static UserDetailVO toDetailVO(User user) {
        if (user == null) {
            return null;
        }
        UserDetailVO vo = BeanCopyUtils.copyBean(user, UserDetailVO.class);
        enrichUserDetailVO(vo, user);
        return vo;
    }

    // ==================== 私有辅助方法 ====================

    /**
     * 丰富 UserVO 的业务描述字段
     */
    private static void enrichUserVO(UserVO vo, User user) {
        vo.setTypeDesc(DictUtils.getTypeDesc(user.getType()));
        vo.setStatusDesc(DictUtils.getStatusDesc(user.getStatus()));
        vo.setSexDesc(DictUtils.getSexDesc(user.getSex()));
    }

    /**
     * 丰富 UserDetailVO 的业务描述字段
     */
    private static void enrichUserDetailVO(UserDetailVO vo, User user) {
        vo.setTypeDesc(DictUtils.getTypeDesc(user.getType()));
        vo.setStatusDesc(DictUtils.getStatusDesc(user.getStatus()));
        vo.setSexDesc(DictUtils.getSexDesc(user.getSex()));
    }

}
