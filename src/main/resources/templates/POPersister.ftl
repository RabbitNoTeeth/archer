package ${packageName};

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ${modelQualifiedName};
import ${basePOMapperQualifiedName};
import ${basePOPersisterQualifiedName};
import ${pOMapperQualifiedName};

import java.util.List;

@Service
public class ${modelName}Persister implements BasePOPersister<${modelName}> {

    @Autowired
    private ${modelName}Mapper ${modelVariableName}Mapper;

    @Override
    public BasePOMapper<${modelName}> getMapper() {
        return ${modelVariableName}Mapper;
    }

}