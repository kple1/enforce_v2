package io.plugin.enforce_v2.Listener;

import io.plugin.enforce_v2.Data.UserData;
import io.plugin.enforce_v2.Main;
import io.plugin.enforce_v2.Utils.Color;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        YamlConfiguration config = UserData.getPlayerConfig(player);
        int getLock = config.getInt("Lock");
        for (int i = 0; i < 36; i++) {
            if (getLock == 0) {
                if (i == 10 || i == 12 || i == 16) {
                    continue;
                }
            } else {
                player.sendMessage(title + "강화도중에는 아이템을 꺼낼 수 없습니다.");
                event.setCancelled(true);
                break;
            }

            if (event.getRawSlot() == i) {
                event.setCancelled(true);
            }
        }

        if (event.getSlot() == 14) {
            //enchant level 얻어오기
            int level = 0;
            Map<Enchantment, Integer> enchantments = event.getView().getItem(10).getEnchantments();
            if (!enchantments.isEmpty()) {
                level = enchantments.entrySet().iterator().next().getValue();
            }

            int maxEnchantmentLevel = 0;
            Map<Enchantment, Integer> enchantments1 = event.getView().getItem(12).getEnchantments();
            if (!enchantments1.isEmpty()) {
                maxEnchantmentLevel = enchantments1.entrySet().iterator().next().getValue();
            }

            //최대레벨 저장
            int num = 0;
            int percent = 0;
            String pattern = "\\d+";
            Pattern p = Pattern.compile(pattern);

            String lore = event.getView().getItem(12).getItemMeta().getLore().toString();
            Matcher matcher = p.matcher(lore);

            String numericPartLevel = "";
            String numericPartPercent = "";

            if (matcher.find()) {
                numericPartLevel = matcher.group();
            }

            if (matcher.find()) {
                numericPartPercent = matcher.group();
            }

            num = Integer.parseInt(numericPartLevel);
            percent = Integer.parseInt(numericPartPercent);

            //data save map으로 가능하지만 눈으로 볼 수 있는 도구가 필요 했음
            ItemStack itemStack = event.getView().getItem(10);
            ItemMeta itemMeta10Slot = itemStack.getItemMeta();

            config.set("itemInfo.1.level", level);
            config.set("itemInfo.2.level", maxEnchantmentLevel);
            config.set("itemInfo.2.maxLevel", num);
            config.set("itemInfo.2.percent", percent);
            Main.getPlugin().saveYamlConfiguration();

            ItemStack itemInSlot10 = event.getView().getItem(10);
            ItemStack itemInSlot12 = event.getView().getItem(12);
            if (itemInSlot10 == null || itemInSlot12 == null) {
                player.sendMessage(title + "강화할 도구 또는 사용권을 배치해주세요.");
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
                        || itemType == Material.GOLDEN_HELMET || itemType == Material.DIAMOND_PICKAXE || itemType == Material.IRON_PICKAXE
                        || itemType == Material.GOLDEN_PICKAXE || itemType == Material.WOODEN_PICKAXE || itemType == Material.DIAMOND_AXE
                        || itemType == Material.IRON_AXE || itemType == Material.GOLDEN_AXE || itemType == Material.WOODEN_AXE
                        || itemType == Material.DIAMOND_SHOVEL || itemType == Material.IRON_SHOVEL || itemType == Material.GOLDEN_SHOVEL
                        || itemType == Material.WOODEN_SHOVEL || itemType == Material.DIAMOND_HOE || itemType == Material.IRON_HOE
                        || itemType == Material.GOLDEN_HOE || itemType == Material.WOODEN_HOE || itemType == Material.ELYTRA
                        || itemType == Material.FISHING_ROD)) {
                    player.sendMessage(title + "도구의 아이템 정보를 불러올 수 없습니다.");
                    return;
                }

                Material enforceType = itemInSlot12.getType();
                if (enforceType != Material.BOOK) {
                    player.sendMessage(title + "사용권의 아이템 정보를 불러올 수 없습니다.");
                    return;
                }

                int getLevel1 = config.getInt("itemInfo.1.level");
                int getLevel2 = config.getInt("itemInfo.2.level");
                if (!(getLevel1 - getLevel2 == -1)) {
                    player.sendMessage(title + "아이템 레벨격차가 맞지않습니다.");
                    return;
                }

                //최대레벨 제한
                int getOneSlotLevel = config.getInt("itemInfo.1.level");
                int getTwoSlotLevel = config.getInt("itemInfo.2.maxLevel");
                if (getOneSlotLevel == getTwoSlotLevel) {
                    player.sendMessage(title + "최대레벨에 도달했습니다!");
                    return;
                }

                //강화가 진행중인데도 버튼을 누름을 방지하기 위한 Lock
                if (getLock == 1) {
                    player.sendMessage(title + "강화가 진행 중입니다!");
                    return;
                }

                Main.getPlugin().startTimer(event.getInventory(), event, player); //결과 도출

                config.set("Lock", 1);
                Main.getPlugin().saveYamlConfiguration();
            }
        }
    }
}
