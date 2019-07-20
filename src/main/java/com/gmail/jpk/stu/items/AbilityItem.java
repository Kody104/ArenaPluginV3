package com.gmail.jpk.stu.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.gmail.jpk.stu.Entities.ArenaEntity;
import com.gmail.jpk.stu.abilities.Ability;
import com.gmail.jpk.stu.abilities.StatusEffect;
import com.gmail.jpk.stu.arena.GlobalW;

public class AbilityItem extends ItemStack {

	private Ability owner;
	

	/**
	 * The Unique Identification (UID) of a SpecialItem is a unique value assigned to each item.
	 * The UIDs for SpecialItems start with 100. (i.e. 100-199 are valid special items)
	 */
	private final int UID;
	
	//The display name of this special item
	private ItemMeta meta;
	
	public AbilityItem(int uid, Ability owner) {
		super(Material.STICK);
		
		//Set Item Stack properties
		this.meta = this.getItemMeta();
		List<String> item_lore = new ArrayList<String>();
		
		item_lore.add("Ability");
		item_lore.add(owner.getDescription());
		item_lore.add("Target: " + owner.getTargetType().getTargetType().toString());
		item_lore.add("Range: " + owner.getTargetType().getCastRange() + " person(s)");
		item_lore.add("Splash: " + owner.getTargetType().getAbilityRange() + " person(s)");
		item_lore.add("Damage Type: " + owner.getDamageType());
		
		meta.setDisplayName(owner.getName());
		meta.setLore(item_lore);
		
		//Set local properties
		this.UID = uid;
		setItemMeta(meta);
		this.owner = owner;
	}
	
	public void useAbility(ArenaEntity caster, ArenaEntity... targets) {
		for(ArenaEntity entity : targets) {
			useAbility(caster, entity);
		}
	}
	
	public void useAbility(ArenaEntity caster, ArenaEntity target) {
		// Calculate the damage
		double damage = owner.getBaseDmg();
		damage += owner.getAtkScale() * caster.getAtk();
		damage += owner.getMagScale() * caster.getMag();
		damage += owner.getAmrScale() * caster.getDef();
		damage += owner.getResScale() * caster.getRes();
		damage += owner.getHpScale() * caster.getMaxHp();
		
		target.takeDamage(damage, owner.getDamageType());
		
		// If there is a status effect in this ability
		if(owner.getAllStatusEffects().size() > 0) {
			// For all the status effects
			for(int i = 0; i < owner.getAllStatusEffects().size(); i++) {
				StatusEffect s = owner.getAllStatusEffects().get(i);
				// Calculate the target's tenacity (chance to not get hit by status effect)
				int chance = GlobalW.rand.nextInt(100) + 1;
				if(chance > target.getTenacity()) { // Their tenacity doesn't save them
					target.addStatusEffect(s);
					caster.addStatusEffect(s);
				}
			}
		}
	}
	
	public String getDisplayName() {
		return meta.getDisplayName();
	}

	public int getUID() {
		return UID;
	}

	public Ability getOwner() {
		return owner;
	}

	public void setOwner(Ability owner) {
		this.owner = owner;
	}
}
