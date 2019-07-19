package com.gmail.jpk.stu.items;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.gmail.jpk.stu.arena.GlobalW;
import com.gmail.jpk.stu.tasks.DelayedExplosionTask;

public class BoomStick extends SpecialItem implements UsableItem {

	public BoomStick() {
		super(Material.STICK, SpecialItems.BOOM_STICK.getUID(), SpecialItems.BOOM_STICK.getDisplayName(), "Explodes when left behind.");
	}

	@Override
	public void useItem(Player player) {
		new DelayedExplosionTask(player).runTaskLater(GlobalW.getPlugin(), 60);
		player.playSound(player.getLocation(), Sound.ENTITY_CREEPER_PRIMED, 1f, 1f);
	}
	
}
