package fun.fifu.nekokecore.zxbskyworld.utils;

import fun.fifu.nekokecore.zxbskyworld.Main;
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
    static final String indexInfosPATH = "./plugins/ZxbSkyWorld/index.json";
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
            initJson(datePATH + spawnSkyLoc + ".json", defaultJsonStr);
        } catch (Exception e) {
            Main.plugin.getLogger().info("配置文件加载错误！为了数据安全！服务器无法启动！" + e);
            try {
                Main.plugin.getLogger().info("傻眼儿了吧？谁***叫你乱改配置文件的？改坏了吧？这下没得玩儿了吧？**玩意儿！");
                Thread.sleep(999999999);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    //查询
    public ArrayList<String> getAllOwnerSkyLoc(String uuid) {
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

    public ArrayList<String> getAllMembersSkyLoc(String uuid) {
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

    public JSONObject getJSONObject(String SkyLoc) {
        try {
            return IOTools.getJSONObject(datePATH + Helper.simplify(SkyLoc) + ".json");
        } catch (ParseException e) {
            Main.plugin.getLogger().warning("getJSONObject:" + SkyLoc + ":" + e);
            return null;
        }
    }

    public String getDefaultSkyLoc(String uuid) {
        JSONObject jsonObject = getIndexInfos();
        String SkyLoc = (String) jsonObject.get(uuid);
        return SkyLoc;
    }

    public JSONObject getIndexInfos() {
        try {
            return IOTools.getJSONObject(indexInfosPATH);
        } catch (ParseException e) {
            Main.plugin.getLogger().warning("getIndexInfos:" + ":" + e);
            return null;
        }
    }

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

    public JSONArray getOwnersList(String SkyLoc) {
        JSONObject jsonObject = getJSONObject(SkyLoc);
        return (JSONArray) Objects.requireNonNull(jsonObject).get("Owners");
    }

    public JSONArray getMembersList(String SkyLoc) {
        JSONObject jsonObject = getJSONObject(SkyLoc);
        return (JSONArray) Objects.requireNonNull(jsonObject).get("Members");
    }

    public JSONObject getOthers(String SkyLoc) {
        JSONObject jsonObject = getJSONObject(SkyLoc);
        return (JSONObject) Objects.requireNonNull(jsonObject).get("Others");
    }

    //存储
    public void saveDefaultSkyLoc(String uuid, String SkyLoc) {
        JSONObject jsonObject = getIndexInfos();
        jsonObject.put(uuid, SkyLoc);
        IOTools.writeJsonFile(jsonObject, indexInfosPATH);
    }

    public void saveJSONObject(JSONObject jsonObject, String SkyLoc) {
        IOTools.writeJsonFile(jsonObject, datePATH + Helper.simplify(SkyLoc) + ".json");
    }

    public void saveOwnerslist(JSONArray jsonArray, String SkyLoc) {
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

    public void saveMemberslist(JSONArray jsonArray, String SkyLoc) {
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

    public void saveOthers(JSONObject jsonObject, String SkyLoc) {
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


    /**
     * 初始化JSON文件，确保能用
     *
     * @param configpath
     */
    public static JSONObject initJson(String configpath, String initStr) {
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
            } catch (IOException e) {
                System.out.println("尝试生成。" + temp);
                e.printStackTrace();
            }
            temp++;
        }
        try {
            return IOTools.getJSONObject(configpath);
        } catch (ParseException e) {
            System.out.println("Json文件加载失败，请检查文件格式，然后在尝试。" + configpath);
            e.printStackTrace();
        }
        return null;
    }
}
