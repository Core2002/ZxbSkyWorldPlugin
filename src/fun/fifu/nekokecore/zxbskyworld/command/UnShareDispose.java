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

import java.io.IOException;

import static fun.fifu.nekokecore.zxbskyworld.command.ShareDispose.isOwner;

public class UnShareDispose implements CommandExecutor {
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
            if (unShareSkyWorld(shareUuid, SkyLoc)) {
                player.sendMessage("成功把成员" + player.getName() + "从这个岛" + SkyLoc + "移除了");
            } else {
                player.sendMessage("这个玩家" + player.getName() + "不是这个岛" + SkyLoc + "上的成员,无需删除");
            }
            player.sendMessage("他的UUID是：" + shareUuid);
            return true;
        }
        return false;
    }

    /**
     * 将目标uuid从岛屿删除，true：已删除，false：不在岛上，无需删除
     *
     * @param unShareUuid
     * @param skyLoc
     * @return
     */
    public boolean unShareSkyWorld(String unShareUuid, String skyLoc) {
        try {
            if (ShareDispose.isRepetition(unShareUuid, skyLoc)) {
                JSONArray jsonArray = IsLand.dateAdmin.getMembersList(skyLoc);
                jsonArray.remove(unShareUuid);
                IsLand.dateAdmin.saveMemberslist(jsonArray, skyLoc);
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
