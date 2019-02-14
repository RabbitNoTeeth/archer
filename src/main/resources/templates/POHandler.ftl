package ${packageName};

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ${modelQualifiedName};
import ${basePOMapperQualifiedName};
import ${basePOHandlerQualifiedName};
import ${pOMapperQualifiedName};

import java.util.List;

@Service
public class ${modelName}Handler implements BasePOHandler<${modelName}> {

    @Autowired
    private ${modelName}Mapper ${modelVariableName}Mapper;

    @Override
    public BasePOMapper<${modelName}> getMapper() {
        return ${modelVariableName}Mapper;
    }

}