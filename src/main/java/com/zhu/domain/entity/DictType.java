package com.zhu.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 字典类型表(DictType)表实体类
 *
 * @author xiaozhu
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_dict_type")
@Schema(description = "字典类型实体类")
public class DictType implements Serializable {
    
    /**
     * 字典主键
     */
    private Long id;
    
    /**
     * 字典名称
     */
    @Schema(description = "字典名称")
    private String dictName;
    
    /**
     * 字典类型
     */
    @Schema(description = "字典类型")
    private String dictType;
    
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
