package fun.fifu.nekokecore.zxbskyworld.command;

import fun.fifu.nekokecore.zxbskyworld.Main;
import fun.fifu.nekokecore.zxbskyworld.IsLand;
import fun.fifu.nekokecore.zxbskyworld.utils.Helper;
import fun.fifu.nekokecore.zxbskyworld.utils.SoundPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Commain "s"
 *
 * @author NekokeCore
 */
public class MainDispose implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        // 判断输入的指令是否是 /s ,不区分大小写
        if ("s".equalsIgnoreCase(command.getName())) {
            // 判断输入者的类型 为了防止出现 控制台或命令方块 输入的情况
            if (!(commandSender instanceof Player)) {
                commandSender.sendMessage("你必须是一名玩家!");
                // 这里返回true只是因为该输入者不是玩家,并不是输入错指令,所以我们直接返回true即可
                return true;
            }
            // 如果我们已经判断好sender是一名玩家之后,我们就可以将其强转为Player对象,把它作为一个"玩家"来处理
            Player player = (Player) commandSender;
            new SoundPlayer().playCat(player);
            //获取UUID
            String UUID = player.getUniqueId().toString();
            IsLand isLand = new IsLand(UUID);
            String SkyLoc = isLand.getSkyLoc();
            if (isLand.inSkyWorld((int) player.getLocation().getX(), (int) player.getLocation().getZ())) {
                Helper.goSpawn(player);
                int xx = Integer.parseInt(Main.util_jsonObject.get("spawn_xx").toString());
                int zz = Integer.parseInt(Main.util_jsonObject.get("spawn_zz").toString());
                player.sendMessage(player.getName() + "欢迎来到主城!  " + Helper.toSkyLoc(xx, zz));
                new SoundPlayer().playCat(player);
                return true;
            }
            Helper.tpSkyLoc(player, SkyLoc);
            player.sendMessage(player.getName() + "欢迎回家!  " + isLand.getSkyLoc());
            new SoundPlayer().playCat(player);

            // 返回true防止返回指令的usage信息
            return true;
        }
        return false;

    }
}
