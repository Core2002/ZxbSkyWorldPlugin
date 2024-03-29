/*
 * Copyright (c) 2020 Core2002
 * ZxbSkyWorldPlugin is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 *          http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 */

package fun.fifu.nekokecore.zxbskyworld.utils;

import fun.fifu.nekokecore.zxbskyworld.BaseIsLand;
import fun.fifu.nekokecore.zxbskyworld.IsLand;
import fun.fifu.nekokecore.zxbskyworld.Main;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH;


public class Helper {
    public static ArrayList<String> noP = new ArrayList<>();

    /**
     * 把玩家传送到岛上，脚下第四格永远是基岩
     *
     * @param player 要传送的玩家
     * @param SkyLoc 岛的坐标
     */
    public static void tpSkyLoc(Player player, String SkyLoc) {
        if (!skyLocValidity(SkyLoc)) {
            player.sendMessage("坐标" + SkyLoc + "不合法！最大边界为+-" + IsLand.MAX_SKY_LOC + "，若有疑问，请联系服务器管理员。");
            return;
        }
        World world = Bukkit.getWorld(IsLand.dynamicEternalMap.base_sky_world);
        Location location = new Location(world, getRRCentered(IsLand.getSkyX(SkyLoc)), 64, getRRCentered(IsLand.getSkyY(SkyLoc)));
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
        return Math.abs(SkyX) < IsLand.MAX_SKY_LOC && Math.abs(SkyY) < IsLand.MAX_SKY_LOC;
    }

    public static int getRRCentered(int SkyR) {
        return (IsLand.getRREnd(SkyR) - IsLand.getRRForm(SkyR)) / 2 + IsLand.getRRForm(SkyR);
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
        return inSkyWorld(xx, zz, "(0,0)");
    }

    public static boolean inSpawn(String SkyLoc) {
        return simplify(SkyLoc).equalsIgnoreCase(DateAdmin.spawnSkyLoc);
    }

    public static boolean in(int now, int from, int end) {
        return now >= from && now <= end;
    }

    public static boolean inSkyWorld(int xx, int zz, String SkyLoc) {
        int SkyX = IsLand.getSkyX(SkyLoc);
        int SkyY = IsLand.getSkyY(SkyLoc);
        return in(xx, IsLand.getRRForm(SkyX), IsLand.getRREnd(SkyX)) && in(zz, IsLand.getRRForm(SkyY), IsLand.getRREnd(SkyY));
    }

    public static boolean inSkyWorld(int xx, int zz, int SkyX, int SkyY) {
        return in(xx, IsLand.getRRForm(SkyX), IsLand.getRREnd(SkyX)) && in(zz, IsLand.getRRForm(SkyY), IsLand.getRREnd(SkyY));
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
        if (rr >= 0) return rr / BaseIsLand.SIDE;
        else return -((-rr + BaseIsLand.SIDE - 1) / BaseIsLand.SIDE);
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
        if (!player.getWorld().getName().equals(IsLand.dynamicEternalMap.base_sky_world)) return true;
        if (IsLand.dynamicEternalMap.opCanPermiss && (player.isOp() || player.getUniqueId().toString().equals(IsLand.dynamicEternalMap.zxb)))
            return true;
        String UUID = player.getUniqueId().toString();
        if (noP.contains(UUID)) {
            return noP(UUID);
        }
        Location location = player.getLocation();
        int xx = location.getBlockX();
        int zz = location.getBlockZ();
        String SkyLoc = Helper.toSkyLoc(xx, zz);
        try {
            IsLand.dateAdmin.getJSONObject(SkyLoc);
        } catch (Exception e) {
            player.playSound(player.getLocation(), Sound.ENTITY_LLAMA_SPIT, 20.0f, 20.0f);
            return noP(UUID);
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
        return noP(UUID);
    }

    public static boolean noP(String uuid) {
        if (!noP.contains(uuid)) {
            noP.add(uuid);
            new Thread(() -> {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                noP.clear();
            }).start();
        }
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

    public static void showDamage(Player player, LivingEntity livingEntity) {
        new BukkitRunnable() {
            @Override
            public void run() {
                int i = (int) livingEntity.getHealth();
                int j = (int) Objects.requireNonNull(livingEntity.getAttribute(GENERIC_MAX_HEALTH)).getValue();
                String color = "§f";
                double c = i / 1.0 / j;
                if (c <= 1.0 && c >= 0.825) {
                    color = "§a";
                } else if (c < 0.825 && c >= 0.66) {
                    color = "§2";
                } else if (c < 0.66 && c >= 0.495) {
                    color = "§e";
                } else if (c < 0.495 && c >= 0.33) {
                    color = "§6";
                } else if (c < 0.33 && c >= 0.165) {
                    color = "§c";
                } else if (c < 0.165) {
                    color = "§4";
                }
                player.sendTitle("", color + livingEntity.getName() + "->HP:" + i + "/" + j, 2, 20, 6);
            }
        }.runTaskLater(Main.plugin, 1);
    }

    public static boolean hasSpawnProtection(Location l1) {
        double l;

        int x = l1.getBlockX();
        int y = l1.getBlockY();
        int z = l1.getBlockZ();

        int x1 = Integer.parseInt((String) DateAdmin.util_jsonObject.get("spawn_xx"));
        int y1 = Integer.parseInt((String) DateAdmin.util_jsonObject.get("spawn_yy"));
        int z1 = Integer.parseInt((String) DateAdmin.util_jsonObject.get("spawn_zz"));

        l = Math.sqrt((x - x1) * (x - x1) + (y - y1) * (y - y1) + (z - z1) * (z - z1));

        return l < 4;
    }

    /**
     * 判断脚下是否踩空
     *
     * @param location 玩家所在坐标
     * @return True:踩空 False:不踩空
     */
    public static boolean footVoid(Location location) {
        int blockX = location.getBlockX();
        int blockY = location.getBlockY();
        int blockZ = location.getBlockZ();

        for (int i = 1; i < 6; i++) {
            if (!new Location(location.getWorld(), blockX, blockY - i, blockZ).getBlock().getState().getType().equals(Material.AIR))
                return false;
        }
        return true;
    }
}
