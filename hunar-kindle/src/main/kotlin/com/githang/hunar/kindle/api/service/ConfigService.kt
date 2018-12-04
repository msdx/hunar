package com.githang.hunar.kindle.api.service

import com.githang.hunar.kindle.KindleConfig
import com.githang.hunar.kindle.api.success
import com.github.yoojia.web.Response
import com.github.yoojia.web.http.Controller
import com.github.yoojia.web.supports.GET

/**
 * @author haohang (msdx.android@qq.com)
 * @version 2018-12-03
 * @since 2018-12-03
 */
@Controller("/config")
class ConfigService {
    @GET("/sender")
    fun showSender(response: Response) {
        response.success(KindleConfig.get(KindleConfig.MAILER_SMTP_USER))
    }
}