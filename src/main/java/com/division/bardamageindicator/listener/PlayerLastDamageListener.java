package com.division.bardamageindicator.listener;

import com.division.bardamageindicator.constant.BarConstant;
import com.division.bardamageindicator.data.BarManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
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
            handleDamage(hitter, victim, event.getFinalDamage());
        }
        else if (hitter instanceof Projectile) {
            Projectile projectile = (Projectile) hitter;
            if (projectile.getShooter() instanceof Player) {
                handleDamage(hitter, victim, event.getFinalDamage());
            }
        }
    }

    private void handleDamage(Entity hitter, Entity victim, double damage) {
        Player p = (Player) hitter;
        LivingEntity livingVictim = (LivingEntity) victim;
        manager.setBossBar(p, livingVictim, damage);
        p.setMetadata(BarConstant.BAR_META_KEY, new FixedMetadataValue(Plugin, System.currentTimeMillis())); //최종 엔티티 타격시간을 메타데이터에 저장.

    }
}
