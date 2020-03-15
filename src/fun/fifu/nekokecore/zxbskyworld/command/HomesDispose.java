package fun.fifu.nekokecore.zxbskyworld.command;

import fun.fifu.nekokecore.zxbskyworld.IsLand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.json.simple.parser.ParseException;

import java.io.IOException;

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
            try {
                player.sendMessage("||你拥有的岛有：" + IsLand.dateAdmin.getAllOwnerSkyLoc(uuid));
            } catch (IOException e) {
                e.printStackTrace();
            }
            player.sendMessage("||---------------------------------|");
            try {
                player.sendMessage("||你加入的岛有：" + IsLand.dateAdmin.getAllMembersSkyLoc(uuid));
            } catch (IOException e) {
                e.printStackTrace();
            }
            player.sendMessage("||=================================|");
            return true;
        }
        return false;
    }
}
