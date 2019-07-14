package com.gmail.jpk.stu.items;

import org.bukkit.Material;

import com.gmail.jpk.stu.abilities.Ability;

public class AbilityItem extends SpecialItem {

	private Ability owner;
	
	public AbilityItem(Material material, int uid, Ability owner, String display_name, String[] lore) {
		super(material, uid, "Ability", owner.getName(), owner.getDescription());
	}

	public Ability getOwner() {
		return owner;
	}

	public void setOwner(Ability owner) {
		this.owner = owner;
	}
}
