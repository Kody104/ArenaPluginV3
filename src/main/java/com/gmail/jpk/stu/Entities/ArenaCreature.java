package com.gmail.jpk.stu.Entities;

import org.bukkit.entity.LivingEntity;

import com.gmail.jpk.stu.abilities.DamageType;
import com.gmail.jpk.stu.abilities.PassiveAbility;
import com.gmail.jpk.stu.arena.GlobalW;

public class ArenaCreature extends ArenaEntity {
	
	private int lvl;
	private PassiveAbility passive;
	
	private int fireRes;
	private int waterRes;
	private int earthRes;
	private int airRes;
	private int holyRes;
	private int darkRes;
	
	public ArenaCreature(LivingEntity entityType, int lvl, PassiveAbility passive) {
		setLivingEntity(entityType);
		this.lvl = lvl;
		this.passive = passive;
		entityType.setCustomName(entityType.getType() + " Lvl " + lvl);
		entityType.setCustomNameVisible(true);
		setPassive(passive);
		switch(entityType.getType()) {
			case BAT:
				break;
			case BLAZE:
				break;
			case CAT:
				break;
			case CAVE_SPIDER:
				break;
			case CHICKEN:
				break;
			case COD:
				break;
			case COW:
				break;
			case CREEPER:
			{
				setMaxHp(135d + (23.4d * lvl));
				setCurHp(getMaxHp());
				setAtk(50.0d + (8.9d * lvl));
				setMag(0.0d);
				setDef(20.0d + (1.2d * lvl));
				setFireRes(75);
				setWaterRes(20);
				setEarthRes(15);
				setAirRes(75);
				setHolyRes(20);
				setDarkRes(25);
				break;
			}
			case DOLPHIN:
				break;
			case DONKEY:
				break;
			case DROWNED:
				break;
			case ELDER_GUARDIAN:
				break;
			case ENDERMAN:
				break;
			case ENDERMITE:
				break;
			case ENDER_DRAGON:
				break;
			case EVOKER:
				break;
			case FOX:
				break;
			case GHAST:
				break;
			case GIANT:
				break;
			case GUARDIAN:
				break;
			case HORSE:
				break;
			case HUSK:
				break;
			case ILLUSIONER:
				break;
			case IRON_GOLEM:
				break;
			case LLAMA:
				break;
			case MAGMA_CUBE:
				break;
			case MULE:
				break;
			case MUSHROOM_COW:
				break;
			case OCELOT:
				break;
			case PANDA:
				break;
			case PARROT:
				break;
			case PHANTOM:
				break;
			case PIG:
				break;
			case PIG_ZOMBIE:
				break;
			case PILLAGER:
				break;
			case POLAR_BEAR:
				break;
			case PUFFERFISH:
				break;
			case RABBIT:
				break;
			case RAVAGER:
				break;
			case SALMON:
				break;
			case SHEEP:
				break;
			case SHULKER:
				break;
			case SILVERFISH:
				break;
			case SKELETON:
			{
				setMaxHp(90d + (21.8d * lvl));
				setCurHp(getMaxHp());
				setAtk(90.0d + (18.5d * lvl));
				setMag(7.8d * lvl);
				setDef(18.0d + (3.1d * lvl));
				setFireRes(0);
				setWaterRes(15);
				setEarthRes(40);
				setAirRes(25);
				setHolyRes(40);
				setDarkRes(35);
				break;
			}
			case SKELETON_HORSE:
				break;
			case SLIME:
				break;
			case SNOWMAN:
				break;
			case SPIDER:
			{
				setMaxHp(80d + (19.9d * lvl));
				setCurHp(getMaxHp());
				setAtk(35.0d + (4.9d * lvl));
				setMag(10.0d + (3.8 * lvl));
				setDef(18.0d + (3.1d * lvl));
				setFireRes(0);
				setWaterRes(15);
				setEarthRes(25);
				setAirRes(10);
				setHolyRes(15);
				setDarkRes(80);
				break;
			}
			case SQUID:
				break;
			case STRAY:
				break;
			case TROPICAL_FISH:
				break;
			case TURTLE:
				break;
			case VEX:
				break;
			case VILLAGER:
				break;
			case VINDICATOR:
				break;
			case WANDERING_TRADER:
				break;
			case WITCH:
				break;
			case WITHER:
				break;
			case WITHER_SKELETON:
				break;
			case WOLF:
				break;
			case ZOMBIE:
			{
				setMaxHp(70.0d + (18.7d * lvl));
				setCurHp(getMaxHp());
				setAtk(45.0d + (2.7d * lvl));
				setMag(0.0d);
				setDef(10.0d + (2.9d * lvl));
				setFireRes(15);
				setWaterRes(10);
				setEarthRes(10);
				setAirRes(10);
				setHolyRes(0);
				setDarkRes(35);
				break;
			}
			case ZOMBIE_HORSE:
				break;
			case ZOMBIE_VILLAGER:
				break;
			default:
				break;
		}
	}
	
	@Override
	public double takeDamage(double damage, DamageType damageType) {
		// Calculate resistance based on damage type
		double damageAfter = 0.0d;
		if(damageType == DamageType.PHYSICAL) {
			double amrMulti = (100.0d / (100.0d + getDef()));
			damageAfter = damage * amrMulti;
			//TODO: Subtract from health
		}
		else if(damageType == DamageType.MAGICAL) {
			double resMulti = (100.0d / (100.0d + getRes()));
			damageAfter = damage * resMulti;
			//TODO: Subtract from health
		}
		else if(damageType == DamageType.FIRE) {
			double resMulti = ((100.0d - getFireRes()) / 100.0d);
			damageAfter = damage * resMulti;
			//TODO: Subtract from health
		}
		else if(damageType == DamageType.WATER) {
			double resMulti = ((100.0d - getWaterRes()) / 100.0d);
			damageAfter = damage * resMulti;
			//TODO: Subtract from health
		}
		else if(damageType == DamageType.EARTH) {
			double resMulti = ((100.0d - getEarthRes()) / 100.0d);
			damageAfter = damage * resMulti;
			//TODO: Subtract from health
		}
		else if(damageType == DamageType.AIR) {
			double resMulti = ((100.0d - getAirRes()) / 100.0d);
			damageAfter = damage * resMulti;
			//TODO: Subtract from health
		}
		else if(damageType == DamageType.HOLY) {
			double resMulti = ((100.0d - getHolyRes()) / 100.0d);
			damageAfter = damage * resMulti;
			//TODO: Subtract from health
		}
		else if(damageType == DamageType.DARK) {
			double resMulti = ((100.0d - getDarkRes()) / 100.0d);
			damageAfter = damage * resMulti;
			//TODO: Subtract from health
		}
		else if(damageType == DamageType.TRUE) {
			//TODO: Subtract from health
		}
		else {
			damageAfter = 0.0d;
			GlobalW.getPlugin().getLogger().info("[ERROR] What is this damage type? " + damageType);
		}
		setCurHp(getCurHp() - damageAfter);
		getLivingEntity().damage(0.0d); // Use this so the monster shows damage. Don't actually damage the monster though.
		if(getCurHp() <= 0.0d) {
			getLivingEntity().setHealth(0.0d);
		}
		return damageAfter;
	}
	
	/**
	 * Returns how much experience an ArenaEntity drops.
	 * @param bonus any bonus experience dropped from this ArenaEntity
	 * @return the amount of experience dropped
	 */
	public int getExpDrop(int bonus) {
		return (int) (bonus + (15 + (0.03 * Math.pow(lvl, 2))));
	}

	public int getFireRes() {
		return fireRes;
	}

	public void setFireRes(int fireRes) {
		this.fireRes = fireRes;
	}

	public int getWaterRes() {
		return waterRes;
	}

	public void setWaterRes(int waterRes) {
		this.waterRes = waterRes;
	}

	public int getEarthRes() {
		return earthRes;
	}

	public void setEarthRes(int earthRes) {
		this.earthRes = earthRes;
	}

	public int getAirRes() {
		return airRes;
	}

	public void setAirRes(int airRes) {
		this.airRes = airRes;
	}

	public int getHolyRes() {
		return holyRes;
	}

	public void setHolyRes(int holyRes) {
		this.holyRes = holyRes;
	}

	public int getDarkRes() {
		return darkRes;
	}

	public void setDarkRes(int darkRes) {
		this.darkRes = darkRes;
	}

	public int getLvl() {
		return lvl;
	}

	public void setLvl(int lvl) {
		this.lvl = lvl;
	}

	public PassiveAbility getPassive() {
		return passive;
	}

	public void setPassive(PassiveAbility passive) {
		this.passive = passive;
	}
}
