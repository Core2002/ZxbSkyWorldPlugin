package fun.fifu.nekokecore.zxbskyworld.command;

import fun.fifu.nekokecore.zxbskyworld.IsLand;
import fun.fifu.nekokecore.zxbskyworld.Main;
import fun.fifu.nekokecore.zxbskyworld.utils.Helper;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;

public class ShareDispose implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if ("share".equalsIgnoreCase(command.getName())) {
            if (!(commandSender instanceof Player)) {
                commandSender.sendMessage("你必须是一名玩家!");
                return true;
            }
            if (strings.length != 1) {
                Main.plugin.getLogger().info(strings + ":" + strings.length + "，!=1");
                return false;
            }
            String sp = strings[0];
            Player sharePlayer = Bukkit.getPlayer(sp);
            Player player = (Player) commandSender;
            String uuid = player.getUniqueId().toString();
            String shareUuid = sharePlayer.getUniqueId().toString();
            String SkyLoc = Helper.toSkyLoc(player.getLocation());
            if (!isOwner(uuid, SkyLoc)) {
                player.sendMessage("你似乎不是这个岛的主人，不可以这么做哦");
                return true;
            }
            if (shareSkyWorld(shareUuid, SkyLoc)) {
                player.sendMessage("成功把玩家" + player.getName() + "作为成员添加在" + SkyLoc + "上了");
            } else {
                player.sendMessage("这个玩家" + player.getName() + "已经在这个岛" + SkyLoc + "上了");
            }
            player.sendMessage("他的UUID是：" + shareUuid);
            return true;
        }
        return false;
    }

    /**
     * 分享目标岛屿作为成员给，返回true：没有重复，false：有重复，无需再添加
     *
     * @param uuid
     * @param SkyLoc
     * @return
     */
    public boolean shareSkyWorld(String uuid, String SkyLoc) {
        if (!isRepetition(uuid, SkyLoc)) {
            JSONArray jsonArray = IsLand.dateAdmin.getMembersList(SkyLoc);
            jsonArray.add(uuid);
            IsLand.dateAdmin.saveMemberslist(jsonArray, SkyLoc);
            return true;
        }
        return false;
    }

    /**
     * 判断uuid是否与skyLoc里的成员列表重复。true：重复，false：不重复
     *
     * @param uuid
     * @param SkyLoc
     * @return
     */
    public boolean isRepetition(String uuid, String SkyLoc) {
        JSONArray jsonArray = IsLand.dateAdmin.getMembersList(SkyLoc);
        if (jsonArray == null) {
            return false;
        }
        for (Object obj : jsonArray) {
            String uid = (String) obj;
            if (uid.equalsIgnoreCase(uuid)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断uuid是否是skyLoc的所有者：true：是，false：不是
     *
     * @param uuid
     * @param SkyLoc
     * @return
     */
    public static boolean isOwner(String uuid, String SkyLoc) {
        for (Object obj : IsLand.dateAdmin.getOwnersList(SkyLoc)) {
            String uid = (String) obj;
            if (uuid.equalsIgnoreCase(uid)) {
                return true;
            }
        }
        return false;
    }
}
