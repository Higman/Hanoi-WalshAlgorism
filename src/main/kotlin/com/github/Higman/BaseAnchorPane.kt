package com.github.Higman

import com.github.Higman.hanoi.Hanoi
import com.github.Higman.hanoi.HanoiTowerAlgorithm
import com.github.Higman.hanoi.HanoiWalshAlgorithm
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.control.*
import javafx.scene.layout.BorderPane
import javafx.scene.layout.FlowPane
import javafx.scene.layout.Pane
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.Alert



class BaseAnchorPane : BorderPane() {
    @FXML
    lateinit var diskSpinner: Spinner<Int>
    @FXML
    lateinit var controllerFlowPane: FlowPane
    @FXML
    lateinit var drawPane: Pane
    @FXML
    lateinit var controllButton: Button
    @FXML
    lateinit var hanoiWalshRadioButton: RadioButton
    @FXML
    lateinit var towerRadioButton: RadioButton

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
            hanoiDrawer = HanoiDrawer(execAlgorithm(hanoi), drawPane)
            setDisableAllChildren(true)
        } else {
            if (!hanoiDrawer.next()) {
                isStartedHanoi = !isStartedHanoi
                setDisableAllChildren(false)
                controllButton.text = "開始"
                showFinishAlert()
            }
        }
    }

    private fun showFinishAlert() {
        val alert = Alert(AlertType.INFORMATION)
        alert.title = "終了"
        alert.headerText = null
        alert.contentText = "すべての円盤の移動が完了しました。"
        alert.showAndWait()
    }

    private fun setDisableAllChildren(b: Boolean) {
        towerRadioButton.isDisable = b
        hanoiWalshRadioButton.isDisable = b
        diskSpinner.isDisable = b
    }

}
