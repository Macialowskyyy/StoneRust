package me.macialowskyyy.stonerust.data;

import com.google.common.collect.ImmutableList;

import me.macialowskyyy.stonerust.main.Main;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Config {
    //=========================================================================
    private static Config inst;
    private Main MainInstance;
    public FileConfiguration cfg = Main.getInst().getConfig();

    //GUI

    public Integer onez;
    public Integer twoz;
    public Integer onex;
    public Integer twox;

    public Integer w;
    public Integer s;
    public Integer i;
    public Integer g;
    public Integer d;
    public Integer n;

    public Integer hp;



    //=========================================================================

    //=========================================================================
    //Reload
    public void reload(){
        this.MainInstance.reloadConfig();
        this.cfg = this.MainInstance.getConfig();
        load();
    }
    //=========================================================================

    public Config(Main main) {
        inst = this;
        this.MainInstance = main;
    }




    //=========================================================================
    //Load
    public void load(){
        Bukkit.getLogger().info("Ladowanie config.yml...");
        try {
            Main.SQL.connect(cfg.getString("mysql.host"), cfg.getString("mysql.port"), cfg.getString("mysql.data"), cfg.getString("mysql.user"), cfg.getString("mysql.pass"), cfg.getBoolean("mysql.useSSL"));
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Nie udalo sie polaczyc z baza danych!");
            Main.getInst().getPluginLoader().disablePlugin(Main.getInst());
            e.printStackTrace();
        }
        if (Main.SQL.isConnected()) {
            System.out.println("Udalo sie polaczyc z baza danych!");
            Main.data.createTable();
        }

        onex = cfg.getInt("border.one.x");
        onez = cfg.getInt("border.one.z");
        twox = cfg.getInt("border.two.x");
        twoz = cfg.getInt("border.two.z");

        w = cfg.getInt("tools.wood");
        s = cfg.getInt("tools.stone");
        i = cfg.getInt("tools.iron");
        g = cfg.getInt("tools.gold");
        d = cfg.getInt("tools.diax");
        n = cfg.getInt("tools.nethe");

        hp=cfg.getInt("others.hp");

        Bukkit.getLogger().info("Wladowano config.yml");
    }



    //=========================================================================

    //=========================================================================
    //Instance
    public static Config getInst(){
        return inst;
    }
    //=========================================================================

}
