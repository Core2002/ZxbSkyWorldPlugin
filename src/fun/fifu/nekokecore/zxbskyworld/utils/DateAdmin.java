package fun.fifu.nekokecore.zxbskyworld.utils;

import fun.fifu.nekokecore.zxbskyworld.IsLand;
import fun.fifu.nekokecore.zxbskyworld.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class DateAdmin {
    static final String datePATH = "./plugins/ZxbSkyWorld/date/";
    static final String utilConfigPATH = "./plugins/ZxbSkyWorld/util_config.json";
    static final String unAntiExplosion = "./plugins/ZxbSkyWorld/unAntiExplosion.json";
    static final String indexInfosPATH = "./plugins/ZxbSkyWorld/index.json";
    static final String playerHomeInfoPATH = "./plugins/ZxbSkyWorld/playerHomeInfoPATH.json";
    public static JSONObject util_jsonObject = null;
    public static String spawnSkyLoc = "(0,0)";
    public static String defaultJsonStr = "{\"Owners\":[],\"Members\":[],\"Others\":{}}";

    public DateAdmin() {
        try {
            String util_initStr = "{\"spawn_world\":\"world\",\"spawn_xx\":\"359\",\"spawn_yy\":\"109\",\"spawn_zz\":\"295\",\"spawn_yaw\":\"180\",\"spawn_pitch\":\"0\"}";
            util_jsonObject = initJson(utilConfigPATH, util_initStr);
            spawnSkyLoc = Helper.toSkyLoc(Integer.parseInt(Objects.requireNonNull(util_jsonObject).get("spawn_xx").toString()), Integer.parseInt(util_jsonObject.get("spawn_yy").toString()));
            spawnSkyLoc = Helper.simplify(spawnSkyLoc);
            initJson(indexInfosPATH, "{}");
            initJson(unAntiExplosion, "{}");
            initJson(datePATH + spawnSkyLoc + ".json", defaultJsonStr);
            initJson(playerHomeInfoPATH, "{}");
        } catch (Exception e) {
            Main.plugin.getLogger().info("配置文件初始化错误！为了数据安全！服务器无法启动！" + e);
            try {
                Main.plugin.getLogger().info("傻眼儿了吧？谁***叫你乱改配置文件的？改坏了吧？这下没得玩儿了吧？**玩意儿！");
                Thread.sleep(999999999);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 尝试获取玩家的家的坐标
     * @param uuid
     * @return
     */
    public Location getPlayerHomeLocation(String uuid) {
        JSONObject jsonObject = null;
        try {
            jsonObject = IOTools.getJSONObject(playerHomeInfoPATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject Object = (JSONObject) jsonObject.get(uuid);
        if (Object != null) {
            String world_str = Object.get("world").toString();
            int xx = Integer.parseInt(Object.get("xx").toString());
            int yy = Integer.parseInt(Object.get("yy").toString());
            int zz = Integer.parseInt(Object.get("zz").toString());
            float yaw = Float.parseFloat(Object.get("yaw").toString());
            float pitch = Float.parseFloat(Object.get("pitch").toString());
            World world = Bukkit.getWorld(world_str);
            return new Location(world, xx, yy, zz, yaw, pitch);
        } else {
            World world = Bukkit.getWorld("world");
            String SkyLoc = IsLand.dateAdmin.getDefaultSkyLoc(uuid);
            Location location = new Location(world, Helper.getxxCentered(IsLand.getSkyX(SkyLoc)), 64, Helper.getyyCentered(IsLand.getSkyY(SkyLoc)));
            return location;
        }
    }

    /**
     * 尝试保存玩家家的坐标
     * @param uuid
     * @param location
     * @throws IOException
     */
    public void savePlayerHomeLocation(String uuid,Location location) throws IOException {
        JSONObject jsonObject = null;
        try {
            jsonObject= (JSONObject) new JSONParser().parse("{}");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        jsonObject.put("world",location.getWorld().getName());
        jsonObject.put("xx",location.getBlockX());
        jsonObject.put("yy",location.getBlockY());
        jsonObject.put("zz",location.getBlockZ());
        jsonObject.put("yaw",location.getYaw());
        jsonObject.put("pitch",location.getPitch());
        JSONObject object=new JSONObject();
        object.put(uuid,jsonObject);
        IOTools.writeJsonFile(object,playerHomeInfoPATH);
    }

    /**
     * 判断一个区块是否允许爆炸
     *
     * @param CLoc
     * @return
     */
    public boolean getCanExplosion(String CLoc) {
        JSONObject jsonObject = null;
        try {
            jsonObject = IOTools.getJSONObject(unAntiExplosion);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String str = (String) jsonObject.get(CLoc);
        if (str == null) {
            return false;
        }
        return str.equalsIgnoreCase("on");
    }

    /**
     * 设置一个区块是否可以爆炸 true：可以，false：不可以
     *
     * @param bool
     * @param CLoc
     */
    public void setCanExplosion(boolean bool, String CLoc) {
        JSONObject jsonObject = null;
        try {
            jsonObject = IOTools.getJSONObject(unAntiExplosion);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String str;
        if (bool) {
            str = "on";
        } else {
            str = "off";
        }
        jsonObject.put(CLoc, str);
        try {
            IOTools.writeJsonFile(jsonObject, unAntiExplosion);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得uuid作为所有者拥有的所有岛屿
     *
     * @param uuid
     * @return
     */
    public ArrayList<String> getAllOwnerSkyLoc(String uuid) throws IOException {
        ArrayList<String> arrayList = new ArrayList<String>();
        ArrayList<String> all = getAllSkyLoc();
        for (String skyLoc : all) {
            for (Object own : getOwnersList(skyLoc)) {
                if (uuid.equalsIgnoreCase((String) own)) {
                    arrayList.add((String) skyLoc);
                }
            }
        }
        return arrayList;
    }

    /**
     * 获得uuid作为成员所拥有的所有的岛屿
     *
     * @param uuid
     * @return
     */
    public ArrayList<String> getAllMembersSkyLoc(String uuid) throws IOException {
        ArrayList<String> arrayList = new ArrayList<String>();
        ArrayList<String> all = getAllSkyLoc();
        for (String skyLoc : all) {
            for (Object mem : getMembersList(skyLoc)) {
                if (uuid.equalsIgnoreCase((String) mem)) {
                    arrayList.add((String) skyLoc);
                }
            }
        }
        return arrayList;
    }

    /**
     * 获得SkyLoc的json对象
     *
     * @param SkyLoc
     * @return
     */
    public JSONObject getJSONObject(String SkyLoc) throws IOException {
        return IOTools.getJSONObject(datePATH + Helper.simplify(SkyLoc) + ".json");
    }

    /**
     * 获得uuid最初获得的那个岛屿
     *
     * @param uuid
     * @return
     */
    public String getDefaultSkyLoc(String uuid) {
        JSONObject jsonObject = getIndexInfos();
        String SkyLoc = (String) jsonObject.get(uuid);
        return SkyLoc;
    }

    /**
     * 获得索引的json对象
     *
     * @return
     */
    public JSONObject getIndexInfos() {
        try {
            return IOTools.getJSONObject(indexInfosPATH);
        } catch (IOException e) {
            Main.plugin.getLogger().warning("getIndexInfos:" + ":" + e);
            return null;
        }
    }

    /**
     * 获得所有的岛屿的岛屿列表
     *
     * @return
     */
    public ArrayList<String> getAllSkyLoc() {
        ArrayList<String> SkyLocs = new ArrayList<String>();
        File file = new File(datePATH);
        File[] fs = file.listFiles();
        for (File f : fs) {
            if (!f.isDirectory())
                SkyLocs.add(f.getName().substring(0, f.getName().lastIndexOf(".")));
        }
        return SkyLocs;
    }

    /**
     * 获得某岛屿所有的所有者
     *
     * @param SkyLoc
     * @return
     */
    public JSONArray getOwnersList(String SkyLoc) throws IOException {
        JSONObject jsonObject = getJSONObject(SkyLoc);
        return (JSONArray) jsonObject.get("Owners");
    }

    /**
     * 获得某岛屿所有的成员列表
     *
     * @param SkyLoc
     * @return
     */
    public JSONArray getMembersList(String SkyLoc) throws IOException {
        JSONObject jsonObject = getJSONObject(SkyLoc);
        return (JSONArray) jsonObject.get("Members");
    }

    /**
     * 获得某岛屿的其他信息的json对象
     *
     * @param SkyLoc
     * @return
     */
    public JSONObject getOthers(String SkyLoc) throws IOException {
        JSONObject jsonObject = getJSONObject(SkyLoc);
        return (JSONObject) jsonObject.get("Others");
    }

    /**
     * 将uuid和某岛作为索引对应起来，使岛成为玩家初始岛屿
     *
     * @param uuid
     * @param SkyLoc
     */
    public void saveDefaultSkyLoc(String uuid, String SkyLoc) {
        JSONObject jsonObject = getIndexInfos();
        jsonObject.put(uuid, SkyLoc);
        try {
            IOTools.writeJsonFile(jsonObject, indexInfosPATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存某岛屿的成员配置文件
     *
     * @param jsonObject
     * @param SkyLoc
     */
    public void saveJSONObject(JSONObject jsonObject, String SkyLoc) {
        try {
            IOTools.writeJsonFile(jsonObject, datePATH + Helper.simplify(SkyLoc) + ".json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存某岛屿的所有者列表
     *
     * @param jsonArray
     * @param SkyLoc
     */
    public void saveOwnerslist(JSONArray jsonArray, String SkyLoc) throws IOException {
        JSONObject jso = null;
        try {
            jso = (JSONObject) new JSONParser().parse("{}");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        IOTools.removeDuplicate(getMembersList(SkyLoc));
        jso.put("Owners", jsonArray);
        jso.put("Members", getMembersList(SkyLoc));
        jso.put("Others", getOthers(SkyLoc));
        saveJSONObject(jso, SkyLoc);
    }

    /**
     * 保存某岛屿的成员列表
     *
     * @param jsonArray
     * @param SkyLoc
     */
    public void saveMemberslist(JSONArray jsonArray, String SkyLoc) throws IOException {
        JSONObject jso = null;
        try {
            jso = (JSONObject) new JSONParser().parse("{}");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        IOTools.removeDuplicate(getOwnersList(SkyLoc));
        jso.put("Owners", getOwnersList(SkyLoc));
        jso.put("Members", jsonArray);
        jso.put("Others", getOthers(SkyLoc));
        saveJSONObject(jso, SkyLoc);
    }

    /**
     * 保存某岛屿的其他信息列表
     *
     * @param jsonObject
     * @param SkyLoc
     */
    public void saveOthers(JSONObject jsonObject, String SkyLoc) throws IOException {
        JSONObject jso = null;
        try {
            jso = (JSONObject) new JSONParser().parse("{}");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        IOTools.removeDuplicate(getOwnersList(SkyLoc));
        IOTools.removeDuplicate(getMembersList(SkyLoc));
        jso.put("Owners", getOwnersList(SkyLoc));
        jso.put("Members", getMembersList(SkyLoc));
        jso.put("Others", jsonObject);
        saveJSONObject(jso, SkyLoc);
    }


    public boolean isExist(String SkyLoc) {
        for (String loc : IsLand.dateAdmin.getAllSkyLoc()) {
            if (loc.equalsIgnoreCase(SkyLoc)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 初始化JSON文件，确保能用
     *
     * @param configpath
     */
    public static JSONObject initJson(String configpath, String initStr) throws IOException {
        int temp = 0;
        System.out.println("正在加载json文件：" + configpath);
        while (!new File(configpath).exists()) {
            //尝试试探json文件
            try {
                System.out.println("文件不存在，尝试生成。" + configpath);
                new File(new File(configpath).getParent()).mkdirs();
                new File(configpath).createNewFile();
                IOTools.writeTextFile(initStr, "UTF-8", configpath);
                IOTools.zhengliJsonFile(configpath);
            } catch (IOException | ParseException e) {
                System.out.println("尝试生成。" + temp);
                e.printStackTrace();
            }
            temp++;
        }
        return IOTools.getJSONObject(configpath);
    }
}
