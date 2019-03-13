package `fun`.bookish.plugin.archer.torqueverify.mysql.v1.actions

import `fun`.bookish.plugin.archer.torqueverify.mysql.v1.utils.FileGenerateUtil
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent


/**
 * 生成controller的action
 */
class ControllerGenAction : AnAction("`fun`.bookish.plugin.archer.actions.ControllerGenAction") {

    override fun actionPerformed(event: AnActionEvent) {
        FileGenerateUtil.createController(event)
    }

}