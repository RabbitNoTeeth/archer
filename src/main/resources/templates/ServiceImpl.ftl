package ${packageName};

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import ${buttonQualifiedName};
import ${pOMapperQualifiedName};
import ${buttonUtilsQualifiedName};

import java.util.ArrayList;
import java.util.List;

@Service
public class ${modelName}ServiceImpl implements ${modelName}Service {

    @Autowired
    private ${modelName}POMapper ${modelVariableName}POMapper;

    private final String WRITE_PERMISSION = "";

    @Override
    public List<Button> getToolBarButtons() {
        return ButtonUtils.getToolbar(WRITE_PERMISSION, null, writeBtns -> {
            writeBtns.add(Button.BTN_ADD);
            writeBtns.add(Button.BTN_DELETE);
        });
    }

    @Override
    public List<Button> getOperatingButtons() {
        return ButtonUtils.getOperatingButtons(WRITE_PERMISSION, readBtns -> {
            readBtns.add(Button.OPTION_INFO);
        }, writeBtns -> {
            writeBtns.add(Button.OPTION_EDIT);
            writeBtns.add(Button.OPTION_DELETE);
        });
    }

}