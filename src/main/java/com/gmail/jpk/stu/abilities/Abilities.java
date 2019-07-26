package com.gmail.jpk.stu.abilities;

import com.gmail.jpk.stu.abilities.Ability;
import com.gmail.jpk.stu.abilities.AbilityTarget;

import org.bukkit.ChatColor;


public enum Abilities {
	
	HOOKSHOT(new Ability("Hookshot", "Drag and slow enemies for 3 seconds.", new AbilityTarget(AbilityTarget.TargetType.SINGLE_ENEMY, 5, 1), 
			DamageType.PHYSICAL, 400, ChatColor.BLUE, 0, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, HiddenEffect.HOOK, new StatusEffect(StatusEffect.StatusEffectTarget.TARGET, 
					StatusEffect.StatusEffectTrigger.IMMEDIATE, StatusEffect.StatusEffectType.SOFT_SLOW, 0.0d, 0, 60))),
	ANNIHILATE(new Ability("Annihilate", "Deal bonus damage scaling with HP.", new AbilityTarget(AbilityTarget.TargetType.SINGLE_ENEMY, 0, 0),
			DamageType.PHYSICAL, 240, ChatColor.BLUE, 35, 0.0d, 0.0d, 0.0d, 0.0d, 0.05d)),
	NO_REMORSE(new Ability("No Remorse", "Gain defense for 5 seconds.", new AbilityTarget(AbilityTarget.TargetType.SELF, 0, 0),
			DamageType.PHYSICAL, 400, ChatColor.BLUE, 0, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, new StatusEffect(StatusEffect.StatusEffectTarget.TARGET,
					StatusEffect.StatusEffectTrigger.IMMEDIATE, StatusEffect.StatusEffectType.BUFF_DEF, 15.0d, 0, 100)));
	
	private final Ability ability;
	
	Abilities(Ability a) {
		this.ability = a;
	}
	
	public Ability getAbility() {
		return ability;
	}
	
	public static Ability getAbilityByName(String name) {
		for(int i = 0; i < Abilities.values().length; i++) {
			Ability a = Abilities.values()[i].getAbility();
			if(a.getName().equalsIgnoreCase(name)) {
				return a;
			}
		}
		return null;
	}
}
