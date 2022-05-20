package ru.nbk.hologram.api;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import ru.nbk.hologram.api.HologramManagerImpl.LineFactory;
import ru.nbk.hologram.api.util.Hologram;
import ru.nbk.hologram.api.util.HologramLine;

public class HologramImpl implements Hologram{

	private static final double SPACE = 0.3D;
	
	private LineFactory lineFactory;
	private final UUID id;
	private Location location;
	private List<HologramLine> lines;
	private boolean visible;
	
	
	public HologramImpl(Location location, LineFactory lineFactory) {
		this.lineFactory = lineFactory;
		this.id = UUID.randomUUID();
		this.location = location;
		this.lines = new ArrayList<>();
	}
	
	@Override
	public UUID getUUID() {
		return id;
	}

	@Override
	public Location getLocation() {
		return location;
	}

	@Override
	public void teleport(Location location) {
		this.location = location;
		lines.forEach(line -> line.teleport(location));
	}

	@Override
	public void addLine(HologramLine line) {
		lines.add(line);
		
		line.teleport(location.clone().add(0, SPACE * lines.size(), 0));
		location.getWorld().getPlayers().forEach(p -> line.spawn(p));
		line.refresh();
	}

	@Override
	public HologramLine getLine(int index) {
		return lines.get(index);
	}

	@Override
	public void addLine(String text) {
		HologramLine line = lineFactory.product();
		
		line.setName(text);
		
		addLine(line);
	}

	@Override
	public boolean isHiden() {
		return visible;
	}

	@Override
	public void setVisible(boolean visible) {
		this.visible = visible;
		
		lines.forEach(line -> {
			line.setVisible(visible);
		});
	}

	@Override
	public void spawnForPlayer(Player p) {
		lines.forEach(line -> line.spawn(p));
	}
	
}
