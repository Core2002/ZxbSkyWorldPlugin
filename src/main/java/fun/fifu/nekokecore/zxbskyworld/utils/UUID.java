package fun.fifu.nekokecore.zxbskyworld.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class UUID implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (cmd.getName().equalsIgnoreCase("uuid")) {

            //玩家输入   后无参数
            if (args.length == 0) {
                sender.sendMessage("§7§l[§e!§7§l] §6======§7UUID§6======");
                sender.sendMessage("§7§l[§e!§7§l] §7/UUID CX §7查询你的UUID");
                sender.sendMessage("§7§l[§e!§7§l] §7/UUID CXID [玩家名字] §7查询别人的UUID");
                return true;
            }
            //玩家输入  后无参数
            if (args[0].equalsIgnoreCase("CX")) {
                //给玩家发送信息
                sender.sendMessage("§7§l[§e!§7§l] §7玩家名:" + sender.getName());
                sender.sendMessage("§7§l[§e!§7§l] §7UUID:" + ((Entity) sender).getUniqueId());
                return true;
            }
            if (args[0].equalsIgnoreCase("CXID")) {
                //只输入了  没输入后面别人的id
                if (args.length != 2) {
                    sender.sendMessage("§7§l[§e!§7§l] §7请输入正确的ID");
                    return true;
                }
                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    sender.sendMessage("§7§l[§e!§7§l] §7请输入正确的ID");
                    return true;
                }
                sender.sendMessage("§7§l[§e!§7§l] §7玩家名:" + target.getName());
                sender.sendMessage("§7§l[§e!§7§l] §7UUID:" + target.getUniqueId());
                return true;
            }
        }
        return false;
    }
}
