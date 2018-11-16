package ${package};

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ${modelName}Mapper extends BaseMapper<${modelName}>{

}