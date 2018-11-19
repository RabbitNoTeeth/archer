package `fun`.bookish.plugin.archer.actions

import `fun`.bookish.plugin.archer.templates.ServiceInterfaceTemplate
import com.intellij.ide.highlighter.JavaFileType
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.LangDataKeys
import com.intellij.openapi.application.runWriteAction
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.util.PsiTreeUtil


/**
 * 生成service接口的action
 */
class ServiceInterfaceGeneratorAction : AnAction("`fun`.bookish.plugin.archer.actions.ServiceInterfaceGeneratorAction") {

    override fun actionPerformed(event: AnActionEvent) {
        val project = event.project!!
        val editor = event.getData(CommonDataKeys.EDITOR)
        val psiFile = event.getData(LangDataKeys.PSI_FILE)
        val element = psiFile!!.findElementAt(editor!!.caretModel.offset)
        val psiClass = PsiTreeUtil.getParentOfType(element, PsiClass::class.java)!!

        // model实体类名
        val modelName = psiClass.name!!
        // service接口文件内容
        val content = ServiceInterfaceTemplate.generate(project, psiClass)
        // 生成service接口文件
        WriteCommandAction.runWriteCommandAction(project){
            runWriteAction {
                val mapperInterfaceFile = PsiFileFactory.getInstance(project)
                        .createFileFromText("${modelName}Service.java", JavaFileType.INSTANCE, content)
                psiFile.parent!!.add(mapperInterfaceFile)
            }
        }

    }

}