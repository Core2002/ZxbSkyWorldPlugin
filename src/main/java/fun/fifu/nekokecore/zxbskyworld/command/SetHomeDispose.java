package fun.fifu.nekokecore.zxbskyworld.command;

import fun.fifu.nekokecore.zxbskyworld.IsLand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class SetHomeDispose implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if ("sethome".equalsIgnoreCase(command.getName())) {
            if (!(commandSender instanceof Player)) {
                commandSender.sendMessage("你必须是一名玩家!");
                return true;
            }
        }
        Player player = (Player) commandSender;
        String uuid = player.getUniqueId().toString();
        try {
            IsLand.dateAdmin.savePlayerHomeLocation(uuid,player.getLocation());
        } catch (IOException e) {
            e.printStackTrace();
            player.sendMessage("操作失败，请重试，若有疑问，请联系服务器管理员");
            return true;
        }
        player.sendMessage("成功设置家,使用/s h 来回到你的家");
        return true;
    }
}
