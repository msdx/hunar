package com.githang.hunar.kindle.entity

import javax.persistence.*

/**
 * @author haohang (msdx.android@qq.com)
 * @version 2018-12-05
 * @since 2018-12-05
 */
@Entity
@Table(name = "t_send_queue")
class SendBook {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0
    @Column(name = "book_name")
    var book: String? = null
    @Column(name = "book_path")
    var path: String? = null
    @Column(name = "kindle_email")
    var email: String? = null

    constructor()

    constructor(book: String?, path: String?, email: String) {
        this.book = book
        this.path = path
        this.email = email
    }
}