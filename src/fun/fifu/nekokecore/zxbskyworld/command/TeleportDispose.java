package fun.fifu.nekokecore.zxbskyworld.command;

import fun.fifu.nekokecore.zxbskyworld.SkyWorld.IsLand;
import fun.fifu.nekokecore.zxbskyworld.utils.Helper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
            Player player = (Player) commandSender;
            if (strings == null) {
                return false;
            }
            try {
                if (IsLand.getSkyX(strings[0]) == 0 && IsLand.getSkyY(strings[0]) == 0 && !player.isOp()) {
                    player.sendMessage("权限不足！");
                    return true;
                }

                Helper.tpSkyLoc(player, strings[0]);
            } catch (Exception e) {
                System.out.println(e.toString());
                return false;
            }
            return true;
        }
        return false;
    }
}
