package com.github.oouo.switch2kiro.settings

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil

@State(
    name = "com.github.oouo.switch2kiro.settings.AppSettingsState",
    storages = [Storage("Switch2KiroSettings.xml")]
)
class AppSettingsState : PersistentStateComponent<AppSettingsState> {
    var kiroPath: String = "kiro"

    override fun getState(): AppSettingsState = this

    override fun loadState(state: AppSettingsState) {
        XmlSerializerUtil.copyBean(state, this)
    }

    companion object {
        fun getInstance(): AppSettingsState =
            ApplicationManager.getApplication().getService(AppSettingsState::class.java)
    }
}
