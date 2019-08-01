package com.gmail.jpk.stu.arena;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
 * A ChatSystem controls player chat on the server. There are three channels users can speak on.<br />
 * The default (player) channel is open to all.<br />
 * The VIP channel is restricted to VIPs and Developers.<br />
 * The Dev channel is restricted to Devs.<br />
 * The system will also save and load to file.
 */
public class ChatSystem {
	
	//The ArenaPlugin this ChatSystem works for
	private ArenaPlugin plugin;
	
	//The name of the ChatSystem file
	private final String FILE_NAME = "chsys.ser";
	
	//The location of the ChatSystem file
	private String path;
	
	//The Map with all of the Players
	private HashMap<UUID, Role> players;
	
	//The lists of who is a VIP and who is a DEV
	private List<UUID> vips;
	private List<UUID> devs;
	
	/**
	 * Creates a ChatSystem for the given Plugin and loads (or creates) the necessary files at the specified location.
	 * @param plugin the plugin this ChatSystem works for
	 * @param path the location of the ChatSystem file
	 */
	public ChatSystem(ArenaPlugin plugin, String path) {
		setPlugin(plugin);
		setPath(path + FILE_NAME);
		setDevs(new ArrayList<UUID>());
		setVips(new ArrayList<UUID>());
		
		initialize();
	}
	
	/**
	 * Adds a Player to the ChatSystem. Will overwrite a player's role if they already exist in the system.
	 * @param uid who to add to the ChatSystem
	 * @param role what their (new) role will be
	 */
	public void addPlayer(UUID uid, Role role) {
		switch (role) {
			case DEV:
			{
				devs.add(uid);
				break;
			}
			case PLAYER:
			{
				break;
			}
			case VIP:
			{
				vips.add(uid);
				break;
			}
			
			default:
			{
				return;
			}
		}
		
		players.put(uid, role);
	}
	
	/**
	 * Closes the ChatSystem
	 * @return true if successful 
	 */
	public boolean close() {
		try {
			saveFile();
			return true;
		}
		
		catch (IOException e) {
			log(String.format("FAILED TO SAVE %s.", FILE_NAME));
			return false;
		}
	}
	
	/**
	 * Determines if the Player is in the ChatSystem.
	 * @param uid who to check 
	 * @return true if they are in the ChatSystem
	 */
	public boolean contains(UUID uid) {
		return (players.containsKey(uid));
	}
	
	/**
	 * Gets the ChatSystem's Tag
	 * @return the tag, "Arena-ChatSystem"
	 */
	public String getChatSystemTag() {
		return ("<Arena-ChatSystem> ");
	}
	
	public Role getRole(UUID uid) {
		return (players.get(uid));
	}
	
	/**
	 * Attempts to initialize the ChatSystem.
	 * @return true if successful
	 */
	public boolean initialize() {
		File file = new File(path);
		
		try {
			if (!file.exists()) {
				//File doesn't exist, we'll create it.
				log(String.format("%s was not found. Attempting to create the file.", FILE_NAME));
				file.getParentFile().mkdirs();
				
				//Initialize players
				players = new HashMap<UUID, Role>();
				
				//Write the object and exit.
				saveFile();
				
				log(String.format("%s was successfully created.", FILE_NAME));
				
				return true;
			}
			
			loadFile();
			populateLists();
			
			log(String.format("Loaded %d unique IDs from %s. There are %d vips and %d devs.", players.size(), FILE_NAME, vips.size(), devs.size()));
			return true;
		}
		
		catch (Exception e) {
			log("FAILED TO INITIALIZE CHATSYSTEM!");
			e.printStackTrace();
			return false;
		}	
	}
	
	/**
	 * Loads the chsys.ser file to initialize players.
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	private void loadFile() throws IOException, ClassNotFoundException {
		FileInputStream fis = new FileInputStream(path);
		ObjectInputStream ois = new ObjectInputStream(fis);
		
		players = (HashMap<UUID, Role>) ois.readObject();
		
		fis.close();
		ois.close();
	}
	
	/**
	 * Logs a message to the console
	 * @param message what to say
	 */
	public void log(String message) {
		plugin.getLogger().info(message);
	}
	
	/**
	 * Messages all the players on the Server
	 * @param sender who sent the message (or null if the console)
	 * @param message what they said
	 */
	public void messageAll(UUID sender, String message) {	
		OfflinePlayer off = Bukkit.getOfflinePlayer(sender);
		Role role = (sender == null) ? (Role.CONSOLE) : (getRole(sender));
		String name = (sender == null) ? ("Console") : (off.getName());
		String tag = role.getTag(name);
		
		//Message the players
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.sendMessage(tag + message);
		}
	}
	
	
	/**
	 * Messages the devs (if any)
	 * @param sender who sent the message (or null if console)
	 * @param message what they said
	 */
	public void messageDevs(UUID sender, String message) {
		OfflinePlayer off = Bukkit.getOfflinePlayer(sender);
		Role role = (sender == null) ? (Role.CONSOLE) : (getRole(sender));
		String name = (sender == null) ? ("Console") : (off.getName());
		String tag = role.getTag(name + ":dev");
		
		//Message the devs
		for (UUID uid : devs) {
			off = Bukkit.getOfflinePlayer(uid);
			
			if (off != null && off.isOnline()) {
				((Player) off).sendMessage(tag + message);
			}
		}
	}
	
	/**
	 * Messages the vips and devs (if any)
	 * @param sender who sent the message (or null if console)
	 * @param message what they said
	 */
	public void messageVips(UUID sender, String message) {
		OfflinePlayer off = Bukkit.getOfflinePlayer(sender);
		Role role = (sender == null) ? (Role.CONSOLE) : (getRole(sender));
		String name = (sender == null) ? ("Console") : (off.getName());
		String tag = role.getTag(name + ":vip");
		
		List<UUID> combined = new ArrayList<UUID>();
		combined.addAll(devs);
		combined.addAll(vips);
		
		//Message the devs
		for (UUID uid : combined) {
			off = Bukkit.getOfflinePlayer(uid);
			
			if (off != null && off.isOnline()) {
				((Player) off).sendMessage(tag + message);
			}
		}
	}
	
	/**
	 * Populates the vip and dev lists.
	 */
	private void populateLists() {
		for (UUID uid : players.keySet()) {
			if (players.get(uid) == Role.VIP) {
				vips.add(uid);
			}
			
			else if (players.get(uid) == Role.DEV) {
				devs.add(uid);
			}
		}
	}
	
	/**
	 * Attempts to remove a Player from the ChatSystem. Returns false if they weren't found.
	 * @param uid who to remove
	 * @return true if they were removed
	 */
	public boolean removePlayer(UUID uid) {
		devs.remove(uid);
		vips.remove(uid);
		return (players.remove(uid) != null);
	}
	
	/**
	 * Saves the players HashMap to a file.
	 * @throws IOException
	 */
	public void saveFile() throws IOException {
		FileOutputStream fos = new FileOutputStream(path);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		
		oos.writeObject(players);
		
		fos.close();
		oos.close();		
	}
	
	/**
	 * Sends a message to the Player from the ChatSystem. The player does not need to be in the ChatSystem to send the message.
	 * @param player who should receive this message
	 */
	public void toPlayer(Player player, String message) {
		player.sendMessage(getChatSystemTag() + message);
	}

	public List<UUID> getDevs() {
		return devs;
	}

	public void setDevs(List<UUID> devs) {
		this.devs = devs;
	}

	/**
	 * The built-in ChatSystem roles.
	 *
	 */
	public enum Role {
		PLAYER,
		VIP,
		DEV,
		CONSOLE;
		
		/**
		 * Gets the tag associated with this Role
		 * @param name who the tag is for
		 * @return the decorated tag
		 */
		public String getTag(String name) {
			switch (this) {
				case CONSOLE:
				{
					return (ChatColor.GOLD + "<" + ChatColor.DARK_PURPLE + name + ChatColor.GOLD + "> " + ChatColor.RESET);
				}
				case DEV:
				{
					return (ChatColor.GOLD + "<" + ChatColor.LIGHT_PURPLE + name + ChatColor.GOLD + "> " + ChatColor.RESET);
				}
				case VIP:
				{
					return (ChatColor.GOLD + "<" + ChatColor.BLUE + name + ChatColor.GOLD + "> " + ChatColor.RESET);
				}
				default:
					return ("<" + name + "> ");
			}
		}
		
		/**
		 * Gets a Role by a String input
		 * @param role which role you need
		 * @return the Role or null if not found
		 */
		public static Role getRoleByString(String role) {
			if (role.equalsIgnoreCase("console")) {
				return CONSOLE;
			}
			
			else if (role.equalsIgnoreCase("player")) {
				return PLAYER;
			}
			
			else if (role.equalsIgnoreCase("vip")) {
				return VIP;
			}
			
			else if (role.equalsIgnoreCase("dev")) {
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
	
	public ArenaPlugin getPlugin() {
		return plugin;
	}

	public void setPlugin(ArenaPlugin plugin) {
		this.plugin = plugin;
	}

	public List<UUID> getVips() {
		return vips;
	}

	public void setVips(List<UUID> vips) {
		this.vips = vips;
	}

	public void toCommandSender(CommandSender sender, String message) {
		sender.sendMessage(getChatSystemTag() + message);
	}	
}