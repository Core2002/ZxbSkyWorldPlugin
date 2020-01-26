package fun.fifu.nekokecore.permission;

import fun.fifu.nekokecore.zxbskyworld.utils.Helper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;

public class BlockDispose implements Listener {
    String str = "你没权限";

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getPlayer().isOp()) {
            return;
        }
        int xx = (int) event.getBlock().getLocation().getX();
        int zz = (int) event.getBlock().getLocation().getZ();
        if (Helper.inSpawn(xx, zz)) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(str);
        }
    }

    @EventHandler
    public void onBlockBurn(BlockBurnEvent event) {
        int xx = (int) event.getBlock().getLocation().getX();
        int zz = (int) event.getBlock().getLocation().getZ();
        if (Helper.inSpawn(xx, zz)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockCanBuild(BlockCanBuildEvent event) {
        Player player = null;
        if ((player = event.getPlayer()) != null && player.isOp()) {
            return;
        }
        int xx = (int) event.getBlock().getLocation().getX();
        int zz = (int) event.getBlock().getLocation().getZ();
        if (Helper.inSpawn(xx, zz)) {
            event.setBuildable(false);
            if (player != null) {
                player.sendMessage(str);
            }

        }
    }

    @EventHandler
    public void onLeavesDecay(LeavesDecayEvent event) {
        int xx = (int) event.getBlock().getLocation().getX();
        int zz = (int) event.getBlock().getLocation().getZ();
        if (Helper.inSpawn(xx, zz)) {
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void onBlockIgnite(BlockIgniteEvent event) {
        if (event.getPlayer() != null && event.getPlayer().isOp()) {
            return;
        }
        int xx = (int) event.getBlock().getLocation().getX();
        int zz = (int) event.getBlock().getLocation().getZ();
        if (Helper.inSpawn(xx, zz)) {
            event.setCancelled(true);
        }
    }

}
