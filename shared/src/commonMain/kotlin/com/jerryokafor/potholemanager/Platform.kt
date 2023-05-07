package com.jerryokafor.potholemanager

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform