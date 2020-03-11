package fun.fifu.nekokecore.zxbskyworld.command;

import fun.fifu.nekokecore.zxbskyworld.Main;
import fun.fifu.nekokecore.zxbskyworld.utils.Helper;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;

public class SeclusionDispose implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if ("seclusion".equalsIgnoreCase(command.getName())) {
            if (!(commandSender instanceof Player)) {
                commandSender.sendMessage("你必须是一名玩家!");
                return true;
            }
            Player player = (Player) commandSender;
            String uuid = player.getUniqueId().toString();
            if (strings.length >= 2) {
                if (swi(uuid, false)) {
                    player.sendMessage(player.getName() + "你关闭了Seclusion，现在其他玩家可以使用/goto来传送到你的岛上了");
                    player.sendMessage("输入/seclusion来打开");
                } else {
                    player.sendMessage("你似乎不是这个岛的主人，不可以这么做哦");
                }
            } else {
                if (swi(uuid, true)) {
                    player.sendMessage(player.getName() + "你打开了Seclusion，现在其他玩家不能使用/goto来传送到你的岛上了");
                    player.sendMessage("输入/seclusion false来取消开启");
                } else {
                    player.sendMessage("你似乎不是这个岛的主人，不可以这么做哦");
                }
            }
            return true;
        }
        return false;
    }

    public boolean swi(String uuid, boolean state) {
        for (String SkyLoc : Main.dateAdmin.getAllOwnerSkyLoc(uuid)) {
            if (SkyLoc.equalsIgnoreCase(Helper.toSkyLoc(Bukkit.getPlayer(uuid).getLocation()))) {
                JSONObject jsonObject = Main.dateAdmin.getOthers(SkyLoc);
                jsonObject.put("Seclusion", state);
                Main.dateAdmin.saveOthers(jsonObject, SkyLoc);
                return true;
            }
        }
        return false;
    }

    public boolean getswi(JSONObject jsonObject) {
        return jsonObject.get("Seclusion").toString().equalsIgnoreCase("true");
    }
}
