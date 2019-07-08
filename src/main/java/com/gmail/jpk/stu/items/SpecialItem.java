package com.gmail.jpk.stu.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SpecialItem extends ItemStack {
	
	public SpecialItem(Material material, String... lore) {
		super(material);
		ItemMeta meta = this.getItemMeta();
		List<String> itemLore = new ArrayList<String>();
		for(String l : lore) {
			itemLore.add(l);
		}
		meta.setLore(itemLore);
		setItemMeta(meta);
	}
}
