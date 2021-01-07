package amata1219.fimbulwinter;

import amata1219.fimbulwinter.dsl.ExecutableItem;
import amata1219.fimbulwinter.dsl.event.PlayerClickExecutableItemEvent;
import amata1219.fimbulwinter.dsl.type.EnchantmentLevel;
import amata1219.fimbulwinter.dsl.type.ItemAmount;
import amata1219.fimbulwinter.dsl.type.Tick;
import amata1219.fimbulwinter.enchantment.GleamEnchantment;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;
import java.util.function.Consumer;

public class SampleItem extends ExecutableItem {

    public SampleItem() {
        super("sample_item", true, new Tick(20), (player, remainingTimeTicks) -> {
            player.sendMessage(ChatColor.RED + "Can't run it, it's on cooldown. " + remainingTimeTicks + " ticks remaining.");
            player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_CHAIN, 1.0f, 0.2f);
        }, Action.RIGHT_CLICK_AIR, Action.RIGHT_CLICK_BLOCK);

        // super(id, consumable, cooldownTicks, actionDuringCooldown, triggers...)
    }

    // Define the item
    @Override
    public ItemStack build(Player user) {
        return define(i -> {
            // Set the type
            i.type = Material.GLASS_BOTTLE;

            // Set the amount
            i.amount = new ItemAmount(3);

            // Set the display name
            i.displayName = ChatColor.WHITE + "Sylphid's Magic in a Bottle";

            // Add the lore
            i.lore(
                    ChatColor.GRAY + "Don't use this magic.",
                    ChatColor.GRAY + "Be proud to keep this magic locked in a bottle."
            );

            // Enchant
            // `GleamEnchantment` is an effect undefined enchantment intended to grant enchantment auras.
            i.enchant(GleamEnchantment.INSTANCE, new EnchantmentLevel(1));

            // Modify the attribute
            i.modifyAttribute(Attribute.GENERIC_LUCK, new AttributeModifier(UUID.randomUUID(), "generic.luck", 7.0f, AttributeModifier.Operation.ADD_NUMBER));
        });
    }

    // Write the action to be executed when clicked
    @Override
    public void execute(PlayerClickExecutableItemEvent event) {
        event.blockClicked.getWorld().createExplosion(event.blockClicked.getLocation(), 12.0f);
        event.player.sendMessage(ChatColor.GREEN + "Sylphid's magic overflowed.");
    }

}
