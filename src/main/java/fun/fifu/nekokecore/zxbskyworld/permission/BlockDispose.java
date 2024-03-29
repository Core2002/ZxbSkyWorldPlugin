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
import fun.fifu.nekokecore.zxbskyworld.utils.Helper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import static org.bukkit.event.block.Action.*;

public class BlockDispose implements Listener {
    String str = "你没权限";

    /**
     * 当一个方块被玩家破坏的时候，调用本事件.
     *
     * @param event
     */
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (!Helper.havePermission(event.getPlayer())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(str);
        }
    }

    /**
     * 当一个方块被火烧掉的时候触发此事件.
     *
     * @param event
     */
    @EventHandler
    public void onBlockBurn(BlockBurnEvent event) {
        if (event.getBlock().getLocation().getWorld().getName().equals(IsLand.dynamicEternalMap.base_sky_world)) {
            event.setCancelled(true);
        }
    }

    /**
     * 玩家试图交互
     *
     * @param event
     */
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == LEFT_CLICK_AIR || event.getAction() == RIGHT_CLICK_AIR || event.getAction() == PHYSICAL) {
            return;
        }
        Player player = event.getPlayer();
        if (!Helper.havePermission(player)) {
            event.getPlayer().sendMessage(str);
            event.setCancelled(true);
        }
    }

    /**
     * 玩家用完一只桶后触发此事件.
     *
     * @param event
     */
    @EventHandler
    public void onBucketEmpty(PlayerBucketEmptyEvent event) {
        if (!Helper.havePermission(event.getPlayer())) {
            event.getPlayer().sendMessage("你没权限");
            event.setCancelled(true);
        }

    }

    /**
     * 水桶装满水事件.
     *
     * @param event
     */
    @EventHandler
    public void onBucketFill(PlayerBucketFillEvent event) {
        if (!Helper.havePermission(event.getPlayer())) {
            event.getPlayer().sendMessage("你没权限");
            event.setCancelled(true);
        }
    }

    /**
     * 当树叶消失时触发此事件.
     *
     * @param event
     */
    @EventHandler
    public void onLeavesDecay(LeavesDecayEvent event) {
        if (!event.getBlock().getWorld().getName().equalsIgnoreCase(IsLand.dynamicEternalMap.base_sky_world))
            return;
        int xx = (int) event.getBlock().getLocation().getX();
        int zz = (int) event.getBlock().getLocation().getZ();
        if (Helper.inSpawn(xx, zz)) {
            event.setCancelled(true);
        }

    }

    /**
     * 当一个方块被点燃时触发.
     *
     * @param event
     */
    @EventHandler
    public void onBlockIgnite(BlockIgniteEvent event) {
        if (!event.getBlock().getWorld().getName().equalsIgnoreCase(IsLand.dynamicEternalMap.base_sky_world))
            return;
        if (event.getBlock().getLocation().getWorld().getName().equals(IsLand.dynamicEternalMap.base_sky_world) && event.getCause() != BlockIgniteEvent.IgniteCause.FLINT_AND_STEEL) {
            event.setCancelled(true);
            return;
        }
        if (event.getPlayer() != null && event.getPlayer().isOp()) {
            return;
        }
        int xx = (int) event.getBlock().getLocation().getX();
        int zz = (int) event.getBlock().getLocation().getZ();
        if (Helper.inSpawn(xx, zz)) {
            event.setCancelled(true);
        }
    }

}
