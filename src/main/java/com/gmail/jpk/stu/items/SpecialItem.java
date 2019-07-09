package com.gmail.jpk.stu.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class SpecialItem extends ItemStack {
	
	//The display name of this special item
	private String display_name;
	
	public SpecialItem(Material material, String display_name, String... lore) {
		super(material);
		ItemMeta meta = this.getItemMeta();
		List<String> itemLore = new ArrayList<String>();
		for(String l : lore) {
			itemLore.add(l);
		}
		meta.setLore(itemLore);
		meta.setDisplayName(display_name);
		setItemMeta(meta);
	}
	
	public String getDisplayName() {
		return display_name;
	}
	
	public void setDisplayName(String display_name) {
		this.display_name = display_name;
	}
}
