package fun.fifu.nekokecore.zxbskyworld;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static com.google.common.collect.ComparisonChain.start;


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

    public static void mainll(String[] args) {
        String SkyLoc = "(0,0)";
        int SkyX = IsLand.getSkyX(SkyLoc);
        int SkyY = IsLand.getSkyY(SkyLoc);
        int xxfrom = IsLand.getrrForm(SkyX);
        int zzfrom = IsLand.getrrForm(SkyY);
        int xxend = IsLand.getrrEnd(SkyX);
        int zzend = IsLand.getrrEnd(SkyY);
        List<int[]> dataList = new ArrayList<>();
        //遍历岛的所有坐标
        for (int zz = zzfrom; zz <= zzend; zz++) {
            for (int xx = xxfrom; xx <= xxend; xx++) {
                dataList.add(new int[]{xx, zz});
                //System.out.println(xx + "_" + zz);
            }
        }
        //限制条数
        int pointsDataLimit = 16;
        int size = dataList.size();
        //判断是否有必要分批
        if (pointsDataLimit < size) {
            //分批数
            int part = size / pointsDataLimit;
            System.out.println("共有 ： " + size + "条，！" + pointsDataLimit + "为一批" + " 分为 ：" + part + "批");
            for (int i = 0; i < part; i++) {
                List<int[]> listPage = dataList.subList(0, pointsDataLimit);
                for (int[] li : listPage) {
                    new Thread() {

                        int[] temp = li;

                        @Override
                        public void run() {
                            System.out.println("处理完毕" + temp[0] + "," + temp[1]);

                        }
                    }.start();

                }


                //剔除
                dataList.subList(0, pointsDataLimit).clear();
            }

            if (!dataList.isEmpty()) {
                for (int[] li : dataList) {
                    new Thread() {
                        int[] temp = li;

                        @Override
                        public void run() {
                            System.out.println("处理完毕" + temp[0] + "," + temp[1]);
                        }
                    }.start();

                }


                //表示最后剩下的数据

            }
        } else {
            for (int[] i : dataList) {
                System.out.println("处理完毕" + i[0] + "," + i[1]);
            }

        }

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
