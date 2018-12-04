package com.githang.hunar.kindle.api

import com.githang.hunar.kindle.api.service.ConfigService
import com.github.yoojia.web.Context
import com.github.yoojia.web.ProvidedServlet

/**
 * @author haohang (msdx.android@qq.com)
 * @version 2018-12-03
 * @since 2018-12-03
 */
class ApiServlet : ProvidedServlet() {
    override fun getClasses(context: Context): List<Class<*>> {
        return from(ConfigService::class.java)
    }
}