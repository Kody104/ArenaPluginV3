package com.gmail.jpk.stu.arena;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
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
	
	//The unique channels for this server
	private List<UUID> vips;
	private List<UUID> devs;
	
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
	 * Adds a player to the system. Will overwrite the Player if they are already in the system.
	 * @param uid the UUID of the player to add
	 * @param role what their role should be
	 */
	public void addPlayer(UUID uid, Role role) {
		if (uid != null && role != null) {
			players.put(uid, role);
			
			if (role == Role.VIP) {
				vips.add(uid);
			}
			
			else if (role == Role.DEV) {
				devs.add(uid);
			}
		}
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
	 * Determines if the system contains a specified Player
	 * @param uid the UUID of the player
	 * @return true if they are in the system
	 */
	public boolean contains(UUID uid) { 
		return (players.containsKey(uid));
	}
	
	/**
	 * @return "Console"
	 */
	private static String getConsoleName() {
		return ("Console");
	}
	
	private static String getChatSystemTag() {
		return ("<Arena-Chat> ");
	}
	
	/**
	 * Gets the role of the player. May return null if the player isn't in the ChatSystem.
	 * @param uid the unique id of the player
	 * @return the role
	 */
	public Role getRole(UUID uid) {
		return players.get(uid);
	}
	
	/**
	 * Determines if a Player is a dev
	 * @param uid the UUID of the player
	 * @return true if they're a dev
	 */
	public boolean isDev(UUID uid) {
		return devs.contains(uid);
	}
	
	/**
	 * Determines if a Player is a VIP
	 * @param uid the UUID of the player
	 * @return true if they're a VIP
	 */
	public boolean isVip(UUID uid) {
		return vips.contains(uid);
	}
	
	/**
	 * Messages all of the players on the server. The console shouldn't use this function.
	 * @param sender who sent the message 
	 * @param message what they said
	 */
	public boolean messageAll(UUID sender, String message) {
		//Console shouldn't use this method
		if (sender == null || !players.containsKey(sender)) {
			return false;
		}
		
		//Grab name and tag
		String name = Bukkit.getOfflinePlayer(sender).getName();
		Role role = (getRole(sender) == null ? Role.PLAYER : getRole(sender));
		String tag = String.format("<%s> ", role.getFormattedName(name));
		
		//Message the players
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.sendMessage(tag + message);
		}
		
		return true;
	}
	
	/**
	 * Sends a message to the Dev channel
	 * @param sender who is sending the message (use null if sending from console)
	 * @param message what they are sending
	 * @return true if the message was sent
	 */
	public boolean messageDevChannel(UUID sender, String message) {
		//Can the sender message this channel?
		if (sender != null && !isDev(sender)) {
			return false;
		}
		
		//Get name and tag.
		OfflinePlayer off;
		String name = (sender == null) ? getConsoleName() : (Bukkit.getOfflinePlayer(sender).getName());
		Role role = (sender == null) ? (Role.CONSOLE) : (Role.DEV);
		String tag = String.format("<%s:dev> ", role.getFormattedName(name));
		
		
		//Message the players
		for (UUID uid : devs) {
			off = Bukkit.getOfflinePlayer(uid);
			
			if (off != null && off.isOnline()) {
				((Player) off).sendMessage(tag + message);
			}
		}
		
		return true;
	}
	
	/**
	 * Sends a message to the VIP (and dev) channel.
	 * @param sender who is sending the message (use null if sending from console)
	 * @param message what they are sending
	 * @return true if the message was sent 
	 */
	public boolean messageVIPChannel(UUID sender, String message) {
		List<UUID> combined = new ArrayList<UUID>();
		combined.addAll(vips);
		combined.addAll(devs);

		OfflinePlayer off;
		Role role = null;
		String name = "";
		String tag = "";
				
		//Did the console send this message?
		if (sender == null) {
			role = Role.CONSOLE;
			name = getConsoleName();
		}
		
		else if (combined.contains(sender)) {
			role = (isVip(sender)) ? (Role.VIP) : (Role.DEV);
			name = Bukkit.getOfflinePlayer(sender).getName();
		} 
		
		else {
			return false;
		}
		
		//Grab the name and the tag
		tag = String.format("<%s:vip> ", role.getFormattedName(name));
		
		//Message the players
		for (UUID uid : combined) {
			off = Bukkit.getOfflinePlayer(uid);
			
			//Check the player is online
			if (off != null && off.isOnline()) {
				((Player) off).sendMessage(tag + message);
			}
		}
		
		return true;
	}
	
	/**
	 * Initializes the HashMap. If no file is found at the given location, then it creates
	 * a new instance of the HashMap.
	 * @param path
	 */
	@SuppressWarnings("unchecked")
	private void initSerializedMap(String path) {
		File file = new File(path + "players.ser");
		
		//Check if the file exists
		if (!file.exists()) {
			//Make a new instance and return.
			players = new HashMap<UUID, Role>();
			devs = new ArrayList<UUID>();
			vips = new ArrayList<UUID>();
			return;
		}
		
		//Otherwise we load the file and populate the lists.
		try {
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			players = (HashMap<UUID, Role>) (ois.readObject());
			
			//Populate channels
			for (UUID uid : players.keySet()) {
				if (players.get(uid) == Role.VIP) {
					vips.add(uid);
				} 
				
				else if (players.get(uid) == Role.DEV) {
					devs.add(uid);
				}
			}
			
			ois.close();
			fis.close();
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Removes a player from the system.
	 * @param uid the UUID of the player
	 * @return if successful
	 */
	public boolean removePlayer(UUID uid) {
		return (uid != null && players.remove(uid) != null);		
	}
	
	/**
	 * Sends a message to the player from the ChatSystem
	 * @param player the target player
	 * @param message the message
	 */
	public static void toPlayer(Player player, String message) {
		player.sendMessage(String.format("%s%s", getChatSystemTag(), message));
	}
	
	/**
	 * Sends a message to a CommandSender from the ChatSystem
	 * @param sender the CommandSender
	 * @param message what to say
	 */
	public static void toCommandSender(CommandSender sender, String message) {
		sender.sendMessage(String.format("%s%s", getChatSystemTag(), message));
	}
	
	public enum Role {
		CONSOLE,
		PLAYER,
		VIP,
		DEV;
		
		/**
		 * Returns the name in the color associated with this Role
		 * @param name the name of the sender
		 * @return the name in its proper color
		 */
		public String getFormattedName(String name) {
			switch (this) {
				case VIP:
					return (ChatColor.BLUE + name + ChatColor.RESET);
				case CONSOLE:
					return (ChatColor.DARK_PURPLE + name + ChatColor.RESET);
				case DEV:
					return (ChatColor.LIGHT_PURPLE + name + ChatColor.RESET);
				default:
					return name;
			}
		}
		
		/**
		 * Gets a Role by the String input.
		 * @param name the name of the role
		 * @return the role or null if not found
		 */
		public static Role getRoleByString(String name) {
			if (name.equalsIgnoreCase("console")) {
				return CONSOLE;
			}
			
			else if (name.equalsIgnoreCase("player")) {
				return PLAYER;
			}
			
			else if (name.equalsIgnoreCase("vip")) {
				return VIP;
			}
			
			else if (name.equalsIgnoreCase("dev")) {
				return DEV;
			}
			
			else {
				return null;
			}
		}
	}
 	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
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
