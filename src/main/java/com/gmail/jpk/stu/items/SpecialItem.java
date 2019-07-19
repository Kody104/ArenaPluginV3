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
	private final int UID;
	
	//The display name of this special item
	private ItemMeta meta;
	private Material material;
	
	//The default price of this item (a value of -1 means this item can not be bought)
	private int price;
	
	/**
	 * Creates a SpecialItem with a given Material, price, Unique ID, Display Name, and lore. <br/>
	 * @param material the material to represent the SpecialItem
	 * @param price the default price of this material in the shop
	 * @param uid its unique identification
	 * @param display_name its display name
	 * @param lore any desire lore for the item
	 */
	public SpecialItem(Material material, int price, int uid, String display_name, String...lore) {
		super(material);
		
		//Set Item Stack properties
		this.meta = this.getItemMeta();
		List<String> item_lore = new ArrayList<String>();
		
		for (String string : lore) {
			item_lore.add(string);
		}
		
		//Add the price to lore (if applicable)
		if (price > 0) {
			item_lore.add(String.format("Item value: %d Golden Scraps", price));
		}
		
		meta.setLore(item_lore);
		meta.setDisplayName(display_name);
		
		//Set local properties
		this.UID = uid;
		setItemMeta(meta);
		setMaterial(material);
	}
	
	/**
	 * Creates a SpecialItem with a given Material, Unique ID, Display Name, and lore. <br/>
	 * Note: Creating an item like this sets it unpurchasable in shop by default.
	 * @param material the material to represent the SpecialItem
	 * @param uid its unique identification
	 * @param display_name its display name
	 * @param lore any desire lore for the item
	 */
	public SpecialItem(Material material, int uid, String display_name, String... lore) {
		this(material, -1, uid, display_name, lore);
	}
	
	public String getDisplayName() {
		return meta.getDisplayName();
	}
	
	public Material getMaterial() {
		return material;
	}
	
	public int getPrice() {
		return price;
	}
	
	public int getUID() {
		return UID;
	}
	
//	public static ShapedRecipe getGoldenBarRecipe() {
//		ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(GlobalW.getPlugin(), "key"), SpecialItems.GOLDEN_BAR.getItem());
//		return recipe.shape("GGG", "GGG", "GGG").setIngredient('G', Material.GOLD_NUGGET);
//	}
	
	public void setMaterial(Material material) {
		this.material = material;
	}

	public void setPrice(int price) {
		this.price = price;
	}
}
