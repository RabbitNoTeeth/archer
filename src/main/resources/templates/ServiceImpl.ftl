package ${packageName};

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import ${buttonQualifiedName};
import ${baseMapperQualifiedName};
import ${modelServiceQualifiedName};
import ${modelMapperQualifiedName};

import java.util.ArrayList;
import java.util.List;

@Service
public class ${modelName}ServiceImpl implements ${modelName}Service {

    @Autowired
    private ${modelName}Mapper ${modelVariableName}Mapper;

    @Override
    public BaseMapper<${modelName}> getMapper() {
        return ${modelVariableName}Mapper;
    }

    private final String WRITE_PERMISSION = "";

    @Override
    public List<Button> getToolBarButtons() {
        return baseGetToolbar(WRITE_PERMISSION, null, writeBtns -> {
            writeBtns.add(Button.BTN_ADD);
            writeBtns.add(Button.BTN_DELETE);
        });
    }

    @Override
    public List<Button> getOperatingButtons() {
        return baseGetOperatingButtons(WRITE_PERMISSION, readBtns -> {
            readBtns.add(Button.OPTION_INFO);
        }, writeBtns -> {
            writeBtns.add(Button.OPTION_EDIT);
            writeBtns.add(Button.OPTION_DELETE);
        });
    }

}