package amata1219.fimbulwinter.dsl.type;

import org.bukkit.inventory.meta.Damageable;

public class ItemDamage {

    public static final ItemDamage DEFAULT = new ItemDamage(0);

    private final int damage;

    public ItemDamage(int damage) {
        if (damage < 0) throw new IllegalArgumentException("damage must be non-negative integer.");
        this.damage = damage;
    }

    public void setDamage(Damageable damageable) {
        damageable.setDamage(damage);
    }

}
