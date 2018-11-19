package `fun`.bookish.plugin.archer.templates

import `fun`.bookish.plugin.archer.utils.JAVA_NEW_LINE

object ServiceInterfaceTemplate {

    fun generate(packageName: String, modelName: String): String{
        return StringBuilder().apply {
            append("package $packageName;")
            append(JAVA_NEW_LINE)
            append(JAVA_NEW_LINE)
            append(JAVA_NEW_LINE)
            append("public interface ${modelName}Service extends BaseService<$modelName> {")
            append(JAVA_NEW_LINE)
            append("}")
        }.toString()
    }

}