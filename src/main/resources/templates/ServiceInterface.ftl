package ${packageName};

import ${buttonQualifiedName};
import ${baseServiceQualifiedName};
import ${modelQualifiedName};

import java.util.List;

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