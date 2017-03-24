package BarrosCompany.ModestoDiscord;

import BarrosCompany.ModestoDiscord.Data.dbManager;
import BarrosCompany.ModestoDiscord.Handlers.EventHandler;
import sx.blah.discord.api.ClientBuilder;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.util.DiscordException;

public class ModestoBot {
	static String Token = "Mjk0NjM3MzA0NDQwNjg0NTQ2.C7YG8A.JBm_NYq6IJ4PKYn9mOUcDKn_8TE";
	
	public static IDiscordClient Bot;

	public static void main(String[] args) throws Exception {
		Bot = getClient(Token);
		Bot.getDispatcher().registerListener(new EventHandler());
		new dbManager();
	}
	
	public static IDiscordClient getClient(String Token) throws DiscordException{
		return new ClientBuilder().withToken(Token).login();
	}

}

