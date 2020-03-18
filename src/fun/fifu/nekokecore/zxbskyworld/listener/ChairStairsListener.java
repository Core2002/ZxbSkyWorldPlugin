package fun.fifu.nekokecore.zxbskyworld.listener;

import java.util.HashMap;
import java.util.Map;

import fun.fifu.nekokecore.zxbskyworld.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class ChairStairsListener implements Listener {
    public Map<Player, Location> playerLocation = new HashMap<Player, Location>();
    public Map<Player, Entity> chairList = new HashMap<Player, Entity>();
    public Map<Player, Location> chairLocation = new HashMap<Player, Location>();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getClickedBlock() != null) {
            if (!player.isSneaking()) {
                return;
            }
            Block block = event.getClickedBlock();
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK && !player.isInsideVehicle() && player.getInventory().getItemInMainHand().getType() == Material.AIR) {
                //范围
                double range = 1.0;
                if (player.getLocation().distance(block.getLocation()) - 1.0 > range) {
                    return;
                }
                World world = player.getWorld();
                this.playerLocation.put(player, player.getLocation());
                Entity chair = world.spawnEntity(player.getLocation(), EntityType.ARROW);
                this.chairList.put(player, chair);
                chair.teleport(block.getLocation().add(0.5, 0.2, 0.5));
                this.chairLocation.put(player, chair.getLocation());
                chair.addPassenger(player);
                player.sendMessage(player.getName() + "小鸟坐");
                event.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if (this.playerLocation.containsKey(event.getPlayer())) {
            if (event.getPlayer().isSneaking()) {
                Player player;
                final Player sit_player = player = event.getPlayer();
                final Location stand_location = this.playerLocation.get(player);
                Entity sit_chair = this.chairList.get(player);
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, () -> {
                    sit_player.teleport(stand_location);
                    sit_player.setSneaking(false);
                }, 1L);
                event.setCancelled(true);
                this.playerLocation.remove(player);
                sit_chair.remove();
                this.chairList.remove(player);
                event.setCancelled(true);
                return;
            }
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player;
        if (this.playerLocation.containsKey(event.getPlayer()) && (player = event.getPlayer()).getLocation() != this.playerLocation.get(player) && !player.isInsideVehicle()) {
            World world = player.getWorld();
            if (this.chairLocation.containsKey(player)) {
                Entity chair = world.spawnEntity(this.chairLocation.get(player), EntityType.ARROW);
                chair.addPassenger(player);
            }
        }
    }
}

