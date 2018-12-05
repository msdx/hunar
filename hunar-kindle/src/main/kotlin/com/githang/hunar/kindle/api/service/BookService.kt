package com.githang.hunar.kindle.api.service

import com.githang.hunar.kindle.api.Const
import com.githang.hunar.kindle.api.failed
import com.githang.hunar.kindle.api.success
import com.githang.hunar.kindle.entity.Book
import com.githang.hunar.kindle.entity.SendBook
import com.githang.hunar.kindle.sender.BookSender
import com.githang.hunar.kindle.support.OrmUtil
import com.githang.hunar.kindle.support.PreferenceCache
import com.github.yoojia.web.Request
import com.github.yoojia.web.Response
import com.github.yoojia.web.http.Controller
import com.github.yoojia.web.supports.GET
import com.github.yoojia.web.supports.GETPOST
import java.util.regex.Pattern

/**
 * @author haohang (msdx.android@qq.com)
 * @version 2018-12-05
 * @since 2018-12-05
 */
@Controller("/book")
class BookService {
    @GET("/list")
    fun queryBook(req: Request, resp: Response) {
        val name = req.paramAsString("name")
        val query = OrmUtil.session.createQuery("from Book where name like ?1")
        query.setParameter(1, "%$name%")
        val books = query.list()
        resp.success(books)
    }

    @GETPOST("/send")
    fun sendBook(req: Request, resp: Response) {
        val id = req.paramAsInt("id")
        val email = req.paramAsString("email")

        if (id < 0 || !pattern.matcher(email).find()) {
            resp.failed(Const.ILLEGAL_ARGUMENT, "Parameter invalid")
            return
        }

        val limit = PreferenceCache.sendLimit
        if (limit <= 0) {
            resp.failed(Const.OVER_LIMIT, "over limit")
            return
        }

        val session = OrmUtil.session
        val emailQuery = session.createQuery("select count(*) from SendBook where email = ?1")
        emailQuery.setParameter(1, email)
        if (emailQuery.singleResult as Long >= 3) {
            resp.failed(Const.OVER_LIMIT, "You have send it too frequently")
            return
        }

        val bookQuery = session.createQuery("from Book where id = ?1", Book::class.java)
        bookQuery.setParameter(1, id)
        val book = bookQuery.list().firstOrNull()
        if (book == null) {
            resp.failed(Const.ILLEGAL_ARGUMENT, "Book id is not exists")
            return
        }

        val sendBook = SendBook(book.name, book.path, email)
        session.save(sendBook)
        BookSender.notifyToSend()
        resp.success()
    }

    companion object {
        private val pattern by lazy {
            Pattern.compile(
                "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$"
            )
        }
    }
}
