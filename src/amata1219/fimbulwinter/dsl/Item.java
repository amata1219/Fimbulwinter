package amata1219.fimbulwinter.dsl;

import amata1219.fimbulwinter.Constants;
import amata1219.fimbulwinter.dsl.type.EnchantmentLevel;
import amata1219.fimbulwinter.dsl.type.ItemAmount;
import amata1219.fimbulwinter.dsl.type.ItemDamage;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;
import java.util.function.Consumer;

public class Item {

    public ItemStack baseItemStack;
    public Material type = Material.AIR;
    public ItemAmount amount = ItemAmount.ONE;
    public Map<Enchantment, EnchantmentLevel> enchantments = new HashMap<>();
    public ItemDamage damage = ItemDamage.DEFAULT;
    public String displayName;
    public List<String> lore = new ArrayList<>();
    public Set<ItemFlag> flags = new HashSet<>();
    public Multimap<Attribute, AttributeModifier> attributeModifiers = ArrayListMultimap.create();
    public Consumer<ItemStack> additionalSettings = Constants.noOperation();

    public void enchant(Enchantment enchantment, EnchantmentLevel level) {
        enchantments.put(enchantment, level);
    }

    public void lore(String... lines) {
        lore.addAll(Arrays.asList(lines));
    }

    public void flags(ItemFlag... flags) {
        this.flags.addAll(Arrays.asList(flags));
    }

    public void modifyAttribute(Attribute attribute, AttributeModifier modifier) {
        attributeModifiers.put(attribute, modifier);
    }

    ItemStack build(String executableItemId) {
        ItemStack item = baseItemStack != null ? baseItemStack : new ItemStack(type);
        amount.setAmount(item);

        enchantments.forEach((enchantment, level) -> item.addEnchantment(enchantment, level.level));

        ItemMeta meta = item.getItemMeta();
        if (meta instanceof Damageable) damage.setDamage((Damageable) meta);

        meta.setDisplayName(displayName);
        meta.setLore(lore);
        meta.addItemFlags(Iterables.toArray(flags, ItemFlag.class));
        meta.setAttributeModifiers(attributeModifiers);

        meta.getPersistentDataContainer().set(
                Constants.namespaced_key_of_executable_item_id(),
                PersistentDataType.STRING,
                executableItemId
        );

        item.setItemMeta(meta);

        additionalSettings.accept(item);

        return item;
    }

}
