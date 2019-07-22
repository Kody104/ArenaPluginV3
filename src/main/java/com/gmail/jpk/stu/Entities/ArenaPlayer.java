package com.gmail.jpk.stu.Entities;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.gmail.jpk.stu.abilities.DamageType;
import com.gmail.jpk.stu.abilities.PassiveAbility;
import com.gmail.jpk.stu.abilities.StatusEffect;
import com.gmail.jpk.stu.arena.GlobalW;
import com.gmail.jpk.stu.commands.BasicCommand;
import com.gmail.jpk.stu.items.AbilityItem;
import com.gmail.jpk.stu.items.SpecialItem;
import com.gmail.jpk.stu.items.SpecialItems;

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
	
	/**
	 * Adds and updates Exp to the player
	 * @param exp the amount to give
	 */
	public void addExp(int gained_exp) {
		int next_exp = getNextExp();
		int	upd_exp = exp + gained_exp;
		
		if (upd_exp >= next_exp) {
			LevelUp();
			upd_exp = (next_exp) - (exp + gained_exp);
			addExp(upd_exp);
		}
		
		mPlayer.setLevel(Level);
		mPlayer.setExp((float)(upd_exp / getNextExp()));
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
						
			if (Level % 5 == 0) {
				GlobalW.toArenaPlayers(GlobalW.getChatTag() + String.format("%s the %s is now level %d!", mPlayer.getName(), this.classRole.getName(), Level));
			} 
			
			else if (Level == 18) {
				GlobalW.toArenaPlayers(GlobalW.getChatTag() + String.format("%s the %s is MAX level!", mPlayer.getName(), this.classRole.getName()));
			}
			
			else {
				GlobalW.toPlayer(mPlayer, String.format("Hoorah! You are now level %d!", Level));
			}
			
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
	 * Converts an amount in GS value to the proper combination of Flawless Diamonds, Chipped Emeralds, Golden Bars, and Golden Scraps.
	 * @param amount the GS (Golden Scraps) Value
	 * @return the currencies as an array
	 */
	private int[] getAmountAsCurrencies(int amount) {
		int[] currencies = {0, 0, 0, 0};
		
		//Determine diamonds, emeralds, and bars
		for (int k = 90000, i = 0; k > 1; k /= 100) {
			if (amount >= k) {
				currencies[i] = (int) (amount / k);
				amount -= (currencies[i] * k);
			}
			
			i++;
		}
		
		//Grab any remaining scraps
		currencies[3] = (amount > 0 ? amount : 0);
		
		return currencies;
	}
	
	/**
	 * Adds the specified amount of money (in gold and gems) to the player. The function determines which gold and gems to give.<br/>
	 * Example: 99999 = 90000 + 9000 + 900 + 90 + 9 = 1 Flawless Diamond, 11 Chipped Emeralds, 11 Gold bars.
	 * @param amount the amount of money to add (must be positive)
	 * @return true if successful
	 */
	public boolean addMoney(int amount) {
		if (amount < 0) {
			return false;
		}
		
		int[] currencies = getAmountAsCurrencies(amount);
		
		//Grant Items
		BasicCommand.executeCommand(String.format("gci %s %d %d", mPlayer.getName(), SpecialItems.FLAWLESS_DIAMOND.getUID(), currencies[0]));
		BasicCommand.executeCommand(String.format("gci %s %d %d", mPlayer.getName(), SpecialItems.CHIPPED_EMERALD.getUID(), currencies[1]));
		BasicCommand.executeCommand(String.format("gci %s %d %d", mPlayer.getName(), SpecialItems.GOLDEN_BAR.getUID(), currencies[2]));
		BasicCommand.executeCommand(String.format("gci %s %d %d", mPlayer.getName(), SpecialItems.GOLDEN_SCRAP.getUID(), currencies[3]));
		
		return true;
	}
	
	/**
	 * Gets how much total GS value this ArenaPlayer has.
	 * @return their total wealth in GS (Golden Scraps)
	 */
	public int getMoney() {
		int[] gems = getPlayerGems();
		return ((90000 * gems[0]) + (900 * gems[1]) + (9 * gems[2]) + (gems[3]));	
	}
	
	/**
	 * Counts and gets the number of gems the player is holding.
	 * {Diamonds, Emeralds, Bars, Scraps}
	 * @return
	 */
	public int[] getPlayerGems() {
		int[] gems = {0, 0, 0, 0};
		
		PlayerInventory player_invetory = mPlayer.getInventory();
		String display_name = "";
		
		for (int i = 0; i < mPlayer.getInventory().getSize(); i++) {
			ItemStack stack = player_invetory.getItem(i);
			
			if (stack == null) {
				continue;
			}
			
			display_name = stack.getItemMeta().getDisplayName();
			
			if (SpecialItems.getSpecialItemByDisplayName(display_name) != null) {
				if (display_name.equalsIgnoreCase(SpecialItems.GOLDEN_SCRAP.getDisplayName())) {
					gems[3] += stack.getAmount();
				}
				else if (display_name.equalsIgnoreCase(SpecialItems.GOLDEN_BAR.getDisplayName())) {
					gems[2] += stack.getAmount();
				}
				else if (display_name.equalsIgnoreCase(SpecialItems.CHIPPED_EMERALD.getDisplayName())) {
					gems[1] += stack.getAmount();
				}
				else if (display_name.equalsIgnoreCase(SpecialItems.FLAWLESS_DIAMOND.getDisplayName())) {
					gems[0] += stack.getAmount();
				}
			}			
		}
		
		return gems;
	}
	
	/**
	 * Clears all of the gems out of a Player's inventory.
	 */
	public void clearPlayerGems() {
		PlayerInventory player_invetory = mPlayer.getInventory();
		String display_name = "";
		
		for (int i = 0; i < mPlayer.getInventory().getSize(); i++) {
			ItemStack stack = player_invetory.getItem(i);
			
			if (stack == null) {
				continue;
			}
			
			display_name = stack.getItemMeta().getDisplayName();
			
			if (SpecialItems.getSpecialItemByDisplayName(display_name) != null) {
				if (display_name.equalsIgnoreCase(SpecialItems.GOLDEN_SCRAP.getDisplayName())) {
					stack.setAmount(0);
				}
				else if (display_name.equalsIgnoreCase(SpecialItems.GOLDEN_BAR.getDisplayName())) {
					stack.setAmount(0);
				}
				else if (display_name.equalsIgnoreCase(SpecialItems.CHIPPED_EMERALD.getDisplayName())) {
					stack.setAmount(0);
				}
				else if (display_name.equalsIgnoreCase(SpecialItems.FLAWLESS_DIAMOND.getDisplayName())) {
					stack.setAmount(0);
				}
			}
		}
	}
	
	/**
	 * Updates the player's gems to set array.
	 * @param gems the new array (must have length of 4)
	 */
	public void updatePlayerGems(int[] gems) {
		if (gems.length != 4) {
			return;
		}
		
		clearPlayerGems();
		BasicCommand.executeCommand(String.format("gci %d %s %d", SpecialItems.FLAWLESS_DIAMOND.getUID(), mPlayer.getName(), gems[3]));
		BasicCommand.executeCommand(String.format("gci %d %s %d", SpecialItems.CHIPPED_EMERALD.getUID(), mPlayer.getName(), gems[2]));
		BasicCommand.executeCommand(String.format("gci %d %s %d", SpecialItems.GOLDEN_BAR.getUID(), mPlayer.getName(), gems[1]));
		BasicCommand.executeCommand(String.format("gci %d %s %d", SpecialItems.GOLDEN_SCRAP.getUID(), mPlayer.getName(), gems[0]));		
	}
	
	/**
	 * Removes money from the ArenaPlayer by utilizing larger currencies first.
	 * The amount must be less than the ArenaPlayer's total money.
	 * @param amount the amount to charge the player
	 * @return true if successful
	 */
	public boolean removeMoney(int amount) {
		int money = getMoney();
		
		mPlayer.sendMessage(String.format("Item costs %d. You have %d.", amount, money));
		
		if (amount > money) {
			return false;
		}
		
		int change = (money - amount);
		int[] gems = getAmountAsCurrencies(change);
		mPlayer.sendMessage(String.format("%d diamonds, %d emeralds, %d bars, %d scraps", gems[0], gems[1], gems[2], gems[3]));
		
		//Update Player Inventory
		updatePlayerGems(gems);
		
		return true;
	}
	
	/**
	 * Removes a number of SpecialItem from a player's inventory by its display name
	 * @param amount the amount of SpecialItems to remove
	 * @param display_name the display name of the SpecialItem
	 * @return true if successful
	 */
	public boolean removeSpecialItem(SpecialItem special_item) {
		PlayerInventory player_inventory = mPlayer.getInventory();
		ItemStack stack;
		SpecialItem spec_item;
		String display_name = "";
		
		for (int i = 0; i < player_inventory.getSize(); i++) {
			stack = player_inventory.getItem(i);
						
			if (stack == null) {
				continue;	
			}
						
			display_name = stack.getItemMeta().getDisplayName();
			spec_item = SpecialItems.getSpecialItemByDisplayName(display_name);
			
			if (spec_item != null) {
				spec_item.setAmount(0);
			}
		}
		
		return true;
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

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}
}
