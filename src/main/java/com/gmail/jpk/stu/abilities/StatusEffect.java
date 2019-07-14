package com.gmail.jpk.stu.abilities;

import org.bukkit.entity.LivingEntity;

public class StatusEffect {
	
	public enum StatusEffectTrigger {
		ON_HIT, IMMEDIATE;
	}
	
	public enum StatusEffectType {
		BUFF_ARMOR, SUBVERT_DAMAGE; // This is specially used for Brute Juggernaut
	}
	
	private StatusEffectTrigger trigger;
	private StatusEffectType type;
	private long triggerDuration; // Duration of the EffectTrigger, not the EffectType
	private long duration; // Duration of the EffectType, not the EffectTrigger
	private LivingEntity caster; // Caster of the status effect
	
	public StatusEffect(StatusEffectTrigger trigger, StatusEffectType type, long triggerDuration, long duration, LivingEntity caster) {
		this.trigger = trigger;
		this.type = type;
		this.triggerDuration = triggerDuration;
		this.duration = duration;
		this.caster = caster;
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

	public long getTriggerDuration() {
		return triggerDuration;
	}

	public void setTriggerDuration(long triggerDuration) {
		this.triggerDuration = triggerDuration;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public LivingEntity getCaster() {
		return caster;
	}

	public void setCaster(LivingEntity caster) {
		this.caster = caster;
	}
	
}
