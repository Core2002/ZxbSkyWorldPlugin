package fun.fifu.nekokecore.zxbskyworld.utils;

import fun.fifu.nekokecore.zxbskyworld.SkyWorld.IsLand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class Helper {
    /**
     * 把玩家传送到岛上，脚下第四格永远是玻璃
     *
     * @param player
     * @param SkyLoc
     */
    public static void tpSkyLoc(Player player, String SkyLoc) {
        World world = Bukkit.getWorld("world");
        Location location = new Location(world, getxxCentered(IsLand.getSkyX(SkyLoc)), 64, getyyCentered(IsLand.getSkyY(SkyLoc)));
        Location location1 = new Location(location.getWorld(), location.getX(), location.getY() - 4, location.getZ());
        Block block = location1.getBlock();
        block.setType(Material.GLASS);
        player.teleport(location);

    }

    public static int getxxCentered(int SkyX) {
        return (IsLand.getxxEnd(SkyX) - IsLand.getxxForm(SkyX)) / 2 + IsLand.getxxForm(SkyX);
    }

    public static int getyyCentered(int SkyY) {
        return (IsLand.getyyEnd(SkyY) - IsLand.getyyForm(SkyY)) / 2 + IsLand.getyyForm(SkyY);
    }

}
