package com.gmail.jpk.stu.items;

import org.bukkit.Material;

import com.gmail.jpk.stu.Entities.ArenaEntity;
import com.gmail.jpk.stu.abilities.Ability;
import com.gmail.jpk.stu.abilities.DamageType;
import com.gmail.jpk.stu.abilities.StatusEffect;
import com.gmail.jpk.stu.arena.GlobalW;

public class AbilityItem extends SpecialItem {

	private Ability owner;
	
	public AbilityItem(int uid, Ability owner) {
		super(Material.STICK, uid, "Ability - " + owner.getName(), owner.getDescription(), "Target: "+owner.getTargetType().getTargetType().toString(), "Range: "+owner.getTargetType().getCastRange() + " person(s)", "Splash: "+owner.getTargetType().getAbilityRange() + " person(s)", "Damage Type: " + owner.getDamageType());
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
				}
			}
		}
	}

	public Ability getOwner() {
		return owner;
	}

	public void setOwner(Ability owner) {
		this.owner = owner;
	}
}
