package model;

import java.util.Arrays;
import java.util.Random;

public class GridNumber {
    private final int X_COUNT;
    private final int Y_COUNT;

    private int MarkPoint;
    private boolean isMove;
    private int[][] numbers;

    private boolean ifGenerate=false;

    static Random random = new Random();

    public GridNumber(int xCount, int yCount) {
        this.X_COUNT = xCount;
        this.Y_COUNT = yCount;
        this.numbers = new int[this.X_COUNT][this.Y_COUNT];
        this.initialNumbers();
        setisMove(true);
    }

    public void initialNumbers() {
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < numbers[0].length; j++) {
                numbers[i][j]=0;
            }
        }
        int count = 0;
        int temp=2;
        while (count < 2) {
            int i = random.nextInt(numbers.length);
            int j = random.nextInt(numbers[0].length);
            if (numbers[i][j] == 0) {
                numbers[i][j]=temp;
                temp=temp*2;
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
            MarkPoint=0;
            JustMoveRight();
            for (int i = 0; i < numbers.length; i++) {
                for (int j = numbers[0].length - 1; j >= 0; j--) {
                    if (j != 0 && numbers[i][j] != 0 && numbers[i][j - 1] == numbers[i][j]) {
                        numbers[i][j] = 2 * numbers[i][j];
                        numbers[i][j - 1] = 0;
                        MarkPoint+=numbers[i][j];
                        ifGenerate=true;
                    }
                }
            }
            boolean temp =ifGenerate;
            JustMoveRight();
            ifGenerate= temp;
            generateNumberRandomly();
    }

    public void moveLeft() {
            MarkPoint=0;
            JustMoveLeft();
            for (int i = 0; i < numbers.length; i++) {
                for (int j = 0; j < numbers[0].length; j++) {
                    if (j != numbers[0].length - 1 && numbers[i][j] != 0 && numbers[i][j + 1] == numbers[i][j]) {
                        numbers[i][j] = 2 * numbers[i][j];
                        numbers[i][j + 1] = 0;
                        MarkPoint+=numbers[i][j];
                        ifGenerate=true;
                    }
                }
            }
            boolean temp =ifGenerate;
            JustMoveLeft();
            ifGenerate= temp;
            generateNumberRandomly();
    }

    public void moveUp() {
            MarkPoint=0;
            JustMoveUp();
            for (int j = 0; j < numbers[0].length; j++) {
                for (int i = 0; i < numbers.length; i++) {
                    if (i != numbers[0].length - 1 && numbers[i][j] != 0 && numbers[i][j] == numbers[i + 1][j]) {
                        numbers[i][j] = 2 * numbers[i][j];
                        numbers[i + 1][j] = 0;
                        MarkPoint+=numbers[i][j];
                        ifGenerate=true;
                    }
                }
            }
            boolean temp =ifGenerate;
            JustMoveUp();
            ifGenerate= temp;
            generateNumberRandomly();
    }

    public void moveDown() {
            MarkPoint=0;
            JustMoveDown();
            for (int j = 0; j < numbers[0].length; j++) {
                for (int i = numbers.length-1; i >=0; i--) {
                    if (i!=0 && numbers[i][j] != 0 && numbers[i][j] == numbers[i - 1][j]) {
                        numbers[i][j] = 2 * numbers[i][j];
                        numbers[i - 1][j] = 0;
                        MarkPoint+=numbers[i][j];
                        ifGenerate=true;
                    }
                }
            }
            boolean temp =ifGenerate;
            JustMoveDown();
            ifGenerate= temp;
            generateNumberRandomly();
    }

    public void JustMoveRight() {
        ifGenerate=false;
        for (int i = 0; i < numbers.length; i++) {
            for (int j = numbers[0].length - 1; j >= 0; j--) {
                if (j != numbers[0].length - 1 && numbers[i][j] != 0) {
                    int k = j + 1;
                    /*
                    k用于记录下一格坐标
                    */
                    while (k < numbers[0].length && numbers[i][k] == 0) {
                        ifGenerate=true;
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
        ifGenerate=false;
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < numbers[0].length; j++) {
                if (j != 0 && numbers[i][j] != 0) {
                    int k = j - 1;
                    /*
                    k用于记录下一格坐标
                    */
                    while (k >= 0 && numbers[i][k] == 0) {
                        ifGenerate=true;
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
        ifGenerate=false;
        for (int j = 0; j < numbers[0].length; j++) {
            for (int i = 0; i < numbers.length; i++) {
                if (i != 0 && numbers[i][j] != 0) {
                    int k = i - 1;
                    while (k >= 0 && numbers[k][j] == 0) {
                        int temp = numbers[k + 1][j];
                        numbers[k + 1][j] = 0;
                        numbers[k][j] = temp;
                        k--;
                        ifGenerate=true;
                    }
                }
            }
        }
    }

    public void JustMoveDown() {
        ifGenerate=false;
        for (int j = 0; j < numbers[0].length; j++) {
            for (int i = numbers.length-1; i >=0; i--) {
                if (i != numbers.length - 1 && numbers[i][j] != 0) {
                    int k = i + 1;
                    while (k < numbers.length && numbers[k][j] == 0) {
                        ifGenerate=true;
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

    public int[][] getNumbers()
    {
        return numbers;
    }

    public void printNumber() {
        for (int[] line : numbers) {
            System.out.println(Arrays.toString(line));
        }
    }

    public int getMarkPoint()
    {
        return MarkPoint;
    }

    public boolean getIfGenerate() {
        return ifGenerate;
    }
    public void setisMove(boolean b)
    {
        this.isMove=b;
    }
}
