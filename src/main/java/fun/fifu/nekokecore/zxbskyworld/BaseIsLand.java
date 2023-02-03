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
