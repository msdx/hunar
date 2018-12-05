package com.githang.hunar.kindle.sender

import com.githang.hunar.kindle.KindleConfig
import com.githang.hunar.kindle.entity.SendBook
import com.githang.hunar.kindle.entity.SendRecord
import com.githang.hunar.kindle.support.OrmUtil
import com.sun.mail.util.MailSSLSocketFactory
import org.slf4j.LoggerFactory
import java.io.File
import java.util.*
import java.util.concurrent.Executors
import javax.mail.*
import javax.mail.internet.*

/**
 * @author haohang (msdx.android@qq.com)
 * @version 2018-12-05
 * @since 2018-12-05
 */
object BookSender {
    private val log = LoggerFactory.getLogger(BookSender::class.java)

    private val user = KindleConfig.get(KindleConfig.MAILER_SMTP_USER)
    private val password = KindleConfig.get(KindleConfig.MAILER_SMTP_PASSWORD)
    private val host = KindleConfig.get(KindleConfig.MAILER_SMTP_HOST)
    private val port = KindleConfig.get(KindleConfig.MAILER_SMTP_PORT)

    private val properties = Properties().apply {
        setProperty("mail.smtp.auth", "true")
        setProperty("mail.smtp.host", host)
        setProperty("mail.smtp.port", port)
        setProperty("mail.transport.protocol", "smtp")
        setProperty("mail.smtp.ssl.enable", "true")
        put("mail.smtp.ssl.socketFactory", MailSSLSocketFactory())
    }

    private val from = InternetAddress(user)

    private val sendPool = Executors.newSingleThreadExecutor()

    private var isSending = false

    private val runnable = Runnable {
        isSending = true
        val session = OrmUtil.session
        while (true) {
            val query = session.createQuery("from SendBook order by id", SendBook::class.java)
            query.firstResult = 0
            query.maxResults = 1
            val sendBook = query.list().firstOrNull() ?: break
            try {
                val file = File(sendBook.path)
                if (file.isDirectory || !file.exists()) {
                    updateRecord(sendBook, false, "图书不存在")
                    continue
                }
                sendEmail(sendBook, file)
                updateRecord(sendBook, true)
            } catch (e: Exception) {
                e.printStackTrace()
                val reason = e.message + " cause by " + e.cause?.message
                log.error(reason)
                updateRecord(sendBook, false, reason)
            }
        }
        isSending = false
    }

    private fun updateRecord(sendBook: SendBook, isSuccess: Boolean, failReason: String? = "") {
        val session = OrmUtil.openSession()
        val transaction = session.beginTransaction()
        session.delete(sendBook)
        session.save(SendRecord(sendBook.book, sendBook.email, isSuccess, failReason))
        transaction.commit()
        session.close()
    }

    private fun sendEmail(record: SendBook, file: File) {
        log.info("Sending ${record.book} to ${record.email}")
        val session = Session.getDefaultInstance(properties, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(user, password)
            }
        })
        val msg = MimeMessage(session)
        msg.setFrom(from)
        msg.setRecipient(Message.RecipientType.TO, InternetAddress(record.email))
        msg.setSubject(record.book, "utf-8")
        val multipart = MimeMultipart("mixed")
        val attachBodyPart = MimeBodyPart()
        attachBodyPart.attachFile(file)
        attachBodyPart.fileName = MimeUtility.encodeText(record.book)
        multipart.addBodyPart(attachBodyPart)
        msg.setContent(multipart)
        Transport.send(msg)

        log.info("Book [${record.book}] has sent to [${record.email}]")
    }

    fun notifyToSend() {
        if (!isSending) {
            sendPool.submit(runnable)
        }
    }
}