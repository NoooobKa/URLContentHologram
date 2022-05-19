package ru.nbk.hologram.command;

import java.util.concurrent.CompletableFuture;

import org.bukkit.entity.Player;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import ru.nbk.hologram.Main;
import ru.nbk.hologram.api.util.Hologram;

@CommandAlias("addholo")
@CommandPermission("addholo.perm")
public class HologramCommands extends BaseCommand{
	
	@Default
	public void onHoloAdd(Player p, WrappedURL wrapped) {
		if (wrapped == null) {
			p.sendMessage("Укажите корректную ссылку");
			return;
		}
		
		CompletableFuture.supplyAsync(() -> wrapped.getContent()).thenAcceptAsync(content -> {
						
			if (content == null) {
				p.sendMessage("При получении данных произашла ошибка...");
				return;
			}
			
			Hologram h = Main.getInstance().getHologramManager().createHologram(p.getLocation());
			
			h.addLine(content);
			
			p.sendMessage("Голограмма успешно создана");
		}).exceptionally(e -> {
			e.printStackTrace();
			return null;
		});
		
	}
	
}
