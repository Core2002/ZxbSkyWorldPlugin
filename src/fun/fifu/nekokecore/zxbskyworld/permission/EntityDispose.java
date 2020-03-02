package fun.fifu.nekokecore.zxbskyworld.permission;

import fun.fifu.nekokecore.zxbskyworld.utils.Helper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.List;

public class EntityDispose implements Listener {
    /**
     * 玩家复活事件
     *
     * @param creatureSpawnEvent
     */
    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent creatureSpawnEvent) {
        int xx = (int) creatureSpawnEvent.getLocation().getX();
        int zz = (int) creatureSpawnEvent.getLocation().getZ();
        if (Helper.inSpawn(xx, zz)) {
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

}
