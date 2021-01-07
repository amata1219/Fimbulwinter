package amata1219.fimbulwinter.dsl;

import amata1219.fimbulwinter.Constants;
import amata1219.fimbulwinter.dsl.event.PlayerClickExecutableItemEvent;
import amata1219.fimbulwinter.dsl.type.Tick;
import com.google.common.collect.ImmutableSet;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import java.util.Set;
import java.util.function.Consumer;

public abstract class ExecutableItem {

    public final String id;
    public final boolean consumable;
    public final Tick cooldownTicks;
    public final Consumer<Player> actionDuringCooldown;
    public final Set<Action> triggers;

    public ExecutableItem(String id, boolean consumable, Tick cooldownTicks, Consumer<Player> actionDuringCooldown, Action... triggers) {
        this.id = id;
        this.consumable = consumable;
        this.actionDuringCooldown = actionDuringCooldown;
        this.cooldownTicks = cooldownTicks;
        this.triggers = ImmutableSet.copyOf(triggers);
    }

    public ExecutableItem(String id, boolean consumable, Tick cooldownTicks, Action... triggers) {
        this(id, consumable, cooldownTicks, Constants.noOperation(), triggers);
    }

    public abstract ItemStack build(Player user);

    protected ItemStack define(Consumer<Item> settings) {
        Item item = new Item();
        settings.accept(item);
        return item.build(id);
    }

    public abstract void execute(PlayerClickExecutableItemEvent event);

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}
