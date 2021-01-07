package amata1219.fimbulwinter.listener;

import amata1219.fimbulwinter.Constants;
import amata1219.fimbulwinter.ExecutableItemRegistry;
import amata1219.fimbulwinter.dsl.event.PlayerClickExecutableItemEvent;
import amata1219.fimbulwinter.dsl.ExecutableItem;
import amata1219.fimbulwinter.dsl.type.Tick;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;

public class PlayerClickExecutableItemListener implements Listener {

    private final ExecutableItemRegistry registry;
    private final HashMap<Player, HashMap<ExecutableItem, Long>> playersToCooldowns = new HashMap<>();

    public PlayerClickExecutableItemListener(ExecutableItemRegistry registry) {
        this.registry = registry;
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerClickExecutableItem(PlayerInteractEvent event) {
        if (event.getAction() == Action.PHYSICAL || !event.hasItem()) return;

        ItemStack item = event.getItem();
        if (!item.hasItemMeta()) return;

        String executableItemId = item.getItemMeta().getPersistentDataContainer().get(Constants.namespaced_key_of_executable_item_id(), PersistentDataType.STRING);
        if (executableItemId == null) return;

        ExecutableItem executableItem = registry.executableItem(executableItemId);
        if (executableItem == null || !executableItem.triggers.contains(event.getAction())) return;

        Player player = event.getPlayer();
        Tick cooldownTicks = executableItem.cooldownTicks;
        if (cooldownTicks.ticks > 0) {
            HashMap<ExecutableItem, Long> cooldowns = playersToCooldowns.get(player);
            if (cooldowns.containsKey(executableItem)) {
                long elapsedTimeTicks = (System.currentTimeMillis() - cooldowns.get(executableItem)) / 50;
                if (elapsedTimeTicks <= cooldownTicks.ticks) {
                    executableItem.actionDuringCooldown.accept(player);
                    return;
                } else {
                    cooldowns.put(executableItem, System.currentTimeMillis());
                }
            }
        }

        executableItem.execute(new PlayerClickExecutableItemEvent(player, event.getHand(), event.getClickedBlock(), event.getBlockFace()));

        if (executableItem.consumable) item.setAmount(item.getAmount() - 1);

        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        playersToCooldowns.put(event.getPlayer(), new HashMap<>());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        playersToCooldowns.remove(event.getPlayer());
    }

}
