package fun.fifu.nekokecore.zxbskyworld.command;

import fun.fifu.nekokecore.zxbskyworld.IsLand;
import fun.fifu.nekokecore.zxbskyworld.Main;
import fun.fifu.nekokecore.zxbskyworld.utils.Helper;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * exple <Player> 把玩家从你的岛踢出
 *
 * @author NekokeCore
 */
public class ExpleDispose implements CommandExecutor, Runnable {


    /**
     * "玩家UUID"，<"岛"，时间>
     */
    public static HashMap<String, HashMap<String, Integer>> map = new HashMap<String, HashMap<String, Integer>>();

    private static boolean door = true;

    @Override
    public void run() {
        Main.plugin.getLogger().info("守护线程已启动！");

        while (true) {
            try {
                if (door) {
                    door = false;
                }
                Thread.sleep(1000 * 60);
                for (String uuid : ExpleDispose.map.keySet()) {
                    for (String SkyLoc : ExpleDispose.map.get(uuid).keySet()) {
                        if (ExpleDispose.map.get(uuid).get(SkyLoc) > 0) {
                            int temp = ExpleDispose.map.get(uuid).get(SkyLoc);
                            temp -= 60;
                            ExpleDispose.map.get(uuid).put(SkyLoc, temp);
                            Main.plugin.getLogger().info(uuid + "的剩余时间：" + temp);
                        } else {
                            Main.plugin.getLogger().info(uuid + "的剩余时间小于0，已清除");
                            ExpleDispose.map.get(uuid).remove(SkyLoc);
                        }

                    }
                }
            } catch (InterruptedException e) {
                door = true;
                Main.plugin.getLogger().warning("守护线程：" + e.toString());
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if ("exple".equalsIgnoreCase(s)) {
            if (door) {
                new Thread(this).start();
            }
            if (!(commandSender instanceof Player)) {
                commandSender.sendMessage("你必须是一名玩家!");
                return true;
            }
            Player player = (Player) commandSender;
            String PlayerName = player.getName();
            String explePlayerName;
            try {
                explePlayerName = strings[0];
            } catch (Exception e) {
                explePlayerName = "Error";
            }
            Player explePlayer = Main.plugin.getServer().getPlayer(explePlayerName);
            String expleUUID = explePlayer.getUniqueId().toString();
            if (explePlayer.isOnline()) {
                Location loc = explePlayer.getLocation();
                int xx = loc.getBlockX();
                int zz = loc.getBlockZ();
                ArrayList<String> arrayList = null;
                try {
                    //踢人玩家当前岛的所有的所有者
                    arrayList = IsLand.dateAdmin.getOwnersList(Helper.toSkyLoc(player.getLocation()));
                } catch (IOException e) {
                    e.printStackTrace();
                    player.sendMessage("操作失败，请重试，若有疑问，请联系服务器管理员");
                    return true;
                }
                for (String owneruuid : arrayList) {
                    //命令发送者是该岛屿的主人
                    if (player.getUniqueId().toString().equalsIgnoreCase(owneruuid)) {
                        //命令发送者和被踢出者在同一个岛屿
                        String SkyLoc = Helper.toSkyLoc(explePlayer.getLocation());
                        if (SkyLoc.equals(Helper.toSkyLoc(player.getLocation()))) {
                            HashMap<String, Integer> temp = new HashMap<String, Integer>();
                            SkyLoc = Helper.simplify(SkyLoc);
                            temp.put(SkyLoc, 1000);
                            ExpleDispose.map.put(expleUUID, temp);
                            int time = ExpleDispose.map.get(expleUUID).get(SkyLoc);
                            if (explePlayer.isOnline()) {
                                explePlayer.sendMessage("你被玩家" + PlayerName + "踢出了他的岛," + time + "秒后解除。");
                            }
                            if (player.isOnline()) {
                                player.sendMessage("你踢出了玩家" + PlayerName + "," + time + "秒后解除。");
                            }
                            Helper.goSpawn(explePlayer);
                            return true;
                        }
                        player.sendMessage("该玩家和你不在同一个岛上");
                        return true;
                    }
                }
                player.sendMessage("你不是该岛屿的主人");
            }
            return true;
        }
        return false;
    }
}
