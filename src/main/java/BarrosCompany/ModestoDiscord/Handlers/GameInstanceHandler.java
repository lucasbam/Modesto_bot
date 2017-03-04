package BarrosCompany.ModestoDiscord.Handlers;

import java.util.ArrayList;

import sx.blah.discord.handle.obj.IMessage;

public class GameInstanceHandler {
	
	public static boolean podeIniciarJogo(IMessage msg, ArrayList<String> listaDeJogos){
		String jogoId = msg.getAuthor().getID() + "," + msg.getGuild().getID();
		
		boolean naoExiste = true;
		
		if (listaDeJogos.isEmpty()){
			return naoExiste;
		}
		
		for (String id : listaDeJogos){
			if(id.equals(jogoId)){
				naoExiste = false;
			}
		}
		
		return naoExiste;
		
	}
	
}
