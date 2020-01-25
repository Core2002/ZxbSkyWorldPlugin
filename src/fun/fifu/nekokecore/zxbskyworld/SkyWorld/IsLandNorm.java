package fun.fifu.nekokecore.zxbskyworld.SkyWorld;

import fun.fifu.nekokecore.zxbskyworld.Main;
import fun.fifu.nekokecore.zxbskyworld.utils.IOTools;
import org.json.simple.JSONArray;

/**
 * @author NekokeCore
 */
public abstract class IsLandNorm {
    /**
     * @return 岛的坐标的字符串
     */
    public abstract String getSkyLoc();

    public static final int SIDE = 1024;
    public static final int MAXSKYLOC = 29296;
    public static String UUID = "Error UUID";

    public JSONArray jsonArray;

    public static int getxxForm(int SkyX) {
        return SIDE * SkyX;
    }

    public static int getxxEnd(int SkyX) {
        return (SIDE * (SkyX + 1)) - 1;
    }

    public static int getyyForm(int SkyY) {
        return SIDE * SkyY;
    }

    public static int getyyEnd(int SkyY) {
        return (SIDE * (SkyY + 1)) - 1;
    }

    public int getxxCentered() {
        return (getxxEnd(SkyX) - getxxForm(SkyX)) / 2 + getxxForm(SkyX);
    }

    public int getyyCentered() {
        return (getyyEnd(SkyY) - getyyForm(SkyY)) / 2 + getyyForm(SkyY);
    }

    /**
     * @param Loc 把SkyLoc坐标添加岛本UUID名下
     */
    public abstract void addSkyWorld(String Loc);

    public abstract String getMainIsland();

    public boolean in(int tmp, int from, int end) {
        return tmp >= from && tmp <= end;
    }

    public boolean inSkyWorld(int xx, int zz) {
        return in(xx, getxxForm(SkyX), getxxEnd(SkyX)) && in(zz, getyyForm(SkyY), getyyEnd(SkyY));
    }

    public void trim() {
        //去重、添加、保存
        IOTools.removeDuplicate(jsonArray);
        Main.jsonObject.put(UUID, jsonArray);
        IOTools.writeJsonFile(Main.jsonObject, Main.CONFIGPATH);
    }

    int SkyX;
    int SkyY;


}
