package fun.fifu.nekokecore.zxbskyworld.command;

import fun.fifu.nekokecore.zxbskyworld.Main;
import fun.fifu.nekokecore.zxbskyworld.IsLand;
import fun.fifu.nekokecore.zxbskyworld.utils.Helper;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
        if ("exple".equalsIgnoreCase(command.getName())) {
            if (door) {
                new Thread(this).start();
            }

            // 判断输入者的类型 为了防止出现 控制台或命令方块 输入的情况
            if (!(commandSender instanceof Player)) {
                commandSender.sendMessage("你必须是一名玩家!");
                // 这里返回true只是因为该输入者不是玩家,并不是输入错指令,所以我们直接返回true即可
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
                IsLand isLand = new IsLand(player.getUniqueId().toString());
                for (String SkyLoc : isLand.getIslandList()) {
                    //System.out.println("Debug:遍历的" + player.getName() + "的SkyLoc:" + SkyLoc);
                    if (Helper.inSkyWrold(xx, zz, SkyLoc)) {
                        HashMap<String, Integer> temp = new HashMap<String, Integer>();
                        SkyLoc = Helper.simplify(SkyLoc);
                        temp.put(SkyLoc, 1000);
                        ExpleDispose.map.put(expleUUID, temp);
                        //System.out.println("ExpleDispose_Map:" + ExpleDispose.map);
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
                }
                player.sendMessage("玩家" + explePlayerName + "不在你的岛里。");

            }
            return true;
        }
        return false;
    }
}
