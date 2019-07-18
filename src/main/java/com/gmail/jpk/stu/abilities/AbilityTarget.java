package com.gmail.jpk.stu.abilities;

public class AbilityTarget {
	
	public enum TargetType {
		SINGLE_ENEMY, AOE_ENEMIES,
		SINGLE_ALLY, AOE_ALLIES,
		SELF, AOE_SELF, // Aoe-Self is cast on yourself and around you.
		SINGLE_ANY, AOE_ANY;
	}
	
	private TargetType targetType; // Who is affected
	private int castRange; // The range from the target this can be cast
	private int abilityRange; // The range that this ability effects. Single targets don't use this
	
	public AbilityTarget(TargetType target, int castRange, int abilityRange) {
		this.targetType = target;
		this.castRange = castRange;
		this.abilityRange = abilityRange;
	}

	public TargetType getTargetType() {
		return targetType;
	}

	public void setTargetType(TargetType targetType) {
		this.targetType = targetType;
	}

	public int getCastRange() {
		return castRange;
	}

	public void setCastRange(int castRange) {
		this.castRange = castRange;
	}

	public int getAbilityRange() {
		return abilityRange;
	}

	public void setAbilityRange(int abilityRange) {
		this.abilityRange = abilityRange;
	}
}
