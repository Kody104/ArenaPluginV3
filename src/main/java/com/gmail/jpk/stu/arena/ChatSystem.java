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
import org.bukkit.command.CommandSender;
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
	 * Adds a player to the ChatSystem with a given role. If the player already exists in the role,
	 * then their role is updated.
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
	 * Sends a messages to all Players in this ChatSystem
	 * @param name the name of the sender
	 * @param message the message to send everyone
	 */
	public void messageAllPlayers(String name, String message) {
		String tag = getAllChatTag(name);
		
		//Message all online players
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.sendMessage(tag + message);
		}		
	}
	
	
	/**
	 * Messages a CommandSender as the console.
	 * @param sender CommandSender
	 */
	public void messageCommandSender(CommandSender sender, String message) {
		sender.sendMessage(getDefaultChatTag() + message); 
	}
	
	/**
	 * Sends a message from the sender to the target. Use "null" for sender to indicate
	 * that the console is sending a message.
	 * @param sender the UUID of the sender, or null if it's the console
	 * @param target the target player 
	 * @param message the message the send wants to send.
	 */
	public void messagePlayer(UUID sender, Player target, String message) {
		//Get the sender's info
		String name;
		Role role;
		
		if (sender == null) {
			name = "Console";
			role = Role.CONSOLE;
		} else {
			name = Bukkit.getOfflinePlayer(sender).getName();
			role = getRole(sender);
		}
		
		target.sendMessage(role.getChatTag(name) + message);
	}
	
	/**
	 * Messages a player's default channel. Should not be used by the console.
	 * @param sender the UUID of the sender
	 * @param message the message they wish to send
	 */
	public void messagePlayerChannel(UUID sender, String message) {
		if (sender != null) {
			Role role = getRole(sender);
			this.messagePlayersByRole(sender, role, message);
			return;
		}
	}
		
	/**
	 * Messages all the players in a given set.
	 * @param sender the UUID of the sender or null if using the console
	 * @param message the message
	 * @param players the players to whom this message will be sent
	 */
	public void messagePlayers(UUID sender, String message, Set<UUID> players) {
		//Send a message to all the players in a list
		Player target;
		
		for (UUID uuid : players) {
			target = Bukkit.getOfflinePlayer(uuid).getPlayer();
			
			if (target != null) {
				this.messagePlayer(sender, target, message);
			}
		}
	}
	
	/**
	 * Sends a message to all Players of a given role. Will not execute if the sender
	 * does not have permission to speak in that channel.
	 * @param sender the UUID of the sender or null if using the console
	 * @param role the targeted role
	 * @param objects the message
	 */
	public void messagePlayersByRole(UUID sender, Role role, String message) {
		Set<UUID> players = getPlayersByRole(role);

		if (sender != null && hasPermissionToSpeak(sender, role)) {
			this.messagePlayers(sender, message, players);
		}
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
	 * @return a decorated message (if applicable)
	 */
	public static String replaceCodes(String message) {
		if (message.split("&\\w").length <= 1) {
			return message;
		}
		
		return ChatColor.translateAlternateColorCodes('&', message);
	}
	
	/**
	 * Gets the default chat tag
	 * @return &lt;Arena-Chat&gt;
	 */
	public static String getDefaultChatTag() {
		return ("<Arena-Chat> ");
	}
	
	/**
	 * Gets the All channel chat tag
	 * @param sender the name of the sender
	 * @return &lt;sender-name:ALL&gt;
	 */
	public static String getAllChatTag(String sender) {
		String tag = "<%s:all> ".replace("%s", sender);
		
		return (ChatColor.GOLD + tag + ChatColor.RESET);
	}
	
	/**
	 * Gets the tag for the console
	 * @return &lt;Console&gt;
	 */
	public static String getConsoleTag() {
		return (ChatColor.LIGHT_PURPLE + "<Console> " + ChatColor.RESET);
	}

	/**
	 * Gets the dev channel chat tag
	 * @param sender the name of the sender
	 * @return &lt;sender-name:dev&gt;
	 */
	public static String getDevChatTag(String sender) {
		String tag = "<%s:dev> ".replace("%s", sender);
		
		return (ChatColor.LIGHT_PURPLE + tag + ChatColor.RESET);
	}
	
	/**
	 * Gets the Player Channel chat tag
	 * @param sender the name of the sender
	 * @return &lt;sender-name:player&gt;
	 */
	public static String getPlayerChatTag(String sender) {
		String tag = "<%s:player> ".replace("%s", sender);
		
		return (ChatColor.LIGHT_PURPLE + tag + ChatColor.RESET);
	}
	
	/**
	 * Gets the unregistered player tag
	 * @param sender the name of the sender
	 * @return &lt;sender-name:UNREGISTERED&gt;
	 */
	public static String getUnregisteredTag(String sender) {
		return ("<%s:UNREGISTERED> ").replace("%s", sender);
	}
	
	/**
	 * Gets the VIP Channel Tag
	 * @param sender the name of the sender
	 * @return &lt;sender-name:vip&gt;
	 */
	public static String getVIPChatTag(String sender) {
		String tag = "<%s:vip> ".replace("%s", sender);
		return (ChatColor.BLUE + tag + ChatColor.RESET);
	}
	
	//The Roles in the ChatSystem
	public enum Role { 
		PLAYER, VIP, DEV, ALL, CONSOLE;
		
		public String toString() {
			switch (this) {
			case ALL:
				return "ALL";
			case CONSOLE:
				return "Console";
			case DEV:
				return "DEV";
			case PLAYER:
				return "Player";
			case VIP:
				return "VIP";
			default:
				return "UNREGISTERED";
			}
		}
		
		/**
		 * Gets a role by a string input. May return null.
		 * @param role the name of the role
		 * @return the role or null if not found
		 */
		public static Role getRoleByString(String role) {
			switch (role.toUpperCase()) {
				case "ALL":
					return ALL;
				case "CONSOLE:":
					return CONSOLE;
				case "DEV":
					return DEV;
				case "PLAYER":
					return PLAYER;
				case "VIP":
					return VIP;
				default:
					return null;
			}
		}
		
		/**
		 * Gets the ChatTag associated with this role.
		 * @param name
		 * @return
		 */
		public String getChatTag(String name) {
			switch (this) {
			case ALL:
				return getAllChatTag(name);
			case CONSOLE:
				return getConsoleTag();
			case DEV:
				return getDevChatTag(name);
			case PLAYER:
				return getPlayerChatTag(name);
			case VIP:
				return getVIPChatTag(name);
			default:
				return getUnregisteredTag(name);
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
