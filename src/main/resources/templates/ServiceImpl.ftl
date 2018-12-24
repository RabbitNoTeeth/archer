package ${packageName};

import org.springframework.stereotype.Service;
import ${buttonQualifiedName};
import ${buttonUtilQualifiedName};

import java.util.ArrayList;
import java.util.List;

@Service
public class ${modelName}ServiceImpl implements ${modelName}Service {

    @Override
    public List<Button> getToolBarButtons() {
        List<Button> buttons = new ArrayList<>();
        // todo
        return ButtonUtil.filterByCurrentUser(buttons);
    }

    @Override
    public List<Button> getOperatingButtons() {
        List<Button> buttons = new ArrayList<>();
        // todo
        return ButtonUtil.filterByCurrentUser(buttons);
    }

}