package BarrosCompany.ModestoDiscord.Handlers;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Hashtable;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.obj.IMessage;

public class EventHandler {
	String Prefixo = "%";
	private Hashtable<String, String> Falas;
	private HashMap<String, Runnable> Comandos;
	private IMessage message;
	
	public EventHandler() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException{
		getFalas();
		getComandos();
	}
	
	@EventSubscriber
	public void onReadyEvent(ReadyEvent event){
		System.out.println("Entrou");
	}
	
	@EventSubscriber
	public void onMessageEvent(MessageReceivedEvent event){
		message = event.getMessage();
		String[] arr = message.getContent().substring(1).split(" "); 
				
		if(message.getAuthor().isBot())
			return;
		
		if(message.toString().startsWith(Prefixo))
		{
			if(isFala(arr[0]))
				MensageHandler.enviarMensagem(Falas.get(arr[0]), message);
			
			if(isComando(arr[0]))
				Comandos.get(arr[0]).run();
		}
	}

	private void getFalas() throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("Dialogo.txt"), "UTF-8"));
		Falas = new Hashtable<String, String>();
		
		while(br.ready()){
			String[] linha = br.readLine().split("=");
			Falas.put(linha[0], linha[1]);   
		}
		
		br.close();
	}
	
	private void getComandos() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException{
		
		Comandos = new HashMap<String, Runnable>();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("Comandos.txt"), "UTF-8"));
		
		while(br.ready()){
			   String[] linha = br.readLine().split("=");
			   System.out.println(linha[1]);
			   Class<?> c = Class.forName("BarrosCompany.ModestoDiscord.Commands." + linha[1]);
			   final Object o = c.newInstance();
			   final Method m = c.getDeclaredMethod("Executar", IMessage.class);
	
			   Runnable e = new Runnable(){
				   @Override
				   public void run(){
					   try{
						   m.invoke(o, message);
					   }catch(Exception e){
						   e.printStackTrace();
					   }
				   }
			   };
			   
			   Comandos.put(linha[0], e);
			 }
		
		br.close();
	}
	
	private boolean isFala(String f){
		return Falas.containsKey(f.toLowerCase());		
	}
	
	private boolean isComando(String c) {
		return Comandos.containsKey(c.toLowerCase());
	}
	
}