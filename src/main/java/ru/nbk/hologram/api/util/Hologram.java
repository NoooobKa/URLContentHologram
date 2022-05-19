package ru.nbk.hologram.api.util;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface Hologram {

	public UUID getUUID();
	
	Location getLocation();
	
	void teleport(Location location);
	
	void addLine(HologramLine line);
	
	void addLine(String text);
	
	HologramLine getLine(int index);
	
	void setVisible(boolean visible);
	
	boolean isHiden();
	
	void spawnForPlayer(Player p);
}
