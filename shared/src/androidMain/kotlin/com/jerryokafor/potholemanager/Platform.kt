@file:Suppress("MatchingDeclarationName")

package com.jerryokafor.potholemanager

class AndroidPlatform : Platform {
    override val name: String = "Android ${android.os.Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()
