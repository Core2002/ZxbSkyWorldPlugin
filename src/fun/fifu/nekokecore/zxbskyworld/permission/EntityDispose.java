package fun.fifu.nekokecore.zxbskyworld.permission;

import fun.fifu.nekokecore.zxbskyworld.Main;
import fun.fifu.nekokecore.zxbskyworld.utils.Helper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;


public class EntityDispose implements Listener {
    /**
     * 当一个生物体在世界中出生时触发该事件.
     *
     * @param creatureSpawnEvent
     */
    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent creatureSpawnEvent) {
        int xx = (int) creatureSpawnEvent.getLocation().getX();
        int zz = (int) creatureSpawnEvent.getLocation().getZ();
        if (Helper.inSkyWrold(xx, zz, Main.spawnSkyLoc)) {
            Main.plugin.getLogger().info("事件:当一个生物体在世界中出生时触发:其坐标xx:" + xx + "，zz：" + zz + "已拦截！");
            creatureSpawnEvent.setCancelled(true);
        }

    }

    /**
     * 实体爆炸事件
     *
     * @param explodeEvent
     */
    @EventHandler(ignoreCancelled = true)
    public void onEntityExplode(EntityExplodeEvent explodeEvent) {
        explodeEvent.blockList().clear();
    }

    /**
     * 容器被打开事件
     *
     * @param event
     */
    public void onOpen(InventoryOpenEvent event) {
        InventoryType inventoryType = event.getInventory().getType();
        Player player;
        if (event.getPlayer() instanceof Player) {
            player = (Player) event.getPlayer();
        } else {
            return;
        }
        if (!Helper.havePermission(player)) {
            player.sendMessage("你没权限");
            event.setCancelled(true);
        }
    }

}
