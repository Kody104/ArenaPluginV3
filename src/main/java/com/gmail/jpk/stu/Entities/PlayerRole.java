package com.gmail.jpk.stu.Entities;

public enum PlayerRole {	
	
	BRUTE_JUGGERNAUT("An angry brute who can take a lot of punishment."), 
	HOLY_KNIGHT("A noble knight whose stories can entertain crowds."), 
	FIGHTER("A simple man who loves the feel of a sword crushing his victims' skulls."), 
	BLIGHT_ARCHER("A crafty bow-man who likes to spread the plague."), 
	CLERIC("A righteous holy man who wants to spread the word of his Lord."), 
	UTILITY_MAGE("A mage who can manipulate the word and those around him."),
	SPECTATOR("He dreams of one day fighting in the arena.");
	
	//Descriptions for this role
	private String[] description;
	
	private PlayerRole(String... description) {
		this.description = description;
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
