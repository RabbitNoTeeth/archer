package `fun`.bookish.plugin.archer.actions

import `fun`.bookish.plugin.archer.template.Template
import com.intellij.ide.highlighter.JavaFileType
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.LangDataKeys
import com.intellij.openapi.application.runWriteAction
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.search.PsiShortNamesCache
import com.intellij.psi.util.PsiTreeUtil
import java.util.HashMap


/**
 * 生成mapper接口的action
 */
class MapperInterfaceGeneratorAction : AnAction("`fun`.bookish.plugin.archer.actions.MapperInterfaceGeneratorAction") {

    override fun actionPerformed(event: AnActionEvent) {
        val project = event.project!!
        val editor = event.getData(CommonDataKeys.EDITOR)
        val psiFile = event.getData(LangDataKeys.PSI_FILE)
        val element = psiFile!!.findElementAt(editor!!.caretModel.offset)
        val psiClass = PsiTreeUtil.getParentOfType(element, PsiClass::class.java)!!

        // 模版中变量值
        val packageName = psiClass.qualifiedName!!.substringBeforeLast(".")
        val modelName = psiClass.name!!
        val modelQualifiedName = psiClass.qualifiedName!!
        val baseMapperQualifiedName = PsiShortNamesCache.getInstance(project)
                                                        .getClassesByName("BaseMapper", GlobalSearchScope.projectScope(project))[0]
                                                        .qualifiedName!!
        val data = HashMap<String, String>().apply {
            put("packageName", packageName)
            put("modelName", modelName)
            put("modelQualifiedName", modelQualifiedName)
            put("baseMapperQualifiedName", baseMapperQualifiedName)
        }
        // 进行模版变量替换
        val content = Template.get("MapperInterface.ftl", data).replace("\r\n", "\n")
        // 生成mapper接口文件
        WriteCommandAction.runWriteCommandAction(project){
            runWriteAction {
                val mapperInterfaceFile = PsiFileFactory.getInstance(project)
                        .createFileFromText("${modelName}Mapper.java", JavaFileType.INSTANCE, content)
                psiFile.parent!!.add(mapperInterfaceFile)
            }
        }

    }

}