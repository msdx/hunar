package com.githang.hunar.kindle.api

import com.squareup.moshi.Moshi

/**
 * @author haohang (msdx.android@qq.com)
 * @version 2018-12-03
 * @since 2018-12-03
 */
object Json {
    private val json = Moshi.Builder().build()

    fun <T> toJson(value: T): String {
        return json.adapter(Any::class.java).toJson(value)
    }
}
