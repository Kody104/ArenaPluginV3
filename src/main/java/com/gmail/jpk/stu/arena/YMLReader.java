package com.gmail.jpk.stu.arena;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class YMLReader {

	//The YML File this YMLReader reads
	private File file;
	private FileConfiguration config;
	
	public YMLReader(String path) {
		file = new File(path);
		config = YamlConfiguration.loadConfiguration(file);
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
	
	/**
	 * Returns a String from the given path
	 * @param path the path of the value
	 * @return the String
	 */
	public double getDouble(String path) {
		return config.getDouble(path);
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
	 * Returns a String from the given path
	 * @param path the path of the value
	 * @return the String
	 */
	public String getString(String path) {
		return config.getString(path);
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
