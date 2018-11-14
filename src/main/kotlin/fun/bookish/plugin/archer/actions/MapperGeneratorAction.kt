package `fun`.bookish.plugin.archer.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.LangDataKeys
import com.intellij.psi.PsiClass
import com.intellij.psi.util.PsiTreeUtil


/**
 * 生成mapper接口的action
 */
class MapperGeneratorAction : AnAction("`fun`.bookish.plugin.archer.actions.MapperGeneratorAction") {

    override fun actionPerformed(event: AnActionEvent) {
        val editor = event.getData(CommonDataKeys.EDITOR)
        val psiFile = event.getData(LangDataKeys.PSI_FILE)
        val element = psiFile!!.findElementAt(editor!!.caretModel.offset)
        val psiClass = PsiTreeUtil.getParentOfType(element, PsiClass::class.java)
        psiClass!!.allFields.forEach {
            println(it.name)
        }
    }

}