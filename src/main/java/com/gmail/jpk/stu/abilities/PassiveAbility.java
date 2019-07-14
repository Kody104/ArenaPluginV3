package com.gmail.jpk.stu.abilities;

public enum PassiveAbility {
	POISON_BITE("Poison Bite", "Victims take (10 * Lvl) + (35% mag) poison damage per second over 4 seconds."),
	EXPLODE("Explode!", "Victims take (10% + (1.5% * Lvl)) + (30% total atk) of their max hp as physical damage."),
	SPECTATION("Spectation", "Your spectator blood gives you invulnerability."),
	BRAWN("Brawn", "Physical damage that exceeds 15% of your max hp is reduced by 35%."),
	RESISTANT("Resistant", "Reduces debuffs' effectiveness by 15% on you."),
	HONED_SKILLS("Honed Skills", "Your strikes ignore 20% of your targer's armor."),
	SCAVENGE("Scavenge", "You gain 1 arrow per enemy that dies around you."),
	HOLY_TOUCH("Holy Touch", "Your strikes deal an additional 15% of your [mag] as holy damage."),
	ANTI_MAGIC("Anti-Magic", "For every 5 [mag] you gave you gain 1 bonus [res].");
	
	
	private String name;
	private String description;
	
	PassiveAbility(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
