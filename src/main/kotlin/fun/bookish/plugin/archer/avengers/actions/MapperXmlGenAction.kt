package `fun`.bookish.plugin.archer.avengers.actions

import `fun`.bookish.plugin.archer.avengers.utils.FileGenerateUtil
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent


/**
 * 生成controller的action
 */
class MapperXmlGenAction : AnAction("`fun`.bookish.plugin.archer.actions.MapperXmlGenAction") {

    override fun actionPerformed(event: AnActionEvent) {
        FileGenerateUtil.createMapperXml(event)
    }



}