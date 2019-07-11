package com.gmail.jpk.stu.abilities;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;

public class Ability {
	
	public enum AbilityType {
		NORMAL, ULTIMATE;
	}
	
	private String name;
	private AbilityType type; // The type of ability this is.
	private int lvl; // Level of the ability
	private boolean onCooldown; // Is this ability castable?
	private long cooldown; // The duration of cooldown for this ability
	private ChatColor color; // May or may not be implemented. Thinking about it
	private List<StatusEffect> allStatusEffects; // All the status effects this ability does.
	
	public Ability(String name, AbilityType type, long cooldown, ChatColor color, StatusEffect... effects) {
		this.name = name;
		this.type = type;
		this.lvl = 0;
		this.onCooldown = false;
		this.cooldown = cooldown;
		this.color = color;
		this.allStatusEffects = new ArrayList<StatusEffect>();
		for(StatusEffect e : effects) {
			allStatusEffects.add(e);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AbilityType getType() {
		return type;
	}

	public void setType(AbilityType type) {
		this.type = type;
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

	public long getCooldown() {
		return cooldown;
	}

	public void setCooldown(long cooldown) {
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
}
