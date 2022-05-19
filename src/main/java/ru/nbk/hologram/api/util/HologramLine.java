package ru.nbk.hologram.api.util;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface HologramLine extends Nameable{

	Location getLocation();
	
	void teleport(Location location);
	
	void spawn(Player player);
	
	void remove(Player player);
	
	void show();
	
	void hide();
	
	void refresh();
		
}
