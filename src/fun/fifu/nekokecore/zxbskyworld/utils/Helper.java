package fun.fifu.nekokecore.zxbskyworld.utils;

import fun.fifu.nekokecore.zxbskyworld.IsLand;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.io.IOException;

import static org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH;


public class Helper {
    /**
     * 把玩家传送到岛上，脚下第四格永远是基岩
     *
     * @param player
     * @param SkyLoc
     */
    public static void tpSkyLoc(Player player, String SkyLoc) {
        if (!skyLocValidity(SkyLoc)) {
            player.sendMessage("坐标" + SkyLoc + "不合法！最大边界为+-" + IsLand.MAXSKYLOC + "，若有疑问，请联系服务器管理员。");
            return;
        }
        World world = Bukkit.getWorld("world");
        Location location = new Location(world, getxxCentered(IsLand.getSkyX(SkyLoc)), 64, getyyCentered(IsLand.getSkyY(SkyLoc)));
        Location location1 = new Location(location.getWorld(), location.getX(), location.getY() - 4, location.getZ());
        Block block = location1.getBlock();
        block.setType(Material.BEDROCK);
        player.teleport(location);
        new SoundPlayer().playCat(player);
    }

    public static boolean skyLocValidity(String SkyLoc) {
        if (SkyLoc.equals("Error")) {
            return false;
        }
        int SkyX = IsLand.getSkyX(SkyLoc);
        int SkyY = IsLand.getSkyY(SkyLoc);
        return Math.abs(SkyX) < IsLand.MAXSKYLOC && Math.abs(SkyY) < IsLand.MAXSKYLOC;
    }

    public static int getxxCentered(int SkyX) {
        return (IsLand.getrrEnd(SkyX) - IsLand.getrrForm(SkyX)) / 2 + IsLand.getrrForm(SkyX);
    }

    public static int getyyCentered(int SkyY) {
        return (IsLand.getrrEnd(SkyY) - IsLand.getrrForm(SkyY)) / 2 + IsLand.getrrForm(SkyY);
    }

    public static void goSpawn(Player player) {
        player.teleport(getSpawnLocation());
    }

    public static Location getSpawnLocation() {
        String world_str = DateAdmin.util_jsonObject.get("spawn_world").toString();
        int xx = Integer.parseInt(DateAdmin.util_jsonObject.get("spawn_xx").toString());
        int yy = Integer.parseInt(DateAdmin.util_jsonObject.get("spawn_yy").toString());
        int zz = Integer.parseInt(DateAdmin.util_jsonObject.get("spawn_zz").toString());
        int yaw = Integer.parseInt(DateAdmin.util_jsonObject.get("spawn_yaw").toString());
        int pitch = Integer.parseInt(DateAdmin.util_jsonObject.get("spawn_pitch").toString());
        World world = Bukkit.getWorld(world_str);
        return new Location(world, xx, yy, zz, yaw, pitch);
    }

    public static boolean inSpawn(int xx, int zz) {
        return inSkyWrold(xx, zz, "(0,0)");
    }

    public static boolean inSpawn(String SkyLoc) {
        return simplify(SkyLoc).equalsIgnoreCase(DateAdmin.spawnSkyLoc);
    }

    public static boolean in(int now, int from, int end) {
        return now >= from && now <= end;
    }

    public static boolean inSkyWrold(int xx, int zz, String SkyLoc) {
        int SkyX = IsLand.getSkyX(SkyLoc);
        int SkyY = IsLand.getSkyY(SkyLoc);
        return in(xx, IsLand.getrrForm(SkyX), IsLand.getrrEnd(SkyX)) && in(zz, IsLand.getrrForm(SkyY), IsLand.getrrEnd(SkyY));
    }

    public static boolean inSkyWrold(int xx, int zz, int SkyX, int SkyY) {
        return in(xx, IsLand.getrrForm(SkyX), IsLand.getrrEnd(SkyX)) && in(zz, IsLand.getrrForm(SkyY), IsLand.getrrEnd(SkyY));
    }

    public static String toSkyLoc(int xx, int zz) {
        return "(" + getSkyR(xx) + "," + getSkyR(zz) + ")";
    }

    public static String toSkyLoc(Location location) {
        int xx = location.getBlockX();
        int zz = location.getBlockZ();
        return "(" + getSkyR(xx) + "," + getSkyR(zz) + ")";
    }

    public static int getSkyR(int rr) {
        int SkyR = 0;
        if (rr > 0) {
            while (!in(rr, IsLand.getrrForm(SkyR), IsLand.getrrEnd(SkyR))) {
                if (Math.abs(SkyR) > IsLand.MAXSKYLOC) {
                    throw new RuntimeException("R轴SkyLoc正越界！rr=" + rr);
                }
                SkyR++;
            }
        } else if (rr < 0) {
            while (!in(rr, IsLand.getrrForm(SkyR), IsLand.getrrEnd(SkyR))) {
                if (Math.abs(SkyR) > IsLand.MAXSKYLOC) {
                    throw new RuntimeException("R轴SkyLoc负越界！rr=" + rr);
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
        Location location = player.getLocation();
        int xx = location.getBlockX();
        int zz = location.getBlockZ();
        if (inSpawn(xx, zz)) {
            player.playSound(player.getLocation(), Sound.ENTITY_LLAMA_SPIT, 20.0f, 20.0f);
            return false;
        }
        if (player.isOp()) {
            return true;
        }
        String SkyLoc = Helper.toSkyLoc(xx, zz);
        try {
            IsLand.dateAdmin.getJSONObject(SkyLoc);
        } catch (Exception e) {
            player.playSound(player.getLocation(), Sound.ENTITY_LLAMA_SPIT, 20.0f, 20.0f);
            return false;
        }
        try {
            for (Object obj : IsLand.dateAdmin.getOwnersList(SkyLoc)) {
                String uuid = (String) obj;
                if (UUID.equalsIgnoreCase(uuid)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            for (Object obj : IsLand.dateAdmin.getMembersList(SkyLoc)) {
                String uuid = (String) obj;
                if (UUID.equalsIgnoreCase(uuid)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        player.playSound(player.getLocation(), Sound.ENTITY_LLAMA_SPIT, 20.0f, 20.0f);
        return false;
    }

    public static int getCLocX(String CLoc) {
        return Integer.parseInt(CLoc.substring(CLoc.indexOf('[') + 1, CLoc.indexOf(',')));
    }

    public static int getCLocZ(String CLoc) {
        return Integer.parseInt(CLoc.substring(CLoc.indexOf(',') + 1, CLoc.indexOf(']')));
    }

    public static String toCLoc(Chunk chunk) {
        int cX = chunk.getX();
        int cZ = chunk.getZ();
        return "[" + cX + "," + cZ + "]";
    }

    public static void showDamage(Player player,LivingEntity livingEntity){
        int i = (int) livingEntity.getHealth();
        int j = (int) livingEntity.getAttribute(GENERIC_MAX_HEALTH).getValue();
        player.sendTitle("", "HP:" + i + "/" + j, 10, 40, 5);
        player.sendMessage(livingEntity.getName() + "受伤：" + (int) livingEntity.getLastDamage() + "，HP:" + i + "/" + j);
    }
}
