package fun.fifu.nekokecore.zxbskyworld.command;

import fun.fifu.nekokecore.zxbskyworld.IsLand;
import fun.fifu.nekokecore.zxbskyworld.Main;
import fun.fifu.nekokecore.zxbskyworld.permission.DynamicEternalMap;
import fun.fifu.nekokecore.zxbskyworld.utils.Helper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;

import java.io.IOException;

public class GiveupDispose implements CommandExecutor {
    private static String nanoTime = "!IKnowWhatIDo!" + System.nanoTime();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if ("giveup".equalsIgnoreCase(command.getName())) {
            if (!(commandSender instanceof Player player)) {
                commandSender.sendMessage("你必须是一名玩家!");
                return true;
            }
            if (!player.getWorld().getName().equals(DynamicEternalMap.base_sky_world))
                return true;
            if (strings != null && strings.length > 0) {
                String input = strings[0];
                if (nanoTime.equalsIgnoreCase(input)) {
                    nanoTime = "!IKnowWhatIDo!" + System.nanoTime();
                    String uuid = player.getUniqueId().toString();
                    String DefSkyLoc = IsLand.dateAdmin.getDefaultSkyLoc(uuid);
                    try {
                        IsLand.dateAdmin.saveOwnerslist(new JSONArray(), DefSkyLoc);
                        IsLand.dateAdmin.saveDefaultSkyLoc(uuid,"REMOVE");
                    } catch (IOException e) {
                        long time = System.nanoTime();
                        player.sendMessage("操作过程貌似出错了，如果有问题，请拿着下面这串数字找服务器管理员" + time);
                        Main.plugin.getLogger().info("错误标记：" + time);
                        e.printStackTrace();
                        return true;
                    }
                    player.sendMessage("操作成功，原来的岛屿已被放弃，但保留所有数据");
                    Helper.goSpawn(player);
                    player.sendMessage("输入/s可分配一个新的岛");
                    return true;
                }
            }
            player.sendMessage("[危]      请三思！     [危]");
            player.sendMessage("警告！你现在的操作十分危险！！");
            player.sendMessage("警告！你在试图清空岛屿！！！！");
            player.sendMessage("警告！你现在的操作十分危险！！");
            player.sendMessage("[危]      请三思！     [危]");
            nanoTime = "!IKnowWhatIDo!" + System.nanoTime();
            player.sendMessage("若要继续，请输入：/giveup " + nanoTime);
            return true;
        }
        return false;
    }
}
