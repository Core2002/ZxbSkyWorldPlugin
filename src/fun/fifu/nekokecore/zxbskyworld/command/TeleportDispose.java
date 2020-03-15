package fun.fifu.nekokecore.zxbskyworld.command;

import fun.fifu.nekokecore.zxbskyworld.IsLand;
import fun.fifu.nekokecore.zxbskyworld.utils.Helper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class TeleportDispose implements CommandExecutor {
    /**
     * 命令goto
     */
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if ("goto".equalsIgnoreCase(command.getName())) {
            if (!(commandSender instanceof Player)) {
                commandSender.sendMessage("你必须是一名玩家!");
                return true;
            }
            if (strings == null) {
                return false;
            }
            String SkyLoc;
            try {
                SkyLoc = strings[0];
                SkyLoc = Helper.simplify(SkyLoc);
            } catch (Exception e) {
                return false;
            }
            Player player = (Player) commandSender;
            if (Helper.inSpawn(SkyLoc)){
                if (player.isOp()){
                    Helper.tpSkyLoc(player, SkyLoc);
                    return true;
                }else {
                    player.sendMessage("你没权限");
                    return true;
                }

            }
            if (!IsLand.dateAdmin.isExist(SkyLoc)) {
                player.sendMessage("您输入的岛屿" + SkyLoc + "不存在，请检查输入！");
                return true;
            }
            String UUID = player.getUniqueId().toString();
            HashMap<String, HashMap<String, Integer>> tempmap = null;
            if (ExpleDispose.map != null) {
                tempmap = ExpleDispose.map;
            } else {
                Helper.tpSkyLoc(player, SkyLoc);
                return true;
            }
            if (SeclusionDispose.getSwi(SkyLoc)) {
                player.sendMessage("这个岛的主人设置了禁止使用/goto传送");
                return true;
            }
            if (tempmap.get(UUID) != null) {
                if (!tempmap.get(UUID).isEmpty()) {
                    if (!tempmap.get(UUID).keySet().contains(Helper.simplify(SkyLoc))) {
                        Helper.tpSkyLoc(player, SkyLoc);
                    } else {
                        player.sendMessage("你被踢出岛" + SkyLoc + "," + tempmap.get(UUID).get(SkyLoc) + "秒之后解除！");
                    }
                } else {
                    Helper.tpSkyLoc(player, SkyLoc);
                }
                return true;
            }
            Helper.tpSkyLoc(player, SkyLoc);
            return true;
        }
        return false;
    }

}
