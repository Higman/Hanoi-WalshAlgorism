package com.github.Higman

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage

class HanoiApplication : Application() {
    /**
     * GUIをスタートさせる
     * @param primaryStage 土台となるコンテナ(トップレベルコンテナ)
     */
    override fun start(primaryStage: Stage) {
        primaryStage.title = "Hanoi"

        primaryStage.scene = Scene(BaseAnchorPane())

        //GUIを表示
        primaryStage.show()
    }
}
