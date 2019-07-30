package com.gmail.jpk.stu.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.gmail.jpk.stu.Entities.ArenaCreature;
import com.gmail.jpk.stu.Entities.ArenaPlayer;
import com.gmail.jpk.stu.Entities.ArenaWave;
import com.gmail.jpk.stu.arena.ArenaPlugin;
import com.gmail.jpk.stu.arena.GlobalW;
import com.gmail.jpk.stu.listeners.ItemInteractionListener;

/**
 * Runs various tasks for the Arena. Intended to be used more for dev work than in-game content.<br/><br/>
 * 
 * Current Commands
 * <ul>
 * 	<li>arena -cc : clears all creatures from the arena.</li>
 *  <li>arena -csi: clears all special items from the arena.</li>
 *  <li>arena -fr : readies all arena players.</li>
 *  <br />
 *  <li>arena -spawn [quantity]: spawns a set number of ArenaCreature Zombies</li>
 *  <li>arena -round [round]: sets (but does not start) the round number</li>
 *  <br />
 *  <li>arena -xp [player] [amount]: gives an arena player exp.</li>
 *  <li>arena -lvl [player] [level]: sets the level of an ArenaPlayer.</li>
 * </ul>
 */
public class ArenaCommand extends BasicCommand {

	public ArenaCommand(ArenaPlugin plugin) {
		super(plugin);
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		if (args.length == 0) {
			boolean e = GlobalW.getLocationData().getFile().exists();
			sender.sendMessage("Exists?: " + e);
			sender.sendMessage("List?: " + GlobalW.getLocationData().getDouble("player-spawns.location-0"));
			GlobalW.getLocationData().save();
			return true;
		}
		
		//Single Argument Commands 
		if (args.length == 1) {
			//Clear Arena Creatures
			if (args[0].equalsIgnoreCase("-cc")) {
				sender.sendMessage("Force clearing all creatures from the arena.");

				//Kill Entities
				for (ArenaCreature creature : GlobalW.getCreaturesInArena()) {
					creature.setCurHp(0);
				}
				
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
				return true;
			}
		} 
		
		//Handles two args
		if (args.length == 2) {
			int val = this.getValidInteger(args[1]);

			//Handles /arena -round [round]
			if (args[0].equalsIgnoreCase("-round")) {
				
				//Validate round
				if (val < 0) {
					sender.sendMessage(GlobalW.getChatErrorTag() + String.format("%s is not a valid round.", args[1]));
					return true;
				}
				
				GlobalW.setRound(val);
			}
			
			//Handles /arena -spawn [value]
			if (args[0].equalsIgnoreCase("-spawn")) {
				ArenaWave.createBasicWave(val, 1, EntityType.ZOMBIE).startWaveSequentially(1, 3);
				return true;
			}
		}
		
		//Handles three arguments
		if (args.length == 3) {
			//Validate args
			Player player = this.getPlayer(args[1]);
			int val = this.getValidInteger(args[2]);
			
			//Verify args
			if (player == null || val <= 0) {
				sender.sendMessage(GlobalW.getChatErrorTag() + String.format("Invalid argument(s): %s, %s", args[1], args[2]));
				return true;
			}
			
			ArenaPlayer arena_player = GlobalW.getArenaPlayer(player);
			
			if (arena_player == null) {
				sender.sendMessage(GlobalW.getChatErrorTag() + GlobalW.ErrorMsgs.NOT_ARENA_PLAYER);
				return true;
			}
						
			//Handles /arena -xp [player] [amount]
			if (args[0].equalsIgnoreCase("-xp")) {
				arena_player.addExp(val, false);
				sender.sendMessage(GlobalW.getChatTag() + String.format("Gave %s %d experience.", player.getName(), val));
				return true;
			}
			
			//Handles /arena -lvl [player] [amount]
			if (args[0].equalsIgnoreCase("-lvl")) {
				//Verify lvl <= 18
				if (val > 18) {
					sender.sendMessage(GlobalW.getChatErrorTag() + String.format("%d is not a valid arena player level.", val));
					return true;
				}
				
				//Set the level, clear exp
				arena_player.setLevel(val);
				player.setLevel(val);
				player.setExp(0);
				sender.sendMessage(GlobalW.getChatTag() + String.format("Set %s to level %d.", player.getName(), val));
				GlobalW.toPlayer(player, String.format("Your level has been set to %d.", val));
				return true;
			}
			
		}
		
		return false;
	}
}
