package fun.fifu.nekokecore.zxbskyworld.listener;

import fun.fifu.nekokecore.zxbskyworld.IsLand;
import fun.fifu.nekokecore.zxbskyworld.command.InfoDispose;
import fun.fifu.nekokecore.zxbskyworld.command.SeclusionDispose;
import fun.fifu.nekokecore.zxbskyworld.command.ShareDispose;
import fun.fifu.nekokecore.zxbskyworld.permission.DynamicEternalMap;
import fun.fifu.nekokecore.zxbskyworld.utils.Helper;
import fun.fifu.nekokecore.zxbskyworld.utils.SoundPlayer;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
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
        playerJoinEvent.getPlayer().setFlying(false);
        playerJoinEvent.getPlayer().setAllowFlight(false);
        player.leaveVehicle();
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
        //获取UUID
        String UUID = player.getUniqueId().toString();
        String SkyLoc = IsLand.dateAdmin.getDefaultSkyLoc(UUID);
        //如果玩家没有岛屿，给他显示帮助
        if (SkyLoc == null) {
            player.sendTitle("§a欢迎新人owo" + player.getDisplayName(), "§a使用/s以开始你的空岛生涯", 10, 20 * 60 * 60 * 24, 20);
        }
    }

    /**
     * 玩家重生事件.
     */

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        event.getPlayer().setFlying(false);
        event.getPlayer().setAllowFlight(false);
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
        if (event.getOffHandItem() != null && event.getOffHandItem().getType().equals(Material.BUCKET) && player.isSneaking()) {
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
     * 玩家传送
     *
     * @param event
     */
    @EventHandler
    public void onTP(PlayerTeleportEvent event) {
        Location from = event.getFrom();
        Location to = event.getTo();
        int fx = from.getBlockX();
        int fz = from.getBlockZ();
        int tx = to.getBlockX();
        int tz = to.getBlockZ();

        String SkyLoc = Helper.toSkyLoc(to);
        String UUID = event.getPlayer().getUniqueId().toString();

        //如果传送距离低于16格，忽略
        if (Math.sqrt((fx - tx) * (fx - tx) + (fz - tz) * (fz - tz)) < 16)
            return;
        //如果是主人或者是成员，则可以传送
        String owner;
        event.getPlayer().resetTitle();
        try {
            owner = InfoDispose.toRenHua(IsLand.dateAdmin.getOwnersList(SkyLoc)).toString();
        } catch (IOException e) {
            event.getPlayer().sendTitle("§a传送成功", "§a该岛屿是无人认领的", 10, 50, 20);
            return;
        }
        if (ShareDispose.isOwner(UUID, SkyLoc)) {
            event.getPlayer().sendTitle("§a传送成功", "§a主人:" + owner, 10, 50, 20);
            return;
        } else if (ShareDispose.isRepetition(UUID, SkyLoc)) {
            event.getPlayer().sendTitle("§a传送成功", "§a主人:" + owner, 10, 50, 20);
            return;
        }
        //如果岛屿是隐居的，则玩家无法传送过去
        if (SeclusionDispose.getSwi(SkyLoc)) {
            event.setCancelled(true);
            event.getPlayer().resetTitle();
            event.getPlayer().sendTitle("§4传送失败", "§c该岛屿是隐居的", 10, 50, 20);
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
        Helper.showDamage(event.getPlayer(), livingEntity);
    }

    /**
     * 点击牌子
     *
     * @param event
     */
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.hasBlock() && event.getClickedBlock() != null) {
            //event.getPlayer().sendMessage("debug1:hasBlock");
            if (event.getClickedBlock().getState() instanceof Sign) {
                Sign sign = (Sign) event.getClickedBlock().getState();
                String[] lines = sign.getLines();
                if (lines[0].equalsIgnoreCase("[命令]")) {
                    event.getPlayer().chat(lines[1]);
                }
            }
        }
    }

}
