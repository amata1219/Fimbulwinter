package amata1219.fimbulwinter.dsl.type;

public class Tick {

    public static final Tick ZERO = new Tick(0);

    public final int ticks;

    public Tick(int ticks) {
        if (ticks < 0) throw new IllegalArgumentException("ticks must be non-negative integer.");
        this.ticks = ticks;
    }

}
