package com.gmail.jpk.stu.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.jpk.stu.Entities.ArenaPlayer;
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
		//Minimum necessary check
		ArenaPlayer arena_player = getArenaPlayer(sender);
		
		//Validate
		if (arena_player == null) {
			//If not, then exit.
			return true;
		}
		
		Player player = (Player) sender;
		
		if (args.length < 2) {
			GlobalW.toPlayerError(player, "Not enough arguments.");
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
				
				GlobalW.toPlayer(player, String.format("Gave %s %d %s", target_player.getName(), special_item_quantity, special_item.getDisplayName()));
				GlobalW.toPlayer(target_player, String.format(ChatColor.GREEN + "You have received %d %s(s)!", special_item_quantity, special_item.getDisplayName()));
				return true;
			} else {
				GlobalW.toPlayer(player, "Item not found.");
				return true;
			}
		}
		
		GlobalW.toPlayer(player, String.format("Failed to give %s %d %s", args[1], special_item_quantity, args[0]));
		
		return false;
	}
}
