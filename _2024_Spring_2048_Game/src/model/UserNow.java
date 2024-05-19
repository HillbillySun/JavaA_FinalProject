package model;

import view.GridComponent;

public class UserNow {
    public static String username;
    public static GridComponent[][]a;
    public static void setUsername(String name) {
        username = name;
    }

    public static String getUsername() {
        return username;
    }

    public static void setA(GridComponent[][] a) {
        UserNow.a = a;
    }

    public static GridComponent[][] getA() {
        return a;
    }
}
