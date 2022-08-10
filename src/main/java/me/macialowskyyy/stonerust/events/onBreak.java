package me.macialowskyyy.stonerust.events;

import me.macialowskyyy.stonerust.data.Config;
import me.macialowskyyy.stonerust.main.Main;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import java.util.ArrayList;
import java.util.List;

public class onBreak implements Listener {

    private List<String> holoak = new ArrayList<>();

    @EventHandler
    public void onBreaks(BlockBreakEvent e) {
        if(e.getBlock().getType()== Material.DEEPSLATE) {
            String okej = " " +e.getBlock().getLocation().getBlockX() + " " + e.getBlock().getLocation().getBlockY() + " " + e.getBlock().getLocation().getBlockZ();
            if(Main.data.existsCenter(okej)) {
                e.setCancelled(true);
                Config cfg = Config.getInst();
                int zniszczenia = 0;
                if(e.getPlayer().getItemInHand() != null) {
                    if(e.getPlayer().getItemInHand().getType() == Material.WOODEN_PICKAXE) {
                        zniszczenia = cfg.w;
                    } else if (e.getPlayer().getItemInHand().getType() == Material.STONE_PICKAXE) {
                        zniszczenia = cfg.s;
                    }
                    else if (e.getPlayer().getItemInHand().getType() == Material.IRON_PICKAXE) {
                        zniszczenia = cfg.i;
                    }
                    else if (e.getPlayer().getItemInHand().getType() == Material.GOLDEN_PICKAXE) {
                        zniszczenia = cfg.g;
                    }
                    else if (e.getPlayer().getItemInHand().getType() == Material.DIAMOND_PICKAXE) {
                        zniszczenia = cfg.d;
                    }
                    else if (e.getPlayer().getItemInHand().getType() == Material.NETHERITE_PICKAXE) {
                        zniszczenia = cfg.n;
                    }

                } else {
                    zniszczenia = 0;
                }


                Main.data.removeHp(Main.data.getCenter(okej), zniszczenia);

                e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§a§L" +Main.data.getHp(okej) + "HP / " + "§c§L" + Config.getInst().hp + " HP"));
                /*List<String> cord = new ArrayList<>();

                for(String str : Main.data.getCenter(okej).split(" ")) {
                    cord.add(str);
                }
                double y2 = Integer.parseInt(cord.get(1))+3.5;
                double x2 = Integer.parseInt(cord.get(0))+0.5;
                double z2 = Integer.parseInt(cord.get(2))+0.5;
                Plugin plugin = Main.getInst(); // Your plugin's instance
                Location where = new Location(e.getBlock().getWorld(),x2,y2,z2);
                Hologram holo = null;
                if(!holoak.contains("siema")) {
                    for(Hologram holos : HologramsAPI.getHolograms(plugin)) {
                        holos.delete();
                    }
                    holo = HologramsAPI.createHologram(plugin, where);
                    holo.appendItemLine(new ItemStack(Material.GOLDEN_PICKAXE));
                    holo.appendTextLine("§a§L" +Main.data.getHp(okej) + "HP / " + "§c§L" + Config.getInst().hp + " HP");
                    holo.getVisibilityManager().setVisibleByDefault(false);
                    holo.getVisibilityManager().showTo(e.getPlayer());
                    holo.teleport(where);
                    holoak.add("siema");
                } else {
                    for(Hologram holos : HologramsAPI.getHolograms(plugin)) {
                        holos.delete();
                    }
                    holo = HologramsAPI.createHologram(plugin, where);
                    holo.appendItemLine(new ItemStack(Material.GOLDEN_PICKAXE));
                    holo.appendTextLine("§a§L" +Main.data.getHp(okej) + "HP / " + "§c§L" + Config.getInst().hp + " HP");
                    holo.getVisibilityManager().setVisibleByDefault(false);
                    holo.getVisibilityManager().showTo(e.getPlayer());
                    holo.teleport(where);
                    holoak.clear();
                }*/


                if(Main.data.getHp(okej) <= 0) {

                    for(String it : Main.data.getBlocks(Main.data.getCenter(okej).replace(", ", " "))) {
                        StringBuilder st = new StringBuilder(it);
                        if(st.charAt(0)==' ') {
                            st.deleteCharAt(0);
                        }

                        it = st.toString();

                        String[] str = it.split(" ");
                        e.getPlayer().getWorld().getBlockAt(Integer.valueOf(str[0]), Integer.valueOf(str[1]), Integer.valueOf(str[2])).setType(Material.AIR);
                        e.getPlayer().getWorld().spawnParticle(Particle.BLOCK_CRACK, e.getPlayer().getWorld().getBlockAt(Integer.valueOf(str[0]), Integer.valueOf(str[1]), Integer.valueOf(str[2])).getLocation(),70, Material.DEEPSLATE.createBlockData());
                        e.getPlayer().getWorld().playSound(e.getPlayer().getWorld().getBlockAt(Integer.valueOf(str[0]), Integer.valueOf(str[1]), Integer.valueOf(str[2])).getLocation(),Sound.BLOCK_STONE_BREAK, 100, 1);
                    }

                    Main.data.deleteCenter(Main.data.getCenter(okej));
                    Main.data.deleteChunk(String.valueOf(e.getBlock().getChunk()));
                    Main.getInst().getData().set("struct",Main.getInst().getData().getInt("struct")-1);
                    Main.getInst().saveData();

                }

                /*final Timer timer = new Timer();

                final TimerTask task = new TimerTask() {

                    @Override
                    public void run() {
                        for(Hologram holos : HologramsAPI.getHolograms(plugin)) {
                            holos.delete();
                        }
                        timer.cancel(); // stop timer after execution
                    }
                };

                timer.schedule(task, 2000); // schedule task with delay of 1000ms*/

            }
        }
    }
}
