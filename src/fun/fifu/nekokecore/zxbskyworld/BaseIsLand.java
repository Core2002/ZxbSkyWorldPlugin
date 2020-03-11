package fun.fifu.nekokecore.zxbskyworld;

/**
 * @author NekokeCore
 */
public abstract class BaseIsLand {
    int SkyX;
    int SkyY;

    public static final int SIDE = 1024;
    public static final int MAXSKYLOC = 29296;

    public static int getrrForm(int SkyX) {
        return SIDE * SkyX;
    }

    public static int getrrEnd(int SkyX) {
        return (SIDE * (SkyX + 1)) - 1;
    }

    public int getrrCentered() {
        return (getrrEnd(SkyX) - getrrForm(SkyX)) / 2 + getrrForm(SkyX);
    }

    public boolean in(int tmp, int from, int end) {
        return tmp >= from && tmp <= end;
    }

    public boolean inSkyWorld(int xx, int zz) {
        return in(xx, getrrForm(SkyX), getrrEnd(SkyX)) && in(zz, getrrForm(SkyY), getrrEnd(SkyY));
    }

}
