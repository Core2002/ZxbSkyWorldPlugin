package fun.fifu.nekokecore.zxbskyworld.listener;

import fun.fifu.nekokecore.zxbskyworld.utils.Helper;
import fun.fifu.nekokecore.zxbskyworld.utils.SoundPlayer;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.util.io.BukkitObjectOutputStream;


public class PlayerDispose implements Listener {
    /**
     * 重生点处理
     *
     * @param playerJoinEvent
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent playerJoinEvent) {
        Player player = playerJoinEvent.getPlayer();
        if (!player.getGameMode().equals(GameMode.SURVIVAL)) {
            player.setGameMode(GameMode.SURVIVAL);
        }
        Helper.goSpawn(player);
        player.sendMessage(player.getName() + "欢迎来到主城!");
        new SoundPlayer().playCat(player);
    }

    /**
     * 重生点处理
     */

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        event.setRespawnLocation(Helper.getSpawnLocation());

    }

    private int inventorySize(ItemStack[] contents) {
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

}
