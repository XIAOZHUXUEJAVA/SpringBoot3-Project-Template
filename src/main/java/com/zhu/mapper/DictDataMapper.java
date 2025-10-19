package com.zhu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhu.domain.entity.DictData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 字典数据表(DictData)表数据库访问层
 *
 * @author xiaozhu
 */
public interface DictDataMapper extends BaseMapper<DictData> {

    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据列表
     */
    List<DictData> selectDictDataByType(@Param("dictType") String dictType);

    /**
     * 根据字典类型和字典值查询字典标签
     *
     * @param dictType 字典类型
     * @param dictValue 字典值
     * @return 字典标签
     */
    String selectDictLabel(@Param("dictType") String dictType, @Param("dictValue") String dictValue);
}
