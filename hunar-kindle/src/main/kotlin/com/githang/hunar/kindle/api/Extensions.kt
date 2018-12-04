package com.githang.hunar.kindle.api

import com.githang.hunar.kindle.api.resp.BaseResponse
import com.githang.hunar.kindle.api.resp.DataResponse
import com.github.yoojia.web.Response

/**
 * @author haohang (msdx.android@qq.com)
 * @version 2018-12-03
 * @since 2018-12-03
 */
fun Response.success() {
    this.writeTextSilently(Json.toJson(BaseResponse()))
}

fun <T> Response.success(data: T) {
    this.writeTextSilently(Json.toJson(DataResponse(data)))
}

fun Response.failed(code: Int, msg: String) {
    this.writeTextSilently(Json.toJson(BaseResponse(code, msg)))
}
