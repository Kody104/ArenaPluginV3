package com.gmail.jpk.stu.abilities;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;

public class Ability {
	
	
	private String name;
	private String description; // What this ability does.
	private AbilityTarget targetType; // The target and range of this ability.
	private DamageType damageType; // The type of damage this ability does.
	private int lvl; // Level of the ability
	private boolean onCooldown; // Is this ability castable?
	private int cooldown; // The duration of cooldown for this ability
	private ChatColor color; // May or may not be implemented. Thinking about it
	private List<StatusEffect> allStatusEffects; // All the status effects this ability does.
	private double baseDmg; // The base damage the ability will do.
	private double atkScale; // If this ability scales with atk, use this.
	private double magScale; // If this ability scales with mag, use this.
	private double amrScale; // If this ability scales with amr, use this.
	private double resScale; // If this ability scales with res, use this.
	private double hpScale; // If this ability scales with hp, use this.
	
	/**
	 * 
	 * @param name	The ability's name
	 * @param description The ability's description
	 * @param type The type of ability this is
	 * @param cooldown The cooldown in milliseconds
	 * @param color The color this ability shows in chat
	 * @param baseDmg The base damage of this ability
	 * @param atkScale The attack stat scaling
	 * @param magScale The magic stat scaling
	 * @param amrScale The armor stat scaling
	 * @param resScale The resistance stat scaling
	 * @param hpScale The maxhp stat scaling
	 * @param effects The status effects that this ability applies
	 */
	public Ability(String name, String description, AbilityTarget target, DamageType damageType, int cooldown, ChatColor color, double baseDmg, double atkScale, double magScale, double amrScale, double resScale, double hpScale, StatusEffect... effects) {
		this.name = name;
		this.description = description;
		this.targetType = target;
		this.damageType = damageType;
		this.lvl = 0;
		this.onCooldown = false;
		this.cooldown = cooldown;
		this.color = color;
		this.allStatusEffects = new ArrayList<StatusEffect>();
		for(StatusEffect e : effects) {
			allStatusEffects.add(e);
		}
		this.baseDmg = baseDmg;
		this.atkScale = atkScale;
		this.magScale = magScale;
		this.amrScale = amrScale;
		this.resScale = resScale;
		this.hpScale = hpScale;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public AbilityTarget getTargetType() {
		return targetType;
	}

	public void setTargetType(AbilityTarget target) {
		this.targetType = target;
	}

	public DamageType getDamageType() {
		return damageType;
	}

	public void setDamageType(DamageType damageType) {
		this.damageType = damageType;
	}

	public int getLvl() {
		return lvl;
	}

	public void setLvl(int lvl) {
		this.lvl = lvl;
	}

	public boolean isOnCooldown() {
		return onCooldown;
	}

	public void setOnCooldown(boolean onCooldown) {
		this.onCooldown = onCooldown;
	}

	public int getCooldown() {
		return cooldown;
	}

	public void setCooldown(int cooldown) {
		this.cooldown = cooldown;
	}

	public ChatColor getColor() {
		return color;
	}

	public void setColor(ChatColor color) {
		this.color = color;
	}

	public List<StatusEffect> getAllStatusEffects() {
		return allStatusEffects;
	}

	public void setAllStatusEffects(List<StatusEffect> allStatusEffects) {
		this.allStatusEffects = allStatusEffects;
	}

	public double getBaseDmg() {
		return baseDmg;
	}

	public void setBaseDmg(double baseDmg) {
		this.baseDmg = baseDmg;
	}

	public double getAtkScale() {
		return atkScale;
	}

	public void setAtkScale(double atkScale) {
		this.atkScale = atkScale;
	}

	public double getMagScale() {
		return magScale;
	}

	public void setMagScale(double magScale) {
		this.magScale = magScale;
	}

	public double getAmrScale() {
		return amrScale;
	}

	public void setAmrScale(double amrScale) {
		this.amrScale = amrScale;
	}

	public double getResScale() {
		return resScale;
	}

	public void setResScale(double resScale) {
		this.resScale = resScale;
	}

	public double getHpScale() {
		return hpScale;
	}

	public void setHpScale(double hpScale) {
		this.hpScale = hpScale;
	}
}
