package fun.fifu.nekokecore.zxbskyworld.command;

import fun.fifu.nekokecore.zxbskyworld.Main;
import fun.fifu.nekokecore.zxbskyworld.SkyWorld.IsLand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

/**
 * @author NekokeCore
 */
public class CommandLitener implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        // 判断输入的指令是否是 /s
        if (Main.COMMAND.equalsIgnoreCase(command.getName())) {
            // 判断输入者的类型 为了防止出现 控制台或命令方块 输入的情况
            if (!(commandSender instanceof Player)) {
                commandSender.sendMessage("你必须是一名玩家!");
                // 这里返回true只是因为该输入者不是玩家,并不是输入错指令,所以我们直接返回true即可
                return true;
            }
            // 如果我们已经判断好sender是一名玩家之后,我们就可以将其强转为Player对象,把它作为一个"玩家"来处理
            Player player = (Player) commandSender;
            player.sendMessage("你成功的执行了指令/" + Main.COMMAND);


            //获取UUID
            String UUID = player.getUniqueId().toString();
            IsLand isLand=new IsLand(UUID);
            String SkyLoc= isLand.getSkyLoc();





            // 返回true防止返回指令的usage信息
            return true;
        }
        return false;

    }
}
