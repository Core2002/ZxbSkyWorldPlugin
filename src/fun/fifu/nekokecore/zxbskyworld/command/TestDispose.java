package fun.fifu.nekokecore.zxbskyworld.command;

import fun.fifu.nekokecore.zxbskyworld.IsLand;
import fun.fifu.nekokecore.zxbskyworld.item.Lcarus;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestDispose implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if ("test".equalsIgnoreCase(command.getName())) {
            if (!(commandSender instanceof Player)) {
                commandSender.sendMessage("你必须是一名玩家!");
                return true;
            }
            Player player=(Player)commandSender;
            String uuid = player.getUniqueId().toString();
            if (!IsLand.dynamicEternalMap.zxb.equals(uuid)) {
                player.sendMessage("只有小白才可以用哦");
                return true;
            }

            player.getInventory().setChestplate(Lcarus.build());

            System.out.println(player.getLocation().getWorld().getName());
            return true;
        }
        return false;
    }
}
