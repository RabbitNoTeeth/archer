package `fun`.bookish.plugin.archer.actions

import `fun`.bookish.plugin.archer.utils.FileGenerateUtil
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent


/**
 * 一键生成action
 */
class AllGenAction : AnAction("`fun`.bookish.plugin.archer.actions.AllGenAction") {

    override fun actionPerformed(event: AnActionEvent) {
        FileGenerateUtil.createPOMapper(event)
        FileGenerateUtil.createPOMapperXml(event)
        FileGenerateUtil.createPOPersister(event)
        FileGenerateUtil.createVO(event)
        FileGenerateUtil.createService(event)
        FileGenerateUtil.createServiceImpl(event)
        FileGenerateUtil.createController(event)
    }

}