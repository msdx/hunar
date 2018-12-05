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
class Book(
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,
    @Column(name = "name", unique = true)
    val name: String,
    @Column(name = "type")
    val type: String,
    @Column(name = "path")
    val path: String,
    @Column(name = "file_size")
    val fileSize: Long,
    @Column(name = "update_time")
    val updateTime: Long
) {
    constructor(name: String, type: String, path: String, fileSize: Long, updateTime: Long)
            : this(0, name, type, path, fileSize, updateTime)

    override fun toString(): String {
        return "Book(name='$name', type='$type', path='$path', fileSize=$fileSize, updateTime=$updateTime, id=$id)"
    }

}