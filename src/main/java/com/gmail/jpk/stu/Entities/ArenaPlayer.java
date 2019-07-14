package com.gmail.jpk.stu.Entities;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.gmail.jpk.stu.abilities.Ability;
import com.gmail.jpk.stu.abilities.PassiveAbility;

public class ArenaPlayer extends ArenaEntity {

	private Player mPlayer;
	private PlayerRole classRole;
	private int Level;
	private PassiveAbility passive;
	private List<Ability> allAbilties;
	private boolean is_ready;
	
	public ArenaPlayer(Player mPlayer, PlayerRole role) {
		this.mPlayer = mPlayer;
		this.classRole = role;
		this.Level = 1;
		this.allAbilties = new ArrayList<Ability>();
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
	public String[] getClassRoleDescription() {
		return classRole.getDescription();
	}
	
	/**
	 * <b>Gets a specific description for a role.
	 * @param role the requested role
	 * @return the description for the role
	 */
	public String[] getClassRoleDescription(String role) {
		String role_upper = role.toUpperCase();
		PlayerRole player_role = PlayerRole.getRoleByString(role_upper);
		
		if (player_role != null) {
			return player_role.getDescription();
		} else {
			return new String[] { String.format("There is no %s role. Try " + ChatColor.GOLD + "/role all " + ChatColor.RED + "for help.", role) };
		}
	}
	
	/**
	 * <b>Determines if this ArenaPlayer is ready to fight.</b>
	 * @return their readiness state
	 */
	public boolean isReady() {
		return is_ready;
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
	
	public List<Ability> getAllAbilties() {
		return allAbilties;
	}
}
