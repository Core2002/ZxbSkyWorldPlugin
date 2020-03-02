package fun.fifu.nekokecore.zxbskyworld.permission;

import fun.fifu.nekokecore.zxbskyworld.utils.Helper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class EntityDispose implements Listener {

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent creatureSpawnEvent) {
        int xx = (int) creatureSpawnEvent.getLocation().getX();
        int zz = (int) creatureSpawnEvent.getLocation().getZ();
        if (Helper.inSpawn(xx, zz)) {
            creatureSpawnEvent.setCancelled(true);
        }

    }

}
