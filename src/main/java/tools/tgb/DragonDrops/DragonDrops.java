package tools.tgb.DragonDrops;

import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class DragonDrops extends JavaPlugin implements Listener {

    private static File configFile;
    private static YamlConfiguration config;

    @Override
    public void onEnable() {

        configFile = new File(getDataFolder(), "config.yml");
        config = YamlConfiguration.loadConfiguration(configFile);

        try {
            if(!configFile.exists()) {
                configFile.getParentFile().mkdirs();
                config.set("drop-elytra", true);
                config.set("drop-egg", true);
                config.save(configFile);
            } else {
                config.load(configFile);
            }
        }catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }

        getLogger().info("Dragon Drops Enabled!");
        getServer().getPluginManager().registerEvents(this, this);

    }

    @Override
    public void onDisable() {
        getLogger().info("Dragon Drops Disabled!");
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {

        Entity entity = event.getEntity();

        if (entity.getType() == EntityType.ENDER_DRAGON) {
            List<ItemStack> drops = event.getDrops();
            drops.removeAll(drops);
            ItemStack eggStack = new ItemStack(Material.DRAGON_EGG, 1);
            ItemStack elytraStack = new ItemStack(Material.ELYTRA, 1);

            if (config.getBoolean("drop-elytra")) {
                drops.add(elytraStack);
            }

            if (config.getBoolean("drop-egg")) {
                drops.add(eggStack);
            }
        }
    }
}
