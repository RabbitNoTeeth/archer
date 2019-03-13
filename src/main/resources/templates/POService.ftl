package ${packageName};

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ${modelQualifiedName};
import ${basePOMapperQualifiedName};
import ${basePOPersisterQualifiedName};
import ${pOMapperQualifiedName};

import java.util.List;

@Service
public class ${modelName}Service implements BaseService<${modelName}> {

    @Autowired
    private ${modelName}Mapper ${modelVariableName}Mapper;

    @Override
    public BaseMapper<${modelName}> getMapper() {
        return ${modelVariableName}Mapper;
    }

}