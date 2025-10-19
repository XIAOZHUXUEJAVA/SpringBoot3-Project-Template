package com.zhu.service;

import com.zhu.domain.entity.DictData;

import java.util.List;

/**
 * 字典服务接口
 *
 * @author xiaozhu
 */
public interface DictService {

    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据列表
     */
    List<DictData> getDictDataByType(String dictType);

    /**
     * 根据字典类型和字典值获取字典标签
     *
     * @param dictType 字典类型
     * @param dictValue 字典值
     * @return 字典标签
     */
    String getDictLabel(String dictType, String dictValue);

    /**
     * 刷新字典缓存
     */
    void refreshCache();
}
