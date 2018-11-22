package ${packageName};

import com.sunvua.alan.base.model.Button;
import ${baseServiceQualifiedName};
import ${modelQualifiedName};

public interface ${modelName}Service extends BaseService<${modelName}> {

    /**
     * 获取工具栏按钮
     */
    List<Button> getToolBarButtons();

    /**
    * 获取操作按钮
    */
    List<Button> getOperatingButtons();

}