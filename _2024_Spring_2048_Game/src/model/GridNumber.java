package model;

import java.util.Arrays;
import java.util.Random;

public class GridNumber {
    private final int X_COUNT;
    private final int Y_COUNT;

    private int[][] numbers;

    static Random random = new Random();

    public GridNumber(int xCount, int yCount) {
        this.X_COUNT = xCount;
        this.Y_COUNT = yCount;
        this.numbers = new int[this.X_COUNT][this.Y_COUNT];
        this.initialNumbers();
    }

    public void initialNumbers() {
        int count=0;
        while (count<2)
        {
            int i=random.nextInt(4);
            int j=random.nextInt(4);
            if (numbers[i][j]==0)
            {
                numbers[i][j]=random.nextInt(2)==0? 2:4;
                count++;
            }
        }
    }
    //todo: finish the method of four direction moving.
    public void moveRight() {
        for (int i = 0; i < numbers.length; i++) {
            numbers[i][1] += numbers[i][0];
            numbers[i][0] = 0;
        }
    }

    public void moveLeft()
    {}

    public void moveUp()
    {}

    public void moveDown()
    {}
    public int getNumber(int i, int j) {
        return numbers[i][j];
    }

    public void printNumber() {
        for (int[] line : numbers) {
            System.out.println(Arrays.toString(line));
        }
    }
}
