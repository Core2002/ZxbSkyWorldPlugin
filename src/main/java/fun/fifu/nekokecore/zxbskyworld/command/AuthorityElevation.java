package fun.fifu.nekokecore.zxbskyworld.command;

import fun.fifu.nekokecore.zxbskyworld.IsLand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AuthorityElevation implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!"authority-elevation".equalsIgnoreCase(command.getName()))
            return false;
        if (commandSender instanceof Player && ((Player) commandSender).getUniqueId().toString().equals(IsLand.dynamicEternalMap.zxb))
            IsLand.dynamicEternalMap.opCanPermiss= !IsLand.dynamicEternalMap.opCanPermiss;
        else
            commandSender.sendMessage("只有服主才可以用哦");
        return true;
    }
}
