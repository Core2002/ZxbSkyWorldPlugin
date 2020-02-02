package fun.fifu.nekokecore.zxbskyworld;

import fun.fifu.nekokecore.permission.BlockDispose;
import fun.fifu.nekokecore.permission.EntityDispose;
import fun.fifu.nekokecore.zxbskyworld.Listener.PlayerDispose;
import fun.fifu.nekokecore.zxbskyworld.command.MainDispose;
import fun.fifu.nekokecore.zxbskyworld.command.TeleportDispose;
import fun.fifu.nekokecore.zxbskyworld.utils.IOTools;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;

public class Main extends JavaPlugin {
    /**
     * 配置文件
     */
    public static final String CONFIGPATH = "./plugins/ZxbSkyWorld/config.json";
    public static final String UTILCONFIGPATH = "./plugins/ZxbSkyWorld/util_config.json";
    public static final String COMMAND = "s";
    public static JSONObject jsonObject = null;
    public static JSONObject util_jsonObject = null;
    public static Plugin plugin = null;


    @Override
    public void onDisable() {

    }

    @Override
    public void onEnable() {
        //配置文件不存在就创建
        jsonObject = initJson(CONFIGPATH, "{}");
        String util_initStr = "{\"spawn_world\":\"world\",\"spawn_xx\":\"53\",\"spawn_yy\":\"5\",\"spawn_zz\":\"40\"}";
        util_jsonObject = initJson(UTILCONFIGPATH, util_initStr);
        getLogger().info("开始注册命令。");
        //注册命令
        Bukkit.getPluginCommand(COMMAND).setExecutor(new MainDispose());
        Bukkit.getPluginCommand("goto").setExecutor(new TeleportDispose());
        getLogger().info("完毕。");
        getLogger().info("开始注册监听器。");
        //注册监听器
        getServer().getPluginManager().registerEvents(new PlayerDispose(), this);
        getServer().getPluginManager().registerEvents(new EntityDispose(), this);
        getServer().getPluginManager().registerEvents(new BlockDispose(), this);
        getLogger().info("完毕。");
    }

    @Override
    public void onLoad() {
        plugin = this;
    }

    /**
     * 初始化JSON文件，确保能用
     *
     * @param configpath
     */
    public static JSONObject initJson(String configpath, String initStr) {
        int temp = 0;
        System.out.println("正在加载json文件：" + configpath);
        while (!new File(configpath).exists()) {
            //尝试试探json文件
            try {
                System.out.println("文件不存在，尝试生成。" + configpath);
                new File(new File(configpath).getParent()).mkdirs();
                new File(configpath).createNewFile();
                IOTools.writeTextFile(initStr, "UTF-8", configpath);
            } catch (IOException e) {
                System.out.println("尝试生成。" + temp);
                e.printStackTrace();
            }
            temp++;
        }
        try {
            return IOTools.getJSONObject(configpath);
        } catch (ParseException e) {
            System.out.println("Json文件加载失败，请检查文件格式，然后在尝试。" + configpath);
            e.printStackTrace();
        }
        return null;
    }
}
