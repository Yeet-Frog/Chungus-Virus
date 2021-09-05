import javax.swing.JDialog
import javax.swing.JFrame
import javax.swing.JOptionPane
import kotlin.concurrent.thread
import kotlin.random.Random

object WarningPopUp {
    fun popup() {
        thread (start = true) {
            val warning = warningPopups[Random.nextInt(warningPopups.size)]
            val pane = JOptionPane(warning.message, warning.type, JOptionPane.DEFAULT_OPTION)
            val d = pane.createDialog(null as JFrame?, warning.title)
            val pos = randomOnscreenPosition()
            d.setLocation(pos.x, pos.y)
            d.isVisible = true
            d.isAlwaysOnTop = true
        }
    }


    data class WarningPopupData(val message: String, val title: String, val type: Int)
    val warningPopups = arrayOf(
            WarningPopupData("Please delete System32", "Help", JOptionPane.WARNING_MESSAGE),
            WarningPopupData("Fun.jar is sponsored by Raid: Shadow Legends", "Sponsorship", JOptionPane.INFORMATION_MESSAGE),
            WarningPopupData("HaHa lol you are at the hands of Fun.exe", "Bow Down Mortal", JOptionPane.INFORMATION_MESSAGE),
            WarningPopupData("You should delete system32, it is effecting your computer's proformance", "Please", JOptionPane.INFORMATION_MESSAGE)
    )
}