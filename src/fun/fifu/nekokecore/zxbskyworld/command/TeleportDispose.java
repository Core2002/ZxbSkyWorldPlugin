package fun.fifu.nekokecore.zxbskyworld.command;

import fun.fifu.nekokecore.zxbskyworld.Main;
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
            try{
                SkyLoc = strings[0];
                SkyLoc = Helper.simplify(SkyLoc);
            }catch (Exception e){
                return false;
            }
            Player player = (Player) commandSender;
            String UUID = player.getUniqueId().toString();
            try {
                if (IsLand.getSkyX(SkyLoc) == 0 && IsLand.getSkyY(SkyLoc) == 0 && !player.isOp()) {
                    player.sendMessage("权限不足！");
                    return true;
                }
            } catch (Exception e) {
                Main.plugin.getLogger().warning(e.toString());
                return false;
            }
            HashMap<String, HashMap<String, Integer>> tempmap = null;
            if (ExpleDispose.map != null) {
                tempmap = ExpleDispose.map;
            } else {
                Helper.tpSkyLoc(player, SkyLoc);
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
