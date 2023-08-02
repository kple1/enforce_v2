/*
package io.plugin.enforce_v2.Utils;

import io.plugin.enforce_v2.Data.UserData;
import io.plugin.enforce_v2.Main;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Click14SlotStatEnchant {

    String title = Color.chat("&f[ &c&l강화 &f] ");
    private int taskId;

    public void statEnforce(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        //스텟권 감지
        ItemStack Slot12Item = event.getView().getItem(12);
        for (int i = 0; i < 7; i++) {
            if (!(Slot12Item != null && Slot12Item.hasItemMeta() && Slot12Item.getItemMeta().getDisplayName().equals("스텟강화권"))) {
                return;
            }
        }

        //2차 강화 여부
        ItemStack item = event.getView().getItem(10);
        if (item != null && item.hasItemMeta() && item.getItemMeta().hasEnchants()) {
            Map<Enchantment, Integer> enchantments = item.getItemMeta().getEnchants();
            if (enchantments.size() < 2) {
                player.sendMessage(title + "마법부여가 완료되지 않았습니다.");
                return;
            }
        } else {
            player.sendMessage(title + "마법부여가 완료되지 않았습니다.");
            return;
        }
        startTimerClick14SlotNormalEnchant(event.getInventory(), event, player);
    }

    public void startTimerClick14SlotNormalEnchant(Inventory inv, InventoryClickEvent event, Player player) {
        taskId = new BukkitRunnable() {
            int time = 1;

            @Override
            public void run() {
                if (time >= 1 && time <= 9) {
                    inv.setItem(26 + time, ItemBuild.yellowGlass);
                }

                if (time == 9) {
                    YamlConfiguration config = UserData.getPlayerConfig(player);
                    config.set("Lock", 0);
                    Main.getPlugin().saveYamlConfiguration();
                    for (int i = 27; i < 36; i++) {
                        inv.setItem(i, ItemBuild.blackGlass);
                    }

                    if (event.getView().getItem(16) != null) {
                        event.getView().setItem(10, ItemBuild.AIR);
                        event.getView().setItem(12, ItemBuild.AIR);
                    }
                    handleLectureWin(player, inv, event);
                }
                time++;
            }
        }.runTaskTimer(Main.getPlugin(), 0, 20).getTaskId(); // 1초마다 실행
    }

    private final int defaultProbability = 50;

    // 확률에 따른 강 수를 반환하는 함수
    private int getLectureNumber(String displayName) {
        int lectureNumber = 0;
        StringBuilder numberString = new StringBuilder();
        for (char c : displayName.toCharArray()) {
            if (Character.isDigit(c)) {
                numberString.append(c);
            } else {
                break;
            }
        }
        if (numberString.length() > 0) {
            lectureNumber = Integer.parseInt(numberString.toString());
        }
        return lectureNumber;
    }

    private boolean isWin(int lectureNumber) {
        int[] probabilities = {50, 30, 20, 10, 5, 3, 1};
        int probability = probabilities[lectureNumber - 1];
        return Math.random() < (probability / 100.0);
    }

    private void modifyItemByProbability(ItemStack item, Player player) {
        double[] probabilities = {50, 40, 30, 20, 10, 5, 4, 3, 2, 1, 0.5, 0.1};
        double randomValue = Math.random() * 100;
        double cumulativeProbability = 0;

        for (double probability : probabilities) {
            cumulativeProbability += probability;
            if (randomValue < cumulativeProbability) {
                modifyItemStat(item, cumulativeProbability, player);
                return;
            }
        }
    }

    private void modifyItemStat(ItemStack item, double cumulativeProbability, Player player) {
        double statIncrease = cumulativeProbability / 100.0;

        if (item.getType() == Material.DIAMOND_CHESTPLATE || item.getType() == Material.LEATHER_CHESTPLATE) {
           // modifyArmor(item, statIncrease);
        } else if (item.getType() == Material.DIAMOND_SWORD || item.getType() == Material.WOODEN_SWORD) {
            modifyWeapon(item, statIncrease, player);
        }
    }

    */
/*private void modifyArmor(ItemStack item, double statIncrease) {
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta == null) return;

        double currentArmor = 0;
        AttributeModifier existingModifier = (AttributeModifier) itemMeta.getAttributeModifiers(Attribute.GENERIC_ARMOR);
        if (existingModifier != null) {
            currentArmor = existingModifier.getAmount();
        }

        AttributeModifier newModifier = new AttributeModifier(UUID.randomUUID(), "CustomArmor", currentArmor + statIncrease, AttributeModifier.Operation.ADD_NUMBER);
        itemMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, newModifier);

        item.setItemMeta(itemMeta);
    }*//*


    private void modifyWeapon(ItemStack item, double statIncrease, Player player) {
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta == null) {
            player.sendMessage("itemMeta is null");
            return;
        }

        double currentDamage = 0.0;
        Collection<AttributeModifier> attackModifiers = itemMeta.getAttributeModifiers(Attribute.GENERIC_ATTACK_DAMAGE);
        if (attackModifiers != null) {
            for (AttributeModifier modifier : attackModifiers) {
                currentDamage = modifier.getAmount();
            }
        } else {
            player.sendMessage("attackModifiers is null");
        }
        player.sendMessage(String.valueOf(currentDamage));

        AttributeModifier newDamageModifier = new AttributeModifier(UUID.randomUUID(), "generic.attack_damage", currentDamage + statIncrease, AttributeModifier.Operation.ADD_NUMBER);
        itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, newDamageModifier);

        double currentSpeed = 0.0;
        Collection<AttributeModifier> speedModifiers = itemMeta.getAttributeModifiers(Attribute.GENERIC_ATTACK_DAMAGE);
        if (speedModifiers != null) {
            for (AttributeModifier modifier : speedModifiers) {
                currentSpeed = modifier.getAmount();
            }
        } else {
            player.sendMessage("null");
        }

        AttributeModifier newSpeedModifier = new AttributeModifier(UUID.randomUUID(), "generic.attack_speed", currentSpeed + statIncrease, AttributeModifier.Operation.ADD_NUMBER);
        itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, newSpeedModifier);

        item.setItemMeta(itemMeta);
    }

    public void handleLectureWin(Player player, Inventory inv, InventoryClickEvent event) {
        ItemStack item = event.getView().getItem(10);
        String displayName = item.getItemMeta().getDisplayName();
        int lectureNumber = getLectureNumber(displayName);

        if (lectureNumber == 0) {
            lectureNumber = 1;
        }

        if (isWin(lectureNumber)) {
            int num = 0;
            ItemStack itemStack = event.getView().getItem(10);
            ItemMeta itemMeta = itemStack.getItemMeta();
            String displayName1 = itemMeta.getDisplayName();
            String pattern = "\\d+";
            Pattern p = Pattern.compile(pattern);
            Matcher matcher = p.matcher(displayName1);

            if (matcher.find()) {
                String numericPart = matcher.group();
                num = Integer.parseInt(numericPart);
                num = num + 1;
            } else {
                num = 1;
            }

            // 1 ~ 4강 스텟 업그레이드
            for (int i = 1; i < 5; i++) {
                if (num == i) {
                    modifyItemByProbability(item, player);
                }
            }
            itemMeta.setDisplayName(Color.chat("&f[&d&l" + num + "강&f] " + getItemName(itemStack)));
            itemStack.setItemMeta(itemMeta);
            inv.setItem(16, event.getView().getItem(10));
            player.sendMessage(title + "강화에 성공하셨습니다.");
        } else {
            inv.setItem(16, event.getView().getItem(10));
            player.sendMessage(title + "강화에 실패하셨습니다.");
        }
        event.getView().setItem(10, ItemBuild.AIR);
        event.getView().setItem(12, ItemBuild.AIR);
    }

    private String getItemName(ItemStack item) {
        String itemName = item.getType().toString().toLowerCase();
        return itemName.replace("", "");
    }
}*/
