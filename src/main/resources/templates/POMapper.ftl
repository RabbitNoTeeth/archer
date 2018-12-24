package ${packageName};

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import ${baseMapperQualifiedName};
import ${modelQualifiedName};

@Mapper
@Component
public interface ${modelName}Mapper extends BasePOMapper<${modelName}> {
}