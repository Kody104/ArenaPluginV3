package com.gmail.jpk.stu.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.gmail.jpk.stu.Entities.ArenaPlayer;
import com.gmail.jpk.stu.arena.GlobalW;

public class TastySteak extends SpecialItem implements UsableItem {

	private Player user;
	
	public TastySteak() {
		super(Material.COOKED_BEEF, 900, SpecialItems.TASTY_STEAK.getUID(), SpecialItems.TASTY_STEAK.getDisplayName(), "Heals you when you kill an enemy.", "Requires you to eat it.");
	}

	@Override
	public void useItem(Player player) {
		player.sendMessage("I will add a status effect in the future.");
		
		ArenaPlayer arena_player = GlobalW.getArenaPlayer(player);
		
		if (arena_player != null) {
			this.setUser(player);
			//TODO: Add a StatusEffect to the Player after Jeremy's next push.
		}		
	}

	public Player getUser() {
		return user;
	}

	public void setUser(Player user) {
		this.user = user;
	}

}
