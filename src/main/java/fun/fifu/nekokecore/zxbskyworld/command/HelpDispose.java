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

package fun.fifu.nekokecore.zxbskyworld.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author NekokeCore
 */
public class HelpDispose implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if ("help_zxb".equalsIgnoreCase(command.getName())) {
            if (!(commandSender instanceof Player)) {
                commandSender.sendMessage("你必须是一名玩家!");
                return true;
            }
            Player player = (Player) commandSender;
            int index;
            try {
                index = Integer.parseInt(strings[0]);
            } catch (Exception e) {
                index = 1;
            }
            switch (index) {
                case 2:
                    player.sendMessage("||=================================|");
                    player.sendMessage("||                      -帮助-");
                    player.sendMessage("||---------------------------------|");
                    player.sendMessage("|| /biome <biome> 更改当前区块的生物群系");
                    player.sendMessage("|| /seclusion 隐居，使你所在的岛不能被他人传送");
                    player.sendMessage("|| /homes 显示包括分享给你的岛");
                    player.sendMessage("|| /share <Player> 和玩家分享你所在的岛");
                    player.sendMessage("|| /unshare <Player> 取消和某人分享当前岛");
                    player.sendMessage("|| /help 查看帮助                       [2/3]");
                    player.sendMessage("||=================================|");
                    break;
                case 3:
                    player.sendMessage("||=================================|");
                    player.sendMessage("||                      -帮助-");
                    player.sendMessage("||---------------------------------|");
                    player.sendMessage("|| /goto p <Player> 去这个玩家的岛");
                    player.sendMessage("|| /explosion 打开/关闭当前区块的爆炸");
                    player.sendMessage("|| /sethome 设置自己的家");
                    player.sendMessage("|| /giveup 放弃你所在的岛              [危]");
                    player.sendMessage("|| /get 领取你所在的岛(废弃)");
                    player.sendMessage("|| /help 查看帮助                       [3/3]");
                    player.sendMessage("||=================================|");
                    break;
                default:
                    player.sendMessage("||=================================|");
                    player.sendMessage("||                      -帮助-");
                    player.sendMessage("||---------------------------------|");
                    player.sendMessage("|| /s 回到自己的岛，若没有，自动领取");
                    player.sendMessage("|| /goto 去一个岛");
                    player.sendMessage("|| /exple <Player> 把玩家从你的岛踢出");
                    player.sendMessage("|| /infos 查看你所在的岛屿的信息");
                    player.sendMessage("|| /uuid 查询UUID");
                    player.sendMessage("|| /help 查看帮助                       [1/3]");
                    player.sendMessage("||=================================|");
            }
            return true;
        }

        return false;
    }
}
