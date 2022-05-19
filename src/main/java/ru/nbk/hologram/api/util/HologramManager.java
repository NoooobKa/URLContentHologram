package ru.nbk.hologram.api.util;

import org.bukkit.Location;

public interface HologramManager {

	Hologram createHologram(Location location);
	
	HologramLine createLine();
	
}
