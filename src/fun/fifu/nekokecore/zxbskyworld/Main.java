package fun.fifu.nekokecore.zxbskyworld;

import fun.fifu.nekokecore.zxbskyworld.command.CommandLitener;
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
    public static final String COMMAND = "s";
    public static JSONObject jsonObject = null;
    public static Plugin plugin = null;


    @Override
    public void onDisable() {

    }

    @Override
    public void onEnable() {
        //配置文件不存在就创建
        initJson(CONFIGPATH);
        getLogger().info("开始注册命令。");
        //注册命令
        Bukkit.getPluginCommand(COMMAND).setExecutor(new CommandLitener());
        getLogger().info("完毕。");
    }

    @Override
    public void onLoad() {
        plugin=this;
    }

    /**
     * 初始化JSON文件，确保能用
     *
     * @param configpath
     */
    public static void initJson(String configpath) {
        int temp = 0;
        System.out.println("正在加载json文件：" + configpath);
        while (!new File(configpath).exists()) {
            try {
                System.out.println("文件不存在，尝试生成。" + configpath);
                new File(new File(configpath).getParent()).mkdirs();
                new File(configpath).createNewFile();
                IOTools.writeTextFile("{}", "UTF-8", configpath);
                jsonObject = IOTools.getJSONObject(CONFIGPATH);
            } catch (IOException e) {
                System.out.println("尝试生成。" + temp);
                e.printStackTrace();
            } catch (ParseException e) {
                System.out.println("Json文件加载失败，请检查文件格式，然后在尝试。" + configpath);
                e.printStackTrace();
            }
            temp++;
        }
        try {
            jsonObject = IOTools.getJSONObject(CONFIGPATH);
        } catch (ParseException e) {
            System.out.println("Json文件加载失败，请检查文件格式，然后在尝试。" + configpath);
            e.printStackTrace();
        }
    }
}
