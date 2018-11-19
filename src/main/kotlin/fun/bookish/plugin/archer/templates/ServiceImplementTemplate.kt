package `fun`.bookish.plugin.archer.templates

import `fun`.bookish.plugin.archer.utils.JAVA_NEW_LINE
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiClass
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.search.PsiShortNamesCache

object ServiceImplementTemplate {

    fun generate(project: Project, psiClass: PsiClass): String{
        val packageName = psiClass.qualifiedName!!.substringBeforeLast(".")
        val modelName = psiClass.name!!
        val modelQualifiedName = psiClass.qualifiedName!!
        val baseMapperQualifiedName = PsiShortNamesCache.getInstance(project)
                .getClassesByName("BaseMapper", GlobalSearchScope.projectScope(project))[0]
                .qualifiedName
        val mapperQualifiedName = PsiShortNamesCache.getInstance(project)
                .getClassesByName("${modelName}Mapper", GlobalSearchScope.projectScope(project))[0]
                .qualifiedName
        val baseServiceQualifiedName = PsiShortNamesCache.getInstance(project)
                .getClassesByName("BaseService", GlobalSearchScope.projectScope(project))[0]
                .qualifiedName
        val abstractBaseServiceQualifiedName = PsiShortNamesCache.getInstance(project)
                .getClassesByName("AbstractBaseService", GlobalSearchScope.projectScope(project))[0]
                .qualifiedName
        return StringBuilder().apply {
            append("package $packageName;")
            append(JAVA_NEW_LINE)
            append(JAVA_NEW_LINE)
            append("import org.springframework.beans.factory.annotation.Autowired;")
            append(JAVA_NEW_LINE)
            append("import org.springframework.stereotype.Service;")
            append(JAVA_NEW_LINE)
            append("import $modelQualifiedName;")
            append(JAVA_NEW_LINE)
            append("import $baseMapperQualifiedName;")
            append(JAVA_NEW_LINE)
            append("import $mapperQualifiedName;")
            append(JAVA_NEW_LINE)
            append("import $baseServiceQualifiedName;")
            append(JAVA_NEW_LINE)
            append("import $abstractBaseServiceQualifiedName;")
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