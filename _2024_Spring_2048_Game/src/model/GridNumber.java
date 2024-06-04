package model;

import view.GamePanel;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GridNumber {
    private final int X_COUNT;
    private final int Y_COUNT;
    private int MarkPoint;
    private boolean isMove;
    private int[][] numbers;
    private boolean ifGenerate = false;
    static Random random = new Random();
    private int target;
    private boolean isHard = false;
    private GamePanel gamePanel;

    public GridNumber(int xCount, int yCount) {
        this.X_COUNT = xCount;
        this.Y_COUNT = yCount;
        this.numbers = new int[this.X_COUNT][this.Y_COUNT];
        this.initialNumbers();
        setisMove(true);
    }

    public void ReviveNumbers() {
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < numbers[0].length; j++) {
                if (numbers[i][j] == 2 || numbers[i][j] == 4) {
                    numbers[i][j] = 0;
                }
            }
        }
    }

    public boolean isBomb()
    {
        boolean isBomb = false;
        Loop:
        for (int[] number : numbers) {
            for (int k: number) {
                if (k > 4) {
                    isBomb = true;
                    break Loop;
                }
            }
        }
        return isBomb;
    }
    public void LoadNumbers(int[][] _numbers) {
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < numbers[0].length; j++) {
                numbers[i][j] = _numbers[i][j];
            }
        }
    }

    public boolean mutiNumbers()
    {
        boolean isMulti = true;
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < numbers[0].length; j++) {
                if (numbers[i][j] == target/2 )
                {
                    isMulti = false;
                }
            }
        }
        if (isMulti)
        {
            for (int i = 0; i < numbers.length; i++) {
                for (int j = 0; j < numbers[0].length; j++) {
                    numbers[i][j] = 2*numbers[i][j];
                }
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null,"不可通过乘法直接达到目标分数!");
        }
        return isMulti;
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
        try {
            while (isHard && (gamePanel.getSteps() - 1)%10 == 0 && gamePanel.getSteps() > 0 )
            {
                System.out.println(gamePanel.getSteps());
                int count = 0;
                for (int i = 0; i < numbers.length; i++) {
                    for (int j = 0; j < numbers[0].length; j++) {
                        if (numbers[i][j] != 0) {
                            count++;
                        }
                    }
                }
                if (count == numbers.length*numbers[0].length) {
                    System.out.println(count);
                    break;
                }
                int row = random.nextInt(numbers.length);
                int col = random.nextInt(numbers[0].length);
                if (numbers[row][col] == 0) {
                    numbers[row][col] = 1;
                    break;
                }
            }
        }catch (NullPointerException ignore)
        {}
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
        boolean temp = ifGenerate;
        JustMoveRight();
        ifGenerate = temp;
        generateNumberRandomly();
        if (isPlay) {
            playAction("Music/effect.wav");
        }
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
        boolean temp = ifGenerate;
        JustMoveLeft();
        ifGenerate = temp;
        generateNumberRandomly();
        if (isPlay) {
            playAction("Music/effect.wav");
        }
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
        boolean temp = ifGenerate;
        JustMoveUp();
        ifGenerate = temp;
        generateNumberRandomly();
        if (isPlay) {
            playAction("Music/effect.wav");
        }
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
        boolean temp = ifGenerate;
        JustMoveDown();
        ifGenerate = temp;
        generateNumberRandomly();
        if (isPlay) {
            playAction("Music/effect.wav");
        }
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

    public int[][] getNumbers() {
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

    public void playAction(String path) {
        new Thread(() -> {
            try {
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
                AudioInputStream audiostream = AudioSystem.getAudioInputStream(targetFormat, originalAudioStream);

                // 获取音频数据行信
                DataLine.Info info = new DataLine.Info(Clip.class, targetFormat);
                Clip actionAudio = (Clip) AudioSystem.getLine(info);

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
            } catch (LineUnavailableException ex) {
                System.out.println("播放开始1");
                throw new RuntimeException(ex);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (UnsupportedAudioFileException e) {
                throw new RuntimeException(e);
            }
        }
        ).start();
    }
    public void setTarget(int target)
    {this.target = target;}
    public void setisHard(boolean b)
    {
        isHard = b;
    }
    public void setGamePanel(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;
    }
    public boolean getisHard()
    {return isHard;}
}
