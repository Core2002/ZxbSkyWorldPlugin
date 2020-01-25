package fun.fifu.nekokecore.zxbskyworld.Listener;

import fun.fifu.nekokecore.zxbskyworld.utils.Helper;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;


public class PlayerJoin implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent playerJoinEvent) {
        Player player = playerJoinEvent.getPlayer();
        if(!player.getGameMode().equals(GameMode.SURVIVAL)){
            player.setGameMode(GameMode.SURVIVAL);
        }
        Helper.goSpawn(player);
        player.sendMessage(player.getName() + "欢迎来到主城!");
    }


}
