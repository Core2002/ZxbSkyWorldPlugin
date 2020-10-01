package fun.fifu.nekokecore.zxbskyworld.utils;

import fun.fifu.nekokecore.zxbskyworld.IsLand;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class ShardProcessing implements Runnable{

    public ShardProcessing (String SkyLoc){
        World world = Bukkit.getWorld("world");
        int SkyX = IsLand.getSkyX(SkyLoc);
        int SkyY = IsLand.getSkyY(SkyLoc);
        int xxfrom = IsLand.getrrForm(SkyX);
        int zzfrom = IsLand.getrrForm(SkyY);
        int xxend = IsLand.getrrEnd(SkyX);
        int zzend = IsLand.getrrEnd(SkyY);

        for (int zz = zzfrom; zz <= zzend; zz++) {
            for (int xx = xxfrom; xx <= xxend; xx++) {


                System.out.println(xx + "_" + zz);

            }
        }


    }

    @Override
    public void run() {

    }
}
