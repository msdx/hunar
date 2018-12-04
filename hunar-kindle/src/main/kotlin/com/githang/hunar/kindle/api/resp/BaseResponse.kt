package com.githang.hunar.kindle.api.resp

import com.githang.hunar.kindle.api.Const
import com.squareup.moshi.Json

/**
 * @author haohang (msdx.android@qq.com)
 * @version 2018-12-03
 * @since 2018-12-03
 */
open class BaseResponse(
    @Json(name = "code") val code: Int = Const.OK,
    @Json(name = "msg") val msg: String = "Success"
)