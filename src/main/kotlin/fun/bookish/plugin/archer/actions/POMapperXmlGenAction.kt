package `fun`.bookish.plugin.archer.actions

import `fun`.bookish.plugin.archer.utils.FileGenerateUtil
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent


/**
 * 生成controller的action
 */
class POMapperXmlGenAction : AnAction("`fun`.bookish.plugin.archer.actions.POMapperXmlGenAction") {

    override fun actionPerformed(event: AnActionEvent) {
        FileGenerateUtil.createPOMapperXml(event)
    }



}