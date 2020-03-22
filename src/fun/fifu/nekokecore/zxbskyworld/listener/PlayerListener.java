package fun.fifu.nekokecore.zxbskyworld.listener;

import fun.fifu.nekokecore.zxbskyworld.IsLand;
import fun.fifu.nekokecore.zxbskyworld.utils.Helper;
import fun.fifu.nekokecore.zxbskyworld.utils.SoundPlayer;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.bukkit.util.io.BukkitObjectOutputStream;

import static org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH;


/**
 * @author NekokeCore
 */
public class PlayerListener implements Listener {
    /**
     * 玩家进入服务器事件.
     *
     * @param playerJoinEvent
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent playerJoinEvent) {
        Player player = playerJoinEvent.getPlayer();
        try {
            IsLand.dateAdmin.savePlayerInfo(player);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!player.getGameMode().equals(GameMode.SURVIVAL)) {
            player.setGameMode(GameMode.SURVIVAL);
        }
        Helper.goSpawn(player);
        player.sendMessage(player.getName() + "欢迎来到主城!");
        new SoundPlayer().playCat(player);
    }

    /**
     * 玩家重生事件.
     */

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        event.setRespawnLocation(Helper.getSpawnLocation());

    }

    public int inventorySize(ItemStack[] contents) {
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            BukkitObjectOutputStream data = new BukkitObjectOutputStream(os);
            data.writeObject(contents);
            data.close();
            os.close();
            return os.toByteArray().length;
        } catch (IOException ex) {
            ex.printStackTrace();
            return 5000;
        }
    }

    /**
     * 拾取物品处理
     */
    @EventHandler
    public void onPickup(EntityPickupItemEvent e) {
        ItemStack item = e.getItem().getItemStack();
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            BukkitObjectOutputStream data = new BukkitObjectOutputStream(os);
            data.writeObject(item);
            data.close();
            Player p = null;
            boolean isPlayer = false;
            if (e.getEntity() instanceof Player) {
                p = (Player) e.getEntity();
                isPlayer = true;
            }
            int invSize;
            if (p == null) {
                invSize = 10000;
            } else {
                invSize = this.inventorySize(p.getInventory().getContents());
            }
            int itemSize = os.toByteArray().length;
            os.close();
            int maxSize = 1000000;
            if (itemSize + invSize > maxSize) {
                if (isPlayer) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l(&c&l!&8&l) &c你试图捡起的这个物品要素过多，为了安全起见，不能被捡起。"));
                }
                e.setCancelled(true);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 当玩家编辑或签名书与笔时触发。
     *
     * @param e
     */
    @EventHandler
    public void onBookSave(PlayerEditBookEvent e) {
        List<String> pages = e.getNewBookMeta().getPages();
        String text = String.join("", pages);
        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        int maxLength = 1024 * 8;
        if (bytes.length > maxLength) {
            e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l(&c&l!&8&l) &c这本书的内容超过了" + maxLength + "字节，所以不会保存这本书以防止潜在的攻击。"));
            e.setCancelled(true);
        }
    }

    /**
     * 当玩家交换副手时触发本事件.
     */
    @EventHandler
    public void onSwapHand(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();
        if (event.getOffHandItem().getType().equals(Material.BUCKET) && player.isSneaking()) {
            if (Helper.havePermission(player)) {
                Location l = player.getLocation();
                Location location = new Location(player.getWorld(), l.getBlockX(), l.getBlockY() - 1, l.getBlockZ());
                Block block = location.getBlock();
                if (block.getType().equals(Material.OBSIDIAN)) {
                    block.setType(Material.LAVA);
                    player.sendMessage("你成功将黑曜石转化成了岩浆！");
                }
            } else {
                player.sendMessage("你没权限");
            }
        }
    }

    /**
     * 玩家用完一只桶后触发此事件.
     *
     * @param event
     */
    @EventHandler
    public void onBucketEmpty(PlayerBucketEmptyEvent event) {
        if (!Helper.havePermission(event.getPlayer())) {
            event.getPlayer().sendMessage("你没权限");
            event.setCancelled(true);
        }

    }

    /**
     * 水桶装满水事件.
     *
     * @param event
     */
    @EventHandler
    public void onBucketFill(PlayerBucketFillEvent event) {
        if (!Helper.havePermission(event.getPlayer())) {
            event.getPlayer().sendMessage("你没权限");
            event.setCancelled(true);
        }
    }

    /**
     * 玩家死亡
     *
     * @param event
     */
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (Helper.inSpawn(Helper.toSkyLoc(event.getEntity().getLocation()))) {
            if (event.getEntity().getLocation().getBlockY() < -150) {
                event.getEntity().sendMessage("这个世界虽然不完美，我们仍可以治愈自己");
            }

        }
    }

    /**
     * 点击实体
     *
     * @param event
     */
    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Entity entity = event.getRightClicked();
        if (!(entity instanceof LivingEntity)) {
            return;
        }
        LivingEntity livingEntity = (LivingEntity) entity;
        int i = (int) livingEntity.getHealth();
        int j = (int) livingEntity.getAttribute(GENERIC_MAX_HEALTH).getValue();
        Player player = event.getPlayer();
        player.sendTitle("", "HP:" + i + "/" + j, 10, 40, 5);
        player.sendMessage(entity.getName() + "受伤：" + (int) livingEntity.getLastDamage() + "，HP:" + i + "/" + j);
    }
}
