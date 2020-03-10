package fun.fifu.nekokecore.zxbskyworld;

import fun.fifu.nekokecore.zxbskyworld.utils.DateAdmin;
import fun.fifu.nekokecore.zxbskyworld.utils.Helper;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;


/**
 * @author NekokeCore
 */
public class IsLand extends BaseIsLand {

    public static void main(String[] args) {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                System.out.println(i + "," + j);
            }
        }
    }

    public IsLand(String SkyLoc) {
        //如果没有岛，就自动分配
        if (!Main.dateAdmin.getAllSkyLoc().contains(SkyLoc)) {
            String temp = allocationIsLand(Main.dateAdmin.getAllSkyLoc());
            int xxx = getrrForm(getSkyX(temp)) + 511 - 3;
            int zzz = getrrForm(getSkyY(temp)) + 511 - 1;
            World world = Bukkit.getWorld("world");
            //生成执行命令
            String Command = "clone 508 60 510 515 69 516 " + xxx + " " + (64 - 4) + " " + zzz;
            //那个区块如果不存在，就自动生成
            Main.logger.info("chunk.load:" + Objects.requireNonNull(world).getChunkAt(new Location(world, xxx, 64, zzz)).load(true));
            Main.logger.info("chunk.load:" + world.getChunkAt(new Location(world, xxx + 16, 64, zzz)).load(true));
            Main.logger.info("chunk.load:" + world.getChunkAt(new Location(world, xxx - 16, 64, zzz)).load(true));
            Main.logger.info("chunk.load:" + world.getChunkAt(new Location(world, xxx, 64, zzz + 16)).load(true));
            Main.logger.info("chunk.load:" + world.getChunkAt(new Location(world, xxx, 64, zzz - 16)).load(true));
            Main.logger.info("chunk.load:" + world.getChunkAt(new Location(world, xxx + 16, 64, zzz + 16)).load(true));
            Main.logger.info("chunk.load:" + world.getChunkAt(new Location(world, xxx - 16, 64, zzz - 16)).load(true));
            Main.logger.info("chunk.load:" + world.getChunkAt(new Location(world, xxx + 16, 64, zzz + 16)).load(true));
            Main.logger.info("chunk.load:" + world.getChunkAt(new Location(world, xxx - 16, 64, zzz - 16)).load(true));

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
            Bukkit.getScheduler().runTask(Main.plugin, () -> {
                Main.logger.info("开始拷贝初始空岛:" + Command);
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), Command);
            });
            JSONObject jsonObject = null;
            try {
                jsonObject = (JSONObject) (new JSONParser().parse(DateAdmin.defaultJsonStr));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Main.dateAdmin.saveJSONObject(jsonObject, temp);
        }
    }

    public static int getSkyX(String SkyLoc) {
        return Integer.parseInt(SkyLoc.substring(SkyLoc.indexOf('(') + 1, SkyLoc.indexOf(',')));
    }

    public static int getSkyY(String SkyLoc) {
        return Integer.parseInt(SkyLoc.substring(SkyLoc.indexOf(',') + 1, SkyLoc.indexOf(')')));
    }

    /**
     * 分配岛屿_SkyLoc
     *
     * @return
     */
    public String allocationIsLand(ArrayList<String> nowSkyLocs) {

        Random random = new Random(System.currentTimeMillis());
        String tempSkyLoc = "Error";
        int SkyX;
        int SkyZ;
        do {
            SkyX = random.nextInt(MAXSKYLOC) - random.nextInt(MAXSKYLOC);
            SkyZ = random.nextInt(MAXSKYLOC) - random.nextInt(MAXSKYLOC);
            tempSkyLoc = "(" + SkyX + "," + SkyZ + ")";
        } while (nowSkyLocs.contains(tempSkyLoc) || Helper.simplify(tempSkyLoc).equalsIgnoreCase(DateAdmin.spawnSkyLoc) || tempSkyLoc.equals("Error"));
        return tempSkyLoc;
    }
}
