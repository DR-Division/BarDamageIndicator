package com.division.bardamageindicator.data;

import com.division.bardamageindicator.constant.BarConstant;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BarManager {

    private final Map<UUID, BossBar> bossBarMap;

    public BarManager() {
        bossBarMap = new HashMap<>();
    }

    public void setBossBar(Player p, LivingEntity victim, double damage) {
        UUID uuid = p.getUniqueId();
        BossBar bar = getBossBar(uuid);
        String victimName;
        AttributeInstance instance = victim.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        double victimMaxHealth = instance == null ? 20.0 : instance.getBaseValue();
        if (victim instanceof Player)
            victimName = victim.getName();
        else
            victimName = victim.getCustomName() != null ? victim.getCustomName() : victim.getName();
        bar.setTitle("§f" + victimName + " §e" + victim.getHealth() + "§e/" + victimMaxHealth + " §f(§c-" + damage + "§f)");
        if (!bar.getPlayers().contains(p))
            bar.addPlayer(p);
    }

    public void removeBossBar(UUID uuid) {
        if (bossBarMap.containsKey(uuid))
            bossBarMap.get(uuid).removeAll();
        bossBarMap.remove(uuid);
    }

    //없을경우 BossBar를 생성한 뒤 map에 넣고 반환한다.
    public BossBar getBossBar(UUID uuid) {
        BossBar bar;
        if (bossBarMap.containsKey(uuid))
            bar = bossBarMap.get(uuid);
        else
            bar = putBossBar(uuid);
        return bar;
    }

    private BossBar putBossBar(UUID uuid) {
        BossBar bar = Bukkit.createBossBar("", BarColor.RED, BarStyle.SEGMENTED_10);
        bossBarMap.put(uuid, bar);
        return bar;
    }

    //보스바 제거전용 내부클래스
    class BarRemover implements Runnable {
        @Override
        public void run() {
            for (UUID uuid : bossBarMap.keySet()) {
                Player p = Bukkit.getPlayer(uuid);
                if (p == null)
                    removeBossBar(uuid);
                else {
                    MetadataValue value = p.getMetadata(BarConstant.BAR_META_KEY).get(0);
                    if (value != null) {
                        long lastAttackTime = value.asLong();
                        if (System.currentTimeMillis() - lastAttackTime >= BarConstant.BAR_REMOVE_DELAY)
                            bossBarMap.remove(uuid);
                    }
                }
            }
        }
    }
}
