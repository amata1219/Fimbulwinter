package amata1219.fimbulwinter;

import amata1219.fimbulwinter.dsl.ExecutableItem;
import amata1219.fimbulwinter.enchantment.GleamEnchantment;
import amata1219.fimbulwinter.listener.PlayerClickExecutableItemListener;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

public class Fimbulwinter extends JavaPlugin implements FimbulwinterAPI {

    private final ExecutableItemRegistry executableItemRegistry = new ExecutableItemRegistry();

    @Override
    public void onEnable() {
        Constants.NAMESPACED_KEY_OF_EXECUTABLE_ITEM_ID = new NamespacedKey(this, "executable_item_id");

        registerGleamEnchantment();

        getServer().getPluginManager().registerEvents(new PlayerClickExecutableItemListener(executableItemRegistry), this);
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
    }

    public void registerGleamEnchantment() {
        Field acceptingNew;
        try {
            acceptingNew = Enchantment.class.getDeclaredField("acceptingNew");
            acceptingNew.setAccessible(true);
            acceptingNew.set(null, true);
            Enchantment.registerEnchantment(GleamEnchantment.INSTANCE);
            acceptingNew.set(null, false);
            acceptingNew.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException ignored) {

        }
    }

    @Override
    public void register(ExecutableItem item) {
        executableItemRegistry.register(item);
    }

    @Override
    public void unregister(ExecutableItem item) {
        executableItemRegistry.unregister(item);
    }

}
