package com.division.bardamageindicator.listener;

import com.division.bardamageindicator.data.BarManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    private final BarManager manager;

    public PlayerQuitListener(BarManager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        manager.removeBossBar(event.getPlayer().getUniqueId()); //플레이어 퇴장시 보스바 삭제
    }
}
