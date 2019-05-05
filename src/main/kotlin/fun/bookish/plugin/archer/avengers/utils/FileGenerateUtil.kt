package `fun`.bookish.plugin.archer.avengers.utils

import `fun`.bookish.plugin.archer.avengers.template.Template
import com.intellij.ide.highlighter.JavaFileType
import com.intellij.ide.highlighter.XmlFileType
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
import java.util.HashMap


object FileGenerateUtil {

    /**
     * 创建Mapper文件
     */
    fun createMapper(event: AnActionEvent){
        val project = event.project!!
        val editor = event.getData(CommonDataKeys.EDITOR)
        val psiFile = event.getData(LangDataKeys.PSI_FILE)
        val element = psiFile!!.findElementAt(editor!!.caretModel.offset)
        val psiClass = PsiTreeUtil.getParentOfType(element, PsiClass::class.java)!!

        // 模版中变量值
        val packageName = psiClass.qualifiedName!!.substringBeforeLast(".")
        val modelName = psiClass.name!!
        val modelQualifiedName = psiClass.qualifiedName!!
        val baseMapperQualifiedName = PsiShortNamesCache.getInstance(project)
                .getClassesByName("BaseMapper", GlobalSearchScope.projectScope(project))[0]
                .qualifiedName!!
        val data = HashMap<String, String>().apply {
            put("packageName", packageName)
            put("modelName", modelName)
            put("modelQualifiedName", modelQualifiedName)
            put("baseMapperQualifiedName", baseMapperQualifiedName)
        }

        // 进行模版变量替换
        val content = Template.get("Mapper.ftl", data).replace("\r\n", "\n")

        // 生成mapper接口文件
        WriteCommandAction.runWriteCommandAction(project){
            runWriteAction {
                // 创建mapper文件
                val mapperInterfaceFile = PsiFileFactory.getInstance(project)
                        .createFileFromText("${modelName}Mapper.java", JavaFileType.INSTANCE, content)
                // 创建mapper包并将文件放入包中
                val moduleDirectory = psiFile.parent!!.parent!!
                var targetDirectory = moduleDirectory.findSubdirectory("mapper")
                if(targetDirectory == null){
                    targetDirectory = moduleDirectory.createSubdirectory("mapper")
                }
                targetDirectory.add(mapperInterfaceFile)
            }
        }
    }

    /**
     * 创建Mapper xml文件
     */
    fun createMapperXml(event: AnActionEvent){
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
        val tableNameAnnotationQualifiedName = PsiShortNamesCache.getInstance(project)
                .getClassesByName("TableName", GlobalSearchScope.projectScope(project))[0]
                .qualifiedName!!
        val tableName = psiClass.annotations.first { it.qualifiedName!! == tableNameAnnotationQualifiedName }
                .findAttributeValue("value")!!.text.replace("\"","")
        var resultItems = ""
        var baseSaveColumns = ""
        var baseSaveValues = ""
        var baseSaveBatchValues = ""
        var baseUpdateItems = ""
        var baseUpdateBatchItems = ""
        var baseFindListByParamsItems = ""
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

                baseUpdateBatchItems +=
                    "\n\t\t\t${fieldName2ColumnName(fieldName)} = " +
                    "\n\t\t\t<foreach collection=\"beans\" item=\"item\" index=\"index\" separator=\" \" open=\"case id\" close=\"end\">" +
                    "\n\t\t\t\tWHEN #{item.id} THEN #{item.$fieldName}" +
                    "\n\t\t\t</foreach>,"

                baseFindListByParamsItems += if(isStringType(field)){
                    "\n\t\t\t<if test=\"$fieldName != null and $fieldName != ''\">\n\t\t\t\tAND ${fieldName2ColumnName(fieldName)} like CONCAT('%','\${$fieldName}','%')\n\t\t\t</if>"
                }else{
                    "\n\t\t\t<if test=\"$fieldName != null\">\n\t\t\t\tAND ${fieldName2ColumnName(fieldName)} = #{$fieldName}\n\t\t\t</if>"
                }
            }
        }
        val data = HashMap<String, String>().apply {
            put("tableName", tableName)
            put("modelName", modelName)
            put("modelQualifiedName", modelQualifiedName)
            put("mapperQualifiedName", mapperQualifiedName)
            put("modelVariableName", modelVariableName)
            put("resultItems", resultItems)
            put("baseSaveColumns", baseSaveColumns)
            put("baseSaveValues", baseSaveValues)
            put("baseSaveBatchValues", baseSaveBatchValues)
            put("baseUpdateItems", baseUpdateItems)
            put("baseUpdateBatchItems", baseUpdateBatchItems)
            put("baseFindListByParamsItems", baseFindListByParamsItems)
        }

        // 进行模版变量替换
        val content = Template.get("MapperXml.ftl", data).replace("\r\n", "\n")
        // 生成mapper xml文件
        WriteCommandAction.runWriteCommandAction(project){
            runWriteAction {
                val mapperInterfaceFile = PsiFileFactory.getInstance(project)
                        .createFileFromText("${modelName}Mapper.xml", XmlFileType.INSTANCE, content)

                // 创建mapper包并将文件放入包中
                val moduleDirectory = psiFile.parent!!.parent!!
                var targetDirectory = moduleDirectory.findSubdirectory("mapper")
                if(targetDirectory == null){
                    targetDirectory = moduleDirectory.createSubdirectory("mapper")
                }
                targetDirectory.add(mapperInterfaceFile)
            }
        }
    }

    /**
     * 创建service接口
     */
    fun createService(event: AnActionEvent){
        val project = event.project!!
        val editor = event.getData(CommonDataKeys.EDITOR)
        val psiFile = event.getData(LangDataKeys.PSI_FILE)
        val element = psiFile!!.findElementAt(editor!!.caretModel.offset)
        val psiClass = PsiTreeUtil.getParentOfType(element, PsiClass::class.java)!!

        // 模版变量值
        val packageName = psiClass.qualifiedName!!.substringBeforeLast(".")
        val modelName = psiClass.name!!.replace("PO", "")
        val buttonQualifiedName = PsiShortNamesCache.getInstance(project)
                .getClassesByName("Button", GlobalSearchScope.projectScope(project))[0]
                .qualifiedName!!
        val baseServiceQualifiedName = PsiShortNamesCache.getInstance(project)
                .getClassesByName("BaseService", GlobalSearchScope.projectScope(project))[0]
                .qualifiedName!!

        val data = HashMap<String, String>().apply {
            put("packageName", packageName)
            put("modelName", modelName)
            put("buttonQualifiedName", buttonQualifiedName)
            put("baseServiceQualifiedName", baseServiceQualifiedName)
        }

        // 进行模版变量替换
        val content = Template.get("Service.ftl", data).replace("\r\n", "\n")

        // 生成service接口文件
        WriteCommandAction.runWriteCommandAction(project){
            runWriteAction {
                // 创建service文件
                val serviceInterfaceFile = PsiFileFactory.getInstance(project)
                        .createFileFromText("${modelName}Service.java", JavaFileType.INSTANCE, content)
                // 创建service包并将文件放入包中
                val moduleDirectory = psiFile.parent!!.parent!!
                var targetDirectory = moduleDirectory.findSubdirectory("service")
                if(targetDirectory == null){
                    targetDirectory = moduleDirectory.createSubdirectory("service")
                }
                targetDirectory.add(serviceInterfaceFile)
            }
        }
    }

    /**
     * 创建service接口实现
     */
    fun createServiceImpl(event: AnActionEvent){
        val project = event.project!!
        val editor = event.getData(CommonDataKeys.EDITOR)
        val psiFile = event.getData(LangDataKeys.PSI_FILE)
        val element = psiFile!!.findElementAt(editor!!.caretModel.offset)
        val psiClass = PsiTreeUtil.getParentOfType(element, PsiClass::class.java)!!

        // 模版变量值
        val packageName = psiClass.qualifiedName!!.substringBeforeLast(".")
        val modelName = psiClass.name!!
        val modelVariableName = modelName[0].toLowerCase() + modelName.substring(1)
        val modelMapperQualifiedName = PsiShortNamesCache.getInstance(project)
                .getClassesByName("${modelName}Mapper", GlobalSearchScope.projectScope(project))[0]
                .qualifiedName!!
        val modelServiceQualifiedName = PsiShortNamesCache.getInstance(project)
                .getClassesByName("${modelName}Service", GlobalSearchScope.projectScope(project))[0]
                .qualifiedName!!
        val buttonQualifiedName = PsiShortNamesCache.getInstance(project)
                .getClassesByName("Button", GlobalSearchScope.projectScope(project))[0]
                .qualifiedName!!
        val baseMapperQualifiedName = PsiShortNamesCache.getInstance(project)
                .getClassesByName("BaseMapper", GlobalSearchScope.projectScope(project))[0]
                .qualifiedName!!

        val data = HashMap<String, String>().apply {
            put("packageName", packageName)
            put("modelName", modelName)
            put("modelVariableName", modelVariableName)
            put("buttonQualifiedName", buttonQualifiedName)
            put("modelMapperQualifiedName", modelMapperQualifiedName)
            put("baseMapperQualifiedName", baseMapperQualifiedName)
            put("modelServiceQualifiedName", modelServiceQualifiedName)
        }

        // 进行模版变量替换
        val content = Template.get("ServiceImpl.ftl", data).replace("\r\n", "\n")

        // 生成service实现类文件
        WriteCommandAction.runWriteCommandAction(project){
            runWriteAction {
                // 创建service实现类文件
                val serviceInterfaceFile = PsiFileFactory.getInstance(project)
                        .createFileFromText("${modelName}ServiceImpl.java", JavaFileType.INSTANCE, content)
                // 创建service包并将文件放入包中
                val moduleDirectory = psiFile.parent!!.parent!!
                var targetDirectory = moduleDirectory.findSubdirectory("service")
                if(targetDirectory == null){
                    targetDirectory = moduleDirectory.createSubdirectory("service")
                }
                targetDirectory.add(serviceInterfaceFile)
            }
        }
    }

    /**
     * 创建Controller
     */
    fun createController(event: AnActionEvent){
        val project = event.project!!
        val editor = event.getData(CommonDataKeys.EDITOR)
        val psiFile = event.getData(LangDataKeys.PSI_FILE)
        val element = psiFile!!.findElementAt(editor!!.caretModel.offset)
        val psiClass = PsiTreeUtil.getParentOfType(element, PsiClass::class.java)!!

        // 模版变量值
        val packageName = psiClass.qualifiedName!!.substringBeforeLast(".")
        val modelName = psiClass.name!!.replace("PO", "")
        val modelVariableName = modelName[0].toLowerCase() + modelName.substring(1)

        val readAccessQualifiedName = PsiShortNamesCache.getInstance(project)
                .getClassesByName("ReadAccess", GlobalSearchScope.projectScope(project))[0]
                .qualifiedName!!
        val writeAccessQualifiedName = PsiShortNamesCache.getInstance(project)
                .getClassesByName("WriteAccess", GlobalSearchScope.projectScope(project))[0]
                .qualifiedName!!
        val serviceQualifiedName = PsiShortNamesCache.getInstance(project)
                .getClassesByName("${modelName}Service", GlobalSearchScope.projectScope(project))[0]
                .qualifiedName!!
        val jsonResultQualifiedName = PsiShortNamesCache.getInstance(project)
                .getClassesByName("JsonResult", GlobalSearchScope.projectScope(project))[0]
                .qualifiedName!!

        val modelQualifiedName = PsiShortNamesCache.getInstance(project)
                .getClassesByName("${modelName}", GlobalSearchScope.projectScope(project))[0]
                .qualifiedName!!

        val data = HashMap<String, String>().apply {
            put("packageName", packageName)
            put("modelName", modelName)
            put("readAccessQualifiedName", readAccessQualifiedName)
            put("modelVariableName", modelVariableName)
            put("writeAccessQualifiedName", writeAccessQualifiedName)
            put("jsonResultQualifiedName", jsonResultQualifiedName)
            put("serviceQualifiedName", serviceQualifiedName)
            put("modelQualifiedName", modelQualifiedName)
        }

        // 进行模版变量替换
        val content = Template.get("Controller.ftl", data).replace("\r\n", "\n")

        // 生成controller文件
        WriteCommandAction.runWriteCommandAction(project){
            runWriteAction {
                val mapperInterfaceFile = PsiFileFactory.getInstance(project)
                        .createFileFromText("${modelName}Controller.java", JavaFileType.INSTANCE, content)
                psiFile.parent!!.parent!!.add(mapperInterfaceFile)
            }
        }
    }

    /**
     * 是否是基础数据类型
     */
    private fun isBaseType(psiField: PsiField): Boolean{
        val type = psiField.type.presentableText
        return type == "String" ||
                type == "Integer" ||
                type == "Short" ||
                type == "Long" ||
                type == "Double" ||
                type == "Float" ||
                type == "Boolean" ||
                type == "BigDecimal" ||
                type == "Date"
    }

    /**
     * 是否是string类型
     */
    private fun isStringType(psiField: PsiField): Boolean{
        return psiField.type.presentableText == "String"
    }
}