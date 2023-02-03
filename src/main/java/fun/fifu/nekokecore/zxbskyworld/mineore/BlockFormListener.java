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

package fun.fifu.nekokecore.zxbskyworld.mineore;

import fun.fifu.nekokecore.zxbskyworld.IsLand;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFormEvent;

public class BlockFormListener implements Listener {
	@EventHandler
	public void onBlockCanBuild(BlockFormEvent event) {
		if (!event.getBlock().getLocation().getWorld().getName().equals(IsLand.dynamicEternalMap.base_sky_world))
			return;
		// System.out.println("试图放置的方块的X" + block2.getX() + "y" + block2.getY() + "Z" +
		// block2.getZ());

		// 更改目标方块为矿石
		//System.out.println(event.getNewState().getLocation());
		// b.setType(Material.ICE);
		
		if (event.getNewState().getType().equals(Material.COBBLESTONE)) {//当破坏原石
			event.getNewState().setType(BlockLucker.getBlock(event.getNewState().getType(),40));
		}else {
			if (event.getNewState().getType().equals(Material.STONE)) {//当破坏石头
				if (event.getBlock().getWorld().hasStorm()) {//当下雨天
					event.getNewState().setType(BlockLucker.getBlock(event.getNewState().getType(),30));
				}else {//当非下雨天
					event.getNewState().setType(BlockLucker.getBlock(event.getNewState().getType(),20));
				}
				
			}
		}
		
		// b.setType(Material.STONE);
	}
}
