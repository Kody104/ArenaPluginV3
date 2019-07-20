package com.gmail.jpk.stu.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.gmail.jpk.stu.arena.ArenaPlugin;
import com.gmail.jpk.stu.arena.GlobalW;
import com.gmail.jpk.stu.items.SpecialItem;
import com.gmail.jpk.stu.items.SpecialItems;

/**
 * Gives the player a CustomItem. 
 * Use: /gci [item] [player] (number)
 */
public class GiveCustomItemCommand extends BasicCommand {

	public GiveCustomItemCommand(ArenaPlugin plugin) {
		super(plugin);
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {		
		if (args.length < 2) {
			sender.sendMessage("Not enough arguments.");
			return false;
		}
		
		//Process command
		int special_item_id = getValidInteger(args[0]);
		
		@SuppressWarnings("deprecation") //Not sure if there is a better method for this nowadays.
		Player target_player = Bukkit.getServer().getPlayer(args[1]);
		
		int special_item_quantity = ((args.length == 3) ? getValidInteger(args[2]) : 1);
		
		//Verify inputs are valid
		if ( (special_item_id > 0) && (special_item_quantity > 0) && (target_player != null) ) {
			SpecialItem special_item = SpecialItems.getSpecialItemByUID(special_item_id);
			
			//Add item(s) to inventory
			if (special_item != null) {
				//Adds all the items
				for (int i = 0; i < special_item_quantity; i++) {
					target_player.getInventory().addItem(special_item);
				}
				
				sender.sendMessage(String.format("Gave %s %d %s", target_player.getName(), special_item_quantity, special_item.getDisplayName()));
				GlobalW.toPlayer(target_player, String.format(ChatColor.GREEN + "You have received %d %s(s)!", special_item_quantity, special_item.getDisplayName()));
				return true;
			} else {
				sender.sendMessage("Item not found.");
				return true;
			}
		}
		
		//The check prevents console spam for '0' item gifts. This occurs in ArenaPlayer's updatePlayerGems().
		if (!(sender instanceof ConsoleCommandSender)) {
			sender.sendMessage(String.format("Failed to give %s %d %s", args[1], special_item_quantity, args[0]));
		}
		
		return false;
	}
}
