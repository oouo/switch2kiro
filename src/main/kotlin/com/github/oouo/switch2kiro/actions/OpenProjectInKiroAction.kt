package com.github.oouo.switch2kiro.actions

import com.github.oouo.switch2kiro.settings.AppSettingsState
import com.github.oouo.switch2kiro.utils.WindowUtils
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.project.Project

class OpenProjectInKiroAction : AnAction() {
    private val logger = Logger.getInstance(OpenProjectInKiroAction::class.java)

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }

    override fun actionPerformed(e: AnActionEvent) {
        val project: Project = e.project ?: return
        val projectPath = project.basePath ?: return

        val settings = AppSettingsState.getInstance()
        val kiroPath = com.github.oouo.switch2kiro.utils.KiroPathResolver.resolve()

        val command = when {
            System.getProperty("os.name").lowercase().contains("mac") -> {
                if (kiroPath.endsWith(".app") || kiroPath.contains(".app/")) {
                    arrayOf("open", "-a", kiroPath, "--args", projectPath)
                } else {
                    arrayOf(kiroPath, projectPath)
                }
            }
            System.getProperty("os.name").lowercase().contains("windows") -> {
                arrayOf(kiroPath, projectPath)
            }
            else -> {
                arrayOf(kiroPath, projectPath)
            }
        }

        try {
            logger.info("Executing command: ${command.joinToString(" ")}")
            ProcessBuilder(*command).start()
        } catch (ex: Exception) {
            logger.error("Failed to execute Kiro command: ${ex.message}", ex)
            com.intellij.openapi.ui.Messages.showErrorDialog(
                project,
                """
                ${ex.message}
                
                Please check:
                1. Kiro path is correctly configured in Settings > Tools > Switch2Kiro
                2. Kiro is properly installed on your system
                3. The configured path points to a valid Kiro executable
                """.trimIndent(),
                "Error"
            )
        }

        WindowUtils.activeWindow()
    }

    override fun update(e: AnActionEvent) {
        val project = e.project
        e.presentation.isEnabledAndVisible = project != null
    }
}
