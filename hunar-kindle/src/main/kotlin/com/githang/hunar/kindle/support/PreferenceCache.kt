package com.githang.hunar.kindle.support

import org.apache.commons.configuration.PropertiesConfiguration
import java.io.File

/**
 * @author haohang (msdx.android@qq.com)
 * @version 2018-12-04
 * @since 2018-12-04
 */
object PreferenceCache {
    private val prefs: PropertiesConfiguration

    init {
        val file = File("hunar-kindle.pref")
        if (!file.exists()) {
            file.createNewFile()
        }
        prefs = PropertiesConfiguration(file)
        prefs.isAutoSave = true
    }

    var scanTime: Long
        get() = prefs.getLong("scan-time", 0)
        set(value) {
            prefs.setProperty("scan-time", value)
        }

    var sendLimit: Int
        get() = prefs.getInt("limit", 100)
        set(value) = prefs.setProperty("limit", value)
}