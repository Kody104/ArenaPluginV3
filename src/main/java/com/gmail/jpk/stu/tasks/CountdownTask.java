package com.gmail.jpk.stu.tasks;

import org.bukkit.scheduler.BukkitRunnable;

import com.gmail.jpk.stu.arena.GlobalW;

/**
 * Creates a Task that counts downs and alerts the players if desired.
 * @author ccm3227
 *
 */
public class CountdownTask extends BukkitRunnable {

	private String alert_message;
	private boolean alert_players;
	private int seconds;
	
	/**
	 * Creates a CountdownTask that alerts the arena players (if desired) and runs for a set number of seconds.
	 * @param alert_players if the players should be notified of the remaining time.
	 * @param seconds how long the countdown should be
	 * @param message What the alert message should be
	 */
	public CountdownTask(String message, boolean alert_players, int seconds) {
		setAlertMessage(message);
		setAlertPlayers(alert_players);
		setSeconds(seconds);
	}
	
	/**
	 * Creates a CountdownTask (with no custom alert message) that alerts the arena players (if desired) and runs for a set number of seconds.
	 * @param alert_players if the players should be notified of the remaining time.
	 * @param seconds how long the countdown should be
	 */
	public CountdownTask(boolean alert_players, int seconds) {
		this("", alert_players, seconds);
	}
	
	/**
	 * Creates a Countdown task with no custom alert messages that doesn't alert the arena players and runs for a set number of seconds.
	 * @param seconds how long the countdown should be
	 */
	public CountdownTask(int seconds) {
		this("", true, seconds);
	}
	
	@Override
	public void run() {
		if (--seconds > 0) {
			if (alert_players) {
				GlobalW.toArenaPlayers(String.format("%s%d", alert_message, seconds));
			}
			
			this.runTaskLater(GlobalW.getPlugin(), 1000);
		} 
	}
	
	public String getAlertMessage() {
		return alert_message;
	}
	
	public boolean getAlertPlayers() {
		return alert_players;
	}
	
	public int getSeconds() {
		return seconds;
	}
	
	public void setAlertMessage(String alert_message) {
		this.alert_message = alert_message;
	}
	
	public void setAlertPlayers(boolean alert_players) {
		this.alert_players = alert_players;
	}
	
	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}
}
