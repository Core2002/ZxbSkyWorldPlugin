package fun.fifu.nekokecore.zxbskyworld.command;

import fun.fifu.nekokecore.zxbskyworld.utils.Helper;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class BiomeDispose implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if ("Biome".equalsIgnoreCase(command.getName())) {
            if (!(commandSender instanceof Player)) {
                commandSender.sendMessage("你必须是一名玩家!");
                return true;
            }
            Player player = (Player) commandSender;
            World world = player.getWorld();
            if (strings == null || strings.length == 0) {
                Biome[] biomes = Biome.values();
                for (int i = 0; i < biomes.length; i++) {
                    player.sendMessage("可用" + i + "：" + biomes[i].name());
                }
                Location location = player.getLocation();
                int tempy = location.getBlockY() - 1;
                Block block = world.getBlockAt(new Location(world, location.getBlockX(), tempy, location.getBlockZ()));
                player.sendMessage("你脚下方块的生物群系是：" + block.getBiome().name());
                player.sendMessage("使用/biome <biome> 来修改当前区块生物群系");
                player.sendMessage("按F3+G查看区块范围");
                return true;
            }
            String bio;
            Biome biome;
            try {
                bio = strings[0];
                biome = Biome.valueOf(bio);
            } catch (Exception e) {
                player.sendMessage("输入有误!");
                return false;
            }

            if (Helper.havePermission(player)) {
                Location location = player.getLocation();
                Chunk chunk = location.getChunk();
                player.sendMessage("开始任务！把区块" + chunk.getX() + "," + chunk.getZ() + "的生物群系换成" + biome.name());
                work(chunk, biome);
                player.sendMessage("搞完了，已经把区块" + chunk.getX() + "," + chunk.getZ() + "的生物群系换成了" + biome.name());
            } else {
                commandSender.sendMessage("你没权限");
            }
            return true;
        }
        return false;
    }

    public void work(Chunk chunk, Biome biome) {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 256; j++) {
                for (int k = 0; k < 16; k++) {
                    chunk.getBlock(i, j, k).setBiome(biome);
                }
            }
        }
    }
}
