package ru.nbk.hologram.api;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import ru.nbk.hologram.api.util.Hologram;
import ru.nbk.hologram.api.util.HologramLine;
import ru.nbk.hologram.api.util.HologramManager;
import ru.nbk.hologram.nms.v1_16_R3.EntityLine;

public class HologramManagerImpl implements HologramManager{

	private LineFactory lineFactory;
	private List<Hologram> holograms;
	private HologramsHandler handler;
	
	public HologramManagerImpl(Plugin plugin) {
		this.lineFactory = new LineFactory();
		this.holograms = new ArrayList<>();
		this.handler = new HologramsHandler(holograms, plugin);
	}
	
	@Override
	public Hologram createHologram(Location location) {
		Hologram h = new HologramImpl(location);
		
		holograms.add(h);
		
		return h;
	}
	
	@Override
	public HologramLine createLine() {
		return lineFactory.product();
	}
	
	
	private static class LineFactory {
		
		private String version;
		
		public LineFactory() {
			String serverPackage = Bukkit.getServer().getClass().getPackage().getName();
			this.version = serverPackage.substring(serverPackage.lastIndexOf('.') + 1);
		}
		
		public HologramLine product() {
			HologramLine line = null;
			
			switch (version) {
			case "v1_16_R3" -> line = new EntityLine();

			default -> throw new IllegalArgumentException("Unsupported version: " + version);
			}
			
			return line;
		}
		
	}
	
	private static class HologramsHandler implements Listener {
		
		private List<Hologram> hologramList;
		
		public HologramsHandler(List<Hologram> hologramList, Plugin plugin) {
			this.hologramList = hologramList;
			Bukkit.getPluginManager().registerEvents(this, plugin);
		}
		
		@EventHandler
		public void onJoin(PlayerJoinEvent e) {
			hologramList.forEach(h -> {
				if (h.getLocation().getWorld() == e.getPlayer().getWorld() && !h.isHiden()) h.spawnForPlayer(e.getPlayer()); 
			});
		}
		
		@EventHandler
		public void onWorldChange(PlayerChangedWorldEvent e) {
			hologramList.forEach(h -> {
				if (h.getLocation().getWorld() == e.getPlayer().getWorld() && !h.isHiden()) h.spawnForPlayer(e.getPlayer()); 
			});
		}
		
	}
	
}
