package com.zhu.utils;

import com.zhu.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 字典工具类
 * 统一管理各种字典值的描述转换
 * 从数据库动态获取字典数据，支持缓存
 *
 * @author xiaozhu
 */
@Component
public class DictUtils {

    private static DictService dictService;

    @Autowired
    public void setDictService(DictService dictService) {
        DictUtils.dictService = dictService;
    }

    private DictUtils() {
    }

    /**
     * 获取用户类型描述
     *
     * @param type 用户类型值
     * @return 用户类型描述
     */
    public static String getTypeDesc(String type) {
        return getDictLabel("sys_user_type", type, "未知");
    }

    /**
     * 获取状态描述
     *
     * @param status 状态值
     * @return 状态描述
     */
    public static String getStatusDesc(String status) {
        return getDictLabel("sys_normal_disable", status, "未知");
    }

    /**
     * 获取性别描述
     *
     * @param sex 性别值
     * @return 性别描述
     */
    public static String getSexDesc(String sex) {
        return getDictLabel("sys_user_sex", sex, "未知");
    }

    /**
     * 通用方法：根据字典类型和字典值获取字典标签
     *
     * @param dictType 字典类型
     * @param dictValue 字典值
     * @param defaultValue 默认值（当查询不到时返回）
     * @return 字典标签
     */
    public static String getDictLabel(String dictType, String dictValue, String defaultValue) {
        if (dictValue == null || dictValue.isEmpty()) {
            return defaultValue;
        }

        try {
            String label = dictService.getDictLabel(dictType, dictValue);
            return label != null ? label : defaultValue;
        } catch (Exception e) {
            // 如果查询失败，返回默认值
            return defaultValue;
        }
    }

    /**
     * 刷新字典缓存
     */
    public static void refreshCache() {
        if (dictService != null) {
            dictService.refreshCache();
        }
    }
}
