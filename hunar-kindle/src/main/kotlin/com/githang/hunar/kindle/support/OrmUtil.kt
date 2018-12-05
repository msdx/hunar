package com.githang.hunar.kindle.support

import org.hibernate.Session
import org.hibernate.cfg.Configuration

/**
 * @author haohang (msdx.android@qq.com)
 * @version 2018-12-04
 * @since 2018-12-04
 */
object OrmUtil {
    val session: Session by lazy { Configuration().configure().buildSessionFactory().openSession() }
}