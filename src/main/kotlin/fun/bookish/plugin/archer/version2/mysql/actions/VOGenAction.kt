package `fun`.bookish.plugin.archer.version2.mysql.actions

import `fun`.bookish.plugin.archer.version2.mysql.utils.FileGenerateUtil
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent


/**
 * 生成vo的action
 */
class VOGenAction : AnAction("`fun`.bookish.plugin.archer.actions.VOGenAction") {

    override fun actionPerformed(event: AnActionEvent) {
        FileGenerateUtil.createVO(event)
    }

}