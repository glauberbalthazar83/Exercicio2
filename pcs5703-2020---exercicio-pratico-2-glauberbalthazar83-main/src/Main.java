import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Main {

	public static void main(String[] args) {
		// Inicializar o Hecate
		/*try { 
			Runtime.getRuntime().exec("java -jar /home/glauberbalthazar/pcs5703-2020-exercicio-pratico-1-glauberbalthazar83/hecateServer-0.0.2-SNAPSHOT.jar");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//java -jar hecateServer-0.0.4.jar
		 * http://localhost:8080/agent/5
		 * http://localhost:8080/agent/plan/5
		 * 
		 * */	
		
		Util util = new Util();
		ArrayList<Agent> listaAgents = new ArrayList<Agent>();
		ArrayList<Room> listaRoom = new ArrayList<Room>();
		final int MAX_AGENTS = 15;
		final int MAX_ROOMS = 5;

		//criar o mundo
		util.criarMundo(1000);

		//criar os agentes
		util.delay(500, "\nCriar " + MAX_AGENTS + " agentes");
		for (int i = 5; i < MAX_AGENTS+5; i++) {
			String nomeAgente = "Agente" + i;
			Agent agent = util.criarAgent(nomeAgente, 300);
			listaAgents.add(agent);
		}
		
		//criar as salas
		util.delay(500, "\nCriar " + MAX_ROOMS + " salas");
		for (int i = 0; i < MAX_ROOMS; i++) {
			String nomeSala = "Sala" + i;
			Room room = util.criarRoom(nomeSala, 300);
			listaRoom.add(room);
		}
		
		//espalhar os agentes por salas (3 por sala)
		util.delay(500, "\nDistribuir os agentes por salas (3 por sala)");
		int j = 0;
		int MAXAGENT_SALA = 3;
		for (int i = 0; i < MAX_ROOMS; i++) {
			int cont = 0;
			while (cont < MAXAGENT_SALA && j < MAX_AGENTS) {
				Message m = new Message();
				m.setFrom(String.valueOf(j));
				m.setTo(String.valueOf(i));
				m.setMessage("Entrou na sala: "+i);
				listaAgents.get(j).getMessages().add(m);
				util.addAgentToRoom(listaAgents.get(j), listaRoom.get(i), 300);
				cont++;
				j++;
			}
		}		
		//mostrar as salas e os agentes existentes nela
		String msg = "Imprimir as salas e os agentes delas do início do programa";
		util.delay(500, msg);
		for(int i=0; i< MAX_ROOMS; i++) {
			msg += "\n"+ listaRoom.get(i).printListaAgents();
		}
		util.gravarArquivoMensagens(msg, "contexto-inicial");
		
		//realizar as trocas
		for(int i=5; i<MAX_AGENTS+5; i++) {
			int roomDoAgent = 0;
			//primeiro: pegar a sala atual do agente
			Agent ag = util.recuperarAgent(i, 200);
			for(int k=0; k<MAX_ROOMS; k++) {
				k += 20;
				Room room = util.recuperarSala(k, 200);
				for(Agent agts : room.getAgents()) {
					if(agts.getId() == ag.getId()) {
						roomDoAgent = room.getID();
						break;
					}
				}
				if(roomDoAgent!=0) {
					break;
				}
			}
			
			//segundo: randomizar uma sala aleatoria de forma a cada sala ter no maximo 4 agents
			Random r = new Random();
			int n = r.nextInt((24 - 20) + 1) + 20;
			boolean troca = false;
			while(roomDoAgent == n || troca == false) {
				n = r.nextInt((24 - 20) + 1) + 20;
				if(roomDoAgent != n) {
					Room rm = util.recuperarSala(n, 200);
					ArrayList<Agent> agents = rm.getAgents();
					int qtdAcoes = 0;
					for(Agent agt : agents) {
						if(agt.getActions()!=null) {
							qtdAcoes++;
						}
					}
					if(qtdAcoes>=4) {
						troca = false;
					}else {
						troca = true;
					}
				}
			}
			
			//terceiro: montar um plano de ação baseado na sala randomizada
			Action a = new Action();
			a.setActionType("leave");
			a.setActionParameter(String.valueOf(roomDoAgent));//sala atual
			Action b = new Action();
			b.setActionType("move");
			b.setActionParameter(String.valueOf(n));//sala destino
			
			ArrayList<Action> actions =  new ArrayList<Action>();
			actions.add(a);
			actions.add(b);
			
			//quarto: add o plano ao agente atual
			Plan plano = new Plan();
			plano.setActions(actions);
			util.adicionarPlanoAoAgente(i, plano, 500);
		}
		
		//quinto: aplicar o plano de ação para todos os agentes		
		for(int i=5; i<=MAX_AGENTS+5; i++) {
			util.executarPlanoAcaoPorAgente(i, 300);
		}	
		
		//mostrar as salas e os agentes existentes nela
		String msg2 = "\nImprimir as salas e os agentes delas do final do programa";
		util.delay(500, msg2);
		listaRoom = new ArrayList<Room>();
		for(int k=0; k<MAX_ROOMS; k++) {
			int kj = k+ 20;
			listaRoom.add(util.recuperarSala(kj, 200));			
		}
		for(int i=0; i< MAX_ROOMS; i++) {
			msg2 += "\n"+ listaRoom.get(i).printListaAgents();
		}
		util.gravarArquivoMensagens(msg2, "contexto-final");
		
		//imprimir as mensanges dos agentes no arquivo texto "mensagens.txt"
		util.delay(500, "\nimprimir todas as mensagens dos agentes");
		String mensagens = "";
		for (Agent a : listaAgents) {
			mensagens += util.imprimirTodasMensagems(a, 50);
		}
		util.gravarArquivoMensagens(mensagens,"mensagens");    
		
		
		/*Action a = new Action();
		a.setActionType("leave");
		a.setActionParameter("20");//sala atual
		Action b = new Action();
		b.setActionType("move");
		b.setActionParameter("21");//sala destino
		
		ArrayList<Action> actions =  new ArrayList<Action>();
		actions.add(a);
		actions.add(b);
		
		//add o plano ao agente 5
		Plan plano = new Plan();
		plano.setActions(actions);
		util.adicionarPlanoAoAgente(5, plano, 500);
		
		//mando o agente 5 executar o seu plano
		//util.executarPlanoAcaoPorAgente(5, 300);
		
		//util.recuperarPlanoAcaoPorAgent(5, 500);
		
		
		
		//Room room = util.recuperarSala(5, 200);
		
		
		
		
		//mostrar as salas e os agentes existentes nela
		/*String msg2 = "\nImprimir as salas e os agentes delas do final do programa";
		util.delay(500, msg2);
		for(int i=0; i< MAX_ROOMS; i++) {
			msg2 += "\n"+ listaRoom.get(i).printListaAgents();
		}
		util.gravarArquivoMensagens(msg2, "contexto-final");*/
		

/*		

		

		//mostrar as salas e os agentes existentes nela
		util.delay(500, "\nimprimir as salas e os agentes delas");
		listaRoom.get(0).printListaAgents();
		listaRoom.get(1).printListaAgents();
		listaRoom.get(2).printListaAgents();

		//espalhar os agentes por salas de forma aleatória
		util.delay(500, "\nTrocar agentes de salas");
		String salaRandomica = "";
		for (int i = 0; i < MAX_AGENTS; i++) {
			// descobrir a sala atual do agente
			int posSalaAtual = -1;
			for (int m = 0; m < MAX_ROOMS; m++) {
				if (listaRoom.get(m).getID().equals(listaAgents.get(i).getRoom().getID())) {
					posSalaAtual = m;
				}
			}
			// gerar uma sala diferente da atual
			// randomizar uma sala difernete da atual para o listaAgents.get(i)
			String IDSalaNova = "";
			do {
				IDSalaNova = util.randomizarRoom(listaRoom);
			} while (listaAgents.get(i).getRoom().getID().equals(salaRandomica));
			int posicaoSalaNovaNoArray = -1;
			for (int k = 0; k < 3; k++) {
				if (IDSalaNova.equals(listaRoom.get(k).getID())) {
					posicaoSalaNovaNoArray = k;
				}
			}

			// remover o agente da sala atual
			util.removeAgentToRoom(listaAgents.get(i), listaRoom.get(posSalaAtual), 200);

			// add ele a nova sala
			util.addAgentToRoom(listaAgents.get(i), listaRoom.get(posicaoSalaNovaNoArray), 200);
		}

		//mostrar como ficaram os agentes nas salas
		util.delay(500, "\nimprimir as salas e os agentes delas");
		listaRoom.get(0).printListaAgents();
		listaRoom.get(1).printListaAgents();
		listaRoom.get(2).printListaAgents();

		//espalhar os agentes de acordo com as regras solicitadas
		util.delay(500, "\nUma sala com 3 agentes, outras 2 com 2 ou 4 agentes");
		while (listaRoom.get(0).getListaAgents().size() > 4) {
			Agent a = listaRoom.get(0).removeAgentList();
			util.delay(200, "Agente {" + a.getID() + "," + a.getNome() + "} removido da sala {"
					+ listaRoom.get(0).getNome() + "," + listaRoom.get(0).getID() + "}");
		}
		while (listaRoom.get(1).getListaAgents().size() > 3) {
			Agent a = listaRoom.get(1).removeAgentList();
			util.delay(200, "Agente {" + a.getID() + "," + a.getNome() + "} removido da sala {"
					+ listaRoom.get(1).getNome() + "," + listaRoom.get(1).getID() + "}");
		}
		while (listaRoom.get(2).getListaAgents().size() > 5) {
			Agent a = listaRoom.get(2).removeAgentList();
			util.delay(200, "Agente {" + a.getID() + "," + a.getNome() + "} removido da sala {"
					+ listaRoom.get(2).getNome() + "," + listaRoom.get(2).getID() + "}");
		}

		//mostrar mais uma vez como os agentes ficaram em cada sala
		util.delay(500, "\nimprimir as salas e os agentes delas");
		listaRoom.get(0).printListaAgents();
		listaRoom.get(1).printListaAgents();
		listaRoom.get(2).printListaAgents();

		//imprimir as mensanges dos agentes no arquivo texto "mensagens.txt"
		util.delay(500, "\nimprimir todas as mensagens dos agentes");
		for (Agent a : listaAgents) {
			util.imprimirTodasMensagems(a, 50);
		}
		
*/		

	}

}

