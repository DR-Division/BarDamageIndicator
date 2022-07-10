package com.division.bardamageindicator.listener;

import com.division.bardamageindicator.data.BarManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerLastDamageListener implements Listener {

    private final BarManager manager;
    private final JavaPlugin Plugin;

    public PlayerLastDamageListener(BarManager manager, JavaPlugin Plugin) {
        this.manager = manager;
        this.Plugin = Plugin;
    }

    @EventHandler (priority = EventPriority.MONITOR)
    public void onLastDamage(EntityDamageByEntityEvent event) {
        Entity hitter = event.getDamager();
        Entity victim = event.getEntity();
        if (hitter instanceof Player && victim instanceof LivingEntity) {
            Player p = (Player) hitter;
            LivingEntity livingVictim = (LivingEntity) victim;
            double damage = event.getFinalDamage(); //최종데미지를 받는다
            manager.setBossBar(p, livingVictim, damage);
            p.setMetadata("bar_indicate", new FixedMetadataValue(Plugin, System.currentTimeMillis())); //최종 엔티티 타격시간을 메타데이터에 저장.
        }
    }
}
