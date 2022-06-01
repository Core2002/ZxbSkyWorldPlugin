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
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DateAdmin {
    static final String datePATH = "./plugins/ZxbSkyWorld/date/";
    static final String utilConfigPATH = "./plugins/ZxbSkyWorld/util_config.json";
    static final String unAntiExplosion = "./plugins/ZxbSkyWorld/unAntiExplosion.json";
    static final String indexInfosPATH = "./plugins/ZxbSkyWorld/index.json";
    static final String playerHomeInfoPATH = "./plugins/ZxbSkyWorld/playerHomeInfoPATH.json";
    static final String playerNamePATH = "./plugins/ZxbSkyWorld/playerName.json";
    static final String playerIPPATH = "./plugins/ZxbSkyWorld/playerIP.json";
    public static JSONObject util_jsonObject = null;
    public static String spawnSkyLoc = "(0,0)";
    public static String defaultJsonStr = "{\n" +
            "\"Owners\":[],\n" +
            "\"Members\":[],\n" +
            "\"Others\":{}\n" +
            "}";

    static final String baseConfigPATH = "./plugins/ZxbSkyWorld/base_config.json";
    public static JSONObject base_jsonObject = null;
    //数据缓冲机制
    public static HashMap<String, JSONArray> OwnerMap = new HashMap<>();
    public static HashMap<String, JSONArray> MemberMap = new HashMap<>();
    public static HashMap<String, JSONObject> OthersMap = new HashMap<>();

    public DateAdmin() {
        try {
            String base_initStr = "{\n" +
                    "\"base_sky_world\":\"world\",\n" +
                    "\"base_super_op_uuid\":\"3e79580d-cfdb-4b80-999c-99bc2740d194\",\n" +
                    "\"base_side\":\"1024\",\n" +
                    "\"base_max_skyLoc\":\"29296\",\n" +
                    "\"base_x1\":\"508\",\n" +
                    "\"base_y1\":\"60\",\n" +
                    "\"base_z1\":\"510\",\n" +
                    "\"base_x2\":\"515\",\n" +
                    "\"base_y2\":\"69\",\n" +
                    "\"base_z2\":\"516\",\n" +
                    "\"base_xx\":\"-3\",\n" +
                    "\"base_yy\":\"-4\",\n" +
                    "\"base_zz\":\"-1\",\n" +
                    "\"base_build_sky_command\":\"clone ${base_x1} ${base_y1} ${base_z1} ${base_x2} ${base_y2} ${base_z2} ${xxx} ${yyy} ${zzz}\",\n" +
                    "\"注意\":\"此配置文件为核心配置文件，不懂别乱改，改坏了是你自己的事\",\n" +
                    "\"注意1\":\"base_super_op_uuid项为腐竹uuid项，具有一定的跨权能力(仅在空岛世界内有效),默认为插件作者\"\n" +
                    "}";
            base_jsonObject = initJson(baseConfigPATH, base_initStr);
            String util_initStr = "{\n" +
                    "\"spawn_world\":\"world\",\n" +
                    "\"spawn_xx\":\"359\",\n" +
                    "\"spawn_yy\":\"109\",\n" +
                    "\"spawn_zz\":\"295\",\n" +
                    "\"spawn_yaw\":\"180\",\n" +
                    "\"spawn_pitch\":\"0\"\n" +
                    "}";
            util_jsonObject = initJson(utilConfigPATH, util_initStr);
            spawnSkyLoc = Helper.toSkyLoc(Integer.parseInt(Objects.requireNonNull(util_jsonObject).get("spawn_xx").toString()), Integer.parseInt(util_jsonObject.get("spawn_yy").toString()));
            spawnSkyLoc = Helper.simplify(spawnSkyLoc);
            initJson(indexInfosPATH, "{}");
            initJson(unAntiExplosion, "{}");
            initJson(datePATH + spawnSkyLoc + ".json", defaultJsonStr);
            initJson(playerHomeInfoPATH, "{}");
            initJson(playerNamePATH, "{}");
            initJson(playerIPPATH, "{}");

            new Thread(() -> {
                Main.plugin.getLogger().info("数据缓冲模块清理器已开始工作");
                while (true) {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    OwnerMap.clear();
                    MemberMap.clear();
                    OthersMap.clear();
                }

            }).start();
        } catch (Exception e) {
            Main.plugin.getLogger().info("配置文件初始化错误！为了数据安全！服务器无法启动！" + e);
            try {
                Thread.sleep(5000);
                Bukkit.getServer().reload();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    public int getBase_x1() {
        return Integer.parseInt(base_jsonObject.get("base_x1").toString());
    }

    public int getBase_y1() {
        return Integer.parseInt(base_jsonObject.get("base_y1").toString());
    }

    public int getBase_z1() {
        return Integer.parseInt(base_jsonObject.get("base_z1").toString());
    }

    public int getBase_x2() {
        return Integer.parseInt(base_jsonObject.get("base_x2").toString());
    }

    public int getBase_y2() {
        return Integer.parseInt(base_jsonObject.get("base_y2").toString());
    }

    public int getBase_z2() {
        return Integer.parseInt(base_jsonObject.get("base_z2").toString());
    }

    public int getBase_xx() {
        return Integer.parseInt(base_jsonObject.get("base_xx").toString());
    }

    public int getBase_yy() {
        return Integer.parseInt(base_jsonObject.get("base_yy").toString());
    }

    public int getBase_zz() {
        return Integer.parseInt(base_jsonObject.get("base_zz").toString());
    }

    /**
     * 获取空岛世界的名称
     *
     * @return
     */
    public String getBase_sky_world() {
        return base_jsonObject.get("base_sky_world").toString();
    }

    /**
     * 获取超级辅助的uuid
     *
     * @return
     */
    public String getBase_super_op_uuid() {
        return base_jsonObject.get("base_super_op_uuid").toString();
    }

    /**
     * 获取一个岛的最大边长
     *
     * @return
     */
    public int getBase_side() {
        return Integer.parseInt(base_jsonObject.get("base_side").toString());
    }

    /**
     * 获取一个岛最大可以容纳根号下x个岛屿
     *
     * @return
     */
    public int getBase_max_skyLoc() {
        return Integer.parseInt(base_jsonObject.get("base_max_skyLoc").toString());
    }

    /**
     * 获取拷贝空岛所需要使用的命令
     *
     * @return
     */
    public String getBase_build_sky_command() {
        return base_jsonObject.get("base_build_sky_command").toString();
    }

    /**
     * 尝试获取uuid的名字
     *
     * @param uuid
     * @return
     */
    public String getUuidName(String uuid) {
        JSONObject jsonObject = null;
        try {
            jsonObject = IOTools.getJSONObject(playerNamePATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String name = (String) jsonObject.get(uuid);
        if (name != null) {
            return name;
        } else {
            return uuid + "(UUID)";
        }
    }

    /**
     * 存储玩家名字
     *
     * @param player
     * @throws IOException
     */
    public void savePlayerName(Player player) throws IOException {
        String uuid = player.getUniqueId().toString();
        String name = player.getName();
        JSONObject object = IOTools.getJSONObject(playerNamePATH);
        object.put(uuid, name);
        IOTools.writeJsonFile(object, playerNamePATH);
    }

    /**
     * 保存玩家IP
     *
     * @param player
     * @throws IOException
     */
    public void savePlayerIP(Player player) throws IOException {
        String uuid = player.getUniqueId().toString();
        String ip = player.getAddress().getHostName();
        JSONObject object = IOTools.getJSONObject(playerIPPATH);
        Set<String> ipSet = Stream.of(ip).collect(Collectors.toSet());
        List<String> temp = (List) object.get(uuid);
        if (temp != null)
            ipSet.addAll(temp);
        object.put(uuid, ipSet);
        IOTools.writeJsonFile(object, playerIPPATH);
    }

    /**
     * 尝试获取玩家的家的坐标
     *
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
            World world = Bukkit.getWorld(getBase_sky_world());
            String SkyLoc = IsLand.dateAdmin.getDefaultSkyLoc(uuid);
            Location location = new Location(world, Helper.getRRCentered(IsLand.getSkyX(SkyLoc)), 64, Helper.getRRCentered(IsLand.getSkyY(SkyLoc)));
            return location;
        }
    }

    /**
     * 尝试保存玩家家的坐标
     *
     * @param uuid
     * @param location
     * @throws IOException
     */
    public void savePlayerHomeLocation(String uuid, Location location) throws IOException {
        JSONObject object = IOTools.getJSONObject(playerHomeInfoPATH);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("world", location.getWorld().getName());
        jsonObject.put("xx", location.getBlockX());
        jsonObject.put("yy", location.getBlockY());
        jsonObject.put("zz", location.getBlockZ());
        jsonObject.put("yaw", location.getYaw());
        jsonObject.put("pitch", location.getPitch());
        object.put(uuid, jsonObject);
        IOTools.writeJsonFile(object, playerHomeInfoPATH);
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
                    arrayList.add(skyLoc);
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
                    arrayList.add(skyLoc);
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
        JSONArray ja;
        ja = OwnerMap.get(SkyLoc);
        if (ja == null) {
            JSONObject jsonObject = getJSONObject(SkyLoc);
            OwnerMap.put(SkyLoc, (JSONArray) jsonObject.get("Owners"));
            return (JSONArray) jsonObject.get("Owners");
        } else {
            return ja;
        }
    }

    /**
     * 获得某岛屿所有的成员列表
     *
     * @param SkyLoc
     * @return
     */
    public JSONArray getMembersList(String SkyLoc) throws IOException {
        JSONArray ja;
        ja = MemberMap.get(SkyLoc);
        if (ja == null) {
            JSONObject jsonObject = getJSONObject(SkyLoc);
            MemberMap.put(SkyLoc, (JSONArray) jsonObject.get("Members"));
            return (JSONArray) jsonObject.get("Members");
        } else {
            return ja;
        }

    }

    /**
     * 获得某岛屿的其他信息的json对象
     *
     * @param SkyLoc
     * @return
     */
    public JSONObject getOthers(String SkyLoc) throws IOException {
        JSONObject jo;
        jo = OthersMap.get(SkyLoc);
        if (jo == null) {
            JSONObject jsonObject = getJSONObject(SkyLoc);
            OthersMap.put(SkyLoc, (JSONObject) jsonObject.get("Others"));
            return (JSONObject) jsonObject.get("Others");
        } else {
            return jo;
        }
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
        if (SkyLoc.equals("REMOVE")) {
            jsonObject.remove(uuid);
        }
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
        JSONObject jso;
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
