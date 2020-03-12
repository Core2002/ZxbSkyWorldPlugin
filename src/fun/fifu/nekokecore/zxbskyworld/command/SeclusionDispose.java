package fun.fifu.nekokecore.zxbskyworld.command;

import fun.fifu.nekokecore.zxbskyworld.IsLand;
import fun.fifu.nekokecore.zxbskyworld.utils.Helper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
            String SkyLoc = Helper.toSkyLoc(player.getLocation());
            if (strings.length >= 1) {
                if (ShareDispose.isOwner(uuid, SkyLoc)) {
                    if (swi(SkyLoc, false)) {
                        player.sendMessage(player.getName() + "你关闭了Seclusion，现在其他玩家可以使用/goto来传送到你的岛上了");
                        player.sendMessage("输入/seclusion来打开");
                    } else {
                        player.sendMessage("操作失败，请重试，若有疑问，请联系服务器管理员");
                    }
                } else {
                    player.sendMessage("你似乎不是这个岛的主人，不可以这么做哦");
                }
            } else {
                if (ShareDispose.isOwner(uuid, Helper.toSkyLoc(player.getLocation()))) {
                    if (swi(SkyLoc, true)) {
                        player.sendMessage(player.getName() + "你打开了Seclusion，现在其他玩家不能使用/goto来传送到你的岛上了");
                        player.sendMessage("输入/seclusion false来取消开启");
                    } else {
                        player.sendMessage("操作失败，请重试，若有疑问，请联系服务器管理员");
                    }

                } else {
                    player.sendMessage("你似乎不是这个岛的主人，不可以这么做哦");
                }
            }
            return true;
        }
        return false;
    }

    public boolean swi(String SkyLoc, boolean state) {
        JSONObject jsonObject = IsLand.dateAdmin.getOthers(SkyLoc);
        if (jsonObject == null) {
            try {
                jsonObject = (JSONObject) new JSONParser().parse("{}");
            } catch (ParseException e) {
                e.printStackTrace();
                return false;
            }
        }
        jsonObject.put("Seclusion", state);
        IsLand.dateAdmin.saveOthers(jsonObject, SkyLoc);
        return true;
    }

    /**
     * 返回SkyLoc的Others的Seclusion的开启状态，true是开启，false是关闭
     *
     * @param SkyLoc
     * @return
     */
    public static boolean getSwi(String SkyLoc) {
        JSONObject jsonObject = IsLand.dateAdmin.getOthers(SkyLoc);
        if (jsonObject == null) {
            return false;
        }
        String temp = (String) jsonObject.get("Seclusion");
        if (temp == null) {
            return false;
        }
        return temp.equalsIgnoreCase("true");
    }

}
