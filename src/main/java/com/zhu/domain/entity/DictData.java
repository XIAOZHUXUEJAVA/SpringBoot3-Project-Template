package com.zhu.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 字典数据表(DictData)表实体类
 *
 * @author xiaozhu
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_dict_data")
@Schema(description = "字典数据实体类")
public class DictData implements Serializable {
    
    /**
     * 字典编码
     */
    private Long id;
    
    /**
     * 字典排序
     */
    @Schema(description = "字典排序")
    private Integer dictSort;
    
    /**
     * 字典标签
     */
    @Schema(description = "字典标签")
    private String dictLabel;
    
    /**
     * 字典键值
     */
    @Schema(description = "字典键值")
    private String dictValue;
    
    /**
     * 字典类型
     */
    @Schema(description = "字典类型")
    private String dictType;
    
    /**
     * 样式属性（其他样式扩展）
     */
    @Schema(description = "样式属性")
    private String cssClass;
    
    /**
     * 表格回显样式
     */
    @Schema(description = "表格回显样式")
    private String listClass;
    
    /**
     * 是否默认（Y是 N否）
     */
    @Schema(description = "是否默认（Y是 N否）")
    private String isDefault;
    
    /**
     * 状态（0正常 1停用）
     */
    @Schema(description = "状态（0正常 1停用）")
    private String status;
    
    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;
    
    /**
     * 创建人的用户id
     */
    private Long createBy;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 更新人
     */
    private Long updateBy;
    
    /**
     * 更新时间
     */
    private Date updateTime;
    
    /**
     * 删除标志（0代表未删除，1代表已删除）
     */
    private Integer delFlag;
}
