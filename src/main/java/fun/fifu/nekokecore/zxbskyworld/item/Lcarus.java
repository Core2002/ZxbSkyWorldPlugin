package fun.fifu.nekokecore.zxbskyworld.item;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class Lcarus {

    public static ItemStack build() {
        ItemStack is = new ItemStack(Material.ELYTRA);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName("钉三多的翅膀");
        im.setLore(Collections.singletonList("组成翅膀的羽毛来自伊卡洛斯"));
        im.addEnchant(Enchantment.DEPTH_STRIDER, 10, true);
        im.addEnchant(Enchantment.OXYGEN, 10, true);
        im.addEnchant(Enchantment.PROTECTION_FALL, 10, true);
        im.addEnchant(Enchantment.PROTECTION_PROJECTILE, 10, true);
        im.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        im.addEnchant(Enchantment.PROTECTION_FIRE, 10, true);
        im.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 10, true);
        im.setUnbreakable(true);
        is.setItemMeta(im);
        return is;
    }

}
