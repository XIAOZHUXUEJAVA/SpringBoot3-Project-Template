package com.zhu.service.impl;

import com.zhu.domain.entity.DictData;
import com.zhu.mapper.DictDataMapper;
import com.zhu.service.DictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 字典服务实现类
 * 使用内存缓存提升性能
 *
 * @author xiaozhu
 */
@Slf4j
@Service
public class DictServiceImpl implements DictService {

    @Autowired
    private DictDataMapper dictDataMapper;

    /**
     * 字典数据缓存
     * key: dictType, value: List<DictData>
     */
    private final Map<String, List<DictData>> dictCache = new ConcurrentHashMap<>();

    /**
     * 字典标签缓存
     * key: dictType:dictValue, value: dictLabel
     */
    private final Map<String, String> labelCache = new ConcurrentHashMap<>();

    @Override
    public List<DictData> getDictDataByType(String dictType) {
        if (dictType == null || dictType.isEmpty()) {
            return List.of();
        }

        // 先从缓存获取
        if (dictCache.containsKey(dictType)) {
            return dictCache.get(dictType);
        }

        // 缓存未命中，从数据库查询
        List<DictData> dictDataList = dictDataMapper.selectDictDataByType(dictType);

        // 放入缓存
        if (dictDataList != null && !dictDataList.isEmpty()) {
            dictCache.put(dictType, dictDataList);
            log.debug("字典数据已缓存: dictType={}, size={}", dictType, dictDataList.size());
        }

        return dictDataList;
    }

    @Override
    public String getDictLabel(String dictType, String dictValue) {
        if (dictType == null || dictValue == null) {
            return null;
        }

        String cacheKey = dictType + ":" + dictValue;

        // 先从缓存获取
        if (labelCache.containsKey(cacheKey)) {
            return labelCache.get(cacheKey);
        }

        // 缓存未命中，从数据库查询
        String label = dictDataMapper.selectDictLabel(dictType, dictValue);

        // 放入缓存
        if (label != null) {
            labelCache.put(cacheKey, label);
            log.debug("字典标签已缓存: {}={}", cacheKey, label);
        }

        return label;
    }

    @Override
    public void refreshCache() {
        dictCache.clear();
        labelCache.clear();
        log.info("字典缓存已清空");
    }
}
