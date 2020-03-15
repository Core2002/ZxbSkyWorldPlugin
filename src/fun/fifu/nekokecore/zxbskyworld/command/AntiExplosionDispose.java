package fun.fifu.nekokecore.zxbskyworld.command;

import fun.fifu.nekokecore.zxbskyworld.IsLand;
import fun.fifu.nekokecore.zxbskyworld.utils.Helper;
import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static fun.fifu.nekokecore.zxbskyworld.command.ShareDispose.isOwner;

public class AntiExplosionDispose implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if ("anti-explosion".equalsIgnoreCase(command.getName())) {
            if (!(commandSender instanceof Player)) {
                commandSender.sendMessage("你必须是一名玩家!");
                return true;
            }
            Player player = (Player) commandSender;
            if (!isOwner(player.getUniqueId().toString(), Helper.toSkyLoc(player.getLocation()))) {
                player.sendMessage("你似乎不是这个岛的主人，不可以这么做哦");
                return true;
            }
            Chunk chunk = player.getLocation().getChunk();
            String CLoc = Helper.toCLoc(chunk);
            if (strings.length != 1) {
                if (IsLand.dateAdmin.getCanExplosion(CLoc)) {
                    player.sendMessage("你所在的区块可以爆炸");
                } else {
                    player.sendMessage("你所在的区块不可以爆炸");
                }
                return true;
            }
            String sp = strings[0];
            if (sp.equalsIgnoreCase("on")) {
                IsLand.dateAdmin.setCanExplosion(true, CLoc);
                player.sendMessage("你所在的区块已开启爆炸");
            } else if (sp.equalsIgnoreCase("off")) {
                IsLand.dateAdmin.setCanExplosion(false, CLoc);
                player.sendMessage("你所在的区块已关闭爆炸");
            } else {
                return false;
            }
        }
        return false;
    }
}
