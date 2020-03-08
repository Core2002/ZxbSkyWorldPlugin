package fun.fifu.nekokecore.zxbskyworld;

import fun.fifu.nekokecore.zxbskyworld.Main;
import fun.fifu.nekokecore.zxbskyworld.utils.IOTools;
import org.json.simple.JSONArray;

/**
 * @author NekokeCore
 */
public abstract class BaseIsLand {
    /**
     * @return 岛的坐标的字符串
     */
    public abstract String getSkyLoc();

    public static final int SIDE = 1024;
    public static final int MAXSKYLOC = 29296;
    public String UUID = "Error UUID";

    public JSONArray jsonArray;

    public static int getrrForm(int SkyX) {
        return SIDE * SkyX;
    }

    public static int getrrEnd(int SkyX) {
        return (SIDE * (SkyX + 1)) - 1;
    }

    public int getxxCentered() {
        return (getrrEnd(SkyX) - getrrForm(SkyX)) / 2 + getrrForm(SkyX);
    }

    public int getyyCentered() {
        return (getrrEnd(SkyY) - getrrForm(SkyY)) / 2 + getrrForm(SkyY);
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
        return in(xx, getrrForm(SkyX), getrrEnd(SkyX)) && in(zz, getrrForm(SkyY), getrrEnd(SkyY));
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
