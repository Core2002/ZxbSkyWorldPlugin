package fun.fifu.nekokecore.zxbskyworld.item;

import fun.fifu.nekokecore.zxbskyworld.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class Honey implements Listener {
    public static List<String> honeyPlayer = new ArrayList<>();

    @EventHandler
    public static void onItemConsumeEvent(PlayerItemConsumeEvent event) {
        ItemStack item = event.getItem();
        if (item.getType() == Material.HONEY_BOTTLE) {
            Player player = event.getPlayer();
            String uuid = player.getUniqueId().toString();
            if (Honey.honeyPlayer.contains(uuid)) {
                player.sendMessage("小蜜蜂~嗡嗡嗡~");
                event.setCancelled(true);
                return;
            }
            Honey.honeyPlayer.add(uuid);
            player.setAllowFlight(true);
            player.setFlying(true);
            player.sendMessage("你现在是一只小蜜蜂，可以短暂飞行了(500tick)");
            new BukkitRunnable() {
                @Override
                public void run() {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            player.setFlying(false);
                            player.setAllowFlight(false);
                            Honey.honeyPlayer.remove(uuid);
                        }
                    }.runTaskLater(Main.plugin, 200);
                    player.sendMessage("短暂飞行将在200个tick后结束");
                }
            }.runTaskLater(Main.plugin, 300);
        }
    }
}
