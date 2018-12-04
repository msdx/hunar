package com.githang.hunar.kindle.api.resp

import com.githang.hunar.kindle.api.Const
import com.squareup.moshi.Json

/**
 * @author haohang (msdx.android@qq.com)
 * @version 2018-12-03
 * @since 2018-12-03
 */
class DataResponse(
    @Json(name = "data") val t: Any?,
    code: Int = Const.OK, message: String = "Success"
) : BaseResponse(code, message)