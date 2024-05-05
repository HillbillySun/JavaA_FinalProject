package util;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ColorMap {
    static Map<Integer, Color> colorMap = new HashMap<>();

    //todo: complete the method to add other color
    public static void InitialColorMap() {
        colorMap.put(2, new Color(255, 69, 0)); // 红色
        colorMap.put(4, new Color(255, 140, 0)); // 橙色
        colorMap.put(8, new Color(255, 211, 0)); // 黄色
        colorMap.put(16, new Color(0, 255, 0)); // 绿色
        colorMap.put(32, new Color(0, 255, 233)); // 青色
        colorMap.put(64, new Color(0, 0, 255)); // 蓝色
        colorMap.put(128, new Color(138, 43, 226)); // 紫色
        colorMap.put(256, new Color(255, 20, 147)); // 品红色
        colorMap.put(512, new Color(255, 105, 180)); // 粉红色
        colorMap.put(1024, new Color(255, 215, 0)); // 金色
        colorMap.put(2048, new Color(255, 0, 0)); // 橙红色
    }

    public static Color getColor(int i) {
        return colorMap.getOrDefault(i, Color.black);
    }
}