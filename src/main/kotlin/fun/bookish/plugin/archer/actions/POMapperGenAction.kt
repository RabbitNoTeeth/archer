package `fun`.bookish.plugin.archer.actions

import `fun`.bookish.plugin.archer.utils.FileGenerateUtil
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent


/**
 * 生成mapper接口的action
 */
class POMapperGenAction : AnAction("`fun`.bookish.plugin.archer.actions.POMapperGenAction") {

    override fun actionPerformed(event: AnActionEvent) {
        FileGenerateUtil.createPOMapper(event)
    }

}