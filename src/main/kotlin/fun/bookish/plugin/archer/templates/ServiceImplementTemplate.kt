package `fun`.bookish.plugin.archer.templates

import `fun`.bookish.plugin.archer.utils.JAVA_NEW_LINE

object ServiceImplementTemplate {

    fun generate(packageName: String, modelName: String): String{
        return StringBuilder().apply {
            append("package $packageName;")
            append(JAVA_NEW_LINE)
            append(JAVA_NEW_LINE)
            append("import org.springframework.beans.factory.annotation.Autowired;")
            append(JAVA_NEW_LINE)
            append("import org.springframework.stereotype.Service;")
            append(JAVA_NEW_LINE)
            append(JAVA_NEW_LINE)
            append("@Service")
            append(JAVA_NEW_LINE)
            append("public class ${modelName}ServiceImpl extends AbstractBaseService<$modelName> implements ${modelName}Service {")
            append(JAVA_NEW_LINE)
            append(JAVA_NEW_LINE)
            append("    @Autowired")
            append(JAVA_NEW_LINE)
            append("    private ${modelName}Mapper ${modelName[0].toLowerCase() + modelName.substring(1)}Mapper;")
            append(JAVA_NEW_LINE)
            append(JAVA_NEW_LINE)
            append("    @Override")
            append(JAVA_NEW_LINE)
            append("    public BaseMapper<$modelName> getMapper() {")
            append(JAVA_NEW_LINE)
            append("        return ${modelName[0].toLowerCase() + modelName.substring(1)}Mapper;")
            append(JAVA_NEW_LINE)
            append("    }")
            append(JAVA_NEW_LINE)
            append(JAVA_NEW_LINE)
            append("}")
        }.toString()
    }

}