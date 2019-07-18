package com.gmail.jpk.stu.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SpecialItem extends ItemStack {

	/**
	 * The Unique Identification (UID) of a SpecialItem is a unique value assigned to each item.
	 * The UIDs for SpecialItems start with 100. (i.e. 100-199 are valid special items)
	 */
	public final int UID;
	
	//The display name of this special item
	private String display_name;
	private Material material;
	
	/**
	 * Creates a SpecialItem with a given Material, Unique ID, Display Name, and lore
	 * @param material the material to represent the SpecialItem
	 * @param uid its unique identification
	 * @param display_name its display name
	 * @param lore any desire lore for the item
	 */
	public SpecialItem(Material material, int uid, String display_name, String... lore) {
		super(material);
		ItemMeta meta = this.getItemMeta();
		List<String> itemLore = new ArrayList<String>();
		for(String l : lore) {
			itemLore.add(l);
		}
		meta.setLore(itemLore);
		meta.setDisplayName(display_name);
		setMaterial(material);
		setItemMeta(meta);
		UID = uid;
		this.display_name = display_name;
	}
	
	public String getDisplayName() {
		return display_name;
	}
	
	public Material getMaterial() {
		return material;
	}
	
	/**
	 * Gets the SpecialItem as an ItemStack. Does not validate inputs and may return null.
	 * @param quantity the quantity of the SpecialItem
	 * @return the ItemStack associated with this SpecialItem
	 */
	public ItemStack getSpecialItemAsItemStack(int quantity) {
		return new ItemStack(material, quantity);
	}
	
	public void setDisplayName(String display_name) {
		this.display_name = display_name;
	}
	
	public void setMaterial(Material material) {
		this.material = material;
	}
}
