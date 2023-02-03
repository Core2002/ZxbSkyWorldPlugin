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

import fun.fifu.nekokecore.zxbskyworld.IsLand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.ArrayList;

public class HomesDispose implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if ("homes".equalsIgnoreCase(command.getName())) {
            if (!(commandSender instanceof Player)) {
                commandSender.sendMessage("你必须是一名玩家!");
                return true;
            }
            Player player=(Player)commandSender;
            if (!player.getWorld().getName().equals(IsLand.dynamicEternalMap.base_sky_world))
                return true;
            String uuid = player.getUniqueId().toString();
            ArrayList<String> Owners = new ArrayList<String>();
            ArrayList<String> Members = new ArrayList<String>();
            try {
                Owners = IsLand.dateAdmin.getAllOwnerSkyLoc(uuid);
                Members = IsLand.dateAdmin.getAllMembersSkyLoc(uuid);
            } catch (IOException e) {
                e.printStackTrace();
            }
            player.sendMessage("||=================================|");
            player.sendMessage("||你拥有的岛有：" + Owners);
            player.sendMessage("||---------------------------------|");
            player.sendMessage("||你加入的岛有：" + Members);
            player.sendMessage("||=================================|");
            return true;
        }
        return false;
    }


}
