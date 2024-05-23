package model;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class GridNumber {
    private Clip actionAudio;
    private final int X_COUNT;
    private final int Y_COUNT;

    private int MarkPoint;
    private boolean isMove;
    private int[][] numbers;
    private AudioInputStream audiostream;
    private boolean ifGenerate = false;

    private DataLine.Info info;
    static Random random = new Random();

    public GridNumber(int xCount, int yCount) {
        this.X_COUNT = xCount;
        this.Y_COUNT = yCount;
        this.numbers = new int[this.X_COUNT][this.Y_COUNT];
        this.initialNumbers();
        setisMove(true);
    }

    public void ReviveNumbers() {
        ;
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < numbers[0].length; j++) {
                if (numbers[i][j] == 2 || numbers[i][j] == 4) {
                    numbers[i][j] = 0;
                }
            }
        }
    }
    public void LoadNumbers(int[][] _numbers)
    {
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < numbers[0].length; j++) {
                numbers[i][j] = _numbers[i][j];
            }
        }
    }
    public void initialNumbers() {
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < numbers[0].length; j++) {
                numbers[i][j] = 0;
            }
        }
        int count = 0;
        int temp = 2;
        while (count < 2) {
            int i = random.nextInt(numbers.length);
            int j = random.nextInt(numbers[0].length);
            if (numbers[i][j] == 0) {
                numbers[i][j] = temp;
                temp = temp * 2;
                count++;
            }
        }
    }

    //todo: finish the method of four direction moving.

    public void generateNumberRandomly() {
        while (ifGenerate) {
            int row = random.nextInt(numbers.length);
            int col = random.nextInt(numbers[0].length);
            if (numbers[row][col] == 0) {
                numbers[row][col] = random.nextInt(2) == 0 ? 2 : 4;
                break;
            }
        }
    }

    public void moveRight() {
        MarkPoint = 0;
        JustMoveRight();
        boolean isPlay = false;
        for (int i = 0; i < numbers.length; i++) {
            for (int j = numbers[0].length - 1; j >= 0; j--) {
                if (j != 0 && numbers[i][j] != 0 && numbers[i][j - 1] == numbers[i][j]) {
                    numbers[i][j] = 2 * numbers[i][j];
                    numbers[i][j - 1] = 0;
                    MarkPoint += numbers[i][j];
                    ifGenerate = true;
                    isPlay = true;
                }
            }
        }
        if (isPlay) {
            playaudio();
        }
        boolean temp = ifGenerate;
        JustMoveRight();
        ifGenerate = temp;
        generateNumberRandomly();
    }

    public void moveLeft() {
        MarkPoint = 0;
        JustMoveLeft();
        boolean isPlay = false;
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < numbers[0].length; j++) {
                if (j != numbers[0].length - 1 && numbers[i][j] != 0 && numbers[i][j + 1] == numbers[i][j]) {
                    numbers[i][j] = 2 * numbers[i][j];
                    numbers[i][j + 1] = 0;
                    MarkPoint += numbers[i][j];
                    ifGenerate = true;
                    isPlay = true;
                }
            }
        }
        if (isPlay) {
            playaudio();
        }
        boolean temp = ifGenerate;
        JustMoveLeft();
        ifGenerate = temp;
        generateNumberRandomly();
    }

    public void moveUp() {
        MarkPoint = 0;
        JustMoveUp();
        boolean isPlay = false;
        for (int j = 0; j < numbers[0].length; j++) {
            for (int i = 0; i < numbers.length; i++) {
                if (i != numbers[0].length - 1 && numbers[i][j] != 0 && numbers[i][j] == numbers[i + 1][j]) {
                    numbers[i][j] = 2 * numbers[i][j];
                    numbers[i + 1][j] = 0;
                    MarkPoint += numbers[i][j];
                    ifGenerate = true;
                    isPlay = true;
                }
            }
        }
        if (isPlay) {
            playaudio();
        }
        boolean temp = ifGenerate;
        JustMoveUp();
        ifGenerate = temp;
        generateNumberRandomly();
    }

    public void moveDown() {
        MarkPoint = 0;
        JustMoveDown();
        boolean isPlay = false;
        for (int j = 0; j < numbers[0].length; j++) {
            for (int i = numbers.length - 1; i >= 0; i--) {
                if (i != 0 && numbers[i][j] != 0 && numbers[i][j] == numbers[i - 1][j]) {
                    numbers[i][j] = 2 * numbers[i][j];
                    numbers[i - 1][j] = 0;
                    MarkPoint += numbers[i][j];
                    ifGenerate = true;
                    isPlay = true;
                }
            }
        }
        if (isPlay) {
            playaudio();
        }
        boolean temp = ifGenerate;
        JustMoveDown();
        ifGenerate = temp;
        generateNumberRandomly();
    }

    public void JustMoveRight() {
        ifGenerate = false;
        for (int i = 0; i < numbers.length; i++) {
            for (int j = numbers[0].length - 1; j >= 0; j--) {
                if (j != numbers[0].length - 1 && numbers[i][j] != 0) {
                    int k = j + 1;
                    /*
                    k用于记录下一格坐标
                    */
                    while (k < numbers[0].length && numbers[i][k] == 0) {
                        ifGenerate = true;
                        int temp = numbers[i][k - 1];
                        numbers[i][k - 1] = 0;
                        numbers[i][k] = temp;
                        k++;
                    }
                }
            }
        }
    }

    public void JustMoveLeft() {
        ifGenerate = false;
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < numbers[0].length; j++) {
                if (j != 0 && numbers[i][j] != 0) {
                    int k = j - 1;
                    /*
                    k用于记录下一格坐标
                    */
                    while (k >= 0 && numbers[i][k] == 0) {
                        ifGenerate = true;
                        int temp = numbers[i][k + 1];
                        numbers[i][k + 1] = 0;
                        numbers[i][k] = temp;
                        k--;
                    }
                }
            }
        }
    }

    public void JustMoveUp() {
        ifGenerate = false;
        for (int j = 0; j < numbers[0].length; j++) {
            for (int i = 0; i < numbers.length; i++) {
                if (i != 0 && numbers[i][j] != 0) {
                    int k = i - 1;
                    while (k >= 0 && numbers[k][j] == 0) {
                        int temp = numbers[k + 1][j];
                        numbers[k + 1][j] = 0;
                        numbers[k][j] = temp;
                        k--;
                        ifGenerate = true;
                    }
                }
            }
        }
    }

    public void JustMoveDown() {
        ifGenerate = false;
        for (int j = 0; j < numbers[0].length; j++) {
            for (int i = numbers.length - 1; i >= 0; i--) {
                if (i != numbers.length - 1 && numbers[i][j] != 0) {
                    int k = i + 1;
                    while (k < numbers.length && numbers[k][j] == 0) {
                        ifGenerate = true;
                        int temp = numbers[k - 1][j];
                        numbers[k - 1][j] = 0;
                        numbers[k][j] = temp;
                        k++;
                    }
                }
            }
        }
    }

    public int getNumber(int i, int j) {
        return numbers[i][j];
    }
    public void setNumbers(int[][] numbers) {
        this.numbers = numbers;

    }

    public int[][] getNumbers()
    {
        return numbers;
    }

    public void printNumber() {
        for (int[] line : numbers) {
            System.out.println(Arrays.toString(line));
        }
    }

    public int getMarkPoint() {
        return MarkPoint;
    }

    public boolean getIfGenerate() {
        return ifGenerate;
    }

    public void setisMove(boolean b) {
        this.isMove = b;
    }

    public void addaudio(String path) {
        try {
            // 打开音频文件
            File audioFile = new File(path);
            AudioInputStream originalAudioStream = AudioSystem.getAudioInputStream(audioFile);

            // 获取音频格式
            AudioFormat originalFormat = originalAudioStream.getFormat();

            // 定义目标格式 (PCM_SIGNED)
            AudioFormat targetFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    originalFormat.getSampleRate(),
                    16,
                    originalFormat.getChannels(),
                    originalFormat.getChannels() * 2,
                    originalFormat.getSampleRate(),
                    false);

            // 转换音频输入流到目标格式
            audiostream = AudioSystem.getAudioInputStream(targetFormat, originalAudioStream);

            // 获取音频数据行信息
            info = new DataLine.Info(Clip.class, targetFormat);
        } catch (UnsupportedAudioFileException | IOException ex) {
            ex.printStackTrace();
        }
    }

    public void playaudio()
    {
        try {
            addaudio("Music/effect.wav");
            actionAudio = (Clip) AudioSystem.getLine(info);

            // 打开音频剪辑并加载样本
            System.out.println("播放开始");
            actionAudio.open(audiostream);
            actionAudio.start();
            // 获取音频数据行
            actionAudio.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    System.out.println("End");
                    actionAudio.close();
                }
            });
        }catch (LineUnavailableException ex)
        {
            System.out.println("播放开始1");
            throw new RuntimeException(ex);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
