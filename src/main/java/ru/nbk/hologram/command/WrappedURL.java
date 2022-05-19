package ru.nbk.hologram.command;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.Jsoup;

import co.aikar.commands.BukkitCommandExecutionContext;
import co.aikar.commands.contexts.ContextResolver;

public class WrappedURL {

	private URL url;
	
	public WrappedURL(URL url) {
		this.url = url;
	}
	
	public URL getURL() {
		return url;
	}
	
	public String getContent() {
		String content = null;
		
		try {
			content = Jsoup.connect(url.toString()).get().body().text();
		} catch (IOException e) {}
		
		return content;
	}
	
	public static ContextResolver<WrappedURL, BukkitCommandExecutionContext> getContextResolver() {
		return c -> {
			
			String first = c.getFirstArg();
			WrappedURL con = null;
			
			try {
				con = new WrappedURL(new URL(first));
			} catch (MalformedURLException e) {}
			
			return con;
		};
	}
	
}
