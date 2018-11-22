package ${packageName};

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ${buttonQualifiedName};
import ${modelQualifiedName};
import ${baseMapperQualifiedName};
import ${mapperQualifiedName};
import ${serviceQualifiedName};
import ${baseServiceQualifiedName};
import ${abstractBaseServiceQualifiedName};

import java.util.List;

@Service
public class ${modelName}ServiceImpl extends AbstractBaseService<${modelName}> implements ${modelName}Service {

    @Autowired
    private ${modelName}Mapper ${modelVariableName}Mapper;

    @Override
    public BaseMapper<${modelName}> getMapper() {
        return ${modelVariableName}Mapper;
    }

    @Override
    public List<Button> getToolBarButtons() {
        return baseGetToolbar("", readBtns -> {

        }, writeBtns -> {
            writeBtns.add(Button.TOOLBAR_ADD);
            writeBtns.add(Button.TOOLBAR_DELETE_BATCH);
        });
    }

    @Override
    public List<Button> getOperatingButtons() {
        return baseGetOperatingButtons("", readBtns -> {
            readBtns.add(Button.OPERATION_INFO);
        }, writeBtns -> {
            writeBtns.add(Button.OPERATION_EDIT);
            writeBtns.add(Button.OPERATION_DELETE);
        });
    }

}