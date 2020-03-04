package fun.fifu.nekokecore.zxbskyworld.utils;

import fun.fifu.nekokecore.zxbskyworld.Main;
import fun.fifu.nekokecore.zxbskyworld.IsLand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;

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
        int yaw = Integer.parseInt(util_jsonObject.get("spawn_yaw").toString());
        int pitch = Integer.parseInt(util_jsonObject.get("spawn_pitch").toString());
        World world = Bukkit.getWorld(world_str);
        return new Location(world, xx, yy, zz, yaw, pitch);
    }

    public static boolean inSpawn(int xx, int zz) {
        return in(xx, 0, IsLand.SIDE) && in(zz, 0, IsLand.SIDE);
    }

    public static boolean in(int now, int from, int end) {
        return now >= from && now <= end;
    }

    public static boolean inSkyWrold(int xx, int zz, String SkyLoc) {
        int SkyX = IsLand.getSkyX(SkyLoc);
        int SkyY = IsLand.getSkyY(SkyLoc);
        return in(xx, IsLand.getxxForm(SkyX), IsLand.getxxEnd(SkyX)) && in(zz, IsLand.getyyForm(SkyY), IsLand.getyyEnd(SkyY));
    }

    public static boolean inSkyWrold(int xx, int zz, int SkyX, int SkyY) {
        return in(xx, IsLand.getxxForm(SkyX), IsLand.getxxEnd(SkyX)) && in(zz, IsLand.getyyForm(SkyY), IsLand.getyyEnd(SkyY));
    }

    public static String toSkyWorld(int xx, int zz) {
        return "(" + getSkyR(xx) + "," + getSkyR(zz) + ")";
    }

    public static int getSkyR(int rr) {
        int SkyR = 0;
        if (rr > 0) {
            while (!in(rr, IsLand.getxxForm(SkyR), IsLand.getxxEnd(SkyR))) {
                if (SkyR > IsLand.MAXSKYLOC) {
                    throw new RuntimeException("R轴SkyLoc正越界！");
                }
                SkyR++;
            }
        } else if (rr < 0) {
            while (!in(rr, IsLand.getxxForm(SkyR), IsLand.getxxEnd(SkyR))) {
                if (SkyR < -IsLand.MAXSKYLOC) {
                    throw new RuntimeException("R轴SkyLoc负越界！");
                }
                SkyR--;
            }
        }
        return SkyR;
    }

    public static String simplify(String SkyLoc) {
        SkyLoc = SkyLoc.replace("（", "(");
        SkyLoc = SkyLoc.replace("）", ")");
        SkyLoc = SkyLoc.replace("，", ",");
        SkyLoc = SkyLoc.replace(" ", "");
        int SkyX = IsLand.getSkyX(SkyLoc);
        int SkyY = IsLand.getSkyY(SkyLoc);
        return "(" + SkyX + "," + SkyY + ")";
    }

    public static boolean havePermission(Player player) {
        String UUID = player.getUniqueId().toString();
        JSONArray jsonArray;
        //初始化JSON配置文件
        if (Main.jsonObject == null) {
            throw new RuntimeException("配置文件异常，请仔细检查！！！");
        }
        if (Main.jsonObject.get(UUID) == null) {
            jsonArray = new JSONArray();
        } else {
            jsonArray = (JSONArray) Main.jsonObject.get(UUID);
        }
        Location location = player.getLocation();
        int xx = location.getBlockX();
        int zz = location.getBlockZ();
        String SkyLoc;
        for (Object x : jsonArray) {
            SkyLoc = (String) x;
            if (inSkyWrold(xx, zz, SkyLoc)) {
                return true;
            }
        }
        return false;
    }
}
