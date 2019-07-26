package com.gmail.jpk.stu.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import com.gmail.jpk.stu.Entities.ArenaCreature;
import com.gmail.jpk.stu.Entities.ArenaEntity;
import com.gmail.jpk.stu.Entities.ArenaPlayer;
import com.gmail.jpk.stu.abilities.Ability;
import com.gmail.jpk.stu.abilities.StatusEffect;
import com.gmail.jpk.stu.arena.GlobalW;
import com.gmail.jpk.stu.timers.AbilityCooldownTimer;

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
	
	public void useAbility(ArenaEntity caster, List<ArenaEntity> targets) {
		for(ArenaEntity entity : targets) {
			useAbility(caster, entity);
		}
	}
	
	public void useAbility(ArenaEntity caster, ArenaEntity target) {
		if(owner.isOnCooldown()) {
			return;
		}
		if(owner.getHiddenEffect() != null) {
			LivingEntity le = target.getLivingEntity();
			switch(owner.getHiddenEffect()) {
				case HOOK:
				{
					Vector vec = le.getLocation().getDirection().multiply(-2.0d); // Invert the entity's direction
					le.setVelocity(vec); // Give the inversion to the entity
					break;
				}
			}
		}
		// Calculate the damage
		double damage = owner.getBaseDmg();
		damage += owner.getAtkScale() * caster.getAtk();
		damage += owner.getMagScale() * caster.getMag();
		damage += owner.getAmrScale() * caster.getDef();
		damage += owner.getResScale() * caster.getRes();
		damage += owner.getHpScale() * caster.getMaxHp();
		
		double actualDmg = 0.0d;
		
		//If the damage is too small I don't want it to even matter
		if(damage >= 0.1d) {
			actualDmg = target.takeDamage(damage, owner.getDamageType());
		}
		
		// If there is a status effect in this ability
		if(owner.getAllStatusEffects().size() > 0) {
			// For all the status effects
			for(int i = 0; i < owner.getAllStatusEffects().size(); i++) {
				StatusEffect s = owner.getAllStatusEffects().get(i);
				if(s.getTarget() == StatusEffect.StatusEffectTarget.TARGET) {
					// Calculate the target's tenacity (chance to not get hit by status effect)
					int chance = GlobalW.rand.nextInt(100) + 1;
					if(chance > target.getTenacity()) { // Their tenacity doesn't save them
						target.addStatusEffect(s);
					}
				}
				else { // The status effect is a caster status effect
					caster.addStatusEffect(s);
				}
			}
		}
		
		if(caster.getLivingEntity() instanceof Player) {
			Player player = (Player) caster.getLivingEntity();
			ArenaPlayer aPlayer = GlobalW.getArenaPlayer(player);
			
			if(aPlayer != null) {
				GlobalW.toPlayer(player, String.format("You have dealt %.2f %s damage!", actualDmg, owner.getDamageType()));
			}
		}
		else if(target.getLivingEntity() instanceof Player) {
			Player player = (Player) target.getLivingEntity();
			ArenaPlayer aPlayer = GlobalW.getArenaPlayer(player);
			
			if(aPlayer != null) {
				GlobalW.toPlayer(player, String.format("You have taken %.2f %s damage!", actualDmg, owner.getDamageType()));
			}
		}
		
		owner.setOnCooldown(true);
		new AbilityCooldownTimer(caster, owner).runTaskLater(GlobalW.getPlugin(), owner.getCooldown());
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
