package `fun`.bookish.plugin.archer.templates

import `fun`.bookish.plugin.archer.utils.JAVA_NEW_LINE
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiClass
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.search.PsiShortNamesCache

object MapperInterfaceTemplate {

    fun generate(project: Project, psiClass: PsiClass): String{
        val packageName = psiClass.qualifiedName!!.substringBeforeLast(".")
        val modelName = psiClass.name!!
        val modelQualifiedName = psiClass.qualifiedName!!
        val baseMapperQualifiedName = PsiShortNamesCache.getInstance(project)
                            .getClassesByName("BaseMapper", GlobalSearchScope.projectScope(project))[0]
                            .qualifiedName
        return StringBuilder().apply {
            append("package $packageName;")
            append(JAVA_NEW_LINE)
            append(JAVA_NEW_LINE)
            append("import org.apache.ibatis.annotations.Mapper;")
            append(JAVA_NEW_LINE)
            append("import org.springframework.stereotype.Component;")
            append(JAVA_NEW_LINE)
            append("import $baseMapperQualifiedName;")
            append(JAVA_NEW_LINE)
            append("import $modelQualifiedName;")
            append(JAVA_NEW_LINE)
            append(JAVA_NEW_LINE)
            append("@Mapper")
            append(JAVA_NEW_LINE)
            append("@Component")
            append(JAVA_NEW_LINE)
            append("public interface ${modelName}Mapper extends BaseMapper<$modelName> {")
            append(JAVA_NEW_LINE)
            append("}")
        }.toString()
    }

}