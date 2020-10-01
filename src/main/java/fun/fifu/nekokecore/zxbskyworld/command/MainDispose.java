package fun.fifu.nekokecore.zxbskyworld.command;

import fun.fifu.nekokecore.zxbskyworld.IsLand;
import fun.fifu.nekokecore.zxbskyworld.utils.DateAdmin;
import fun.fifu.nekokecore.zxbskyworld.utils.Helper;
import fun.fifu.nekokecore.zxbskyworld.utils.SoundPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

/**
 * Commain "s"
 *
 * @author NekokeCore
 */
public class MainDispose implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if ("s".equalsIgnoreCase(command.getName())) {
            if (!(commandSender instanceof Player)) {
                commandSender.sendMessage("你必须是一名玩家!");
                return true;
            }
            Player player=(Player)commandSender;
            new SoundPlayer().playCat(player);
            //获取UUID
            String UUID = player.getUniqueId().toString();
            String SkyLoc = IsLand.dateAdmin.getDefaultSkyLoc(UUID);
            //如果玩家没有岛屿，就给他new一个，然后在传送
            if (SkyLoc == null) {
                try {
                    new IsLand(UUID);
                } catch (IOException e) {
                    e.printStackTrace();
                    player.sendMessage("操作失败，请重试，若有疑问，请联系服务器管理员");
                }
                return true;
            }
            if (strings != null && strings.length >= 1 && strings[0].equalsIgnoreCase("h")) {
                player.teleport(IsLand.dateAdmin.getPlayerHomeLocation(UUID));
                player.sendMessage(player.getName() + "欢迎回家!  " + Helper.toSkyLoc(player.getLocation()));
                new SoundPlayer().playCat(player);
                return true;
            }
            if (!Helper.inSpawn(player.getLocation().getBlockX(), player.getLocation().getBlockZ())) {
                Helper.goSpawn(player);
                player.sendMessage(player.getName() + "欢迎来到主城!  " + DateAdmin.spawnSkyLoc);
                new SoundPlayer().playCat(player);
                return true;
            }
            Helper.tpSkyLoc(player, SkyLoc);
            player.sendMessage(player.getName() + "欢迎回家!  " + IsLand.dateAdmin.getDefaultSkyLoc(UUID));
            new SoundPlayer().playCat(player);
            return true;
        }
        return false;

    }
}
