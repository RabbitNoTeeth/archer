package `fun`.bookish.plugin.archer.template

import java.io.ByteArrayOutputStream
import java.util.HashMap

object Template {

    private val cache = HashMap<String, String>()

    /**
     * 加载模版文件内容
     */
    private fun loadTemplate(name: String): String{
        if(cache[name] == null){
            val inputStream = this.javaClass.classLoader.getResourceAsStream("template/$name")
            inputStream.use { ins ->
                ByteArrayOutputStream().use {out ->
                    var c = ins.read()
                    while (c != -1){
                        out.write(c)
                        c = ins.read()
                    }
                    cache[name] = out.toString("utf-8")
                }
            }
        }
        return cache[name]!!
    }

    /**
     * 进行变量替换
     */
    fun get(name: String, data: Map<String, String>): String{
        var template = loadTemplate(name)
        for((key, value) in data){
           template = template.replace("\${$key}", value)
        }
        return template
    }

}