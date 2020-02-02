package fun.fifu.nekokecore.zxbskyworld.utils;

import fun.fifu.nekokecore.zxbskyworld.SkyWorld.IsLand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import static fun.fifu.nekokecore.zxbskyworld.Main.util_jsonObject;

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
        block.setType(Material.BEDROCK);
        player.teleport(location);
        new SoundPlayer().playCat(player);

    }

    public static int getxxCentered(int SkyX) {
        return (IsLand.getxxEnd(SkyX) - IsLand.getxxForm(SkyX)) / 2 + IsLand.getxxForm(SkyX);
    }

    public static int getyyCentered(int SkyY) {
        return (IsLand.getyyEnd(SkyY) - IsLand.getyyForm(SkyY)) / 2 + IsLand.getyyForm(SkyY);
    }

    public static void goSpawn(Player player) {
        player.teleport(getSpawnLocation());
    }

    public static Location getSpawnLocation() {
        if (util_jsonObject == null) {
            throw new RuntimeException("配置文件异常，请仔细检查！！！");
        }
        String world_str = util_jsonObject.get("spawn_world").toString();
        int xx = Integer.parseInt(util_jsonObject.get("spawn_xx").toString());
        int yy = Integer.parseInt(util_jsonObject.get("spawn_yy").toString());
        int zz = Integer.parseInt(util_jsonObject.get("spawn_zz").toString());
        World world = Bukkit.getWorld(world_str);
        return new Location(world, xx, yy, zz);
    }

    public static boolean inSpawn(int xx, int zz) {
        return in(xx, 0, 1023) && in(zz, 0, 1023);
    }

    public static boolean in(int tmp, int from, int end) {
        return tmp >= from && tmp <= end;
    }
}
