package amata1219.fimbulwinter.dsl.type;

public class EnchantmentLevel {

    public final int level;

    public EnchantmentLevel(int level) {
        if (!(0 < level && level < 128)) throw new IllegalArgumentException("level must be contained in (0, 128)");
        this.level = level;
    }

}
