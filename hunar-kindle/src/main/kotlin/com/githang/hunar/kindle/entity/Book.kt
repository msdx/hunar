package com.githang.hunar.kindle.entity

import org.hibernate.annotations.SQLInsert
import javax.persistence.*

/**
 * @author haohang (msdx.android@qq.com)
 * @version 2018-12-04
 * @since 2018-12-04
 */
@Entity
@SQLInsert(sql = "REPLACE INTO t_book(file_size, name, path, type, update_time) VALUES (?, ?, ?, ?, ?)")
@Table(name = "t_book")
class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0
    @Column(name = "name", unique = true)
    var name: String? = null
    @Column(name = "type")
    var type: String? = null
    @Column(name = "path")
    var path: String? = null
    @Column(name = "file_size")
    var fileSize: Long? = null
    @Column(name = "update_time")
    var updateTime: Long? = null

    constructor()

    constructor(name: String, type: String, path: String, fileSize: Long, updateTime: Long) {
        this.name = name
        this.type = type
        this.path = path
        this.fileSize = fileSize
        this.updateTime = updateTime
    }

    override fun toString(): String {
        return "Book(name='$name', type='$type', path='$path', fileSize=$fileSize, updateTime=$updateTime, id=$id)"
    }

}