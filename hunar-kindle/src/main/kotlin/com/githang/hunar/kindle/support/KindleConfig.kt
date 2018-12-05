package com.githang.hunar.kindle.support

import java.util.*

/**
 * @author haohang (msdx.android@qq.com)
 * @version 2018-12-03
 * @since 2018-12-03
 */
object KindleConfig {
    const val MAILER_SMTP_USER = "mailer.smtp-user"
    const val MAILER_SMTP_PASSWORD = "mailer.smtp-password"
    const val MAILER_SMTP_HOST ="mailer.smtp-host"
    const val MAILER_SMTP_PORT = "mailer.smtp-port"

    private val properties by lazy {
      Properties().apply {
          val fis = ClassLoader.getSystemClassLoader().getResourceAsStream("hunar-kindle.properties")
          load(fis)
          fis.close()
      }
    }

    fun get(key: String): String {
        return properties[key].toString()
    }
}