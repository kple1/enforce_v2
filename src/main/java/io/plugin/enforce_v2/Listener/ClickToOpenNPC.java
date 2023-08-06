package io.plugin.enforce_v2.Listener;

import io.plugin.enforce_v2.Main;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Set;

import static io.plugin.enforce_v2.Main.plugin;

public class ClickToOpenNPC implements Listener {

    @EventHandler
    public void onNPCRightClick(NPCRightClickEvent event) {
        Player player = event.getClicker();
        NPC npc = event.getNPC();

        Set<String> getNumber = plugin.getConfig().getConfigurationSection("오픈 강화목록").getKeys(false);
        for (String key : getNumber) {
            if (key == null) {
                continue;
            }

            String getConfigKey = plugin.getConfig().getString("오픈 강화목록." + key);
            if (npc.getName().equals(getConfigKey)) {
                Main.getPlugin().inventory(player);
                return;
            }
        }
    }
}
