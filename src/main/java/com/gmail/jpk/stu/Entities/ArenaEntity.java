package com.gmail.jpk.stu.Entities;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.gmail.jpk.stu.abilities.DamageType;
import com.gmail.jpk.stu.abilities.StatusEffect;
import com.gmail.jpk.stu.arena.GlobalW;
import com.gmail.jpk.stu.timers.StatusEffectTimer;

public abstract class ArenaEntity {
	
	private LivingEntity livingEntity; // The living entity this represents
	private double maxHp; // The max hp of this entity
	private double curHp; // This current hp of this entity
	private double atk; // The attack stat of this entity.
	private double mag; // The magic stat of this entity.
	private double def; // The defense stat of this entity.
	private double res; // The resistance stat of this entity.
	private double critHitChance; // The critical hit chance stat of this entity. 
	private double critHitMultiplier; // The critical hit multiplier stat of this entity.
	private int cdr; // The cooldown reduction stat of this entity.
	private int tenacity; // The tenacity stat of this entity.
	private List<StatusEffect> allStatusEffects; // The current status effects affecting this entity.
	
	public ArenaEntity() {
		allStatusEffects = new ArrayList<StatusEffect>();
	}
	
	public ArenaEntity(LivingEntity livingEntity, double Hp, double atk, double mag, double def, double res, double critchance, double critmulti, int cdr, int tenacity) {
		this.livingEntity = livingEntity;
		this.maxHp = Hp;
		this.curHp = Hp;
		this.atk = atk;
		this.mag = mag;
		this.def = def;
		this.res = res;
		this.critHitChance = critchance;
		this.critHitMultiplier = critmulti;
		this.cdr = cdr;
		this.tenacity = tenacity;
		allStatusEffects = new ArrayList<StatusEffect>();
	}
	
	public void takeDamage(double damage, DamageType damageType) {
		//Override this function
	}

	public LivingEntity getLivingEntity() {
		return livingEntity;
	}

	public void setLivingEntity(LivingEntity livingEntity) {
		this.livingEntity = livingEntity;
	}

	public double getMaxHp() {
		return maxHp;
	}

	public void setMaxHp(double maxHp) {
		this.maxHp = maxHp;
	}

	public double getCurHp() {
		return curHp;
	}

	public void setCurHp(double curHp) {
		this.curHp = curHp;
	}

	public double getAtk() {
		return atk;
	}

	public void setAtk(double atk) {
		this.atk = atk;
	}

	public double getMag() {
		return mag;
	}

	public void setMag(double mag) {
		this.mag = mag;
	}

	public double getDef() {
		return def;
	}

	public void setDef(double def) {
		this.def = def;
	}

	public double getRes() {
		return res;
	}

	public void setRes(double res) {
		this.res = res;
	}

	public double getCritHitChance() {
		return critHitChance;
	}

	public void setCritHitChance(double critHitChance) {
		this.critHitChance = critHitChance;
	}

	public double getCritHitMultiplier() {
		return critHitMultiplier;
	}

	public void setCritHitMultiplier(double critHitMultiplier) {
		this.critHitMultiplier = critHitMultiplier;
	}

	public int getCdr() {
		return cdr;
	}

	public void setCdr(int cdr) {
		this.cdr = cdr;
	}

	public int getTenacity() {
		return tenacity;
	}

	public void setTenacity(int tenacity) {
		this.tenacity = tenacity;
	}

	public List<StatusEffect> getAllStatusEffects() {
		return allStatusEffects;
	}
	
	/**
	 * Adds the status effect to the entity. Adds on to status effect if it already exists.
	 * @param statusEffect	The status effect to add
	 */
	public void addStatusEffect(StatusEffect statusEffect) {
		if(hasStatusEffect(statusEffect)) { // Check if entity has the status effect
			for(int i = 0; i < allStatusEffects.size(); i++) {
				StatusEffect s = allStatusEffects.get(i);
				if(s.getType() == statusEffect.getType()) { // Check by status type
					int duration = s.getDuration() + statusEffect.getDuration(); // Combine the durations
					removeStatusEffect(s); // Remove old
					StatusEffect newStatus = new StatusEffect(statusEffect.clone()); // Create new
					newStatus.setDuration(duration); // Set new duration
					allStatusEffects.add(newStatus); // Add new status effect
					new StatusEffectTimer(this, statusEffect).runTaskLater(GlobalW.getPlugin(), duration);
					switch(statusEffect.getType()) {
					case SOFT_SLOW:
						{
							livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, duration, 0));
							break;
						}
						default:
						{
							break;
						}
					}
				}
			}
		}
		else {
			allStatusEffects.add(statusEffect);
			new StatusEffectTimer(this, statusEffect).runTaskLater(GlobalW.getPlugin(), statusEffect.getDuration());
			switch(statusEffect.getType()) {
			case SOFT_SLOW:
				{
					livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, statusEffect.getDuration(), 0));
					break;
				}
				default:
				{
					break;
				}
			}
		}
	}
	
	/**
	 *  Get a specific status effect by the type you want.
	 * @param type	The type of status effect you want to get
	 * @return	The status effect
	 */
	public StatusEffect getStatusEffectByType(StatusEffect.StatusEffectType type) {
		for(StatusEffect s : allStatusEffects) {
			if(s.getType() == type) {
				return s;
			}
		}
		return null;
	}
	
	/**
	 * Check if entity has a specific status effect by the type.
	 * @param type	The type of status effect you want to look up
	 * @return	The status effect
	 */
	public boolean hasStatusEffect(StatusEffect statusEffect) {
		for(StatusEffect s : allStatusEffects) {
			if(s.getType() == statusEffect.getType()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Remove a status effect from the array list.
	 * @param statusEffect	The status effect to remove
	 */
	public void removeStatusEffect(StatusEffect statusEffect) {
		if(hasStatusEffect(statusEffect)) {
			for(int i = 0; i < allStatusEffects.size(); i++) {
				StatusEffect s = allStatusEffects.get(i);
				if(s.getType() == statusEffect.getType()) {
					allStatusEffects.remove(i);
					switch(s.getType()) {
						case SOFT_SLOW:
						{
							livingEntity.removePotionEffect(PotionEffectType.SLOW);
							break;
						}
						default:
						{
							break;
						}
					}
					return;
				}
			}
		}
	}
}
