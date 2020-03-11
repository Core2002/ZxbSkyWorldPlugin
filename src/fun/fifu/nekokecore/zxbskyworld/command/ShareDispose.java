package fun.fifu.nekokecore.zxbskyworld.command;

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
            if (!isRepetition(shareUuid, SkyLoc)) {
                JSONArray jsonArray = Main.dateAdmin.getMembersList(SkyLoc);
                jsonArray.add(shareUuid);
                Main.dateAdmin.saveMemberslist(jsonArray, SkyLoc);
                player.sendMessage("成功把玩家" + player.getName() + "作为成员添加在" + SkyLoc + "上了");
            } else {
                player.sendMessage("这个玩家" + player.getName() + "已经在这个岛" + SkyLoc + "上了");
            }
            player.sendMessage("他的UUID是：" + shareUuid);
            return true;
        }
        return false;
    }

    public boolean isRepetition(String uuid, String SkyLoc) {
        for (Object obj : Main.dateAdmin.getMembersList(SkyLoc)) {
            String uid = (String) obj;
            if (uid.equalsIgnoreCase(uuid)) {
                return true;
            }
        }
        return false;
    }

    public boolean isOwner(String uuid, String SkyLoc) {
        for (Object obj : Main.dateAdmin.getOwnersList(SkyLoc)) {
            String uid = (String) obj;
            if (uuid.equalsIgnoreCase(uid)) {
                return true;
            }
        }
        return false;
    }
}
