package com.gmail.jpk.stu.Entities;

import org.bukkit.entity.Player;

public class ArenaPlayer extends ArenaEntity {

	private Player mPlayer;
	private PlayerRole classRole;
	private int Level;
	
	public ArenaPlayer(Player mPlayer, PlayerRole role) {
		this.mPlayer = mPlayer;
		this.classRole = role;
		this.Level = 1;
		switch(role) {
			case BLIGHT_ARCHER:
			{
				setMaxHp(458.0d);
				setCurHp(getMaxHp());
				setAtk(35.0d);
				setMag(0.0d);
				setDef(0.0d);
				setRes(0.0d);
				setCritHitChance(0.0d);
				setCritHitMultiplier(1.15d);
				setCdr(0);
				setTenacity(0);
				break;
			}
			case BRUTE_JUGGERNAUT:
			{
				setMaxHp(632.0d);
				setCurHp(getMaxHp());
				setAtk(35.0d);
				setMag(0.0d);
				setDef(0.0d);
				setRes(0.0d);
				setCritHitChance(0.0d);
				setCritHitMultiplier(1.15d);
				setCdr(0);
				setTenacity(0);
				break;
			}
			case CLERIC:
			{
				setMaxHp(440.0d);
				setCurHp(getMaxHp());
				setAtk(30.0d);
				setMag(0.0d);
				setDef(0.0d);
				setRes(0.0d);
				setCritHitChance(0.0d);
				setCritHitMultiplier(1.15d);
				setCdr(0);
				setTenacity(0);
				break;
			}
			case FIGHTER:
			{
				setMaxHp(502.0d);
				setCurHp(getMaxHp());
				setAtk(55.0d);
				setMag(0.0d);
				setDef(0.0d);
				setRes(0.0d);
				setCritHitChance(0.0d);
				setCritHitMultiplier(1.15d);
				setCdr(0);
				setTenacity(0);
				break;
			}
			case HOLY_KNIGHT:
			{
				setMaxHp(566.0d);
				setCurHp(getMaxHp());
				setAtk(55.0d);
				setMag(0.0d);
				setDef(0.0d);
				setRes(0.0d);
				setCritHitChance(0.0d);
				setCritHitMultiplier(1.15d);
				setCdr(0);
				setTenacity(0);
				break;
			}
			case UTILITY_MAGE:
			{
				setMaxHp(420.0d);
				setCurHp(getMaxHp());
				setAtk(35.0d);
				setMag(0.0d);
				setDef(0.0d);
				setRes(0.0d);
				setCritHitChance(0.0d);
				setCritHitMultiplier(1.15d);
				setCdr(0);
				setTenacity(0);
				break;
			}
		}
	}
	
	public void LevelUp() {
		if(Level + 1 < 19) {
			Level++;
			switch(classRole) {
				case BLIGHT_ARCHER:
				{
					setMaxHp(getMaxHp() + 27.4d);
					setAtk(getAtk() + 4.1d);
					break;
				}
				case BRUTE_JUGGERNAUT:
				{
					setMaxHp(getMaxHp() + 33.6d);
					setAtk(getAtk() + 3.8d);
					break;
				}
				case CLERIC:
				{
					setMaxHp(getMaxHp() + 29.6d);
					setAtk(getAtk() + 3.8d);
					break;
				}
				case FIGHTER:
				{
					setMaxHp(getMaxHp() + 28.9d);
					setAtk(getAtk() + 3.9d);
					break;
				}
				case HOLY_KNIGHT:
				{
					setMaxHp(getMaxHp() + 32.8d);
					setAtk(getAtk() + 4.2d);
					break;
				}
				case UTILITY_MAGE:
				{
					setMaxHp(getMaxHp() + 28.3d);
					setAtk(getAtk() + 2.8d);
					break;
				}
			}
		}
	}
}
