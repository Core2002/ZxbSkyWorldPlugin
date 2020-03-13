package fun.fifu.nekokecore.zxbskyworld;

import fun.fifu.nekokecore.zxbskyworld.command.ShareDispose;
import fun.fifu.nekokecore.zxbskyworld.command.UnShareDispose;
import fun.fifu.nekokecore.zxbskyworld.utils.DateAdmin;
import fun.fifu.nekokecore.zxbskyworld.utils.Helper;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.json.simple.JSONArray;
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
    public static DateAdmin dateAdmin;

    public static void main(String[] args) {
        dateAdmin = new DateAdmin();
        String uuid = "3e79580d-cfdb-4b80-999c-99bc2740d194";
        //System.out.println(dateAdmin.getAllOwnerSkyLoc(uuid));
        //System.out.println(dateAdmin.getAllSkyLoc());
        System.out.println(dateAdmin.getAllMembersSkyLoc(uuid));
        ShareDispose shareDispose = new ShareDispose();

        if (shareDispose.shareSkyWorld(uuid, "(0,0)")) {
            System.out.println("没有重复，成功添加" + uuid + "到(0,0)");
        } else {
            System.out.println("重复，无需再添加");
        }

        UnShareDispose unShareDispose = new UnShareDispose();
        if (unShareDispose.unShareSkyWorld(uuid, "(0,0)")) {
            System.out.println("成功把成员" + uuid + "从这个岛(0,0)移除了");
        } else {
            System.out.println("这个玩家" + uuid + "不是这个岛(0,0)上的成员,无需删除");
        }


        System.out.println(dateAdmin.getAllMembersSkyLoc(uuid));
    }

    public IsLand(String uuid) {
        String SkyLoc = allocationIsLand(dateAdmin.getAllSkyLoc());
        buildSkyLoc(uuid, SkyLoc);
        Helper.tpSkyLoc(Bukkit.getPlayer(uuid), dateAdmin.getDefaultSkyLoc(uuid));
    }

    public static void buildSkyLoc(String uuid, String SkyLoc) {
        int xxx = getrrForm(getSkyX(SkyLoc)) + 511 - 3;
        int zzz = getrrForm(getSkyY(SkyLoc)) + 511 - 1;
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
            SkyX = random.nextInt(MAXSKYLOC) - random.nextInt(MAXSKYLOC);
            SkyY = random.nextInt(MAXSKYLOC) - random.nextInt(MAXSKYLOC);
            tempSkyLoc = "(" + SkyX + "," + SkyY + ")";
        } while (nowSkyLocs.contains(tempSkyLoc) || Helper.simplify(tempSkyLoc).equalsIgnoreCase(DateAdmin.spawnSkyLoc) || !Helper.skyLocValidity(tempSkyLoc));
        return tempSkyLoc;
    }
}
