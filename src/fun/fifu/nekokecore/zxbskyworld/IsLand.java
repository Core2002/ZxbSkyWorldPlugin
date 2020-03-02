package fun.fifu.nekokecore.zxbskyworld;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.Random;


/**
 * @author NekokeCore
 */
public class IsLand extends BaseIsLand {
    public static void main(String[] args) {
        Main.jsonObject = Main.initJson(Main.CONFIGPATH, "{}");
        IsLand isLand = new IsLand("NekokeCore");
        System.out.println(isLand.SkyX + "," + isLand.SkyY);
        System.out.println(isLand.getxxCentered() + ",," + isLand.getyyCentered());
        System.out.println("xxFrom:" + IsLand.getxxForm(isLand.SkyX));
        System.out.println("yyFrom:" + IsLand.getyyForm(isLand.SkyY));
        System.out.println("xxEnd:" + IsLand.getxxEnd(isLand.SkyX));
        System.out.println("yyEnd:" + IsLand.getyyEnd(isLand.SkyY));
    }

    @Override
    public String getSkyLoc() {
        return "(" + SkyX + "," + SkyY + ")";
    }

    @Override
    public void addSkyWorld(String Loc) {
        if (!jsonArray.contains(Loc)) {
            jsonArray.add(Loc);
            trim();
        }
    }

    @Override
    public String getMainIsland() {
        return "M(" + SkyX + "," + SkyY + ")";
    }

    public ArrayList<String> getIslandList() {
        ArrayList<String> arrayList = new ArrayList<String>();
        for (Object obj : jsonArray) {
            String str = (String) obj;
            arrayList.add(str);
        }
        return arrayList;
    }

    public IsLand(String UUID) {
        //分配UUID
        this.UUID = UUID;
        //初始化JSON配置文件
        if (Main.jsonObject == null) {
            throw new RuntimeException("配置文件异常，请仔细检查！！！");
        }
        if ((jsonArray = (JSONArray) Main.jsonObject.get(UUID)) == null) {
            jsonArray = new JSONArray();
        } else {
            jsonArray = (JSONArray) Main.jsonObject.get(UUID);
        }

        //初始化主岛坐标
        String temp = "Miss Main IsLand!";
        //遍历查找主岛坐标
        for (Object obj : jsonArray) {
            temp = (String) obj;
            if ("M".equals(temp.substring(0, 1))) {
                break;
            }
            temp = (String) jsonArray.get(0);
            temp = String.format("M%s", temp);
        }
        //如果没有主岛，把第一个当成主岛,如果没有岛，就自动分配
        if (!jsonArray.contains(temp)) {
            if (jsonArray.size() == 0) {
                temp = "M" + allocationIsLand();
                int xxx = getxxForm(getSkyX(temp)) + 511 - 3;
                int zzz = getyyForm(getSkyY(temp)) + 511 - 1;
                World world = Bukkit.getWorld("world");
                //生成执行命令
                String Command = "clone 508 60 510 515 69 516 " + xxx + " " + (64 - 4) + " " + zzz;
                //那个区块如果不存在，就自动生成
                System.out.println("chunk.load:" + world.getChunkAt(new Location(world, xxx, 64, zzz)).load(true));
                System.out.println("chunk.load:" + world.getChunkAt(new Location(world, xxx + 16, 64, zzz)).load(true));
                System.out.println("chunk.load:" + world.getChunkAt(new Location(world, xxx - 16, 64, zzz)).load(true));
                System.out.println("chunk.load:" + world.getChunkAt(new Location(world, xxx, 64, zzz + 16)).load(true));
                System.out.println("chunk.load:" + world.getChunkAt(new Location(world, xxx, 64, zzz - 16)).load(true));
                System.out.println("chunk.load:" + world.getChunkAt(new Location(world, xxx + 16, 64, zzz + 16)).load(true));
                System.out.println("chunk.load:" + world.getChunkAt(new Location(world, xxx - 16, 64, zzz - 16)).load(true));
                System.out.println("chunk.load:" + world.getChunkAt(new Location(world, xxx + 16, 64, zzz + 16)).load(true));
                System.out.println("chunk.load:" + world.getChunkAt(new Location(world, xxx - 16, 64, zzz - 16)).load(true));

                System.out.println("chunk0.load:" + world.getChunkAt(new Location(world, 511, 64, 511)).load(true));
                System.out.println("chunk0.load:" + world.getChunkAt(new Location(world, 511 + 16, 64, 511)).load(true));
                System.out.println("chunk0.load:" + world.getChunkAt(new Location(world, 511 - 16, 64, 511)).load(true));
                System.out.println("chunk0.load:" + world.getChunkAt(new Location(world, 511, 64, 511 + 16)).load(true));
                System.out.println("chunk0.load:" + world.getChunkAt(new Location(world, 511, 64, 511 - 16)).load(true));
                System.out.println("chunk0.load:" + world.getChunkAt(new Location(world, 511 + 16, 64, 511 + 16)).load(true));
                System.out.println("chunk0.load:" + world.getChunkAt(new Location(world, 511 - 16, 64, 511 - 16)).load(true));
                System.out.println("chunk0.load:" + world.getChunkAt(new Location(world, 511 + 16, 64, 511 + 16)).load(true));
                System.out.println("chunk0.load:" + world.getChunkAt(new Location(world, 511 - 16, 64, 511 - 16)).load(true));
                //开始拷贝初始空岛
                Bukkit.getScheduler().runTask(Main.plugin, () -> {
                    System.out.println(Command);
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), Command);
                });
                jsonArray.add(0, temp);
            }
            jsonArray.remove(0);
            jsonArray.add(0, temp);
        }
        //赋值
        SkyX = getSkyX(temp);
        SkyY = getSkyY(temp);
        //整理并存档
        trim();
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
    public String allocationIsLand() {
        String temp = jsonArray.toJSONString();
        Random random = new Random(System.currentTimeMillis());
        String tempSkyLoc = "Error";
        int tempxx;
        int tempyy;
        do {
            tempxx = random.nextInt(MAXSKYLOC) - random.nextInt(MAXSKYLOC);
            tempyy = random.nextInt(MAXSKYLOC) - random.nextInt(MAXSKYLOC);
            tempSkyLoc = "(" + tempxx + "," + tempyy + ")";
        } while (temp.contains(tempSkyLoc) || (tempxx == 0 && tempyy == 0));
        return tempSkyLoc;
    }
}
