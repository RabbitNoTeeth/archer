package `fun`.bookish.plugin.archer.version2.ironman.actions

import `fun`.bookish.plugin.archer.version2.ironman.utils.FileGenerateUtil
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent


/**
 * 生成service接口的action
 */
class ServiceGenAction : AnAction("`fun`.bookish.plugin.archer.actions.ServiceGenAction") {

    override fun actionPerformed(event: AnActionEvent) {
        FileGenerateUtil.createService(event)
    }

}