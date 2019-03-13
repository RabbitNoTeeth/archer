package `fun`.bookish.plugin.archer.torqueverify.mysql.v1.actions

import `fun`.bookish.plugin.archer.torqueverify.mysql.v1.utils.FileGenerateUtil
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