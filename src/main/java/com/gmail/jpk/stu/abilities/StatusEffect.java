package com.gmail.jpk.stu.abilities;

public class StatusEffect {
	
	public enum StatusEffectTarget {
		CASTER, TARGET;
	}
	
	public enum StatusEffectTrigger {
		IMMEDIATE, ON_HIT;
	}
	
	public enum StatusEffectType {
		BUFF_DEF, // Buffs armor
		SOFT_SLOW, // Adds slow potion effect tier 1
		
		SUBVERT_DAMAGE; // This is specially used for Brute Juggernaut
	}
	
	private StatusEffectTarget target; // Who is affected by this
	private StatusEffectTrigger trigger; // What triggers this effect
	private StatusEffectType type; // What type of status effect is it
	private double pow; // The power of this status effect. May not be necessary
	private int triggerDuration; // Duration of the EffectTrigger, not the EffectType
	private int duration; // Duration of the EffectType, not the EffectTrigger
	
	public StatusEffect(StatusEffectTarget target, StatusEffectTrigger trigger, StatusEffectType type, double pow, int triggerDuration, int duration) {
		this.target = target;
		this.trigger = trigger;
		this.type = type;
		this.pow = pow;
		this.triggerDuration = triggerDuration;
		this.duration = duration;
	}
	
	public StatusEffect(StatusEffect clone) {
		this.target = clone.target;
		this.trigger = clone.trigger;
		this.type = clone.type;
		this.pow = clone.pow;
		this.triggerDuration = clone.triggerDuration;
		this.duration = clone.duration;
	}

	public StatusEffectTarget getTarget() {
		return target;
	}

	public void setTarget(StatusEffectTarget target) {
		this.target = target;
	}

	public StatusEffectTrigger getTrigger() {
		return trigger;
	}

	public void setTrigger(StatusEffectTrigger trigger) {
		this.trigger = trigger;
	}

	public StatusEffectType getType() {
		return type;
	}

	public void setType(StatusEffectType type) {
		this.type = type;
	}

	public double getPow() {
		return pow;
	}

	public void setPow(double pow) {
		this.pow = pow;
	}

	public int getTriggerDuration() {
		return triggerDuration;
	}

	public void setTriggerDuration(int triggerDuration) {
		this.triggerDuration = triggerDuration;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	@Override
	public StatusEffect clone(){
		return new StatusEffect(target, trigger, type, pow, triggerDuration, duration);
	}
}
