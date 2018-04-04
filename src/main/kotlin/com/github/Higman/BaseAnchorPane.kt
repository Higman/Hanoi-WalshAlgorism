package com.github.Higman

import com.github.Higman.hanoi.Hanoi
import com.github.Higman.hanoi.HanoiTowerAlgorithm
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.Spinner
import javafx.scene.control.SpinnerValueFactory
import javafx.scene.layout.BorderPane
import javafx.scene.layout.FlowPane
import javafx.scene.layout.Pane
import java.net.URL
import java.util.*

class BaseAnchorPane : BorderPane(), Initializable {
    @FXML lateinit var label: Label
    @FXML lateinit var diskSpinner: Spinner<Int>
    @FXML lateinit var controllerFlowPane: FlowPane
    @FXML lateinit var drawPane: Pane
    @FXML lateinit var controllButton: Button

    private var isStartedHanoi = false
    private lateinit var hanoiDrawer: HanoiDrawer

    init {
        val fxmlLoader: FXMLLoader = FXMLLoader(
                this.javaClass.getClassLoader().getResource("layout.fxml"))
        fxmlLoader.setRoot(this)
        fxmlLoader.setController(this)
        fxmlLoader.load<BorderPane>()
    }

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        diskSpinner.valueFactory = SpinnerValueFactory.IntegerSpinnerValueFactory(3, 10, 5)
    }

    @FXML
    fun onClickControllButton(event: ActionEvent) {
        if (!isStartedHanoi) {
            isStartedHanoi = !isStartedHanoi
            controllButton.text = "次へ"
            drawPane.children.clear()
            hanoiDrawer = HanoiDrawer(HanoiTowerAlgorithm(Hanoi(diskSpinner.value)),  drawPane)
        } else {
            if (hanoiDrawer.next()) {
                isStartedHanoi = !isStartedHanoi
                controllButton.text = "開始"
            }
        }
    }

}
