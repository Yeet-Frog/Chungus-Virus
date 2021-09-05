import javafx.beans.binding.Bindings
import javafx.embed.swing.JFXPanel
import javafx.geometry.Rectangle2D
import javafx.scene.Scene
import javafx.scene.layout.StackPane
import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer
import javafx.scene.media.MediaView
import javafx.stage.Screen
import java.awt.BorderLayout
import java.awt.Color
import java.awt.GraphicsEnvironment
import java.awt.Toolkit
import java.awt.event.WindowEvent
import java.awt.event.WindowListener
import javax.swing.JFrame
import javax.swing.JOptionPane


object Rickroll {
    fun rickroll() {
        for (i in GraphicsEnvironment.getLocalGraphicsEnvironment().screenDevices.indices) {
            rickrollScreen(i)
        }
    }
    fun rickrollScreen(screen: Int) {
        val frame = rickrollWindow()
        val ge = GraphicsEnvironment.getLocalGraphicsEnvironment()
        val gd = ge.screenDevices
        frame.setSize(gd[screen].defaultConfiguration.bounds.width, gd[screen].defaultConfiguration.bounds.height)
        frame.setLocation(gd[screen].defaultConfiguration.bounds.x, gd[screen].defaultConfiguration.bounds.y)
        frame.isVisible = true
    }
    fun rickrollWindow(): JFrame {
        val videoPanel = JFrame()
        val VFXPanel = JFXPanel()
        val m = Media(javaClass.getResource("rickroll.mp4").toURI().toString())
        val player = MediaPlayer(m)
        val viewer = MediaView(player)
        val root = StackPane()
        val scene = Scene(root)
        player.onEndOfMedia = Runnable {
            videoPanel.dispose()
        }
        player.onError = Runnable {
            videoPanel.dispose()
        }

        // center video position
        val screen: Rectangle2D = Screen.getPrimary().getVisualBounds()
        scene.fill = javafx.scene.paint.Color(0.0, 0.0, 0.0, 1.0)
        viewer.x = (screen.width - videoPanel.getWidth()) / 2
        viewer.y = (screen.height - videoPanel.getHeight()) / 2

        // resize video based on screen size
        val width = viewer.fitWidthProperty()
        val height = viewer.fitHeightProperty()
        width.bind(Bindings.selectDouble(viewer.sceneProperty(), "width"))
        height.bind(Bindings.selectDouble(viewer.sceneProperty(), "height"))
        viewer.isPreserveRatio = true

        // add video to stackpane
        root.children.add(viewer)
        VFXPanel.scene = scene
        player.play();
        videoPanel.setLayout(BorderLayout())
        videoPanel.add(VFXPanel, BorderLayout.CENTER)
        val screenSize = Toolkit.getDefaultToolkit().screenSize
        val width1 = screenSize.getWidth().toInt()
        val height1 = screenSize.getHeight().toInt()
        videoPanel.setSize(width1, height1)
        videoPanel.isUndecorated = true
        videoPanel.background = Color(0, 0, 0, 255)
        videoPanel.isAlwaysOnTop = true
        videoPanel.defaultCloseOperation = JFrame.DO_NOTHING_ON_CLOSE
        videoPanel.addWindowListener(object : WindowListener {
            override fun windowDeiconified(e: WindowEvent?) {
            }

            override fun windowClosing(e: WindowEvent?) {
                player.stop()
                JOptionPane.showMessageDialog(videoPanel, "No", "", JOptionPane.ERROR_MESSAGE)
                player.play()
            }

            override fun windowClosed(e: WindowEvent?) {
            }

            override fun windowActivated(e: WindowEvent?) {
            }

            override fun windowDeactivated(e: WindowEvent?) {
            }

            override fun windowOpened(e: WindowEvent?) {
            }

            override fun windowIconified(e: WindowEvent?) {
            }

        })
        return videoPanel
    }
}