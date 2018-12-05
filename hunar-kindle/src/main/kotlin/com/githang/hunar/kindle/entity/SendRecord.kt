package com.githang.hunar.kindle.entity

import javax.persistence.*

/**
 * @author haohang (msdx.android@qq.com)
 * @version 2018-12-05
 * @since 2018-12-05
 */
@Entity
@Table(name = "t_record")
class SendRecord {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0
    @Column(name = "book_name")
    var book: String? = null
    @Column(name = "kindle_email")
    var email: String? = null
    @Column(name = "is_sent")
    var isSent = false
    @Column(name = "fail_reason")
    var failReason: String? = ""

    constructor()

    constructor(book: String?, email: String?, isSent: Boolean) {
        this.book = book
        this.email = email
        this.isSent = isSent
    }

    constructor(book: String?, email: String?, isSent: Boolean, failReason: String?) {
        this.book = book
        this.email = email
        this.isSent = isSent
        this.failReason = failReason
    }
}