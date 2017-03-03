package BarrosCompany.ModestoDiscord;

import BarrosCompany.ModestoDiscord.Handlers.EventHandler;
import sx.blah.discord.api.ClientBuilder;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.util.DiscordException;

public class ModestoBot {
	static String Token = "MjgxNTcwNjc3Mjk4MDM2NzM2.C4Z6lg.5deyw9buVaqMxS-VIRnrFjpws8s";
	
	public static IDiscordClient Bot;

	public static void main(String[] args) throws Exception {
		Bot = getClient(Token);
		Bot.getDispatcher().registerListener(new EventHandler());
	}
	
	public static IDiscordClient getClient(String Token) throws DiscordException{
		return new ClientBuilder().withToken(Token).login();
	}

}

