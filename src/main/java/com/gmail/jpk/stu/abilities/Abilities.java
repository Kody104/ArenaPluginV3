package com.gmail.jpk.stu.abilities;

import com.gmail.jpk.stu.abilities.Ability;
import com.gmail.jpk.stu.abilities.AbilityTarget;

import org.bukkit.ChatColor;


public enum Abilities {
	
	HOOKSHOT(new Ability("Hookshot", "Drag and slow enemies for 3 seconds.", new AbilityTarget(AbilityTarget.TargetType.SINGLE_ENEMY, 12, 0), 
			DamageType.PHYSICAL, 400, ChatColor.BLUE, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, HiddenEffect.HOOK, new StatusEffect(StatusEffect.StatusEffectTarget.TARGET, 
					StatusEffect.StatusEffectTrigger.IMMEDIATE, StatusEffect.StatusEffectType.SOFT_SLOW, 0.0d, 0, 60))),
	ANNIHILATE(new Ability("Annihilate", "Deal bonus damage scaling with HP.", new AbilityTarget(AbilityTarget.TargetType.SINGLE_ENEMY, 0, 0),
			DamageType.PHYSICAL, 240, ChatColor.BLUE, 35.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.05d)),
	NO_REMORSE(new Ability("No_Remorse", "Gain defense for 5 seconds.", new AbilityTarget(AbilityTarget.TargetType.SELF, 0, 0),
			DamageType.PHYSICAL, 400, ChatColor.BLUE, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, new StatusEffect(StatusEffect.StatusEffectTarget.CASTER,
					StatusEffect.StatusEffectTrigger.IMMEDIATE, StatusEffect.StatusEffectType.BUFF_DEF, 15.0d, 0, 100))),
	ADRENALINE(new Ability("Adrenaline", "Gain immunity for 5 seconds. At a cost.", new AbilityTarget(AbilityTarget.TargetType.SELF, 0, 0), 
			DamageType.PHYSICAL, 2000, ChatColor.GOLD, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, new StatusEffect(StatusEffect.StatusEffectTarget.CASTER,
					StatusEffect.StatusEffectTrigger.IMMEDIATE, StatusEffect.StatusEffectType.SUBVERT_DAMAGE, 100.0d, 0, 60))),
	SMITTEN(new Ability("Smitten", "Deal holy damage scaling with magic.", new AbilityTarget(AbilityTarget.TargetType.SINGLE_ENEMY, 5, 0),
			DamageType.HOLY, 280, ChatColor.BLUE, 40.0d, 0.0d, 0.25d, 0.0d, 0.0d, 0.0d)),
	JUDGEMENT(new Ability("Judgement", "Enemies take extra holy damage.", new AbilityTarget(AbilityTarget.TargetType.AOE_ENEMIES, 0, 8), 
			DamageType.HOLY, 520, ChatColor.BLUE, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, new StatusEffect(StatusEffect.StatusEffectTarget.TARGET,
					StatusEffect.StatusEffectTrigger.IMMEDIATE, StatusEffect.StatusEffectType.DEBUFF_HOLYRES, 7.0d, 0, 100))),
	SAVIOR(new Ability("Savior", "Jump to location and taunt enemies.", new AbilityTarget(AbilityTarget.TargetType.SELF, 8, 0), 
			DamageType.PHYSICAL, 480, ChatColor.BLUE, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, HiddenEffect.LEAP));
	
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
