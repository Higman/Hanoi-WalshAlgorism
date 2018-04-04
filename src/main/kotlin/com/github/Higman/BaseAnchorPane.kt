package com.github.Higman

import com.github.Higman.hanoi.*
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.control.*
import javafx.scene.layout.BorderPane
import javafx.scene.layout.FlowPane
import javafx.scene.layout.Pane
import java.net.URL
import java.util.*
import kotlin.reflect.KFunction

class BaseAnchorPane : BorderPane() {
    @FXML lateinit var label: Label
    @FXML lateinit var diskSpinner: Spinner<Int>
    @FXML lateinit var controllerFlowPane: FlowPane
    @FXML lateinit var drawPane: Pane
    @FXML lateinit var controllButton: Button
    @FXML lateinit var hanoiWalshRadioButton: RadioButton
    @FXML lateinit var towerRadioButton: RadioButton

    private var isStartedHanoi = false
    private lateinit var hanoiDrawer: HanoiDrawer
    private lateinit var toggleGroup: ToggleGroup

    init {
        val fxmlLoader: FXMLLoader = FXMLLoader(
                this.javaClass.getClassLoader().getResource("layout.fxml"))
        fxmlLoader.setRoot(this)
        fxmlLoader.setController(this)
        fxmlLoader.load<BorderPane>()

        initializeComponent()
    }

    private fun initializeComponent() {
        diskSpinner.valueFactory = SpinnerValueFactory.IntegerSpinnerValueFactory(3, 10, 5)
        toggleGroup = hanoiWalshRadioButton.toggleGroup
        hanoiWalshRadioButton.userData = ::HanoiWalshAlgorithm
        towerRadioButton.userData = ::HanoiTowerAlgorithm
    }

    @FXML
    fun onClickControllButton(event: ActionEvent) {
        if (!isStartedHanoi) {
            isStartedHanoi = !isStartedHanoi
            controllButton.text = "次へ"
            drawPane.children.clear()

            val execAlgorithm = toggleGroup.selectedToggle.userData as Function1<Hanoi, HanoiTowerAlgorithm>
            val hanoi = Hanoi(diskSpinner.value)
            hanoiDrawer = HanoiDrawer(execAlgorithm(hanoi),  drawPane)
        } else {
            if (hanoiDrawer.next()) {
                isStartedHanoi = !isStartedHanoi
                controllButton.text = "開始"
            }
        }
    }

}
