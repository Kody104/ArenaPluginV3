package com.gmail.jpk.stu.arena;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

/**
 * A ChatSystem is a way to organize Players into groups to receive various levels of input.
 * By default there are three channels 
 *
 */
public class ChatSystem {
	
	//The name of the ChatSystem file
	private final String FILE_NAME = "chsys.ser";
	
	//The path to the file
	private String path;
	
	//Should the System message devs with error?
	private boolean showDebugOutput;
	
	//Should the System allow VIPs and higher to use Color Codes?
	private boolean colorCodesEnabled;
	
	//The HashMap that handles the Roles
	private HashMap<UUID, Role> players;
	
	/**
	 * Creates a ChatSystem from the given path. If the path does not contain the proper file,
	 * it will be made.
	 * @param path the location of the serialized HashMap
	 */
	public ChatSystem(String path) {
		setPath(path);
		initSerializedMap(path);
		setShowDebugOutput(true);
		setColorCodesEnabled(true);
	}
	
	/**
	 * Adds a player to the ChatSystem with a given role.
	 * @param uid the unique-ID of the player
	 * @param role the role to assign that player
	 */
	public void addPlayer(UUID uid, Role role) {
		this.players.put(uid, role);
	}
	
	/**
	 * Clears out all players from the System.
	 */
	public void clearAllPlayers() {
		this.players.clear();
	}
	
	/**
	 * Gets if the specified player is registered in the ChatSystem
	 * @return true if this is the case
	 */
	public boolean contains(UUID uid) {
		return this.players.containsKey(uid);
	}
	
	/**
	 * Closes the systems and saves all data.
	 */
	public void closeSystem() {
		try {
			File file = new File(path + FILE_NAME);
			
			//Check the file is there.
			if (!file.exists()) {
				//Make a new instance and return.
				file.getParentFile().mkdir();
			}
			
			//Write the object
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			oos.writeObject(players);
			
			//Close the streams
			oos.close();
			fos.close();
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets a Set of player in the ChatSystem by their role.
	 * @param role the role of the player
	 * @return the set of players
	 */
	public Set<UUID> getPlayersByRole(Role role) {
		Set<UUID> set = new HashSet<UUID>();
		
		//Get all keys by a value
		for (UUID key : this.players.keySet()) {
			if (this.players.get(key) == role) {
				set.add(key);
			}
		}
		
		return set;
	}
	
	/**
	 * Gets the role tag (for a player) as a formatted string.
	 * @param role the player's role
	 * @return the proper player tag
	 */
	public String getFormattedRoleTag(Role role) {
		switch (role) {
		case DEV:
			return (ChatColor.LIGHT_PURPLE + "<%s:DEV>" + ChatColor.RESET);
		case PLAYER:
			return ("<%s:Player>");
		case VIP:
			return (ChatColor.BLUE + "<%s:VIP>" + ChatColor.RESET);
		default:
			return (ChatColor.DARK_RED + "<%s:UNREGISTERED>" + ChatColor.RESET);
		}
	}
	
	/**
	 * Determines if the Player can speak in the targeted role's channel
	 * @param uid the uid of the player
	 * @param target the channel they wish to speak in
	 * @return true if they can speak
	 */
	public boolean hasPermissionToSpeak(UUID uid, Role target) {
		Role sender_role = getRole(uid);
		
		//Devs can send to any channel
		if (sender_role == Role.DEV) {
			return true;
		}
		
		//VIPs can send to VIP and Player channels
		else if (sender_role == Role.VIP && target != Role.DEV) {
			return true;
		}
		
		//Players can only send to Player channels
		else if (sender_role == Role.PLAYER && target == Role.PLAYER) {
			return true;
		}
		
		else {
			return false;
		}
		
	}
	
	/**
	 * Determines if a Player has the given role.
	 * @param uuid the Unique-ID of the player
	 * @param role the Role in question
	 * @return true if the player has this role
	 */
	public boolean hasRole(UUID uuid, Role role) {
		if (this.contains(uuid)) {
			return (role == this.players.get(uuid));
		}
		
		return false;
	}
	
	/**
	 * Initializes the HashMap. If no file is found at the given location, then it creates
	 * a new instance of the HashMap.
	 * @param path
	 */
	@SuppressWarnings("unchecked")
	private void initSerializedMap(String path) {
		File file = new File(path + "chsys.ser");
		
		//Check if the file exists
		if (!file.exists()) {
			//Make a new instance and return.
			players = new HashMap<UUID, Role>();
			return;
		}
		
		//Otherwise we load the file
		try {
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			players = (HashMap<UUID, Role>) (ois.readObject());
			
			ois.close();
			fis.close();
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Messages a single player. Does not include any chat tags.
	 * @param uid the uid of the target player
	 * @param format the format of the message
	 * @param objects the objects of the message
	 */
	public void messagePlayer(UUID uid, String format, Object... objects) {
		OfflinePlayer player = Bukkit.getOfflinePlayer(uid);
		
		//Send the message
		if (player != null && player.isOnline()) {
			player.getPlayer().sendMessage(String.format(format, objects));
		}
	}
	
	/**
	 * Messages the channel from the console
	 * @param role the targetted channel
	 * @param format the format of the message
	 * @param objects the objects of the message
	 */
	public void messageChannelFromConsole(Role role, String format, Object...objects) {
		Set<UUID> keys = getPlayersByRole(role);
		String tag = getFormattedRoleTag(role);
		
		this.messagePlayers(keys, tag + format, "Console", objects);
	}
		
	/**
	 * Messages all the players in a given set.
	 * @param keys the set of players
	 * @param format the format of the message
	 * @param objects the objects of the message
	 */
	public void messagePlayers(Set<UUID> keys, String format, Object... objects) {
		OfflinePlayer player;
		
		//Send each player the message
		for (UUID key : keys) {
			player = Bukkit.getOfflinePlayer(key);
			
			//Send message if player is online
			if (player.isOnline() && player != null) {
				Player target = player.getPlayer();
				target.sendMessage(String.format(format, objects));
			}
		}
	}
	
	/**
	 * Sends a messages to all Players in this ChatSystem
	 * @param format the format of the string
	 * @param objects any objects
	 */
	public void messageAllPlayers(UUID sender, String name, String format, Object...objects) {
		Set<UUID> keys = players.keySet();
		
		this.messagePlayers(keys, getAllFormattedChatTag() + format, name, objects);
	}
	
	/**
	 * Sends a message to all Players of a given role
	 * @param role the role the players should be
	 * @param format the format of the string
	 * @param objects the objects of the string
	 */
	public void messagePlayersByRole(Role role, String format, Object... objects) {
		Set<UUID> keys = this.getPlayersByRole(role);
		String tag = role.getChatTag();
		
		this.messagePlayers(keys, tag + format, objects);
	}
	
	/**
	 * Removes a Player from the Map if and only if they are a valid reference.
	 * @param uid the unique-ID of the Player
	 * @return true if successful
	 */
	public boolean removePlayer(UUID uid) {
		return (this.players.remove(uid) != null);
	}
	
	/**
	 * Replaces color codes with their respective colors
	 * @param message the raw message
	 * @return a decorated message (if applica
	 */
	public static String replaceCodes(String message) {
		if (message.split("&\\w").length <= 1) {
			return message;
		}
		
		return ChatColor.translateAlternateColorCodes('&', message);
	}
	
	/**
	 * Sends a message from a player to all players of a given role -- if the player has permission to do so.
	 * The player will be notified if they can't post in that channel.
	 * @param sender the sender of the message
	 * @param role the group they want to send the message to
	 * @param format the format of the message
	 * @param objects the objects of the message
	 */
	public void sendPlayerChatToRole(Player sender, Role role, String format, Object... objects) {
		UUID uid = sender.getUniqueId();
		String name = sender.getName();
		Role sender_role = getRole(uid);
		
		if (!hasPermissionToSpeak(uid, role)) {
			this.messagePlayer(uid, "You don't have permission to post in this channel.");
			this.messagePlayersByRole(Role.DEV, ChatColor.GRAY + "%s failed to send a message to " + role + " channel.", name);
			this.messagePlayersByRole(Role.DEV, ChatColor.GRAY + "Message: %s", format);
			return;
		} 
		
		this.sendPlayerChatToRole(sender, sender_role, format, objects);
	}
	
	/**
	 * Sends a message from a player to all of the appropriate channels.
	 * NOTE: If you want the player to message a 
	 * @param sender who sent the message
	 * @param format the format of the message
	 * @param objects the objects of the message
	 */
	public void sendPlayerChat(Player sender, String format, Object... objects) {
		UUID uid = sender.getUniqueId();
		String name = sender.getName();
		
		//In the unlikely that a player isn't registered, we'll alert the devs and send their message to all.
		if (!this.contains(uid)) {
			this.messagePlayersByRole(Role.DEV, ChatColor.GRAY + "%s is not registered in the chat system!", name);
			this.messageAllPlayers(sender.getUniqueId(), getAllFormattedChatTag() + format, name, objects);
			return;
		}
		
		//Grab necessary info
		Role role = this.getRole(uid);
		String tag = getFormattedRoleTag(role);
		
		switch (role) {
			case PLAYER:
			{
				messagePlayersByRole(Role.PLAYER, tag + format, name, objects);
				messagePlayersByRole(Role.VIP, tag + format, name, objects);
				messagePlayersByRole(Role.DEV, tag + format, name, objects);
				break;
			}
			
			case VIP:
			{
				messagePlayersByRole(Role.VIP, tag + format, name, objects);
				messagePlayersByRole(Role.DEV, tag  + format, name, objects);
				break;
			}
			case DEV:
			{
				messagePlayersByRole(Role.DEV, tag  + format, name, objects);
				break;
			}
			case ALL:
			{
				this.messageAllPlayers(uid, name, format, objects);
				break;
			}
		}
	}
	
	public static String getDefaultChatTag() {
		return ("<Arena-Chat> ");
	}
	
	/**
	 * Gets the All channel chat tag
	 * @return the Tag
	 */
	public static String getAllFormattedChatTag() {
		return (ChatColor.GOLD + "<%s:ALL> " + ChatColor.RESET);
	}
	
	/**
	 * Gets the Dev Channel chat tag
	 * @return the tag
	 */
	public static String getDevChatTag() {
		return (ChatColor.LIGHT_PURPLE + "<Arena-DEV> " + ChatColor.RESET);
	}
	
	/**
	 * Gets the Player Channel chat tag
	 * @return the tag
	 */
	public static String getPlayerChatTag() {
		return (ChatColor.LIGHT_PURPLE + "<Arena-Player> " + ChatColor.RESET);
	}
	
	/**
	 * Gets the VIP Channel Tag Tag
	 * @return the tag
	 */
	public static String getVIPChatTag() {
		return (ChatColor.BLUE + "<Arena-VIP> " + ChatColor.RESET);
	}
	
	//The Roles in the ChatSystem
	public enum Role { 
		PLAYER, VIP, DEV, ALL;
		
		public String getChatTag() {
			switch (this) {
			case DEV:
				return getDevChatTag();
			case PLAYER:
				return getPlayerChatTag();
			case VIP:
				return getVIPChatTag();
			case ALL:
				return getAllFormattedChatTag();
			default:
				return "";
			}
		}
 	}
	
	/**
	 * Gets the Role of a Player by their UID
	 * @param uid the unique-id of the player
	 * @return the Role or null if not found.
	 */
	public Role getRole(UUID uid) {
		return this.players.get(uid);
	}
 	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public HashMap<UUID, Role> getPlayers() {
		return players;
	}

	public void setPlayers(HashMap<UUID, Role> players) {
		this.players = players;
	}

	public String getFILE_NAME() {
		return FILE_NAME;
	}

	public boolean showDebugOutput() {
		return showDebugOutput;
	}

	public void setShowDebugOutput(boolean showDebugOutput) {
		this.showDebugOutput = showDebugOutput;
	}

	public boolean colorCodesEnabled() {
		return colorCodesEnabled;
	}

	public void setColorCodesEnabled(boolean colorCodesEnabled) {
		this.colorCodesEnabled = colorCodesEnabled;
	}
}
