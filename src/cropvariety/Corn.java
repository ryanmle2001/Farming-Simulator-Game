package cropvariety;

public class Corn extends Crop {
    //TODO Change maxwater needed later
    //TODO add "seedCorn" into index 0 (if we want to have four stages instead of three)
    public Corn() {
        super("corn", 10, new String[] {"sprout", "smallCorn", "bigCorn"}, 3);
    }
}
