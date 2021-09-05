import java.awt.Color
import java.awt.Desktop
import java.awt.Point
import java.awt.Toolkit
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.awt.event.WindowEvent
import java.awt.event.WindowListener
import java.io.BufferedInputStream
import java.lang.Thread.sleep
import java.net.URI
import javax.sound.sampled.*
import javax.swing.ImageIcon
import javax.swing.JFrame
import javax.swing.JLabel
import kotlin.concurrent.thread
import kotlin.random.Random

val chungusLinks = arrayOf(
    "https://www.google.com/search?q=how+to+lose+weight",
    "https://www.google.com/search?q=weight+watchers",
    "https://www.google.com/search?q=low+carb+diet",
    "https://www.google.com/search?q=how+to+avoid+getting+fat",
    "https://www.google.com/search?q=health+food"
)

class ChungusPayload(val pos: Point? = null) : JFrame("Chungus"), LineListener {
    val size = Random.nextInt(300, 750)
    val img = Toolkit.getDefaultToolkit().getImage(javaClass.getResource("chungus.png")).getScaledInstance(size, size, 0)
    var move = 0
    var movetime = 0;
    init {
        val label = JLabel(ImageIcon(img))
        add(label)
        addMouseListener(object : MouseListener {
            override fun mouseReleased(e: MouseEvent?) {
                Desktop.getDesktop().browse(URI(chungusLinks[Random.nextInt(chungusLinks.size)]));
            }

            override fun mouseEntered(e: MouseEvent?) {
            }

            override fun mouseClicked(e: MouseEvent?) {
            }

            override fun mouseExited(e: MouseEvent?) {
            }

            override fun mousePressed(e: MouseEvent?) {
            }

        })
        this.defaultCloseOperation = JFrame.DO_NOTHING_ON_CLOSE;
        this.addWindowListener(object : WindowListener {
            override fun windowDeiconified(e: WindowEvent?) {
            }

            override fun windowClosing(e: WindowEvent?) {
                ChungusPayload()
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

        });
        isUndecorated = true
        this.background = Color(0, 0, 0, 0);
        setSize(size, size)
        isVisible = true
        isAlwaysOnTop = true
        if (pos == null) {
            val screenSize = Toolkit.getDefaultToolkit().screenSize
            val width = screenSize.getWidth()
            val height = screenSize.getHeight()
            location = Point(Random.nextInt((width).toInt() - size / 2), Random.nextInt((height).toInt()) - size / 2)
        } else {
            location = pos
        }
        val audioStream: AudioInputStream = AudioSystem.getAudioInputStream(BufferedInputStream(javaClass.getResourceAsStream("chungus.wav")))

        val format = audioStream.format

        val info = DataLine.Info(Clip::class.java, format)

        val audioClip = AudioSystem.getLine(info) as Clip

        audioClip.addLineListener(this)

        audioClip.open(audioStream)

        audioClip.start()
        thread (start = true) {
            while (true) {
                if (movetime < 1) {
                    move = Random.nextInt(9)
                    movetime = Random.nextInt(10, 400)
                } else {
                    movetime--
                    if (move != 0) {
                        location = Point(
                            location.x +
                                    when (move) {
                                        1, 5, 7 -> 1
                                        2, 6, 8 -> -1
                                        else -> 0
                                    }, location.y +
                                    when (move) {
                                        3, 5, 8 -> 1
                                        4, 6, 7 -> -1
                                        else -> 0
                                    }
                        )
                    }
                }
                validateMove()
                sleep(10)
            }
        }
    }
    fun validateMove() {
        val screenSize = Toolkit.getDefaultToolkit().screenSize
        val width = screenSize.getWidth().toInt()
        val height = screenSize.getHeight().toInt()
        if (location.x > width - (size / 2)) {
            location = Point(-(size / 2) + 10, location.y)
        }
        if (location.y > height - (size / 2)) {
            location = Point(location.x, -(size / 2) + 10)
        }
        if (location.x < -(size / 2)) {
            location = Point(width - (size / 2) - 10, location.y)
        }
        if (location.y < -(size / 2)) {
            location = Point(location.x, height - (size / 2) - 10)
        }
    }

    override fun update(event: LineEvent?) {
        if (event?.type == LineEvent.Type.STOP) {
            dispose()
        }
    }
}