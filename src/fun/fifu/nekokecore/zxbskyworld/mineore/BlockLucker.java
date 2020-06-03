package fun.fifu.nekokecore.zxbskyworld.mineore;

import org.bukkit.Material;

public class BlockLucker {
    public static Material getBlock(Material m, int b) {

        int shuakuang = (int) (Math.random() * 100);
        int chufalv = 45;
        chufalv += b;

        if (shuakuang <= chufalv) {

            int luck = (int) (Math.random() * 100);
            if (chufalv > 80 && luck > 80) {
                luck = (int) (Math.random() * 80);
            }
            // System.out.println("shuakuang=true,luck=" + luck);

            if (luck > 20 && luck < (35)) {// 煤炭
                return Material.COAL_ORE;
            } else {
                if (luck >= 35 && luck < (45)) {// 红石
                    return Material.REDSTONE_ORE;
                } else {
                    if (luck >= 45 && luck < 48) {// 铁
                        return Material.IRON_ORE;
                    } else {
                        if (luck >= 48 && luck < 50) {// 金
                            return Material.GOLD_ORE;
                        } else {
                            if (luck == 50) {// 钻石
                                return Material.DIAMOND_ORE;
                            } else {
                                if (luck >= 51 && luck < 53) {// 青金石
                                    return Material.LAPIS_ORE;
                                } else {
                                    if (luck >= 53 && luck < 57) {// 绿宝石
                                        return Material.EMERALD_ORE;
                                    }
                                }
                            }
                        }
                    }
                }

            }

        } else {
            // System.out.println("shuakuang=false");
            return m;
        }
        return m;
    }

}
