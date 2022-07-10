package com.division.bardamageindicator;

import com.division.bardamageindicator.constant.BarConstant;
import com.division.bardamageindicator.data.BarManager;
import com.division.bardamageindicator.listener.PlayerLastDamageListener;
import com.division.bardamageindicator.listener.PlayerQuitListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class BarDamageIndicator extends JavaPlugin {

    private BarManager.BarRemover remover;

    @Override
    public void onEnable() {
        // Plugin startup logic
        BarManager manager = new BarManager();
        getServer().getPluginManager().registerEvents(new PlayerLastDamageListener(manager, this), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(manager), this);
        remover = manager.new BarRemover();
        Bukkit.getScheduler().runTaskTimer(this, remover, 0L, BarConstant.BAR_REMOVER_INTERVAL);
    }

    @Override
    public void onDisable() {
        remover.removeAll();
        // Plugin shutdown logic
    }
}
