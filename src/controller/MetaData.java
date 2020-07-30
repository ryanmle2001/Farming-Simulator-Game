package controller;

public class MetaData {
    private static String[] cropList = {"corn", "tomato", "pumpkin"}; // png
    private static String[] difficulty = {"easy", "medium", "hard"};
    private static String[] titleImages = {"farm", "farm"}; // png
    private static String[] producerImages = {"koala", "koala"}; // png
    private static String[] topViewImages = {"cloud", "cloud", "cloud", "cloud", "cloud", "sun"};
    private static  int[] moneyList = {300, 200, 100};
    private static int plotNum = 18;
    private static int popUpHeight = 1000;
    private static int popUpWidth = 700;
    // private static int[] maxCapacity = {30, 20, 10}; for future implementation


    public static String[] getTopViewImages() {
        return topViewImages;
    }

    public static String[] getProducerImages() {
        return producerImages;
    }

    public static String[] getTitleImages() {
        return titleImages;
    }

    public static int getCropIndex(String selected) {
        for (int i = 0; i < cropList.length; i++) {
            if (cropList[i].equals(selected)) {
                return i;
            }
        }
        return 0;
    }
    public static int getDifficultyIndex(String selected) {
        for (int i = 0; i < difficulty.length; i++) {
            if (difficulty[i].equals(selected)) {
                return i;
            }
        }
        return 0;
    }

    public static int[] getMoneyList() {
        return moneyList;
    }

    public static int getPlotNum() {
        return plotNum;
    }

    public static String[] getCropList() {
        return cropList;
    }

    public static String[] getDifficulty() {
        return difficulty;
    }
}
