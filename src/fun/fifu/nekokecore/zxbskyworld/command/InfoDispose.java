package fun.fifu.nekokecore.zxbskyworld.command;

import fun.fifu.nekokecore.zxbskyworld.IsLand;
import fun.fifu.nekokecore.zxbskyworld.utils.Helper;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class InfoDispose implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if ("infos".equalsIgnoreCase(command.getName())) {
            Player player = ((Player) commandSender).getPlayer();
            if (player == null) {
                commandSender.sendMessage("你必须是一名玩家!");
                return true;
            }
            Location location = player.getLocation();
            int xx = location.getBlockX();
            int zz = location.getBlockZ();
            String SkyLoc = Helper.toSkyLoc(xx, zz);
            player.sendMessage("||=================================");
            player.sendMessage("||玩家" + player.getName() + "您好！");
            player.sendMessage("||=================================");
            player.sendMessage("||你所在的岛屿是：" + SkyLoc);
            try {
                IsLand.dateAdmin.getJSONObject(SkyLoc);
            } catch (Exception e) {
                player.sendMessage("||这个岛屿无人认领");
                player.sendMessage("||---------------------------------");
                player.sendMessage("||这个岛屿的范围是：");
                player.sendMessage("||在X轴上：从" + IsLand.getrrForm(IsLand.getSkyX(SkyLoc)) + "到" + IsLand.getrrEnd(IsLand.getSkyX(SkyLoc)));
                player.sendMessage("||在z轴上：从" + IsLand.getrrForm(IsLand.getSkyY(SkyLoc)) + "到" + IsLand.getrrEnd(IsLand.getSkyY(SkyLoc)));
                player.sendMessage("||你所在的坐标是：x:" + xx + ",z:" + zz + "，按F3查看详细");
                return true;
            }
            try {
                player.sendMessage("||主人：" + IsLand.dateAdmin.getOwnersList(SkyLoc));
                player.sendMessage("||好友：" + IsLand.dateAdmin.getMembersList(SkyLoc));
                player.sendMessage("||其他属性：" + IsLand.dateAdmin.getOthers(SkyLoc));
            } catch (IOException e) {
                e.printStackTrace();
            }
            player.sendMessage("||---------------------------------");
            player.sendMessage("||这个岛屿的范围是：");
            player.sendMessage("||在X轴上：从" + IsLand.getrrForm(IsLand.getSkyX(SkyLoc)) + "到" + IsLand.getrrEnd(IsLand.getSkyX(SkyLoc)));
            player.sendMessage("||在z轴上：从" + IsLand.getrrForm(IsLand.getSkyY(SkyLoc)) + "到" + IsLand.getrrEnd(IsLand.getSkyY(SkyLoc)));
            player.sendMessage("||你所在的坐标是：x:" + xx + ",z:" + zz + "，按F3查看详细");
            if (Helper.havePermission(player)) {
                player.sendMessage("||你有权限操作这个岛。");
            } else {
                player.sendMessage("||你没有权限操作这个岛。");
            }
            player.sendMessage("||=================================");
            return true;

        }
        return false;
    }
}
