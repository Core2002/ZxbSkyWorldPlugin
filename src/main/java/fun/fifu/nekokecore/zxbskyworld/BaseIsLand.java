package fun.fifu.nekokecore.zxbskyworld;


/**
 * @author NekokeCore
 */
public abstract class BaseIsLand {

    public static int SIDE = 1024;
    public static int MAXSKYLOC = 29296;

    public static int getrrForm(int SkyX) {
        return SIDE * SkyX;
    }

    public static int getrrEnd(int SkyX) {
        return (SIDE * (SkyX + 1)) - 1;
    }

}
