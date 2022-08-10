package me.macialowskyyy.stonerust.main;

import me.macialowskyyy.stonerust.data.Config;
import me.macialowskyyy.stonerust.events.onBreak;
import me.macialowskyyy.stonerust.events.onChunkLoad;
import me.macialowskyyy.stonerust.events.onClick;
import me.macialowskyyy.stonerust.sql.MySQL;
import me.macialowskyyy.stonerust.sql.SQLGetter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.logging.Level;

public final class Main extends JavaPlugin {

    private FileConfiguration dataConfig = null;
    private File configFile = null;
    private static Main inst;
    public static MySQL SQL;
    public static SQLGetter data;
    private static File mainDir;
    private static File cfgFile;

    @Override
    public void onEnable() {
        SQL = new MySQL();
        data = new SQLGetter();
        inst = this;


        saveDefaultData();
        mainDir = this.getDataFolder();
        if(!mainDir.exists()) mainDir.mkdir();
        cfgFile = new File(mainDir, "config.yml");
        if(!cfgFile.exists()) this.saveDefaultConfig();

        Config config = new Config(this);
        config.load();

        Bukkit.getPluginManager().registerEvents(new onChunkLoad(),this);
        Bukkit.getPluginManager().registerEvents(new onBreak(),this);
        Bukkit.getPluginManager().registerEvents(new onClick(),this);




    }

    @Override
    public void onDisable() {
        SQL.disconnect();
    }


    public static Main getInst() {
        return inst;
    }



    public void reloadData() {
        if (this.configFile == null)
            this.configFile = new File(this.getDataFolder(), "data.yml");
        this.dataConfig = YamlConfiguration.loadConfiguration(this.configFile);
        InputStream defaultStream = this.getResource("data.yml");
        if (defaultStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            this.dataConfig.setDefaults(defaultConfig);
        }
    }
    public FileConfiguration getData() {
        if (this.dataConfig == null)
            reloadData();
        return  this.dataConfig;
    }
    public void saveData() {
        if (this.dataConfig == null || this.configFile == null)
            return;
        try {
            this.getData().save(this.configFile);
        } catch (IOException e) {
            this.getLogger().log(Level.SEVERE, "Could not save config to " + this.configFile, e);
        }
    }
    public void saveDefaultData() {
        if (this.configFile == null)
            this.configFile = new File(this.getDataFolder(), "data.yml");
        if (!this.configFile.exists()) {
            this.saveResource("data.yml", false);
        }
    }

}
