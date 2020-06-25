package fun.fifu.nekokecore.zxbskyworld.command;

import fun.fifu.nekokecore.zxbskyworld.Main;
import fun.fifu.nekokecore.zxbskyworld.utils.Helper;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class TPADispose implements CommandExecutor {

    public static Map tpa = new HashMap<String, String>();
    public static Map temp = new HashMap<String, String>();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!"tpa".equalsIgnoreCase(command.getName()) || !(commandSender instanceof Player)) {
            return false;
        }
        Player player = (Player) commandSender;
        String pn = player.getName();

        if (strings == null || strings.length == 0) {
            if (tpa != null && !tpa.isEmpty())
                for (Object o : tpa.keySet()) {
                    Player Pn = Bukkit.getPlayer(tpa.get(o).toString());
                    if (Pn != null && Pn.isOnline() && pn.equals(Pn.getName())) {
                        Player player1 = Bukkit.getPlayer((String) o);
                        if (player1 != null && player1.isOnline()) {
                            temp.put(player1.getName(), Helper.toSkyLoc(player.getLocation()));
                            player1.teleport(player);
                            player.sendMessage("已传送" + player1.getName());
                            temp.remove(player.getName());
                        }
                    }
                }
            else
                player.sendMessage("没有玩家向你发送请求");
            return true;
        }

        if (strings.length == 1 && strings[0] != null && !strings[0].isEmpty()) {
            String p = strings[0];
            Player pp = Bukkit.getPlayer(p);
            if (pp != null && pp.isOnline()) {
                tpa.put(pn, p);
                pp.sendMessage("玩家" + pn + "想传送到你这里来，在8秒内输入/tpa可同意传送");
                player.sendMessage("已向玩家" + pp.getName() + "发起传送请求，8秒未回应则销毁");
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        tpa.remove(pn);
                    }
                }.runTaskLater(Main.plugin, 20 * 8);
            }
            return true;
        }

        return true;
    }
}
