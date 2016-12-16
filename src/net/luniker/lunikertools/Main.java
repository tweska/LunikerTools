package net.luniker.lunikertools;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by 2sk on 12-Dec-16.
 */
public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new Turner(), this);
    }

//    @Override
//    public void onDisable() {
//
//    }
}
