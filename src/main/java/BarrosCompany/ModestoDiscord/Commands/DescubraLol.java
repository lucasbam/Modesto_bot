package BarrosCompany.ModestoDiscord.Commands;

import java.util.ArrayList;

import BarrosCompany.ModestoDiscord.Handlers.GameInstanceHandler;
import BarrosCompany.ModestoDiscord.Handlers.MensageHandler;
import BarrosCompany.ModestoDiscord.Interfaces.IComando;
import BarrosCompany.ModestoDiscord.Interfaces.IJogo;
import BarrosCompany.ModestoDiscord.Jogos.jDescubraLol;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;

public class DescubraLol implements IComando, IJogo {
	ArrayList<String> listaDeJogos = new ArrayList<String>();
	
	public void Executar(IMessage msg) {		
		String idJogo = msg.getAuthor().getID()+","+msg.getGuild().getID();
		
		if(msg.getContent().split(" ").length > 1){
			if (msg.getContent().substring(4).equals("quit")){
				quitarJogo(idJogo, msg.getChannel());
			}
			else{
				MensageHandler.erroComandoInvalido(msg);
			}
			return;
		}
		
		if(GameInstanceHandler.podeIniciarJogo(msg, listaDeJogos)){
			listaDeJogos.add(msg.getAuthor().getID()+","+msg.getGuild().getID());
			new jDescubraLol(msg, this);
		}
		else{
			MensageHandler.erroJogoAcontecendo(msg);
		}
	}
	
	public void quitarJogo(String idJogo, IChannel ch){
		for (int i = 0; i < listaDeJogos.size(); i++) {
			if(listaDeJogos.get(i).equals(idJogo)){
				listaDeJogos.remove(i);
				MensageHandler.enviarMensagem("Jogo encerrado com sucesso.", ch);
			}
		}
	}
}
