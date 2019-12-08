package com.safframework.log.handler

import com.alibaba.fastjson.JSON
import com.safframework.log.L
import com.safframework.log.LogLevel
import com.safframework.log.LoggerPrinter
import com.safframework.log.extension.formatJSON
import com.safframework.log.utils.toJavaClass
import org.json.JSONObject

/**
 * Created by tony on 2017/11/27.
 */
class ObjectHandler:BaseHandler() {

    override fun handle(obj: Any, logLevel: LogLevel, tag: String): Boolean {

        L.printers().map {

            val formatter = it.formatter

            var msg = obj.toJavaClass() + LoggerPrinter.BR + formatter.spliter()

            val message = JSON.toJSONString(obj).run {
                JSONObject(this)
            }
            .formatJSON()
            .run {
                replace("\n", "\n${formatter.spliter()}")
            }

            val s = L.getMethodNames(formatter)
            it.printLog(logLevel,tag,String.format(s, msg + message))
        }
        return true
    }
}