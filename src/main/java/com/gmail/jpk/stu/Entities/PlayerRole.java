package com.gmail.jpk.stu.Entities;

import com.gmail.jpk.stu.abilities.Ability;
import com.gmail.jpk.stu.abilities.PassiveAbility;
import com.gmail.jpk.stu.abilities.Abilities;

public enum PlayerRole {	
	
	BRUTE_JUGGERNAUT("Brute Juggernaut", "An angry brute who can take a lot of punishment.", 
			PassiveAbility.BRAWN, Abilities.HOOKSHOT.getAbility()), 
	HOLY_KNIGHT("Holy Knight", "A noble knight whose stories can entertain crowds.", PassiveAbility.RESISTANT), 
	FIGHTER("Fighter", "A simple man who loves the feel of a sword crushing his victims' skulls.", PassiveAbility.HONED_SKILLS), 
	BLIGHT_ARCHER("Blight Archer", "A crafty bow-man who spreads the plague.", PassiveAbility.SCAVENGE), 
	CLERIC("Cleric", "A man whose spells help support his team.", PassiveAbility.HOLY_TOUCH), 
	UTILITY_MAGE("Utility Mage", "A mage who can manipulate the world and those around him.", PassiveAbility.ANTI_MAGIC),
	SPECTATOR("Spectator", "He dreams of one day fighting in the arena.", PassiveAbility.SPECTATION);
	
	//Descriptions for this role
	private String name;
	private String description;
	private PassiveAbility passive;
	private Ability[] abilities;
	
	private PlayerRole(String name, String description, PassiveAbility passive, Ability... abilities) {
		this.name = name;
		this.description = description;
		this.passive = passive;
		this.abilities = abilities;
	}
	
	/**
	 * <b>Gets the name for each role.</b>
	 * @return The name (i.e. "The Holy Knight")
	 */
	public String getName() {
		return name;
	}
	/**
	 * <b>Gets the description for a given role.</b>
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * <b>Gets the passive ability for a given role.</b>
	 * @return the passive ability
	 */
	public PassiveAbility getPassive() {
		return passive;
	}
	/**
	 * <b>Gets the abilities for a given role.</b>
	 * @return the abilities
	 */
	public Ability[] getAbilities() {
		return abilities;
	}

	public static PlayerRole getRoleByString(String role) {
		//Convert the role input to all uppercase
				String role_upper = role.toUpperCase();
				
				//Grab the description for the role
				switch(role_upper) {
					case "JUGGERNAUT":
						return BRUTE_JUGGERNAUT;
						
					case "KNIGHT":
						return HOLY_KNIGHT;
					
					case "FIGHTER":
						return FIGHTER;
					
					case "ARCHER":
						return BLIGHT_ARCHER;
					
					case "CLERIC":
						return CLERIC;
					
					case "MAGE":
						return UTILITY_MAGE;
					
					case "SPECTATOR":
						return SPECTATOR;
						
					default:
						return null;
		}
	}
}
