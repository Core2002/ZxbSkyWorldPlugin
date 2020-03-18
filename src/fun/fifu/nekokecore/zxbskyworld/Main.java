package fun.fifu.nekokecore.zxbskyworld;

import fun.fifu.nekokecore.zxbskyworld.command.*;
import fun.fifu.nekokecore.zxbskyworld.listener.ChairStairsListener;
import fun.fifu.nekokecore.zxbskyworld.permission.BlockDispose;
import fun.fifu.nekokecore.zxbskyworld.permission.EntityDispose;
import fun.fifu.nekokecore.zxbskyworld.listener.PlayerListener;
import fun.fifu.nekokecore.zxbskyworld.utils.DateAdmin;
import fun.fifu.nekokecore.zxbskyworld.utils.UUID;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class Main extends JavaPlugin {
    /**
     * 配置文件
     */

    public static Plugin plugin;
    private static PluginManager pluginManager;
    static Logger logger;

    @Override
    public void onDisable() {

    }

    @Override
    public void onEnable() {
        getLogger().info("开始注册命令。");
        //注册命令
        Bukkit.getPluginCommand("s").setExecutor(new MainDispose());
        Bukkit.getPluginCommand("goto").setExecutor(new TeleportDispose());
        Bukkit.getPluginCommand("help").setExecutor(new HelpDispose());
        Bukkit.getPluginCommand("exple").setExecutor(new ExpleDispose());
        Bukkit.getPluginCommand("infos").setExecutor(new InfoDispose());
        Bukkit.getPluginCommand("uuid").setExecutor(new UUID());
        Bukkit.getPluginCommand("biome").setExecutor(new BiomeDispose());
        Bukkit.getPluginCommand("seclusion").setExecutor(new SeclusionDispose());
        Bukkit.getPluginCommand("homes").setExecutor(new HomesDispose());
        Bukkit.getPluginCommand("share").setExecutor(new ShareDispose());
        Bukkit.getPluginCommand("unshare").setExecutor(new UnShareDispose());
        Bukkit.getPluginCommand("anti-explosion").setExecutor(new AntiExplosionDispose());
        Bukkit.getPluginCommand("sethome").setExecutor(new SetHomeDispose());
        getLogger().info("完毕。");
        getLogger().info("开始注册监听器。");
        //注册监听器
        pluginManager.registerEvents(new PlayerListener(), this);
        pluginManager.registerEvents(new ChairStairsListener(), this);
        pluginManager.registerEvents(new EntityDispose(), this);
        pluginManager.registerEvents(new BlockDispose(), this);
        getLogger().info("完毕。");
    }

    @Override
    public void onLoad() {
        plugin = this;
        pluginManager = getServer().getPluginManager();
        logger = getLogger();
        IsLand.dateAdmin = new DateAdmin();
    }


}
