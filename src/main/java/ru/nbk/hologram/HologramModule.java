package ru.nbk.hologram;

import com.google.inject.AbstractModule;

import ru.nbk.hologram.api.HologramManagerImpl;
import ru.nbk.hologram.api.util.HologramManager;

public class HologramModule extends AbstractModule{

	private Main main;
	
	HologramModule(Main main) {
		this.main = main;
	}
	
	@Override
	protected void configure() {
		bind(Main.class).toInstance(main);
		bind(HologramManager.class).to(HologramManagerImpl.class);
	}
	
}
