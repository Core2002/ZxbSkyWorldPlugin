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

package fun.fifu.nekokecore.zxbskyworld;

import fun.fifu.nekokecore.zxbskyworld.permission.DynamicEternalMap;
import fun.fifu.nekokecore.zxbskyworld.utils.DateAdmin;
import fun.fifu.nekokecore.zxbskyworld.utils.Helper;
import fun.fifu.nekokecore.zxbskyworld.utils.IOTools;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * @author NekokeCore
 */
public class IsLand extends BaseIsLand {
    public static DateAdmin dateAdmin;
    public static DynamicEternalMap dynamicEternalMap;

    public static void main(String[] args) throws Exception {
        System.out.println("开始");
        String uuid = "UUID2";
        String ip = "127.0.5.4";
        JSONObject object = IOTools.getJSONObject("./plugins/ZxbSkyWorld/playerIP.json");
        Set<String> ipSet = Stream.of(ip).collect(Collectors.toSet());
        List<String> temp = (List) object.get(uuid);
        if (temp != null) ipSet.addAll(temp);
        object.put(uuid, ipSet);
        IOTools.writeJsonFile(object, "./plugins/ZxbSkyWorld/playerIP.json");
        System.out.println("完毕");
    }

    public IsLand(String uuid) throws IOException {
        String SkyLoc = allocationIsLand(dateAdmin.getAllSkyLoc());
        buildSkyLoc(uuid, SkyLoc);
        Helper.tpSkyLoc(Bukkit.getPlayer(UUID.fromString(uuid)), dateAdmin.getDefaultSkyLoc(uuid));
    }

    public static void buildSkyLoc(String uuid, String SkyLoc) throws IOException {
        int xxx = getRRForm(getSkyX(SkyLoc)) + IsLand.dynamicEternalMap.base_side / 2 + IsLand.dynamicEternalMap.base_xx;
        int yyy = 64 + IsLand.dynamicEternalMap.base_yy;
        int zzz = getRRForm(getSkyY(SkyLoc)) + IsLand.dynamicEternalMap.base_side / 2 + IsLand.dynamicEternalMap.base_zz;
        World world = Bukkit.getWorld(IsLand.dynamicEternalMap.base_sky_world);
        //生成执行命令
        String Command = IsLand.dynamicEternalMap.base_build_sky_command.replace("${xxx}", xxx + "");
        Command = Command.replace("${yyy}", yyy + "");
        Command = Command.replace("${zzz}", zzz + "");
        Command = Command.replace("${base_x1}", IsLand.dynamicEternalMap.base_x1 + "");
        Command = Command.replace("${base_y1}", IsLand.dynamicEternalMap.base_y1 + "");
        Command = Command.replace("${base_z1}", IsLand.dynamicEternalMap.base_z1 + "");
        Command = Command.replace("${base_x2}", IsLand.dynamicEternalMap.base_x2 + "");
        Command = Command.replace("${base_y2}", IsLand.dynamicEternalMap.base_y2 + "");
        Command = Command.replace("${base_z2}", IsLand.dynamicEternalMap.base_z2 + "");
        //那个区块如果不存在，就自动生成
        Main.logger.info("chunk.load:" + Objects.requireNonNull(world).getChunkAt(new Location(world, xxx, yyy, zzz)).load(true));
        Main.logger.info("chunk.load:" + world.getChunkAt(new Location(world, xxx + 16, yyy, zzz)).load(true));
        Main.logger.info("chunk.load:" + world.getChunkAt(new Location(world, xxx - 16, yyy, zzz)).load(true));
        Main.logger.info("chunk.load:" + world.getChunkAt(new Location(world, xxx, yyy, zzz + 16)).load(true));
        Main.logger.info("chunk.load:" + world.getChunkAt(new Location(world, xxx, yyy, zzz - 16)).load(true));
        Main.logger.info("chunk.load:" + world.getChunkAt(new Location(world, xxx + 16, yyy, zzz + 16)).load(true));
        Main.logger.info("chunk.load:" + world.getChunkAt(new Location(world, xxx - 16, yyy, zzz - 16)).load(true));
        Main.logger.info("chunk.load:" + world.getChunkAt(new Location(world, xxx + 16, yyy, zzz + 16)).load(true));
        Main.logger.info("chunk.load:" + world.getChunkAt(new Location(world, xxx - 16, yyy, zzz - 16)).load(true));

        Main.logger.info("chunk0.load:" + world.getChunkAt(new Location(world, 511, 64, 511)).load(true));
        Main.logger.info("chunk0.load:" + world.getChunkAt(new Location(world, 511 + 16, 64, 511)).load(true));
        Main.logger.info("chunk0.load:" + world.getChunkAt(new Location(world, 511 - 16, 64, 511)).load(true));
        Main.logger.info("chunk0.load:" + world.getChunkAt(new Location(world, 511, 64, 511 + 16)).load(true));
        Main.logger.info("chunk0.load:" + world.getChunkAt(new Location(world, 511, 64, 511 - 16)).load(true));
        Main.logger.info("chunk0.load:" + world.getChunkAt(new Location(world, 511 + 16, 64, 511 + 16)).load(true));
        Main.logger.info("chunk0.load:" + world.getChunkAt(new Location(world, 511 - 16, 64, 511 - 16)).load(true));
        Main.logger.info("chunk0.load:" + world.getChunkAt(new Location(world, 511 + 16, 64, 511 + 16)).load(true));
        Main.logger.info("chunk0.load:" + world.getChunkAt(new Location(world, 511 - 16, 64, 511 - 16)).load(true));
        //开始拷贝初始空岛
        String finalCommand = Command;
        Bukkit.getScheduler().runTask(Main.plugin, () -> {
            Main.logger.info("开始拷贝初始空岛:" + finalCommand);
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), finalCommand);
        });
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) (new JSONParser().parse(DateAdmin.defaultJsonStr));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dateAdmin.saveJSONObject(jsonObject, SkyLoc);
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(uuid);
        dateAdmin.saveOwnerslist(jsonArray, SkyLoc);
        dateAdmin.saveDefaultSkyLoc(uuid, SkyLoc);
    }

    public static int getSkyX(String SkyLoc) {
        return Integer.parseInt(SkyLoc.substring(SkyLoc.indexOf('(') + 1, SkyLoc.indexOf(',')));
    }

    public static int getSkyY(String SkyLoc) {
        return Integer.parseInt(SkyLoc.substring(SkyLoc.indexOf(',') + 1, SkyLoc.indexOf(')')));
    }

    /**
     * 分配一个没有人用过的岛屿
     *
     * @return
     */
    public static String allocationIsLand(ArrayList<String> nowSkyLocs) {
        Random random = new Random(System.currentTimeMillis());
        String tempSkyLoc = "Error";
        int SkyX;
        int SkyY;
        do {
            SkyX = random.nextInt(MAX_SKY_LOC) - random.nextInt(MAX_SKY_LOC);
            SkyY = random.nextInt(MAX_SKY_LOC) - random.nextInt(MAX_SKY_LOC);
            tempSkyLoc = "(" + SkyX + "," + SkyY + ")";
        } while (nowSkyLocs.contains(tempSkyLoc) || Helper.simplify(tempSkyLoc).equalsIgnoreCase(DateAdmin.spawnSkyLoc) || !Helper.skyLocValidity(tempSkyLoc));
        return tempSkyLoc;
    }
}
