package me.macialowskyyy.stonerust.events;

import me.macialowskyyy.stonerust.data.Config;
import me.macialowskyyy.stonerust.main.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class onChunkLoad implements Listener {

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent e) {
        if(!Main.data.existsChunk(String.valueOf(e.getChunk()))) {
            if(Main.getInst().getData().getInt("struct") <=99) {
                Random rnd = new Random();
                int i = rnd.nextInt(260);
                if (i <= 2) {

                    Random rand = new Random();
                    int x = rand.nextInt(16);
                    int z = rand.nextInt(16);



                    Location highest = e.getChunk().getWorld().getHighestBlockAt(x, z).getLocation();

                    int y = (int) (highest.getY() + 1);

                    while (e.getChunk().getBlock(x, y, z).getType() != Material.AIR) {
                        if (e.getChunk().getBlock(x, y + 1, z).getType() != Material.WATER || e.getChunk().getBlock(x, y + 1, z).getType() != Material.LAVA) {
                            y++;
                        } else {
                            break;
                        }
                    }

                    List<String> str = new ArrayList<>();

                    Location randLoc = e.getChunk().getBlock(x, y, z).getLocation();
                    if(randLoc.getX() <= Config.getInst().onex && randLoc.getZ() <= Config.getInst().onez && randLoc.getX() >= Config.getInst().twox && randLoc.getZ() >= Config.getInst().twoz && (e.getChunk().getBlock(x, y-1, z).getType() == Material.GRASS_BLOCK) || e.getChunk().getBlock(x, y-1, z).getType() == Material.SAND) {
                        for(int x1 = (int) randLoc.getX() - 2; x1 <= (int) randLoc.getX() + 2; x1++) {
                            for(int z1 = (int) randLoc.getZ() - 2; z1 <= (int) randLoc.getZ() + 2; z1++) {
                                e.getChunk().getWorld().getBlockAt(x1, y, z1).setType(Material.DEEPSLATE);
                                str.add(x1 + " " + y + " " + z1);
                            }
                        }
                        for(int x1 = (int) randLoc.getX() - 1; x1 <= (int) randLoc.getX() + 1; x1++) {
                            for(int z1 = (int) randLoc.getZ() - 1; z1 <= (int) randLoc.getZ() + 1; z1++) {
                                e.getChunk().getWorld().getBlockAt(x1, y+1, z1).setType(Material.DEEPSLATE);
                                int y1 = y+1;
                                str.add(x1 + " " + y1 + " " + z1);
                            }
                        }

                        Main.data.createCenter(randLoc.getBlockX() + " " + randLoc.getBlockY() + " " + randLoc.getBlockZ(),str,Config.getInst().hp);

                        Main.data.createChunk(String.valueOf(e.getChunk()));
                        Main.getInst().getData().set("struct",Main.getInst().getData().getInt("struct")+1);
                        Main.getInst().saveData();
                    }
                }
            }
        }
    }


    /*public static void teleport(Player p, World world) {
        try {

            /*ArrayList<Material> disabled_blocks = new ArrayList<>();
            for(String mat : ((List<String>) Configuration.get("disabled_blocks"))) {
                Material material = Material.getMaterial(mat);
                if(material != null) {
                    disabled_blocks.add(material);
                }
            }

            Location loc = null;
            while(loc == null || disabled_blocks.contains(loc.getBlock().getType()) || disabled_blocks.contains(loc.getBlock().getRelative(BlockFace.DOWN).getType())) {
                loc = getLocation(world, getX(), getZ());
            }

            p.teleport(loc);
        } catch (NullPointerException e) {

            System.err.println("[RandomTP] Cannot read list disabled_blocks. There may be occured an error while an administrator created the list.");
            p.teleport(getLocation(world, getX(), getZ()));

        }
    }*/
}
