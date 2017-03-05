package BarrosCompany.ModestoDiscord.Commands;

import BarrosCompany.ModestoDiscord.ModestoBot;
import BarrosCompany.ModestoDiscord.Interfaces.IComando;
import BarrosCompany.ModestoDiscord.Interfaces.IJogo;
import BarrosCompany.ModestoDiscord.Jogos.jDescubraLol;
import sx.blah.discord.handle.obj.IMessage;

public class DescubraLol implements IComando, IJogo {
	
	public void Executar(IMessage msg) {		
		ModestoBot.Bot.getDispatcher().registerListener(new jDescubraLol(msg, this));
	}
	
	public void quitarJogo(String playerId, String guildaId){
	}
}
