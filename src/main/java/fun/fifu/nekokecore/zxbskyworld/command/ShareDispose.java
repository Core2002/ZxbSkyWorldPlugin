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
import fun.fifu.nekokecore.zxbskyworld.utils.Helper;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;

import java.io.IOException;

public class ShareDispose implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if ("share".equalsIgnoreCase(command.getName())) {
            if (!(commandSender instanceof Player)) {
                commandSender.sendMessage("你必须是一名玩家!");
                return true;
            }
            if (strings.length != 1) {
                return false;
            }
            String sp = strings[0];
            Player sharePlayer = Bukkit.getPlayer(sp);
            Player player = (Player) commandSender;
            if (!player.getWorld().getName().equals(IsLand.dynamicEternalMap.base_sky_world))
                return true;
            if (sharePlayer == null) {
                player.sendMessage("玩家" + sp + "不在线，无法操作");
                return true;
            }
            String uuid = player.getUniqueId().toString();
            String shareUuid = sharePlayer.getUniqueId().toString();
            String SkyLoc = Helper.toSkyLoc(player.getLocation());
            if (!isOwner(uuid, SkyLoc)) {
                player.sendMessage("你似乎不是这个岛的主人，不可以这么做哦");
                return true;
            }
            try {
                if (shareSkyWorld(shareUuid, SkyLoc)) {
                    player.sendMessage("成功把玩家" + player.getName() + "作为成员添加在" + SkyLoc + "上了");
                } else {
                    player.sendMessage("这个玩家" + player.getName() + "已经在这个岛" + SkyLoc + "上了");
                }
            } catch (IOException e) {
                player.sendMessage("操作失败：" + e + "若有疑问，请联系腐竹");
                e.printStackTrace();
            }
            player.sendMessage("他的UUID是：" + shareUuid);
            player.sendMessage("如果操作错误，可以使用/unshare命令移除权限，使用/infos可查看岛员信息，/seclusion 可关闭/打开陌生人的传送权限");
            return true;
        }
        return false;
    }

    /**
     * 分享目标岛屿作为成员给，返回true：没有重复，false：有重复，无需再添加
     *
     * @param uuid
     * @param SkyLoc
     * @return
     */
    public boolean shareSkyWorld(String uuid, String SkyLoc) throws IOException {
        if (!isRepetition(uuid, SkyLoc)) {
            JSONArray jsonArray = IsLand.dateAdmin.getMembersList(SkyLoc);
            jsonArray.add(uuid);
            IsLand.dateAdmin.saveMemberslist(jsonArray, SkyLoc);
            return true;
        }
        return false;
    }

    /**
     * 判断uuid是否是SkyLoc的成员。true：是，false：不是
     *
     * @param uuid
     * @param SkyLoc
     * @return
     */
    public static boolean isRepetition(String uuid, String SkyLoc) {
        if (IsLand.dynamicEternalMap.opCanPermiss && uuid.equals(IsLand.dynamicEternalMap.zxb))
            return true;
        try {
            JSONArray jsonArray = IsLand.dateAdmin.getMembersList(SkyLoc);
            if (jsonArray == null) {
                return false;
            }
            for (Object obj : jsonArray) {
                String uid = (String) obj;
                if (uid.equalsIgnoreCase(uuid)) {
                    return true;
                }
            }
        } catch (IOException e) {
            return false;
        }
        return false;
    }

    /**
     * 判断uuid是否是skyLoc的所有者：true：是，false：不是
     *
     * @param uuid
     * @param SkyLoc
     * @return
     */
    public static boolean isOwner(String uuid, String SkyLoc) {
        if (IsLand.dynamicEternalMap.opCanPermiss && uuid.equals(IsLand.dynamicEternalMap.zxb))
            return true;
        try {
            for (Object obj : IsLand.dateAdmin.getOwnersList(SkyLoc)) {
                String uid = (String) obj;
                if (uuid.equalsIgnoreCase(uid)) {
                    return true;
                }
            }
        } catch (IOException e) {
            return false;
        }
        return false;
    }
}
