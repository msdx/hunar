package com.githang.hunar.kindle

import com.githang.hunar.kindle.api.ApiServlet
import com.githang.hunar.kindle.scanner.BookScanner
import com.githang.hunar.kindle.sender.BookSender
import com.githang.hunar.kindle.support.PreferenceCache
import com.github.yoojia.web.server.EmbeddedServer
import org.slf4j.LoggerFactory

/**
 * @author haohang (msdx.android@qq.com)
 * @version 2018-12-03
 * @since 2018-12-03
 */
object KindleLauncher {
    private val log = LoggerFactory.getLogger(KindleLauncher::class.java)

    @JvmStatic
    fun main(args: Array<String>) {
        val bookScanner = BookScanner()
        bookScanner.startScan(PreferenceCache.bookDir)

        val server = EmbeddedServer(8117)
        server.setBootstrapServlet(ApiServlet::class.java)
        server.start()
        log.info("Kindle service started")
        BookSender.notifyToSend()
    }
}
