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
            logger.warn("Failed to execute Kiro command: ${ex.message}", ex)
            com.intellij.notification.NotificationGroupManager.getInstance()
                .getNotificationGroup("Switch2Kiro")
                .createNotification(
                    "Kiro not found",
                    "Please check Kiro path in Settings > Tools > Switch2Kiro",
                    com.intellij.notification.NotificationType.WARNING
                )
                .notify(project)
        }

        WindowUtils.activeWindow()
    }

    override fun update(e: AnActionEvent) {
        val project = e.project
        e.presentation.isEnabledAndVisible = project != null
    }
}
