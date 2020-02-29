package fun.fifu.nekokecore.zxbskyworld.command;

import fun.fifu.nekokecore.zxbskyworld.Main;
import fun.fifu.nekokecore.zxbskyworld.SkyWorld.IsLand;
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
        // 判断输入的指令是否是 /s ,不区分大小写
        if ("goto".equalsIgnoreCase(command.getName())) {
            // 判断输入者的类型 为了防止出现 控制台或命令方块 输入的情况
            if (!(commandSender instanceof Player)) {
                commandSender.sendMessage("你必须是一名玩家!");
                // 这里返回true只是因为该输入者不是玩家,并不是输入错指令,所以我们直接返回true即可
                return true;
            }
            if (strings == null) {
                return false;
            }
            String SkyLoc = strings[0];
            SkyLoc = Helper.simplify(SkyLoc);
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
                //System.out.println("debug001_map为空");
                return true;
            }
            if (tempmap.get(UUID) != null) {
                if (!tempmap.get(UUID).isEmpty()) {
                    if (tempmap.get(UUID).keySet().contains(Helper.simplify(SkyLoc))) {
                        player.sendMessage("你被踢出岛" + SkyLoc + "," + tempmap.get(UUID).get(SkyLoc) + "秒之后解除！");
                        return true;
                    } else {
                        Helper.tpSkyLoc(player, SkyLoc);
                        //System.out.println("debug002_查了，无名"+SkyLoc);
                        //System.out.println(tempmap);
                        return true;
                    }
                } else {
                    Helper.tpSkyLoc(player, SkyLoc);
                    //System.out.println("debug003_map.get(UUID)为空");
                    return true;
                }
            }
            Helper.tpSkyLoc(player, SkyLoc);
            //System.out.println("debug004_正常情况");
            return true;
        }
        return false;
    }
}
