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

        // If it's on PATH (e.g. "kiro"), verify it's actually reachable
        if (!configured.contains(File.separator) && !configured.contains("/") && !configured.contains("\\")) {
            if (isOnPath(configured)) return configured
        }

        // Configured path is broken — re-detect and persist
        val detected = AppSettingsState.detectDefaultKiroPath()
        if (detected != configured) {
            settings.kiroPath = detected
        }
        return detected
    }

    private fun isOnPath(name: String): Boolean {
        return try {
            val os = System.getProperty("os.name").lowercase()
            val cmd = if (os.contains("windows")) arrayOf("where.exe", name) else arrayOf("which", name)
            ProcessBuilder(*cmd).start().waitFor() == 0
        } catch (_: Exception) {
            false
        }
    }
}
