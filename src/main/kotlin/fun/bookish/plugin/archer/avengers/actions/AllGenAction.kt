package `fun`.bookish.plugin.archer.avengers.actions

import `fun`.bookish.plugin.archer.avengers.utils.FileGenerateUtil
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent


/**
 * 一键生成action
 */
class AllGenAction : AnAction("`fun`.bookish.plugin.archer.actions.AllGenAction") {

    override fun actionPerformed(event: AnActionEvent) {
        FileGenerateUtil.createMapper(event)
        FileGenerateUtil.createMapperXml(event)
        FileGenerateUtil.createService(event)
        FileGenerateUtil.createServiceImpl(event)
        FileGenerateUtil.createController(event)
    }

}