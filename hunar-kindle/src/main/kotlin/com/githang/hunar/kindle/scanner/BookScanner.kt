package com.githang.hunar.kindle.scanner

import com.githang.hunar.kindle.KindleConfig
import com.githang.hunar.kindle.entity.Book
import com.githang.hunar.kindle.support.OrmUtil
import com.githang.hunar.kindle.support.PreferenceCache
import org.hibernate.Session
import org.slf4j.LoggerFactory
import java.io.File
import kotlin.concurrent.thread

/**
 * @author haohang (msdx.android@qq.com)
 * @version 2018-12-04
 * @since 2018-12-04
 */
class BookScanner(dir: String) {

    private val booksDir: File = File(dir)
    private var count = 0
    private var isStarted = false

    private val session by lazy { OrmUtil.session }
    private var lastScanTime: Long = 0

    init {
        if (dir.isEmpty()) {
            throw IllegalArgumentException("Please config [${KindleConfig.BOOKS_DIR}] on hunar-kindle.properties")
        }
        if (!booksDir.exists() || !booksDir.isDirectory) {
            log.error("The books dir $dir is not exists or is not a directory")
        }
    }

    fun start() {
        if (isStarted) {
            log.warn("Book scanner is started")
            return
        }
        log.info("Start book scanning")
        isStarted = true
        lastScanTime = PreferenceCache.scanTime
        thread {
            val currentScanTime = System.currentTimeMillis()
            val transaction = session.beginTransaction()
            try {
                scanAndUpdate(booksDir, session)
                transaction.commit()
                PreferenceCache.scanTime = currentScanTime
                log.info("Scan finished. Find new book: $count")
            } catch (e: Exception) {
                log.error("Scan failed", e)
                transaction.rollback()
            }
        }
    }

    private fun scanAndUpdate(file: File, session: Session) {
        file.listFiles { _, s -> !s.startsWith(".git")  && !s.endsWith(".md") }
            .forEach {
                if (it.isFile && it.lastModified() > lastScanTime) {
                    count++
                    val book = Book(it.name, it.extension, it.absolutePath, it.length(), it.lastModified())
                    session.saveOrUpdate(book)
                } else if (it.isDirectory) {
                    scanAndUpdate(it, session)
                }
            }
    }

    companion object {
        private val log = LoggerFactory.getLogger(BookScanner::class.java)
    }
}