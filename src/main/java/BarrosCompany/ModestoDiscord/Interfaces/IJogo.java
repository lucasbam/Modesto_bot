package BarrosCompany.ModestoDiscord.Interfaces;

import java.util.ArrayList;

import sx.blah.discord.handle.obj.IChannel;

public interface IJogo {
	ArrayList<String> listaDeJogos = new ArrayList<String>();
	public void quitarJogo(String id, IChannel ch);
}
