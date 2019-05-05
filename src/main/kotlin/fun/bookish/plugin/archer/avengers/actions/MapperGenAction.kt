package `fun`.bookish.plugin.archer.avengers.actions

import `fun`.bookish.plugin.archer.avengers.utils.FileGenerateUtil
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent


/**
 * 生成mapper接口的action
 */
class MapperGenAction : AnAction("`fun`.bookish.plugin.archer.actions.MapperGenAction") {

    override fun actionPerformed(event: AnActionEvent) {
        FileGenerateUtil.createMapper(event)
    }

}