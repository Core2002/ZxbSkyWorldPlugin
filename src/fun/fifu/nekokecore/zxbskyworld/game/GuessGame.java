package fun.fifu.nekokecore.zxbskyworld.game;

import java.util.Hashtable;
import java.util.Random;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GuessGame implements CommandExecutor {
    // guess的初始化
    static int cishu = 0;
    public Hashtable<CommandSender, Integer> numberList = new Hashtable<CommandSender, Integer>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            if (command.getName().equalsIgnoreCase("guess")) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage("只能由玩家执行此命令");
                    return true;
                }
                if (numberList.containsKey(sender)) {
                    if (args.length >= 1) {
                        if (numberList.get(sender) == Integer.parseInt(args[0])) {
                            sender.sendMessage("§a恭喜你，你一共猜了" + cishu + "次，终于猜对了owo");
                            numberList.clear();
                        } else {
                            cishu++;
                            sender.sendMessage(numberList.get(sender) > Integer.parseInt(args[0]) ? "猜小了" : "猜大了");
                        }
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    numberList.put(sender, new Random(System.nanoTime()).nextInt(100));
                    cishu = 0;
                    sender.sendMessage("§e游戏开始，/guess <数字> ");
                    return true;
                }
            }
        } catch (Exception e) {
            cishu = 0;
            sender.sendMessage("§a乖乖的输入一个数字好不好吖owo" + e);
            return false;
        }

        return onCommand(sender, command, label, args);
    }

}
