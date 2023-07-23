package io.plugin.enforce_v2.Listener;

import io.plugin.enforce_v2.Main;
import io.plugin.enforce_v2.Utils.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InvClickEvent implements Listener {

    String title = Color.chat("&f[ &c&l강화 &f] ");

    @EventHandler
    public void invClickEvent(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (!event.getView().getTitle().equals("강화")) {
            return;
        }

        if (event.getCurrentItem() == null) {
            return;
        }

        if (event.getCurrentItem().getType() == Material.AIR) {
            return;
        }

        if (event.getClickedInventory() == null) {
            return;
        }

        if (event.getClickedInventory().equals(player.getInventory())) {
            return;
        }

        if (event.getRawSlot() >= event.getInventory().getSize()) {
            return;
        }

        for (int i = 0; i < 36; i++) {
            if (i == 10 || i == 12 || i == 16) {
                continue;
            }

            if (event.getRawSlot() == i) {
                event.setCancelled(true);
            }
        }

        if (event.getSlot() == 14) {
            ItemStack itemInSlot10 = event.getView().getItem(10);
            ItemStack itemInSlot12 = event.getView().getItem(12);
            if (itemInSlot10 == null || itemInSlot12 == null) {
                player.sendMessage(title + "강화할 도구 또는 사용권을 배치 해주세요.");
                return;
            }

            ItemMeta itemMeta = itemInSlot10.getItemMeta();
            if (itemMeta != null) {
                Material itemType = itemInSlot10.getType();
                if (!(itemType == Material.DIAMOND_SWORD || itemType == Material.IRON_SWORD || itemType == Material.STONE_SWORD
                        || itemType == Material.GOLDEN_SWORD || itemType == Material.WOODEN_SWORD || itemType == Material.DIAMOND_CHESTPLATE
                        || itemType == Material.IRON_CHESTPLATE || itemType == Material.LEATHER_CHESTPLATE || itemType == Material.GOLDEN_CHESTPLATE
                        || itemType == Material.BOW || itemType == Material.DIAMOND_LEGGINGS || itemType == Material.IRON_LEGGINGS
                        || itemType == Material.LEATHER_LEGGINGS || itemType == Material.GOLDEN_LEGGINGS || itemType == Material.DIAMOND_BOOTS
                        || itemType == Material.IRON_BOOTS || itemType == Material.LEATHER_BOOTS || itemType == Material.GOLDEN_BOOTS
                        || itemType == Material.DIAMOND_HELMET || itemType == Material.IRON_HELMET || itemType == Material.LEATHER_HELMET
                        || itemType == Material.GOLDEN_HELMET)) {
                    player.sendMessage(title + "도구의 아이템 정보를 불러올 수 없습니다.");
                    return;
                }

                Material enforceType = itemInSlot12.getType();
                if (enforceType != Material.BOOK) {
                    player.sendMessage(title + "사용권의 아이템 정보를 불러올 수 없습니다.");
                    return;
                }
                Main.getPlugin().startTimer(event.getInventory(), event, player);
            }
        }
    }
}
