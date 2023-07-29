package io.plugin.enforce_v2.Utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ItemBuild {

    private static ItemStack result_LongType(Material type, int amount, String displayName, String... lore) {
        ItemStack stack = new ItemStack(type, amount);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(Arrays.asList(lore));
        stack.setItemMeta(meta);
        return stack;
    }

    private static ItemStack result_ShortType(Material type, int amount) {
        ItemStack stack = new ItemStack(type, amount);
        return stack;
    }

    private static ItemStack result_NameType(Material type, int amount, String displayName) {
        ItemStack stack = new ItemStack(type, amount);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(displayName);
        stack.setItemMeta(meta);
        return stack;
    }

    public static ItemStack result_EnchantType(Material type, int amount, String displayName, Enchantment enchantment, int level) {
        ItemStack stack = new ItemStack(type, amount);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(displayName);
        meta.addEnchant(enchantment, level, true);
        stack.setItemMeta(meta);
        return stack;
    }

    public static ItemStack giveEnforceItem(Enchantment enchantment, String level, String percentage, Player player, int levels) {
        ItemStack giveItem = result_EnchantType(Material.BOOK, 1, (Color.chat("&b&o&l강화책")), enchantment, levels);
        ItemMeta itemMeta = giveItem.getItemMeta();
        itemMeta.setLore(Arrays.asList(Color.chat("최대 강화 : " + level),
                Color.chat("&a강화 성공 확률 : " + percentage + "%")));
        giveItem.setItemMeta(itemMeta);
        player.getInventory().addItem(giveItem);
        return null;
    }

    public static ItemStack give(Player player) {
        ItemStack giveItem = result_EnchantType(Material.DIAMOND_SWORD, 1, (Color.chat("&b&o&l강화책")), Enchantment.DAMAGE_ALL, 6);
        ItemMeta itemMeta = giveItem.getItemMeta();
        itemMeta.setDisplayName(Color.chat("&f[&d&l6강&f] 다이아몬드 검"));
        giveItem.setItemMeta(itemMeta);
        player.getInventory().addItem(giveItem);
        return null;
    }

    public static ItemStack backUp(InventoryClickEvent event) {
        ItemStack itemToBackup = event.getView().getItem(10);
        event.getView().setItem(16, itemToBackup);
        event.getView().setItem(10, ItemBuild.AIR);
        event.getView().setItem(12, ItemBuild.AIR);
        return itemToBackup;
    }


    public final static ItemStack anvil = result_LongType(Material.ANVIL, 1, (Color.chat("&7[ 강화전용 모루 ]")),
            Color.chat("특수판별 코드 : 0a"));

    public final static ItemStack blackGlass = result_LongType(Material.BLACK_STAINED_GLASS_PANE, 1, "");
    public final static ItemStack AIR = result_ShortType(Material.AIR, 1);
    public final static ItemStack diamond = result_LongType(Material.DIAMOND, 1, Color.chat("&c&l&oStart!"));
    public final static ItemStack yellowGlass = result_NameType(Material.YELLOW_STAINED_GLASS_PANE, 1, Color.chat("&a&o&l강화중.."));
}
