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

package fun.fifu.nekokecore.zxbskyworld.listener;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ChairStairsListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getClickedBlock() != null) {
            Block block = event.getClickedBlock();
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK && !player.isInsideVehicle() && player.getInventory().getItemInMainHand().getType() == Material.ARROW) {
                //范围
                double range = 1.0;
                if (player.getLocation().distance(block.getLocation()) - 1.0 > range) {
                    return;
                }
                World world = player.getWorld();
                Entity chair = world.spawnEntity(player.getLocation(), EntityType.ARROW);
                chair.teleport(block.getLocation().add(0.5, 0.5, 0.5));
                //player.setAllowFlight(true);
                chair.addPassenger(player);
                player.sendMessage(player.getName() + "小鸟坐");
            }

        }
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();
        if (!player.isInsideVehicle() && player.getInventory().getItemInMainHand().getType() == Material.ARROW) {
            //范围
            double range = 1.0;
            if (player.getLocation().distance(entity.getLocation()) - 1.0 > range) {
                return;
            }
            //player.setAllowFlight(true);
            entity.addPassenger(player);
            player.sendMessage(player.getName() + "小瑶瑶");
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        event.getPlayer().leaveVehicle();
    }
}

