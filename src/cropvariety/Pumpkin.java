package cropvariety;

public class Pumpkin extends Crop {
    //TODO Change maxwater needed later
    //TODO add "seedPumpkin" into index 0 (if we want to have four stages instead of three)
    public Pumpkin() {
        super("pumpkin", 7, new String[] {"sprout",  "smallPumpkin", "bigPumpkin"}, 8);
    }
}