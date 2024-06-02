package util;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ColorMap {
    public static Map<Integer, Color> InitialColorMap = new HashMap<>();
    public static Map<Integer,Color> HKcolorMap=new HashMap<>();

    //todo: complete the method to add other color
    public static void InitialColorMap() {
        InitialColorMap.put(2, new Color(248, 236, 154));
        InitialColorMap.put(4, new Color(255, 218, 185)); // 亮黄色
        InitialColorMap.put(8, new Color(255, 220, 0)); // 金黄色
        InitialColorMap.put(16, new Color(255, 200, 0)); // 橙黄色
        InitialColorMap.put(32, new Color(255, 150, 0)); // 橙色
        InitialColorMap.put(64, new Color(255, 100, 0)); // 橙红色
        InitialColorMap.put(128, new Color(255, 50, 0)); // 红橙色
        InitialColorMap.put(256, new Color(255, 0, 0)); // 橙红色
        InitialColorMap.put(512, new Color(220, 0, 0)); // 红色
        InitialColorMap.put(1024, new Color(190, 0, 0)); // 深红色
        InitialColorMap.put(2048, new Color(160, 0, 0)); // 酒红色
    }
//    public static void HkColorMap()
//    {
//        Color color1 = new Color(255, 215, 0);
//        Color color2 = new Color(250, 149, 34);
//        Color color3 = new Color(177, 245, 199);
//        for (int i = 2; i <=2048 ;i = i*2) {
//            if (Calexponent(i)%4 == 0) {
//                HKcolorMap.put(i,color1);
//            }
//            else if (Calexponent(i)%2 == 0) {
//                HKcolorMap.put(i,color2);
//            }
//            else {
//                HKcolorMap.put(i,color3);
//            }
//        }
//    }
//    public static int Calexponent(int number)
//    {
//        int count = 0;
//        while (number/2 != 0)
//        {
//            number = number/2;
//            count++;
//        }
//        return count;
//    }
    public static Color getColor(int i) {
        return InitialColorMap.getOrDefault(i, Color.black);
    }
//    public static Color getHkColor(int i)
//    {
//        return HKcolorMap.getOrDefault(i,Color.black);
//    }
}