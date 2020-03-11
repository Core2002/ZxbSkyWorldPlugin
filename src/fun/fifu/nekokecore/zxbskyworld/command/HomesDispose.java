package fun.fifu.nekokecore.zxbskyworld.command;

import fun.fifu.nekokecore.zxbskyworld.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HomesDispose implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if ("homes".equalsIgnoreCase(command.getName())) {
            if (!(commandSender instanceof Player)) {
                commandSender.sendMessage("你必须是一名玩家!");
                return true;
            }
            Player player = (Player) commandSender;
            String uuid = player.getUniqueId().toString();
            player.sendMessage("||=================================|");
            player.sendMessage("||你拥有的岛有：" + Main.dateAdmin.getAllOwnerSkyLoc(uuid));
            player.sendMessage("||---------------------------------|");
            player.sendMessage("||你加入的岛有：" + Main.dateAdmin.getAllMembersSkyLoc(uuid));
            player.sendMessage("||=================================|");
            return true;
        }
        return false;
    }
}
