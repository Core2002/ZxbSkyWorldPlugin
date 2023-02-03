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

import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Random;

public class SoundPlayer {
    ArrayList<Sound> catList = new ArrayList<Sound>();

    public SoundPlayer() {
        catList.add(Sound.ENTITY_CAT_AMBIENT);
        catList.add(Sound.ENTITY_CAT_BEG_FOR_FOOD);
        catList.add(Sound.ENTITY_CAT_DEATH);
        catList.add(Sound.ENTITY_CAT_EAT);
        catList.add(Sound.ENTITY_CAT_HISS);
        catList.add(Sound.ENTITY_CAT_HURT);
        catList.add(Sound.ENTITY_CAT_PURR);
        catList.add(Sound.ENTITY_CAT_AMBIENT);
        catList.add(Sound.ENTITY_CAT_PURREOW);
        catList.add(Sound.ENTITY_CAT_STRAY_AMBIENT);
    }

    public static Sound extact(ArrayList<Sound> arrayList) {
        Random r = new Random(1);
        int temp = r.nextInt(arrayList.size());
        return arrayList.get(temp);
    }

    public void playCat(Player player) {
        player.playSound(player.getLocation(), extact(catList), 10, 10);
    }
}
