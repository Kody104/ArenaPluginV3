package com.gmail.jpk.stu.Entities;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.gmail.jpk.stu.abilities.DamageType;
import com.gmail.jpk.stu.abilities.PassiveAbility;
import com.gmail.jpk.stu.abilities.StatusEffect;
import com.gmail.jpk.stu.arena.GlobalW;
import com.gmail.jpk.stu.items.AbilityItem;

public class ArenaPlayer extends ArenaEntity {

	private Player mPlayer;
	private PlayerRole classRole;
	private int exp;
	private int Level;
	private PassiveAbility passive;
	private List<AbilityItem> allAbilities;
	private boolean is_ready;
	
	public ArenaPlayer(Player mPlayer, PlayerRole role) {
		this.mPlayer = mPlayer;
		this.classRole = role;
		this.Level = 1;
		this.allAbilities = new ArrayList<AbilityItem>();
		this.is_ready = false;
		switch(role) {
			case BLIGHT_ARCHER:
			{
				passive = PassiveAbility.SCAVENGE;
				setMaxHp(458.0d);
				setCurHp(getMaxHp());
				setAtk(35.0d);
				setMag(0.0d);
				setDef(0.0d);
				setRes(0.0d);
				setCritHitChance(0.0d);
				setCritHitMultiplier(1.15d);
				setCdr(0);
				setTenacity(0);
				break;
			}
			case BRUTE_JUGGERNAUT:
			{
				passive = PassiveAbility.BRAWN;
				setMaxHp(632.0d);
				setCurHp(getMaxHp());
				setAtk(35.0d);
				setMag(0.0d);
				setDef(0.0d);
				setRes(0.0d);
				setCritHitChance(0.0d);
				setCritHitMultiplier(1.15d);
				setCdr(0);
				setTenacity(0);
				break;
			}
			case CLERIC:
			{
				passive = PassiveAbility.HOLY_TOUCH;
				setMaxHp(440.0d);
				setCurHp(getMaxHp());
				setAtk(30.0d);
				setMag(0.0d);
				setDef(0.0d);
				setRes(0.0d);
				setCritHitChance(0.0d);
				setCritHitMultiplier(1.15d);
				setCdr(0);
				setTenacity(0);
				break;
			}
			case FIGHTER:
			{
				passive = PassiveAbility.HONED_SKILLS;
				setMaxHp(502.0d);
				setCurHp(getMaxHp());
				setAtk(55.0d);
				setMag(0.0d);
				setDef(0.0d);
				setRes(0.0d);
				setCritHitChance(0.0d);
				setCritHitMultiplier(1.15d);
				setCdr(0);
				setTenacity(0);
				break;
			}
			case HOLY_KNIGHT:
			{
				passive =  PassiveAbility.RESISTANT;
				setMaxHp(566.0d);
				setCurHp(getMaxHp());
				setAtk(55.0d);
				setMag(0.0d);
				setDef(0.0d);
				setRes(0.0d);
				setCritHitChance(0.0d);
				setCritHitMultiplier(1.15d);
				setCdr(0);
				setTenacity(0);
				break;
			}
			case UTILITY_MAGE:
			{
				passive = PassiveAbility.ANTI_MAGIC;
				setMaxHp(420.0d);
				setCurHp(getMaxHp());
				setAtk(35.0d);
				setMag(0.0d);
				setDef(0.0d);
				setRes(0.0d);
				setCritHitChance(0.0d);
				setCritHitMultiplier(1.15d);
				setCdr(0);
				setTenacity(0);
				break;
			}
			case SPECTATOR:
			{
				passive = PassiveAbility.SPECTATION;
				setMaxHp(100.0d);
				setCurHp(getMaxHp());
				setAtk(0.0d);
				setMag(0.0d);
				setDef(0.0d);
				setRes(0.0d);
				setCritHitChance(0.0d);
				setCritHitMultiplier(1.0d);
				setCdr(0);
				setTenacity(0);
				break;
			}
		}
	}
	
	@Override
	public void addStatusEffect(StatusEffect statusEffect) {
		super.addStatusEffect(statusEffect);
		GlobalW.toPlayer(mPlayer, "You have gotten the " + statusEffect.getType() + " status effect!");
	}
	
	@Override
	public void takeDamage(double damage, DamageType damageType) {
		// Calculate resistance based on damage type
		if(damageType == DamageType.PHYSICAL) {
			double amrMulti = (100.0d / (100.0d + getDef()));
			double damageAfter = damage * amrMulti;
			//TODO: Subtract from health
		}
		else if(damageType == DamageType.MAGICAL) {
			double resMulti = (100.0d / (100.0d + getRes()));
			double damageAfter = damage * resMulti;
			//TODO: Subtract from health
		}
		else if(damageType == DamageType.TRUE) {
			//TODO: Subtract from health
		}
		else {
			damage = 0.0d;
			GlobalW.toPlayerError(mPlayer, "What is this damage type? " + damageType);
		}
	}
	
	public boolean isHoldingAbilityItem() {
		if(mPlayer.getInventory().getItemInMainHand().getItemMeta().getLore().get(0).equalsIgnoreCase("ability")) {
			return true;
		}
		return false;
	}
	
	public void castAbility(ArenaEntity... targets) {
		AbilityItem item = getAbilityItemInHand();
		
		if(item != null) {
			// If there is is only one affected.
			if(targets.length == 1) {
				castAbility(this, targets[0]);
				return;
			}
		}
	}
	
	public void castAbility(ArenaEntity target) {
		AbilityItem item = getAbilityItemInHand();
		
		if(item != null) {
			item.useAbility(this, target);
		}
	}
	
	public AbilityItem getAbilityItemInHand() {
		// Safety check
		if(isHoldingAbilityItem()) {
			// Get the lore to check
			String inHandLore = mPlayer.getInventory().getItemInMainHand().getItemMeta().getLore().get(0);
			for(AbilityItem a : allAbilities) {
				// Check the lore
				if(a.getItemMeta().getLore().get(0).equals(inHandLore)) {
					return a;
				}
			}
		}
		return null;
	}
	
	public void LevelUp() {
		if(Level + 1 < 19) {
			Level++;
			switch(classRole) {
				case BLIGHT_ARCHER:
				{
					setMaxHp(getMaxHp() + 27.4d);
					setAtk(getAtk() + 4.1d);
					break;
				}
				case BRUTE_JUGGERNAUT:
				{
					setMaxHp(getMaxHp() + 33.6d);
					setAtk(getAtk() + 3.8d);
					break;
				}
				case CLERIC:
				{
					setMaxHp(getMaxHp() + 29.6d);
					setAtk(getAtk() + 3.8d);
					break;
				}
				case FIGHTER:
				{
					setMaxHp(getMaxHp() + 28.9d);
					setAtk(getAtk() + 3.9d);
					break;
				}
				case HOLY_KNIGHT:
				{
					setMaxHp(getMaxHp() + 32.8d);
					setAtk(getAtk() + 4.2d);
					break;
				}
				case UTILITY_MAGE:
				{
					setMaxHp(getMaxHp() + 28.3d);
					setAtk(getAtk() + 2.8d);
					break;
				}
				case SPECTATOR:
				{
					//Spectators stats shouldn't change.
					break;
				}
			}
		}
	}

	public Player getmPlayer() {
		return mPlayer;
	}

	public PlayerRole getClassRole() {
		return classRole;
	}
	
	/**
	 * <b>Gets the description for this ArenaPlayer's Role
	 * @return the description
	 */
	public String getClassRoleDescription() {
		return classRole.getDescription();
	}
	
	/**
	 * <b>Gets a specific description for a role.
	 * @param role the requested role
	 * @return the description for the role
	 */
	public String getClassRoleDescription(String role) {
		String role_upper = role.toUpperCase();
		PlayerRole player_role = PlayerRole.getRoleByString(role_upper);
		
		if (player_role != null) {
			return player_role.getDescription();
		} else {
			return String.format("There is no %s role. Try " + ChatColor.GOLD + "/role all " + ChatColor.RED + "for help.", role);
		}
	}
	
	/**
	 * <b>Determines if this ArenaPlayer is ready to fight.</b>
	 * @return their readiness state
	 */
	public boolean isReady() {
		return is_ready;
	}
	
	/**
	 * Gets how much Experience a player needs to gain a level.<br/><br/>
	 * As it stands now, here a few levels and the TOTAL number of monsters a player would need to kill.</br>
	 * <i>This does NOT include experience gained from completing the five-round sections.</i>
	 * <ul>
	 * 	<li>Level 5 requires 82 total monster kills.</li>
	 * 	<li>Level 10 requires 453 total monster kills.</li>
	 * 	<li>Level 15 requires 1,685 total monster kills.</li>
	 * 	<li>Level 18 requires 3,118 total monster kills.</li>
	 * </ul>
	 * 
	 * @return the total experience needed for the next level
	 */
	public int getNextExp() {
		return (int) Math.ceil((2.5 * Math.pow(Level, 3) + 250));
	}
	
	public void setClassRole(PlayerRole classRole) {
		this.classRole = classRole;
	}

	public int getLevel() {
		return Level;
	}

	public void setLevel(int level) {
		Level = level;
	}

	public PassiveAbility getPassive() {
		return passive;
	}
	
	public void setReady(boolean is_ready) {
		this.is_ready = is_ready;
	}
	
	public List<AbilityItem> getAllAbilties() {
		return allAbilities;
	}
}
