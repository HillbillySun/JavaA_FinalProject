package view;

import util.ColorMap;

import javax.swing.*;
import java.awt.*;

public class GridComponent extends JComponent {
    private int row;
    private int col;
    private int number;
    static Font font = new Font("Serif", Font.BOLD, 42);

    public GridComponent(int row, int col, int gridSize) {
        this.setSize(gridSize, gridSize);
        this.row = row;
        this.col = col;
        this.number = 0;
    }

    public GridComponent(int row, int col, int number, int gridSize) {
        this.setSize(gridSize, gridSize);
        this.row = row;
        this.col = col;
        this.number = number;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (number > 0) {
            g.setColor(ColorMap.getColor(number));
            g.fillRect(0, 0, getWidth(), getHeight());
            // 设置边框的粗细为 3，并且设置边框颜色为浅灰色
            ((Graphics2D) g).setStroke(new BasicStroke(5));
            ((Graphics2D) g).setColor(Color.GRAY); // 将边框颜色设置为浅灰色
            // 绘制填充矩形
            g.drawRect(0, 0, getWidth(), getHeight());
            if (number > 4) {
                g.setColor(Color.WHITE);
            } else {
                g.setColor(Color.BLACK);
            }
            g.setFont(font);
            FontMetrics metrics = g.getFontMetrics(g.getFont());
            int textWidth = metrics.stringWidth(String.valueOf(number));
            int x = (getWidth() - textWidth) / 2;
            int y = (getHeight() - metrics.getHeight()) / 2 + metrics.getAscent();
            g.drawString(String.valueOf(number), x, y);
        } else {
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(0, 0, getWidth(), getHeight());
            // 设置边框的粗细为 3，并且设置边框颜色为浅灰色
            ((Graphics2D) g).setStroke(new BasicStroke(5));
            ((Graphics2D) g).setColor(Color.GRAY); // 将边框颜色设置为浅灰色
            // 绘制填充矩形
            g.drawRect(0, 0, getWidth(), getHeight());
        }
    }






    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
