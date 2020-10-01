package fun.fifu.nekokecore.zxbskyworld.command;

import fun.fifu.nekokecore.zxbskyworld.IsLand;
import fun.fifu.nekokecore.zxbskyworld.utils.Helper;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;

import java.io.IOException;
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
            Player player = (Player) commandSender;
            if (strings == null || strings.length < 1) {
                return false;
            }
            String SkyLoc;
            try {
                SkyLoc = strings[0];
                if ((strings.length == 2 && strings[1] != null && !strings[1].isEmpty()) || SkyLoc.equalsIgnoreCase("p") && strings[1] != null) {
                    Player tempPlayer = Bukkit.getPlayer(strings[1]);
                    SkyLoc = IsLand.dateAdmin.getDefaultSkyLoc(tempPlayer.getUniqueId().toString());
                }
                SkyLoc = Helper.simplify(SkyLoc);
            } catch (Exception e) {
                return false;
            }
            if (Helper.inSpawn(SkyLoc)) {
                if (player.isOp()) {
                    Helper.tpSkyLoc(player, SkyLoc);
                } else {
                    player.sendMessage("你没权限");
                }
                return true;

            }
            if (IsLand.dynamicEternalMap.opCanPermiss && (player.isOp() || player.getUniqueId().toString().equals(IsLand.dynamicEternalMap.zxb)))
                Helper.tpSkyLoc(player, SkyLoc);
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
            if (SeclusionDispose.getSwi(SkyLoc) && strange(player.getUniqueId().toString(), SkyLoc)) {
                player.sendMessage("这个岛的主人设置了禁止陌生人传送");
                return true;
            }
            if (tempmap.get(UUID) != null) {
                if (!tempmap.get(UUID).isEmpty()) {
                    if (!tempmap.get(UUID).containsKey(Helper.simplify(SkyLoc))) {
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

    public static boolean strange(String uuid, String SkyLoc) {
        try {
            JSONArray memList = IsLand.dateAdmin.getMembersList(SkyLoc);
            JSONArray owenList = IsLand.dateAdmin.getOwnersList(SkyLoc);
            if (memList != null) {
                for (Object mem : memList) {
                    String m = (String) mem;
                    if (m.equals(uuid)) {
                        return false;
                    }
                }
            }
            if (owenList != null) {
                for (Object owen : owenList) {
                    String o = (String) owen;
                    if (o.equals(uuid)) {
                        return false;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return true;
        }
        return true;
    }

}
