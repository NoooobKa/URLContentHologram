package ru.nbk.hologram;

import org.bukkit.plugin.java.JavaPlugin;

import com.google.inject.Guice;
import com.google.inject.Injector;

import co.aikar.commands.PaperCommandManager;
import ru.nbk.hologram.command.HologramCommands;
import ru.nbk.hologram.command.WrappedURL;

public class Main extends JavaPlugin{

	private Injector injector;
	private PaperCommandManager cmdManager;
	
	public void onEnable() {		
		this.injector = Guice.createInjector(new HologramModule());
		this.cmdManager = injector.getInstance(PaperCommandManager.class);
		registerCommands();
	}
	
	private void registerCommands() {
		cmdManager.getCommandContexts().registerContext(WrappedURL.class, WrappedURL.getContextResolver());
		
		cmdManager.registerCommand(injector.getInstance(HologramCommands.class));
	}
	
}
