package ru.nbk.hologram;

import org.bukkit.plugin.java.JavaPlugin;

import com.google.inject.AbstractModule;

import co.aikar.commands.PaperCommandManager;
import ru.nbk.hologram.api.HologramManagerImpl;
import ru.nbk.hologram.api.util.HologramManager;
import ru.nbk.hologram.command.HologramCommands;

public class HologramModule extends AbstractModule{

	@Override
	protected void configure() {
		Main main = JavaPlugin.getPlugin(Main.class);

		bind(Main.class).toInstance(main);
		bind(HologramManager.class).toInstance(new HologramManagerImpl(main));
		bind(HologramCommands.class).toInstance(new HologramCommands());
		bind(PaperCommandManager.class).toInstance(new PaperCommandManager(main));
	}
	
}
