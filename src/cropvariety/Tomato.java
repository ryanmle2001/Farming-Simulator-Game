package cropvariety;

public class Tomato extends Crop {
    //TODO Change maxwater needed later
    //TODO add "seedTomato" into index 0 (if we want to have four stages instead of three)
    public Tomato() {
        super("tomato", 5, new String[] {"sprout", "smallTomato", "bigTomato"}, 3);
    }
}