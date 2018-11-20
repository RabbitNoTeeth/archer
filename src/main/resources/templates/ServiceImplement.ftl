package ${packageName};

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ${modelQualifiedName};
import ${baseMapperQualifiedName};
import ${mapperQualifiedName};
import ${serviceQualifiedName};
import ${baseServiceQualifiedName};
import ${abstractBaseServiceQualifiedName};

@Service
public class ${modelName}ServiceImpl extends AbstractBaseService<${modelName}> implements ${modelName}Service {

    @Autowired
    private ${modelName}Mapper ${modelVariableName}Mapper;

    @Override
    public BaseMapper<${modelName}> getMapper() {
        return ${modelVariableName}Mapper;
    }

}