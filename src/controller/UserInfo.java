package controller;

/**
 * Stores User information
 * Includes:
 *  - name
 *  - difficulty
 */

public class UserInfo {
    private static String name;
    private static int difficulty;
    private static int currentMoney = 0;

    public static String getName() {
        return name;
    }

    public static int getDifficulty() {
        return difficulty;
    }

    public static void setName(String name) {
        UserInfo.name = name;
    }

    public static void setDifficulty(int difficulty) {
        UserInfo.difficulty = difficulty;
    }

    public static int getCurrentMoney() {
        return currentMoney;
    }
    public static void setCurrentMoney(int money) {
        currentMoney = money;
    }
}
