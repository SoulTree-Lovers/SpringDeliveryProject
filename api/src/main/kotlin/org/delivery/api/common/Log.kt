package org.delivery.api.common

import org.slf4j.Logger
import org.slf4j.LoggerFactory

interface Log {
    // 코틀린에서는 인터페이스에 변수 선언 가능
    val log: Logger get() = LoggerFactory.getLogger(this.javaClass)

}