package me.macialowskyyy.stonerust.events;

import me.macialowskyyy.stonerust.data.Config;
import me.macialowskyyy.stonerust.main.Main;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class onClick implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        if(e.getClickedBlock().getType()== Material.DEEPSLATE) {
            String okej = " " +e.getClickedBlock().getLocation().getBlockX() + " " + e.getClickedBlock().getLocation().getBlockY() + " " + e.getClickedBlock().getLocation().getBlockZ();
            e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§a§L" + Main.data.getHp(okej) + "HP / " + "§c§L" + Config.getInst().hp + " HP"));
        }
    }

}
