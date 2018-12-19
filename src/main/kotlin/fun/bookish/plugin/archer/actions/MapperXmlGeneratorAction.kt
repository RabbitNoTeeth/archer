package `fun`.bookish.plugin.archer.actions

import `fun`.bookish.plugin.archer.template.Template
import `fun`.bookish.plugin.archer.utils.fieldName2ColumnName
import com.intellij.ide.highlighter.JavaFileType
import com.intellij.ide.highlighter.XmlFileType
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.LangDataKeys
import com.intellij.openapi.application.runWriteAction
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiField
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.search.PsiShortNamesCache
import com.intellij.psi.util.PsiTreeUtil
import com.sun.org.apache.xpath.internal.operations.Bool
import java.util.HashMap
import javax.swing.JOptionPane


/**
 * 生成controller的action
 */
class MapperXmlGeneratorAction : AnAction("`fun`.bookish.plugin.archer.actions.MapperXmlGeneratorAction") {

    override fun actionPerformed(event: AnActionEvent) {
        val project = event.project!!
        val editor = event.getData(CommonDataKeys.EDITOR)
        val psiFile = event.getData(LangDataKeys.PSI_FILE)
        val element = psiFile!!.findElementAt(editor!!.caretModel.offset)
        val psiClass = PsiTreeUtil.getParentOfType(element, PsiClass::class.java)!!

        // 模版变量值
        val modelName = psiClass.name!!
        val modelVariableName = modelName[0].toLowerCase() + modelName.substring(1)
        val modelQualifiedName = psiClass.qualifiedName!!
        val mapperQualifiedName = PsiShortNamesCache.getInstance(project)
                .getClassesByName("${modelName}Mapper", GlobalSearchScope.projectScope(project))[0]
                .qualifiedName!!
        var resultItems = ""
        var baseSaveColumns = ""
        var baseSaveValues = ""
        var baseSaveBatchValues = ""
        var baseUpdateItems = ""
        psiClass.fields.forEach { field ->
            val fieldName = field.name
            if(fieldName != "serialVersionUID" && isBaseType(field)){
                resultItems += "\n\t\t<result property=\"$fieldName\" column=\"${fieldName2ColumnName(fieldName)}\"/>"
                baseSaveColumns += ",${fieldName2ColumnName(fieldName)}"
                baseSaveValues += ",#{$fieldName}"
                baseSaveBatchValues +=",#{item.$fieldName}"
                baseUpdateItems += if(isStringType(field)){
                    "\n\t\t\t<if test=\"$fieldName != null and $fieldName != ''\">\n\t\t\t\t${fieldName2ColumnName(fieldName)} = #{$fieldName},\n\t\t\t</if>"
                }else{
                    "\n\t\t\t<if test=\"$fieldName != null\">\n\t\t\t\t${fieldName2ColumnName(fieldName)} = #{$fieldName},\n\t\t\t</if>"
                }
            }
        }
        val data = HashMap<String, String>().apply {
            put("modelName", modelName)
            put("modelQualifiedName", modelQualifiedName)
            put("mapperQualifiedName", mapperQualifiedName)
            put("modelVariableName", modelVariableName)
            put("resultItems", resultItems)
            put("baseSaveColumns", baseSaveColumns)
            put("baseSaveValues", baseSaveValues)
            put("baseSaveBatchValues", baseSaveBatchValues)
            put("baseUpdateItems", baseUpdateItems)
        }
        // 进行模版变量替换
        val content = Template.get("MapperXml.ftl", data).replace("\r\n", "\n")
        // 生成service接口实现类文件
        WriteCommandAction.runWriteCommandAction(project){
            runWriteAction {
                val mapperInterfaceFile = PsiFileFactory.getInstance(project)
                        .createFileFromText("${modelName}Mapper.xml", XmlFileType.INSTANCE, content)
                psiFile.parent!!.add(mapperInterfaceFile)
            }
        }

    }

    /**
     * 是否是基础数据类型
     */
    private fun isBaseType(psiField: PsiField): Boolean{
        val type = psiField.type.presentableText
        return type == "String" || type == "Integer" || type == "Short" || type == "Long" || type == "Double" || type == "Float"
    }

    /**
     * 是否是string类型
     */
    private fun isStringType(psiField: PsiField): Boolean{
        return psiField.type.presentableText == "String"
    }

}