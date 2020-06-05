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

import java.io.IOException;

public class SeclusionDispose implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if ("seclusion".equalsIgnoreCase(command.getName())) {
            if (!(commandSender instanceof Player)) {
                commandSender.sendMessage("你必须是一名玩家!");
                return true;
            }
            Player player=(Player)commandSender;
            if (!player.getWorld().getName().equals(IsLand.dynamicEternalMap.base_sky_world))
                return true;
            String uuid = player.getUniqueId().toString();
            String SkyLoc = Helper.toSkyLoc(player.getLocation());
            if (strings.length >= 1) {
                if (ShareDispose.isOwner(uuid, SkyLoc)) {
                    try {
                        swi(SkyLoc, false);
                        player.sendMessage(player.getName() + "你关闭了Seclusion，现在其他玩家可以使用/goto来传送到你的岛上了");
                        player.sendMessage("输入/seclusion来打开");
                    } catch (IOException e) {
                        player.sendMessage("操作失败，请重试，若有疑问，请联系服务器管理员");
                        e.printStackTrace();
                    }
                } else {
                    player.sendMessage("你似乎不是这个岛的主人，不可以这么做哦");
                }
            } else {
                if (ShareDispose.isOwner(uuid, Helper.toSkyLoc(player.getLocation()))) {
                    try {
                        swi(SkyLoc, true);
                        player.sendMessage(player.getName() + "你打开了Seclusion，现在其他玩家不能使用/goto来传送到你的岛上了");
                        player.sendMessage("输入/seclusion false来取消开启");
                    } catch (IOException e) {
                        player.sendMessage("操作失败，请重试，若有疑问，请联系服务器管理员");
                        e.printStackTrace();
                    }
                } else {
                    player.sendMessage("你似乎不是这个岛的主人，不可以这么做哦");
                }
            }
            return true;
        }
        return false;
    }

    public void swi(String SkyLoc, boolean state) throws IOException {
        JSONObject jsonObject = IsLand.dateAdmin.getOthers(SkyLoc);
        if (jsonObject == null) {
            try {
                jsonObject = (JSONObject) new JSONParser().parse("{}");
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        jsonObject.put("Seclusion", state);
        IsLand.dateAdmin.saveOthers(jsonObject, SkyLoc);
    }

    /**
     * 返回SkyLoc的Others的Seclusion的开启状态，true是开启(不能传送)，false是关闭（可以传送）
     *
     * @param SkyLoc
     * @return
     */
    public static boolean getSwi(String SkyLoc) {
        JSONObject jsonObject = null;
        try {
            jsonObject = IsLand.dateAdmin.getOthers(SkyLoc);
        } catch (IOException e) {
            return false;
        }
        if (jsonObject == null || jsonObject.size() == 0) {
            return false;
        }
        return (boolean) jsonObject.get("Seclusion");
    }

}
