package fun.fifu.nekokecore.permission;

import fun.fifu.nekokecore.zxbskyworld.utils.Helper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;

public class BlockDispose implements Listener {
    String str = "你没权限";

    /**
     * 当一个方块被玩家破坏的时候，调用本事件.
     *
     * @param event
     */
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

    /**
     * 当一个方块被火烧掉的时候触发此事件.
     *
     * @param event
     */
    @EventHandler
    public void onBlockBurn(BlockBurnEvent event) {
        int xx = (int) event.getBlock().getLocation().getX();
        int zz = (int) event.getBlock().getLocation().getZ();
        if (Helper.inSpawn(xx, zz)) {
            event.setCancelled(true);
        }
    }

    /**
     * 当我们尝试建造一个方块的时候，可以看到我们是否可以在此建造它。
     *
     * @param event
     */
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

    /**
     * 当树叶消失时触发此事件.
     *
     * @param event
     */
    @EventHandler
    public void onLeavesDecay(LeavesDecayEvent event) {
        int xx = (int) event.getBlock().getLocation().getX();
        int zz = (int) event.getBlock().getLocation().getZ();
        if (Helper.inSpawn(xx, zz)) {
            event.setCancelled(true);
        }

    }

    /**
     * 当一个方块被点燃时触发.
     *
     * @param event
     */
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
