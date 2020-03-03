package fun.fifu.nekokecore.zxbskyworld.command;

import fun.fifu.nekokecore.zxbskyworld.IsLand;
import fun.fifu.nekokecore.zxbskyworld.Main;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InfoDispose implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if ("info".equalsIgnoreCase(command.getName())) {
            Player player = ((Player) commandSender).getPlayer();
            if (player == null) {
                commandSender.sendMessage("你必须是一名玩家!");
                return true;
            }
            String UUID = player.getUniqueId().toString();
            //初始化JSON配置文件
            if (Main.jsonObject == null) {
                throw new RuntimeException("配置文件异常，请仔细检查！！！");
            }

            IsLand isLand = new IsLand(UUID);
            Location location = player.getLocation();
            int xx = location.getBlockX();
            int zz = location.getBlockZ();
            player.sendMessage("||=================================");
            player.sendMessage("||玩家" + player.getName() + "的岛屿有：");
            player.sendMessage(isLand.getIslandList().toString());
            player.sendMessage("||=================================");
            player.sendMessage("||你所在的岛屿是：(" + xx + "," + zz + ")");
            player.sendMessage("||---------------------------------");
            player.sendMessage("||这个岛屿的范围是：");
            player.sendMessage("||在X轴上：从");
        }
        return false;
    }
}
