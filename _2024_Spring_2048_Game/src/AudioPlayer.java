import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioPlayer {

    private Clip clip;
    private FloatControl volumeControl;
    private float originalVolume; // 记录原始音量值

    public void loadAudio(String filePath) {
        try {
            // 打开音频输入流
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));
            // 获取Clip对象
            clip = AudioSystem.getClip();
            // 打开Clip
            clip.open(audioInputStream);

            // 获取音量控制器
            volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            // 记录原始音量值
            originalVolume = volumeControl.getValue();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if (clip != null) {
            clip.start();
        }
    }

    public void stop() {
        if (clip != null) {
            clip.stop();
        }
    }

    public void setVolume(float volume) {
        if (volumeControl != null) {
            // 设置音量值
            volumeControl.setValue(volume);
        }
    }

    public void resetVolume() {
        if (volumeControl != null) {
            // 恢复原始音量
            volumeControl.setValue(originalVolume);
        }
    }

    public static void main(String[] args) {
        AudioPlayer player = new AudioPlayer();
        player.loadAudio("Music/adsBGM.wav");

        // 播放音频
        player.play();

        // 设置音量为静音
        player.setVolume(Float.MIN_VALUE); // 设置音量为最小值，即静音

        // 在播放时动态地调整音量
        try {
            Thread.sleep(5000); // 延迟5秒
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 恢复原始音量
        player.resetVolume();
    }
}
