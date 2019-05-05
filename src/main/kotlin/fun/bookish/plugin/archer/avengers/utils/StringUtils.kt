package `fun`.bookish.plugin.archer.avengers.utils

import java.util.regex.Pattern


val pattern = Pattern.compile("[A-Z]")!!

fun fieldName2ColumnName(fieldName: String): String {
    var res = fieldName
    val matcher = pattern.matcher(fieldName)
    while (matcher.find()){
        val word = matcher.group()
        res = res.replace(word, "_${word.toLowerCase()}")
    }
    return res
}
