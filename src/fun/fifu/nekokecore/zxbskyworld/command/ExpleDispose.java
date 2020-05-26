package fun.fifu.nekokecore.zxbskyworld.command;

import fun.fifu.nekokecore.zxbskyworld.Main;
import fun.fifu.nekokecore.zxbskyworld.utils.Helper;
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
                //命令发送者和被踢出者在同一个岛屿
                String SkyLoc = Helper.toSkyLoc(explePlayer.getLocation());
                if (ShareDispose.isOwner(player.getUniqueId().toString(), Helper.toSkyLoc(player.getLocation())) && SkyLoc.equals(Helper.toSkyLoc(player.getLocation()))) {
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
                player.sendMessage("该玩家和你不在同一个岛上，或你不是该岛屿的主人");
                return true;
            }
            return true;
        }
        return false;
    }
}
