package com.github.Higman

import com.github.Higman.hanoi.Hanoi
import com.github.Higman.hanoi.HanoiTowerAlgorithm
import com.github.Higman.hanoi.HanoiWalshAlgorithm
import javafx.animation.KeyFrame
import javafx.animation.Timeline
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.control.*
import javafx.scene.layout.BorderPane
import javafx.scene.layout.FlowPane
import javafx.scene.layout.Pane
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.Alert
import javafx.util.Duration


class BaseAnchorPane : BorderPane() {
    @FXML
    lateinit var diskSpinner: Spinner<Int>
    @FXML
    lateinit var controllerFlowPane: FlowPane
    @FXML
    lateinit var drawPane: Pane
    @FXML
    lateinit var controlButton: Button
    @FXML
    lateinit var resetButton: Button
    @FXML
    lateinit var hanoiWalshRadioButton: RadioButton
    @FXML
    lateinit var towerRadioButton: RadioButton
    @FXML
    lateinit var speedSlider: Slider
    @FXML
    lateinit var autoCheckBox: CheckBox

    private var isStartedHanoi = false
    private lateinit var hanoiDrawer: HanoiDrawer
    private lateinit var toggleGroup: ToggleGroup

    var autoTimeline: Timeline? = null

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
            if (!autoCheckBox.isSelected) {
                manualHanoiSetting()
            } else {
                autoHanoiSetting()
            }
        } else {
            if (!hanoiDrawer.next()) {
                isStartedHanoi = !isStartedHanoi
                setDisableAllChildren(false)
                controlButton.text = "開始"
                resetButton.isVisible = false
                showFinishAlert()
            }
        }
    }

    private fun autoHanoiSetting() {
        isStartedHanoi = !isStartedHanoi
        drawPane.children.clear()
        resetButton.isVisible = true

        val execAlgorithm = toggleGroup.selectedToggle.userData as Function1<Hanoi, HanoiTowerAlgorithm>
        val hanoi = Hanoi(diskSpinner.value)
        hanoiDrawer = HanoiDrawer(execAlgorithm(hanoi), drawPane)
        startAutoHanoi()

        setDisableAllChildren(true)
        controlButton.isDisable = true
    }

    private fun initializeTimeline(): Timeline {
        return Timeline((KeyFrame(Duration.millis(speedSlider.value * 10.0), EventHandler {
            hanoiDrawer.next()
        }))).apply {
            setOnFinished {
                resetProcess()
                showFinishAlert()
            }
            cycleCount = (Math.pow(2.0, hanoiDrawer.algorithm.hanoi.diskNum.toDouble()) - 1).toInt()
        }
    }

    private fun startAutoHanoi() {
        autoTimeline = initializeTimeline()
        autoTimeline?.play()
    }

    private fun manualHanoiSetting() {
        isStartedHanoi = !isStartedHanoi
        controlButton.text = "次へ"
        drawPane.children.clear()
        resetButton.isVisible = true

        val execAlgorithm = toggleGroup.selectedToggle.userData as Function1<Hanoi, HanoiTowerAlgorithm>
        val hanoi = Hanoi(diskSpinner.value)
        hanoiDrawer = HanoiDrawer(execAlgorithm(hanoi), drawPane)
        setDisableAllChildren(true)
    }

    @FXML
    fun onClickResetlButton(event: ActionEvent) {
        drawPane.children.clear()
        resetProcess()
    }

    private fun resetProcess() {
        isStartedHanoi = false
        resetButton.isVisible = false
        controlButton.isDisable = false
        setDisableAllChildren(false)
        controlButton.text = "開始"
        if (autoCheckBox.isSelected) autoTimeline?.stop()
    }

    @FXML
    fun checkedAction(event: ActionEvent) {
        speedSlider.isDisable = !autoCheckBox.isSelected
    }

    private fun showFinishAlert() {
        val alert = Alert(AlertType.INFORMATION)
        alert.title = "終了"
        alert.headerText = null
        alert.contentText = "すべての円盤の移動が完了しました。"
        alert.show()
    }

    private fun setDisableAllChildren(b: Boolean) {
        towerRadioButton.isDisable = b
        hanoiWalshRadioButton.isDisable = b
        diskSpinner.isDisable = b
        autoCheckBox.isDisable = b
        speedSlider.isDisable = b
    }

}
