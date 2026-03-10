package com.github.oouo.switch2kiro.utils

import com.github.oouo.switch2kiro.settings.AppSettingsState
import java.io.File

object KiroPathResolver {

    /**
     * Returns a usable Kiro executable path.
     * If the configured path doesn't exist, auto-detect and update settings.
     */
    fun resolve(): String {
        val settings = AppSettingsState.getInstance()
        val configured = settings.kiroPath

        // If configured path is a real file, use it
        if (File(configured).exists()) return configured

        // If it's a bare name on PATH, use it
        if (!configured.contains(File.separator) && !configured.contains("/") && !configured.contains("\\")) {
            if (AppSettingsState.isOnPath(configured)) return configured
        }

        // Configured path is broken — re-detect and persist
        val detected = AppSettingsState.detectDefaultKiroPath()
        if (detected != configured) {
            settings.kiroPath = detected
        }
        return detected
    }
}
