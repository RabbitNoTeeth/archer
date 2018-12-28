package `fun`.bookish.plugin.archer.version2.ironman.actions

import `fun`.bookish.plugin.archer.version2.ironman.utils.FileGenerateUtil
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent


/**
 * 生成service接口实现类的action
 */
class ServiceImplGenAction : AnAction("`fun`.bookish.plugin.archer.actions.ServiceImplGenAction") {

    override fun actionPerformed(event: AnActionEvent) {
        FileGenerateUtil.createServiceImpl(event)
    }

}