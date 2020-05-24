package fun.fifu.nekokecore.zxbskyworld.permission;

import fun.fifu.nekokecore.zxbskyworld.IsLand;
import fun.fifu.nekokecore.zxbskyworld.utils.DateAdmin;
import fun.fifu.nekokecore.zxbskyworld.utils.Helper;
import org.bukkit.Chunk;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.*;


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
        if (Helper.inSkyWrold(xx, zz, DateAdmin.spawnSkyLoc) && (creatureSpawnEvent.getEntity() instanceof Monster)) {
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
        Chunk chunk = explodeEvent.getLocation().getChunk();
        String CLoc = Helper.toCLoc(chunk);
        if (IsLand.dateAdmin.getCanExplosion(CLoc)) {
            return;
        }
        explodeEvent.blockList().clear();
    }

    /**
     * 容器被打开事件
     *
     * @param event
     */
    @EventHandler
    public void onOpen(InventoryOpenEvent event) {
        Inventory inventory = event.getInventory();
        if (inventory instanceof PlayerInventory || inventory instanceof MerchantInventory || inventory instanceof CraftingInventory || inventory instanceof EnchantingInventory) {
            return;
        }
        Player player;
        if (event.getPlayer() instanceof Player) {
            player = (Player) event.getPlayer();
        } else {
            return;
        }
        String title = event.getView().getTitle();
        if ("Slimefun 指南".equals(title)) {
            return;
        }
        if (title.contains("Slimefun") || title.contains("设置") || title.contains("設置") || title.contains("Settings") || title.contains("Searching")) {
            return;
        }
        if (!Helper.havePermission(player)) {
            player.sendMessage("你没权限");
            event.setCancelled(true);
        }
    }

    /**
     * 实体受伤时触发
     * 保护非怪物不被没有权限的玩家伤害
     *
     * @param event
     */
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (Helper.hasSpawnProtection(event.getEntity().getLocation())) {
            event.setCancelled(true);
            return;
        }
        Entity entity = event.getEntity();
        if (entity instanceof LivingEntity) {
            if (event.getDamager() instanceof Player) {
                Player player = (Player) event.getDamager();
                Helper.showDamage(player, (LivingEntity) entity);
                if (!(entity instanceof Monster) && !Helper.havePermission(player) && !Helper.inSpawn(Helper.toSkyLoc(entity.getLocation()))) {
                    player.sendMessage("你没权限伤害她！");
                    event.setCancelled(true);
                }
            } else if (event.getDamager() instanceof Projectile) {
                if (((Projectile) event.getDamager()).getShooter() instanceof Player) {
                    Player player = (Player) ((Projectile) event.getDamager()).getShooter();
                    if (player == null) {
                        event.setCancelled(true);
                        return;
                    }
                    Helper.showDamage(player, (LivingEntity) entity);
                    if (!(entity instanceof Monster) && !Helper.havePermission(player) && !Helper.inSpawn(Helper.toSkyLoc(entity.getLocation()))) {
                        player.sendMessage("你没权限伤害她！");
                        event.setCancelled(true);
                    }
                }
            }

        }
    }

}
