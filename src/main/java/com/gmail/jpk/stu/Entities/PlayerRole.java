package com.gmail.jpk.stu.Entities;

public enum PlayerRole {	
	
	BRUTE_JUGGERNAUT("Brute Juggernaut", "An angry brute who can take a lot of punishment."), 
	HOLY_KNIGHT("Holy Knight", "A noble knight whose stories can entertain crowds."), 
	FIGHTER("Fighter", "A simple man who loves the feel of a sword crushing his victims' skulls."), 
	BLIGHT_ARCHER("Blight Archer", "A crafty bow-man who spreads the plague."), 
	CLERIC("Cleric", "A man whose spells help support his team."), 
	UTILITY_MAGE("Utility Mage", "A mage who can manipulate the world and those around him."),
	SPECTATOR("Spectator", "He dreams of one day fighting in the arena.");
	
	//Descriptions for this role
	private String[] description;
	
	private PlayerRole(String... description) {
		this.description = description;
	}
	
	/**
	 * <b>Gets the preferred name for each role.</b>
	 * @return The preferred name (i.e. "The Holy Knight")
	 */
	public String getPreferredName() {
		return description[0];
	}
	/**
	 * <b>Gets the description for a given role.</b>
	 * @return the description
	 */
	public String[] getDescription() {
		return description;
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
