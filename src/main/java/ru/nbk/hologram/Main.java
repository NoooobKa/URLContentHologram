package ru.nbk.hologram;

import org.bukkit.plugin.java.JavaPlugin;

import co.aikar.commands.PaperCommandManager;
import ru.nbk.hologram.api.HologramManagerImpl;
import ru.nbk.hologram.api.util.HologramManager;
import ru.nbk.hologram.command.HologramCommands;
import ru.nbk.hologram.command.WrappedURL;

public class Main extends JavaPlugin{

	private static Main INSTANCE;
	private static PaperCommandManager cmdManager;
	private HologramManager holoManager;
	
	public void onEnable() {
		INSTANCE = this;
		registerCommands();
		this.holoManager = new HologramManagerImpl(this);
	}
	
	public static Main getInstance() {
		return INSTANCE;
	}
	
	public HologramManager getHologramManager() {
		return holoManager;
	}
	
	private void registerCommands() {
		cmdManager = new PaperCommandManager(this);
		
		cmdManager.getCommandContexts().registerContext(WrappedURL.class, WrappedURL.getContextResolver());
		
		cmdManager.registerCommand(new HologramCommands());
	}
	
	
}
