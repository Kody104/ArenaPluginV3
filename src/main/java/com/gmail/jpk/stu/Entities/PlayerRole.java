package com.gmail.jpk.stu.Entities;

import org.bukkit.command.CommandSender;

public enum PlayerRole {	
	
	BRUTE_JUGGERNAUT("An angry brute who can take a lot of punishment."), 
	HOLY_KNIGHT("A noble knight whose stories can entertain crowds."), 
	FIGHTER("A simple man who loves the feel of a sword crushing his victims' skulls."), 
	BLIGHT_ARCHER("A crafty bow-man who likes to spread the plague."), 
	CLERIC("A righteous holy man who wants to spread the word of his Lord."), 
	UTILITY_MAGE("A mage who can manipulate the word and those around him."),
	SPECTATOR("A fan who doesn't get to compete in the Arena.");
	
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
					
					default:
						return SPECTATOR;
				}
	}
	
	/**
	 * <b>Sends the CommandSender the full description of a role.</b>
	 * @param role the role the CommandSender requested
	 * @param sender the CommandSender
	 */
	
	// Why do we do this here instead of in the RoleCommand class? - Jerome
	public static void showRoleDescription(String role, CommandSender sender) {
		//Convert the role input to all uppercase
		String role_upper = role.toUpperCase();
		String[] description = new String[] {};
		
		//Grab the description for the role
		description = getRoleByString(role_upper).description;
		
		//Send the description to the CommandSender
		for (String string : description) {
			sender.sendMessage(string);
		}
	}
}
