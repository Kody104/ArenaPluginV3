package com.gmail.jpk.stu.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.gmail.jpk.stu.Entities.ArenaCreature;
import com.gmail.jpk.stu.Entities.ArenaPlayer;
import com.gmail.jpk.stu.abilities.Abilities;
import com.gmail.jpk.stu.abilities.Ability;
import com.gmail.jpk.stu.arena.ArenaPlugin;
import com.gmail.jpk.stu.arena.GlobalW;
import com.gmail.jpk.stu.items.AbilityItem;

public class GiveAbilityItemCommand extends BasicCommand {

	public GiveAbilityItemCommand(ArenaPlugin plugin) {
		super(plugin);
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		// Check if sender is player
		ArenaPlayer arena_player = getArenaPlayer(sender);
		
		// Validate
		if(arena_player == null) {
			return true; // Exit
		}
		
		Player player = (Player) sender;
		
		if(args.length < 2) {
			GlobalW.toPlayerError(player, "Not enough arguments.");
			return false;
		}
		
		Ability a = Abilities.getAbilityByName(args[0]);
		
		@SuppressWarnings("deprecation")
		Player target_player = Bukkit.getPlayer(args[1]);
		
		// If the ability doesn't exists or the player doesn't exist
		if(a == null || target_player == null) {
			GlobalW.toPlayer(player, String.format("Failed to give %s %s", args[1], args[0]));
			return true; // Exit
		}
		
		//Give ability item to player
		AbilityItem aItem = new AbilityItem(9000, a);
		target_player.getInventory().addItem(aItem);
		GlobalW.toPlayer(player, String.format("Gave %s %s", args[1], args[0]));
		GlobalW.toPlayer(target_player, String.format(ChatColor.GREEN + "You have received %s!", aItem.getDisplayName()));
		arena_player.getAllAbilties().add(aItem);
		
		//DEBUG 
		
		LivingEntity le = (LivingEntity) player.getWorld().spawnEntity(player.getLocation(), EntityType.ZOMBIE);
		GlobalW.getCreaturesInArena().add(new ArenaCreature(le, 1, null));
		
		return true;
	}

}
