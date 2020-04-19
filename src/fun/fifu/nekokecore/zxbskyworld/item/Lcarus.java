package fun.fifu.nekokecore.zxbskyworld.item;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class Lcarus {

    public static ItemStack build() {
        ItemStack is = new ItemStack(Material.ELYTRA);
        is.addEnchantment(Enchantment.DAMAGE_ALL, 6);
        is.addEnchantment(Enchantment.DEPTH_STRIDER, 6);
        is.addEnchantment(Enchantment.DIG_SPEED, 6);
        is.addEnchantment(Enchantment.LOOT_BONUS_MOBS, 6);
        is.addEnchantment(Enchantment.OXYGEN, 6);
        is.addEnchantment(Enchantment.PROTECTION_FALL, 6);
        is.addEnchantment(Enchantment.PROTECTION_PROJECTILE, 6);
        is.addEnchantment(Enchantment.WATER_WORKER, 6);
        is.addEnchantment(Enchantment.BINDING_CURSE, 6);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName("钉三多的翅膀");
        im.setLore(Arrays.asList("组成翅膀的羽毛来自伊卡洛斯"));
        return is;
    }

}
