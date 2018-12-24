package ${packageName};

import ${buttonQualifiedName};

import java.util.List;

public interface ${modelName}Service {

    /**
     * 获取工具栏按钮
     */
    List<Button> getToolBarButtons();

    /**
     * 获取操作栏按钮
     */
    List<Button> getOperatingButtons();

}