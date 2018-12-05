package com.githang.hunar.kindle.api.service

import com.githang.hunar.kindle.api.success
import com.githang.hunar.kindle.support.OrmUtil
import com.github.yoojia.web.Request
import com.github.yoojia.web.Response
import com.github.yoojia.web.http.Controller
import com.github.yoojia.web.supports.GET

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
}
