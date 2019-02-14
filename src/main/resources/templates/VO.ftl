package ${packageName};

import lombok.Data;

@Data
public class ${modelName} {

    /**
     * 主键标识
     */
    private String id;
    /**
     * 创建时间
     */
    private String createdTime;
    /**
     * 修改时间
     */
    private String updatedTime;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 排序
     */
    private Long sort;

}
