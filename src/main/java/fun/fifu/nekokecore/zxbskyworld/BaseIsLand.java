package fun.fifu.nekokecore.zxbskyworld;


/**
 * @author NekokeCore
 */
public abstract class BaseIsLand {

    public static int SIDE = 1024;
    public static int MAX_SKY_LOC = 29296;

    public static int getRRForm(int SkyR) {
        return SIDE * SkyR;
    }

    public static int getRREnd(int SkyR) {
        return (SIDE * (SkyR + 1)) - 1;
    }

}
