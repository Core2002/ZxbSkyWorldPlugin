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

package fun.fifu.nekokecore.zxbskyworld.permission;

import fun.fifu.nekokecore.zxbskyworld.IsLand;

public class DynamicEternalMap {
    public boolean opCanPermiss = false;
    public final String zxb = IsLand.dateAdmin.getBase_super_op_uuid();
    public final String base_sky_world = IsLand.dateAdmin.getBase_sky_world();
    public final int base_side = IsLand.dateAdmin.getBase_side();
    public final int base_max_skyLoc = IsLand.dateAdmin.getBase_max_skyLoc();
    public final String base_build_sky_command = IsLand.dateAdmin.getBase_build_sky_command();

    public final int base_x1 = IsLand.dateAdmin.getBase_x1();
    public final int base_y1 = IsLand.dateAdmin.getBase_y1();
    public final int base_z1 = IsLand.dateAdmin.getBase_z1();
    public final int base_x2 = IsLand.dateAdmin.getBase_x2();
    public final int base_y2 = IsLand.dateAdmin.getBase_y2();
    public final int base_z2 = IsLand.dateAdmin.getBase_z2();
    public final int base_xx = IsLand.dateAdmin.getBase_xx();
    public final int base_yy = IsLand.dateAdmin.getBase_yy();
    public final int base_zz = IsLand.dateAdmin.getBase_zz();

}
