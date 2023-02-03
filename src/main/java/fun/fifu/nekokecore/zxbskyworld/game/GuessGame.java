/*
 * Copyright (c) 2020 Core2002
 * ZxbSkyWorldPlugin is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 *          http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 */

package fun.fifu.nekokecore.zxbskyworld.game;

import java.util.Hashtable;
import java.util.Random;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class GuessGame implements CommandExecutor {
    // guess的初始化
    static int cishu = 0;
    public Hashtable<CommandSender, Integer> numberList = new Hashtable<CommandSender, Integer>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            if (command.getName().equalsIgnoreCase("guess")) {
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
