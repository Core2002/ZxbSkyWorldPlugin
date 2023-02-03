/*
 * Copyright (c) 2020 Core2002
 * ZxbSkyWorldPlugin is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 *          http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 */

package fun.fifu.nekokecore.zxbskyworld.utils;

import fun.fifu.nekokecore.zxbskyworld.IsLand;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class ShardProcessing implements Runnable {

    public ShardProcessing(String SkyLoc) {
        World world = Bukkit.getWorld("world");
        int SkyX = IsLand.getSkyX(SkyLoc);
        int SkyY = IsLand.getSkyY(SkyLoc);
        int xxfrom = IsLand.getRRForm(SkyX);
        int zzfrom = IsLand.getRRForm(SkyY);
        int xxend = IsLand.getRREnd(SkyX);
        int zzend = IsLand.getRREnd(SkyY);

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
