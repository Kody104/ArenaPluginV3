package com.gmail.jpk.stu.arena;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class YMLReader {

	//The YML File this YMLReader reads
	private File file;
	private FileConfiguration config;
	
	public YMLReader(String path) {
		file = new File(path);
		
		if (!file.exists()) {
			file.getParentFile().mkdirs();
		}
		
		setConfig(YamlConfiguration.loadConfiguration(file));
		save();
	}
	
	/**
	 * Gets a list of locations from the YML File this YMLReads
	 * @param parent the name of the parent node
	 * @return the list or null if an error occurs
	 */
	private List<Location> getLocationList(String parent) {
		List<Location> list = new ArrayList<Location>();
		List<String> data = new ArrayList<String>();
		Set<String> keys = config.getConfigurationSection(parent).getKeys(false);
		
		World world = GlobalW.getInWorld();
		
		try {
		
			for (String string : keys) {
				data = this.getStringList(String.format("%s.%s", parent, string));
								
				double x = Double.valueOf(data.get(0));
				double y = Double.valueOf(data.get(1));
				double z = Double.valueOf(data.get(2));
				
				list.add(new Location(world, x, y, z));
			}
		
		} 
		
		catch (Exception e) {
			GlobalW.getPlugin().getLogger().warning(String.format("Failed to load location list: %s. Please check it is correct.", parent));			
			return null;
		}
		
		return list;
	}
	
	/**
	 * Gets the list of creature spawns listed in the YML. May return null.
	 * @return The list of locations or null (if a critical error occurs)
	 */
	public List<Location> getCreatureSpawns() {
		return getLocationList("creature-spawns");
	}
	
	/**
	 * Gets the list of player spawns listed in the YML. May return null.
	 * @return The list of locations or null (if a critical error occurs)
	 */
	public List<Location> getPlayerSpawns() {
		return getLocationList("player-spawns");
	}
	
	/**
	 * Gets a double from the specified path
	 * @param path the path
	 * @return a double (if present)
	 */
	public double getDouble(String path) {
		return config.getDouble(path);
	}
	
	/**
	 * Returns a Double list from the given path
	 * @param path the path of the value
	 * @return the String
	 */
	public List<Double> getDoubleList(String path) {
		return config.getDoubleList(path);
	}

	/**
	 * Returns an integer from the given path
	 * @param path the path of the value
	 * @return the integer
	 */
	public int getInt(String path) {
		return config.getInt(path);
	}
	
	/**
	 * Converts a list of doubles to a Location if present at the path.
	 * @param path the path of the list
	 */
	public Location getLocation(String path) {
		List<Double> list = getDoubleList(path);
		Location location = new Location(GlobalW.getInWorld(), list.get(0), list.get(1), list.get(2));
		
		return location;
	}
	
	/**
	 * Returns a String from the given path
	 * @param path the path of the value
	 * @return the String
	 */
	public String getString(String path) {
		return config.getString(path);
	}
	
	public List<String> getStringList(String path) {
		return config.getStringList(path);
	}
	
	/**
	 * Returns an object from the given path
	 * @param path the path to the value
	 * @return the value
	 */
	public Object getValue(String path) {
		return config.get(path);
	}
	
	/**
	 * Sets value of a given path in the file
	 * @param path the path to the node (example parent.child.value)
	 * @param value the specified value for that object
	 */
	public void setValue(String path, Object value) {
		config.set(path, value);	
	}
	
	/**
	 * Attempts to save the custom YML File
	 * @return true if successful
	 */
	public boolean save() {
		try {
			config.save(file);
		} 
		
		catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public FileConfiguration getConfig() {
		return config;
	}

	public void setConfig(FileConfiguration config) {
		this.config = config;
	}
	
}
