package amata1219.fimbulwinter.dsl.type;

import org.bukkit.inventory.ItemStack;

public class ItemAmount {

    public static final ItemAmount ONE = new ItemAmount(1);

    private final int amount;

    public ItemAmount(int amount) {
        if (!(0 < amount && amount <= 64)) throw new IllegalArgumentException("amount must be contained in (0, 64]");
        this.amount = amount;
    }

    public void setAmount(ItemStack item) {
        item.setAmount(amount);
    }

}
