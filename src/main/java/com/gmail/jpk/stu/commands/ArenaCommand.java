package com.gmail.jpk.stu.commands;

import org.bukkit.command.CommandSender;

import com.gmail.jpk.stu.Entities.ArenaPlayer;
import com.gmail.jpk.stu.arena.ArenaPlugin;
import com.gmail.jpk.stu.arena.GlobalW;
import com.gmail.jpk.stu.listeners.ItemInteractionListener;

/**
 * Runs various tasks for the Arena. Intended to be used more for dev work than in-game content.
 */
public class ArenaCommand extends BasicCommand {

	public ArenaCommand(ArenaPlugin plugin) {
		super(plugin);
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		//Single Argument Commands 
		if (args.length == 1) {
			//Clear Arena Creatures
			if (args[0].equalsIgnoreCase("-cc")) {
				sender.sendMessage("Force clearing all creatures from the arena.");
				GlobalW.getCreaturesInArena().clear();
				return true;
			}
			
			//Clear SpecialItems (-csi)
			if (args[0].equalsIgnoreCase("-csi")) {
				sender.sendMessage("Force clearing SpecialItems from the ground.");
				ItemInteractionListener.clearDroppedItems();
				return true;
			}
			
			//Force all players to ready
			if (args[0].equalsIgnoreCase("-fr")) {
				//Set all Arena Players ready
				for (ArenaPlayer arena_player : GlobalW.getPlayersInArena()) {
					arena_player.setReady(true);
				}
				
				//Update ReadyCommand and run command
				ReadyCommand.setAllReady(true);
				BasicCommand.executeCommand("ready");				
			}
		}
		
		return false;
	}
}
