package BarrosCompany.ModestoDiscord.Commands;

import BarrosCompany.ModestoDiscord.ModestoBot;
import BarrosCompany.ModestoDiscord.Data.dbManager;
import BarrosCompany.ModestoDiscord.Handlers.MensageHandler;
import BarrosCompany.ModestoDiscord.Interfaces.IComando;
import BarrosCompany.ModestoDiscord.Interfaces.IJogo;
import BarrosCompany.ModestoDiscord.Jogos.jDescubraLol;
import sx.blah.discord.handle.obj.IMessage;

public class DescubraLol implements IComando, IJogo {
	
	public void Executar(IMessage msg) {
		if(msg.getContent().startsWith("%dc")){
			if(msg.getContent().split(" ").length > 1){
				if (msg.getContent().substring(4).equals("quit")){
					quitarJogo(msg);
					return;
			}
			else{
				MensageHandler.erroComandoInvalido(msg);
				return;
				}
			}
		}
		
		if(!dbManager.existeInstancia(msg.getAuthor().getID(), msg.getGuild().getID(), "descubraLol_instancias")){
			ModestoBot.Bot.getDispatcher().registerListener(new jDescubraLol(msg, this));
		}else{
			MensageHandler.enviarMensagem("Erro, jogo já existe!", msg);
			return;
		}
	}
	
	public void quitarJogo(IMessage msg){
		dbManager.excluirInstancia(msg.getAuthor().getID(), msg.getGuild().getID(), "descubraLol_instancias");
	}
	
	public void quitarJogo(String playerId, String guildaId){
		dbManager.excluirInstancia(playerId, guildaId, "descubraLol_instancias");
	}
}
