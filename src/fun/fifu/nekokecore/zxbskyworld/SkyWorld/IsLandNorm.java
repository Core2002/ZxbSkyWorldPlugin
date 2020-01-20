package fun.fifu.nekokecore.zxbskyworld.SkyWorld;

import fun.fifu.nekokecore.zxbskyworld.Main;
import fun.fifu.nekokecore.zxbskyworld.utils.IOTools;
import org.json.simple.JSONArray;

import static fun.fifu.nekokecore.zxbskyworld.Main.jsonObject;

/**
 * @author NekokeCore
 */
public abstract class IsLandNorm {
    /**
     * @return 岛的坐标的字符串
     */
    public abstract String getSkyLoc();

    public static final int SIDE = 1024;

    public JSONArray jsonArray;

    public int getxxForm(int SkyX) {
        return SIDE * SkyX;
    }

    public int getxxEnd(int SkyX) {
        return SIDE * (SkyX + 1) - 1;
    }

    public int getyyForm(int SkyY) {
        return SIDE * SkyY;
    }

    public int getyyEnd(int SkyY) {
        return SIDE * (SkyY + 1) - 1;
    }

    /**
     * @param Loc 把SkyLoc坐标添加岛本UUID名下
     */
    public abstract void addSkyWorld(String Loc);

    public abstract String getMainIsland();

    public boolean in(int tmp, int from, int end) {
        return tmp >= from && tmp <= end;
    }

    public boolean inSkyWorld(int xx, int yy) {
        return in(xx, getxxForm(SkyX), getxxEnd(SkyX)) && in(yy, getyyForm(SkyY), getyyEnd(SkyY));
    }

    public void trim(String UUID) {
        //去重、添加、保存
        IOTools.removeDuplicate(jsonArray);
        jsonObject.put(UUID, jsonArray);
        IOTools.writeJsonFile(jsonObject, Main.CONFIGPATH);
    }

    int SkyX;
    int SkyY;


}
