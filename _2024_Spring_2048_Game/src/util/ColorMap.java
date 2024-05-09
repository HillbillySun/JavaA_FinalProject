package util;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ColorMap {
    static Map<Integer, Color> colorMap = new HashMap<>();
    static Map<Integer,Color> colorMap2=new HashMap<>();

    //todo: complete the method to add other color
    public static void InitialColorMap() {
        colorMap.put(2, new Color(255, 182, 193));
        colorMap.put(4, new Color(255, 218, 185)); // 亮黄色
        colorMap.put(8, new Color(255, 220, 0)); // 金黄色
        colorMap.put(16, new Color(255, 200, 0)); // 橙黄色
        colorMap.put(32, new Color(255, 150, 0)); // 橙色
        colorMap.put(64, new Color(255, 100, 0)); // 橙红色
        colorMap.put(128, new Color(255, 50, 0)); // 红橙色
        colorMap.put(256, new Color(255, 0, 0)); // 橙红色
        colorMap.put(512, new Color(220, 0, 0)); // 红色
        colorMap.put(1024, new Color(190, 0, 0)); // 深红色
        colorMap.put(2048, new Color(160, 0, 0)); // 酒红色
    }

    public static Color getColor(int i) {
        return colorMap.getOrDefault(i, Color.black);
    }
}