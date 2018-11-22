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
 * 生成controller的action
 */
class ControllerGeneratorAction : AnAction("`fun`.bookish.plugin.archer.actions.ControllerGeneratorAction") {

    override fun actionPerformed(event: AnActionEvent) {
        val project = event.project!!
        val editor = event.getData(CommonDataKeys.EDITOR)
        val psiFile = event.getData(LangDataKeys.PSI_FILE)
        val element = psiFile!!.findElementAt(editor!!.caretModel.offset)
        val psiClass = PsiTreeUtil.getParentOfType(element, PsiClass::class.java)!!

        // 模版变量值
        val packageName = psiClass.qualifiedName!!.substringBeforeLast(".")
        val modelName = psiClass.name!!
        val modelVariableName = modelName[0].toLowerCase() + modelName.substring(1)
        val modelQualifiedName = psiClass.qualifiedName!!
        val readAccessQualifiedName = PsiShortNamesCache.getInstance(project)
                .getClassesByName("ReadAccess", GlobalSearchScope.projectScope(project))[0]
                .qualifiedName!!
        val writeAccessQualifiedName = PsiShortNamesCache.getInstance(project)
                .getClassesByName("WriteAccess", GlobalSearchScope.projectScope(project))[0]
                .qualifiedName!!
        val serviceQualifiedName = PsiShortNamesCache.getInstance(project)
                .getClassesByName("${modelName}Service", GlobalSearchScope.projectScope(project))[0]
                .qualifiedName!!
        val jsonResultQualifiedName = PsiShortNamesCache.getInstance(project)
                .getClassesByName("JsonResult", GlobalSearchScope.projectScope(project))[0]
                .qualifiedName!!
        val data = HashMap<String, String>().apply {
            put("packageName", packageName)
            put("modelName", modelName)
            put("modelQualifiedName", modelQualifiedName)
            put("readAccessQualifiedName", readAccessQualifiedName)
            put("modelVariableName", modelVariableName)
            put("writeAccessQualifiedName", writeAccessQualifiedName)
            put("jsonResultQualifiedName", jsonResultQualifiedName)
            put("serviceQualifiedName", serviceQualifiedName)
        }
        // 进行模版变量替换
        val content = Template.get("Controller.ftl", data).replace("\r\n", "\n")
        // 生成service接口实现类文件
        WriteCommandAction.runWriteCommandAction(project){
            runWriteAction {
                val mapperInterfaceFile = PsiFileFactory.getInstance(project)
                        .createFileFromText("${modelName}Controller.java", JavaFileType.INSTANCE, content)
                psiFile.parent!!.add(mapperInterfaceFile)
            }
        }

    }

}