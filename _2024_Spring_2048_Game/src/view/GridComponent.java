package view;

import util.ColorMap;

import javax.swing.*;
import java.awt.*;

public class GridComponent extends JComponent {
    private int row;
    private int col;
    private int number;
    static Font font = util.Font.creatFont("ttfFont/BungeeInline-Regular.ttf",40f);

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
    public void paintComponent(Graphics g1) {
        Graphics2D g = (Graphics2D) g1;
        super.paintComponent(g);
        int arcWidth = getWidth()/5;  // 圆角的宽度
        int arcHeight = getHeight()/5; // 圆角的高度

        if (number %2 == 0 && number > 0) {
            g.setColor(ColorMap.getColor(number));
            g.fillRoundRect(0, 0, getWidth(), getHeight(), arcWidth, arcHeight);
            g.setStroke(new BasicStroke(5));
            g.setColor(Color.GRAY);
            g.drawRoundRect(0, 0, getWidth(), getHeight(), arcWidth, arcHeight);
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
        } else if (number == 1) {
            g.setColor(new Color(201, 178, 142));
            g.fillRoundRect(0, 0, getWidth(), getHeight(), arcWidth, arcHeight);
            g.setStroke(new BasicStroke(5));
            g.setColor(Color.GRAY);
            g.drawRoundRect(0, 0, getWidth(), getHeight(), arcWidth, arcHeight);
        } else {
            g.setColor(Color.LIGHT_GRAY);
            g.fillRoundRect(0, 0, getWidth(), getHeight(), arcWidth, arcHeight);
            g.setStroke(new BasicStroke(5));
            g.setColor(Color.GRAY);
            g.drawRoundRect(0, 0, getWidth(), getHeight(), arcWidth, arcHeight);
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
