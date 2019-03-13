package `fun`.bookish.plugin.archer.version2.mysql.actions

import `fun`.bookish.plugin.archer.version2.mysql.utils.FileGenerateUtil
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent


/**
 * 生成persister实现类的action
 */
class POServiceGenAction : AnAction("`fun`.bookish.plugin.archer.actions.POServiceGenAction") {

    override fun actionPerformed(event: AnActionEvent) {
        FileGenerateUtil.createPOService(event)
    }

}