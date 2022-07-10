package com.division.bardamageindicator;

import com.division.bardamageindicator.constant.BarConstant;
import com.division.bardamageindicator.data.BarManager;
import com.division.bardamageindicator.listener.PlayerLastDamageListener;
import com.division.bardamageindicator.listener.PlayerQuitListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class BarDamageIndicator extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        BarManager manager = new BarManager();
        getServer().getPluginManager().registerEvents(new PlayerLastDamageListener(manager, this), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(manager), this);
        Bukkit.getScheduler().runTaskTimer(this, manager.new BarRemover(), 0L, BarConstant.BAR_REMOVER_INTERVAL);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
