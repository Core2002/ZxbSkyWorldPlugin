package fun.fifu.nekokecore.zxbskyworld.mineore;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFormEvent;

public class BlockFormListener implements Listener {
	@EventHandler
	public void onBlockCanBuild(BlockFormEvent event) {

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
				
			} else {
				
			}
		}
		
		// b.setType(Material.STONE);
	}
}
