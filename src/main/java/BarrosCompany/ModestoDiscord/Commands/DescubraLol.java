package BarrosCompany.ModestoDiscord.Commands;

import BarrosCompany.ModestoDiscord.ModestoBot;
import BarrosCompany.ModestoDiscord.Data.dbManager;
import BarrosCompany.ModestoDiscord.Handlers.MensageHandler;
import BarrosCompany.ModestoDiscord.Interfaces.IComando;
import BarrosCompany.ModestoDiscord.Interfaces.IJogo;
import BarrosCompany.ModestoDiscord.Jogos.jDescubraLol;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;

public class DescubraLol implements IComando, IJogo {
	
	public void Executar(IMessage msg) {
		String playerId = msg.getAuthor().getID(), guildaId = msg.getGuild().getID();
		if(msg.getContent().split(" ").length > 1)
			return;
		if(!dbManager.existeInstancia(playerId, guildaId, "descubraLol_instancias")){
			ModestoBot.Bot.getDispatcher().registerListener(new jDescubraLol(msg, this));
		}else{
			MensageHandler.enviarMensagem("Erro, jogo já existe!", msg);
			return;
		}
	}
	
	public void quitarJogo(IMessage msg, jDescubraLol object){
		String playerId = msg.getAuthor().getID(), guildaId = msg.getGuild().getID();
		object.setJogando(false);
		dbManager.excluirInstancia(playerId, guildaId, "descubraLol_instancias");
	}
	
	public void quitarJogo(String playerId, String guildaId){
		dbManager.excluirInstancia(playerId, guildaId, "descubraLol_instancias");
	}
}
