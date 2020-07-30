package market;

import cropvariety.Corn;
import cropvariety.Crop;
import cropvariety.Pumpkin;
import cropvariety.Tomato;
import seedvariety.CornSeed;
import seedvariety.PumpkinSeed;
import seedvariety.Seed;
import seedvariety.TomatoSeed;

import java.util.Random;
import inventory.*;
import controller.*;

/**
 * This does all the work for the market UI.
 */

public class MarketBackEnd {
    private static Random rand = new Random();

    public static int calculateBuyPrice(Seed seed) {
        return (seed.getBasePrice()
                * ((InitialConfig.getDifficultyIndex() + 1)) + 5);
    }

    public static int calculateSellPrice(Crop crop) {
        return (crop.getBasePrice()
                * (12 / (1 + InitialConfig.getDifficultyIndex())));
    }

    public static void sellSeed(String seed, int price) {
        if (InventoryBackEnd.getSeedList().get(seed) == null) {
            return;
        } else if (InventoryBackEnd.getSeedList().get(seed) > 0) {
            Seed seedy = new CornSeed();
            if (seed.equals("corn")) {
                seedy = new CornSeed();
            } else if (seed.equals("tomato")) {
                seedy = new TomatoSeed();
            } else if (seed.equals("pumpkin")) {
                seedy = new PumpkinSeed();
            }
            UserInfo.setCurrentMoney(UserInfo.getCurrentMoney() + price);
            MarketUI.setMoneyLabel(UserInfo.getCurrentMoney());
            MainFarmScreen.setMoney(UserInfo.getCurrentMoney());
            InventoryBackEnd.removeSeed(seed);
        }
    }
    public static void sellCrop(String crop, int price) {
        if (InventoryBackEnd.getCropList().get(crop) == null) {
            return;
        } else if (InventoryBackEnd.getCropList().get(crop) > 0) {
            Crop croppy = new Corn();
            if (crop.equals("corn")) {
                croppy = new Corn();
            } else if (crop.equals("tomato")) {
                croppy = new Tomato();
            } else if (crop.equals("pumpkin")) {
                croppy = new Pumpkin();
            }
            UserInfo.setCurrentMoney(UserInfo.getCurrentMoney() + price);
            MarketUI.setMoneyLabel(UserInfo.getCurrentMoney());
            MainFarmScreen.setMoney(UserInfo.getCurrentMoney());
            InventoryBackEnd.removeCrop(crop);
        }
    }


    public static int buySeed(String seed, int price) {
        Seed seedy = new CornSeed();
        if (seed.equals("corn")) {
            seedy = new CornSeed();
        } else if (seed.equals("tomato")) {
            seedy = new TomatoSeed();
        } else if (seed.equals("pumpkin")) {
            seedy = new PumpkinSeed();
        }
        if (InventoryBackEnd.getCurrentCapacity() == InventoryBackEnd.getMaxCapacity()) {
            return InventoryBackEnd.addSeed(seed) ? 0 : 1;
        } else if (UserInfo.getCurrentMoney() >= price) {
            UserInfo.setCurrentMoney(UserInfo.getCurrentMoney() - calculateBuyPrice(seedy));
            MarketUI.setMoneyLabel(UserInfo.getCurrentMoney());
            MainFarmScreen.setMoney(UserInfo.getCurrentMoney());
            return InventoryBackEnd.addSeed(seed) ? 0 : 1;
        }

        return -1;
    }
}
