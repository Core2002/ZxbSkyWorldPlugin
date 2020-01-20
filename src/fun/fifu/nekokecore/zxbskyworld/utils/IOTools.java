package fun.fifu.nekokecore.zxbskyworld.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * @author FiFuServer
 */
public class IOTools {
    /**
     * 把字符数组写入文件
     *
     * @param c
     * @param path
     */
    public static void writeFile(byte[] c, String path) {
        try {
            try (FileOutputStream i = new FileOutputStream(path)) {
                i.write(c, 0, c.length);
                i.flush();
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 把字符串写入文件
     *
     * @param text
     * @param encode
     * @param path
     */
    public static void writeTextFile(String text, String encode, String path) {
        try {
            writeFile(text.getBytes(encode), path);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * .
     * 写入规范的json文件到指定目录
     *
     * @param jsonObject
     * @param path
     */
    public static void writeJsonFile(JSONObject jsonObject, String path) {
        writeTextFile(toPrettyFormat(jsonObject.toString()), "utf8", path);
    }

    /**
     * 把文件都成字符数组
     *
     * @param path
     * @return
     */
    public static byte[] readFile(String path) {
        try {
            try (FileInputStream i = new FileInputStream(path)) {
                byte[] buf = new byte[i.available()];
                i.read(buf, 0, buf.length);
                return buf;
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 从目录使用指定编码读文件
     *
     * @param path
     * @param encode
     * @return
     */
    public static String readTextFile(String path, String encode) {
        try {
            return new String(readFile(path), encode);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 格式化输出JSON字符串
     *
     * @return 格式化后的JSON字符串
     */
    public static String toPrettyFormat(String json) {
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(json).getAsJsonObject();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(jsonObject);
    }

    /**
     * 从文件得到JSONObject
     *
     * @param path
     * @return
     * @throws ParseException
     */
    public static JSONObject getJSONObject(String path) throws ParseException {
        return (JSONObject) (new JSONParser().parse(IOTools.readTextFile(path, "utf8")));
    }

    public static void zhengliJsonFile(String path) {
        try {
            IOTools.writeTextFile(toPrettyFormat(new JSONParser().parse(IOTools.readTextFile(path, "utf8")).toString()), "utf8", path);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param arlList 列表去重
     */
    public static void removeDuplicate(ArrayList arlList) {
        HashSet h = new HashSet(arlList);
        arlList.clear();
        arlList.addAll(h);
    }

}
