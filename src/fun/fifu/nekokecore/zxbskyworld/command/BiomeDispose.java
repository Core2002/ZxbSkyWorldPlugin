package fun.fifu.nekokecore.zxbskyworld.command;

import fun.fifu.nekokecore.zxbskyworld.IsLand;
import fun.fifu.nekokecore.zxbskyworld.Main;
import fun.fifu.nekokecore.zxbskyworld.utils.Helper;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.logging.Logger;

public class BiomeDispose implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if ("Biome".equalsIgnoreCase(command.getName())) {
            // 判断输入者的类型 为了防止出现 控制台或命令方块 输入的情况
            if (!(commandSender instanceof Player)) {
                commandSender.sendMessage("你必须是一名玩家!");
                // 这里返回true只是因为该输入者不是玩家,并不是输入错指令,所以我们直接返回true即可
                return true;
            }
            if (strings == null || strings.length == 0) {
                commandSender.sendMessage("生物群系表：" + Arrays.toString(Biome.values()));
                return false;
            }
            String bio = strings[0];
            Biome biome = Biome.valueOf(bio);
            Player player = (Player) commandSender;
            World world = player.getWorld();
            if (Helper.havePermission(player)) {
                Location location = player.getLocation();
                String SkyLoc = Helper.toSkyLoc(location.getBlockX(), location.getBlockZ());
                player.sendMessage("开始任务！把" + SkyLoc + "的生物群系换成" + biome.name());
                int SkyX = IsLand.getSkyX(SkyLoc);
                int SkyY = IsLand.getSkyY(SkyLoc);
                int xxfrom = IsLand.getrrForm(SkyX);
                int zzfrom = IsLand.getrrForm(SkyY);
                int xxend = IsLand.getrrEnd(SkyX);
                int zzend = IsLand.getrrEnd(SkyY);
                Logger logger = Main.plugin.getLogger();
                for (int zz = zzfrom; zz <= zzend; zz++) {
                    for (int xx = xxfrom; xx <= xxend; xx++) {
                        if (!world.getChunkAt(xx, zz).isLoaded()) {
                            logger.info("加载区块" + xx + "," + zz + ":" + world.getChunkAt(xx, zz).load(true));
                        }
                        world.setBiome(xx, zz, biome);
                        world.getChunkAt(xx, zz).unload(true);
                    }
                }
                player.sendMessage("搞完了，已经把岛" + SkyLoc + "的生物群系换成了" + biome.name());
            } else {
                commandSender.sendMessage("你没权限");
            }
            return true;
        }
        return false;
    }

}
