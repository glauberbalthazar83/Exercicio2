import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Util {
	
	public void delay(int _tempoSleep, String mensagem) {
		try {
			System.out.println(mensagem);
			Thread.sleep(_tempoSleep);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void delay(int _tempoSleep) {
		try {
			Thread.sleep(_tempoSleep);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void criarMundo(int _tempoSleep) {
		int HTTP_COD_SUCESSO = 200;
		
		try {
			URL url = new URL("http://localhost:8080//createWorld");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			if (con.getResponseCode() != HTTP_COD_SUCESSO) {
				throw new RuntimeException("HTTP error code : " + con.getResponseCode());
			}

			BufferedReader reader = new BufferedReader(new InputStreamReader((con.getInputStream())));

			String linha;
			StringBuffer buffer = new StringBuffer();
			while ((linha = reader.readLine()) != null) {
				buffer.append(linha);
				buffer.append("\n");
			}
			System.out.println("Mundo criado: "+buffer.toString());
			
			con.disconnect();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		delay(_tempoSleep,"Mundo criado com sucesso!");
	}
	
	public Agent criarAgent(String _nomeAgent, int _tempoSleep) {
		int HTTP_COD_SUCESSO = 200;
		Agent agent =  new Agent();
		Gson gson = new Gson();
		
		try {
			URL url = new URL("http://localhost:8080/createAgent?name="+_nomeAgent);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			if (con.getResponseCode() != HTTP_COD_SUCESSO) {
				throw new RuntimeException("HTTP error code : " + con.getResponseCode());
			}

			BufferedReader reader = new BufferedReader(new InputStreamReader((con.getInputStream())));

			String linha;
			StringBuffer buffer = new StringBuffer();
			while ((linha = reader.readLine()) != null) {
				buffer.append(linha);
				buffer.append("\n");
			}
			TypeToken<Agent> token = new TypeToken<Agent>() {};
			agent = (Agent) gson.fromJson(buffer.toString(),token.getType());
			//agent.setID(buffer.toString().substring(buffer.toString().indexOf(":")+1, buffer.toString().length()).trim());
			//agent.setNome(buffer.toString().substring(buffer.toString().indexOf("t")+1,buffer.toString().indexOf("c")).trim());
			
			con.disconnect();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		delay(_tempoSleep,"Agente {"+agent.toString()+"} criado com sucesso!");
		
		return agent;
	}
	
	public Room criarRoom(String _nomeSala, int _tempoSleep) {
		int HTTP_COD_SUCESSO = 200;
		Room room =  new Room();
		Gson gson = new Gson();
		
		try {
			URL url = new URL("http://localhost:8080//createRoom?name="+_nomeSala);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			if (con.getResponseCode() != HTTP_COD_SUCESSO) {
				throw new RuntimeException("HTTP error code : " + con.getResponseCode());
			}

			BufferedReader reader = new BufferedReader(new InputStreamReader((con.getInputStream())));

			String linha;
			StringBuffer buffer = new StringBuffer();
			while ((linha = reader.readLine()) != null) {
				buffer.append(linha);
				buffer.append("\n");
			}
			TypeToken<Room> token = new TypeToken<Room>() {};
			room = (Room) gson.fromJson(buffer.toString(),token.getType());
			
			//room.setID(buffer.toString().substring(buffer.toString().indexOf(":")+1, buffer.toString().length()).trim());
			//room.setNome(buffer.toString().substring(buffer.toString().indexOf("m")+1,buffer.toString().indexOf("c")).trim());
			
			con.disconnect();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		delay(_tempoSleep,"Sala {"+room.toString()+"} criado com sucesso!");
		
		return room;
	}

	public void addAgentToRoom(Agent agent, Room room, int _tempoSleep) {
		int HTTP_COD_SUCESSO = 200;
		
		try {
			URL url = new URL("http://localhost:8080//addAgentsToRoom?room="+room.getID()+"&agents="+agent.getID());
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			if (con.getResponseCode() != HTTP_COD_SUCESSO) {
				throw new RuntimeException("HTTP error code : " + con.getResponseCode());
			}
			room.setAgentToRoom(agent);
			agent.setRoom(room);
			
			con.disconnect();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/*this.agentEnviaMensagem(agent, room, "AGENTE_"+agent.getID()+"_ENTROU_NA_SALA_"+room.getID(), 50);
		for(Agent a : room.getListaAgents()) {
			this.agentEnviaMensagem(agent, a, "AGENTE_"+agent.getID()+"_ENTROU_NA_SALA_"+room.getID(), 50);
		}*/
		delay(_tempoSleep,"Agent {"+agent.toString()+"} add a sala {"+room.toString()+"} com sucesso.");
	}

	public void removeAgentToRoom(Agent agent, Room room, int _tempoSleep) {
		int HTTP_COD_SUCESSO = 200;
		
		try {
			URL url = new URL("http://localhost:8080//removeAgentsFromRoom?room="+room.getID()+"&agents="+agent.getID());
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			if (con.getResponseCode() != HTTP_COD_SUCESSO) {
				throw new RuntimeException("HTTP error code : " + con.getResponseCode());
			}
			room.removeAgentList(agent);
			
			con.disconnect();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.agentEnviaMensagem(agent, room, "AGENTE_"+agent.getID()+"_SAIU_DA_SALA_"+room.getID(), 50);
		for(Agent a : room.getListaAgents()) {
			this.agentEnviaMensagem(agent, a, "AGENTE_"+agent.getID()+"_SAIU_DA_SALA_"+room.getID(), 50);
		}
		delay(_tempoSleep,"Agent {"+agent.toString()+"} removido da sala {"+room.toString()+"} com sucesso.");
	}
	
	public String randomizarRoom(ArrayList<Room> _listaRoom) {		
		String salaRandomica = "";
		Random random = new Random();
		switch(random.nextInt(3)) {
		case 0:
			salaRandomica = String.valueOf(_listaRoom.get(0).getID());
			break;
		case 1:
			salaRandomica = String.valueOf(_listaRoom.get(1).getID());
			break;
		case 2: 
			salaRandomica = String.valueOf(_listaRoom.get(2).getID());
			break;
		}
		return salaRandomica;
	}

	public void agentEnviaMensagem(Agent agent, Room room, String mensagem, int _tempoSleep) {	
		delay(500);
		int HTTP_COD_SUCESSO = 200;
		try {
			URL url = new URL("http://localhost:8080/sendMessageToRoom?from="+agent.getID()+"&to="+room.getID()+"&message="+mensagem);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			
			if (con.getResponseCode() != HTTP_COD_SUCESSO) {
				throw new RuntimeException("HTTP error code : " + con.getResponseCode());
			}
			
			con.disconnect();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		delay(_tempoSleep,"Mensagem do agente {"+agent.toString()+"} para a sala {"+room.toString()+"} enviada {"+mensagem+"}");
	}

	public void agentEnviaMensagem(Agent agentOrigem, Agent agentDestino, String mensagem, int _tempoSleep) {
		delay(500);
		int HTTP_COD_SUCESSO = 200;
		try {
			URL url = new URL("http://localhost:8080/sendMessageToAgent?from="+agentOrigem.getID()+"&to="+agentDestino.getID()+"&message="+mensagem);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			
			if (con.getResponseCode() != HTTP_COD_SUCESSO) {
				throw new RuntimeException("HTTP error code : " + con.getResponseCode());
			}
			
			con.disconnect();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		delay(_tempoSleep,"Mensagem do agente {"+agentOrigem.toString()+"} para o agente {"+agentDestino.toString()+"} enviada {"+mensagem+"}");
	}

	public String imprimirTodasMensagems(Agent agent, int _tempoSleep) {
		StringBuffer buffer = new StringBuffer();
		try {
			URL url = new URL("http://localhost:8080//agent/messages/"+agent.getID());
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			BufferedReader reader = null;
			
			InputStream inputStream = urlConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String linha;

            while ((linha = reader.readLine()) != null) {
                buffer.append(linha);
                buffer.append("\n");
            }
            
			con.disconnect();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		delay(_tempoSleep,"Mensagens do agente {"+agent.toString()+"}");
		
		return buffer.toString();
	}
	
	public void gravarArquivoMensagens(String msg, String nomeArquivo) {
		try {
			FileWriter arq = new FileWriter("//desenvolvimento//PrjExercicioSMA//"+nomeArquivo+".txt");
	        PrintWriter gravarArq = new PrintWriter(arq);
	        gravarArq.printf(msg);
	        arq.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void recuperarPlanoAcaoPorAgent(int idAgent, int _tempoSleep) {
		int HTTP_COD_SUCESSO = 200;
		Gson gson = new Gson();
		
		try {
			URL url = new URL("http://localhost:8080//agent/plan/"+idAgent);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			if (con.getResponseCode() != HTTP_COD_SUCESSO) {
				throw new RuntimeException("HTTP error code : " + con.getResponseCode());
			}

			BufferedReader reader = new BufferedReader(new InputStreamReader((con.getInputStream())));

			String linha;
			StringBuffer buffer = new StringBuffer();
			while ((linha = reader.readLine()) != null) {
				buffer.append(linha);
				buffer.append("\n");
			}
			System.out.println(buffer.toString());
			//TypeToken<Agent> token = new TypeToken<Agent>() {};
			//agent = (Agent) gson.fromJson(buffer.toString(),token.getType());
			
			con.disconnect();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		delay(_tempoSleep,"Plano de acao do agent ID  {"+idAgent+"} recuperado com sucesso!");
		
	}
	
	public void adicionarPlanoAoAgente(int idAgent, Plan plano, int _tempoSleep) {
		int HTTP_COD_SUCESSO = 200;
		Gson gson = new Gson();
		String json = gson.toJson(plano);
		System.out.println(json);
		
		String uri = "http://localhost:8080/agent/setActions?id="+idAgent+"&actions=";
		
		try {
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
            out.write(json);
            out.close();

            int http_status = connection.getResponseCode();
            if (http_status / 100 != 2) {
            	throw new RuntimeException("HTTP error code : " + http_status);
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		delay(_tempoSleep,"Plano de acao do agent ID  {"+idAgent+"} recuperado com sucesso!");
		
	}
	
	public void executarPlanoAcaoPorAgente(int idAgent, int _tempoSleep) {
		int HTTP_COD_SUCESSO = 200;
		
		String uri = "http://localhost:8080//executePlan/"+idAgent;
		
		try {
			URL url = new URL(uri);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			if (con.getResponseCode() != HTTP_COD_SUCESSO) {
				throw new RuntimeException("HTTP error code : " + con.getResponseCode());
			}

			BufferedReader reader = new BufferedReader(new InputStreamReader((con.getInputStream())));

			String linha;
			StringBuffer buffer = new StringBuffer();
			while ((linha = reader.readLine()) != null) {
				buffer.append(linha);
				buffer.append("\n");
			}
			System.out.println(buffer.toString());
			//TypeToken<Agent> token = new TypeToken<Agent>() {};
			//agent = (Agent) gson.fromJson(buffer.toString(),token.getType());
			
			con.disconnect();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		delay(_tempoSleep,"Plano de acao do agent ID  {"+idAgent+"} recuperado com sucesso!");
		
	}	
	
	public Room recuperarSala(int idSala, int _tempoSleep) {
		int HTTP_COD_SUCESSO = 200;
		Gson gson = new Gson();
		Room room = new Room();
		
		try {
			URL url = new URL("http://localhost:8080//room/"+idSala);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			if (con.getResponseCode() != HTTP_COD_SUCESSO) {
				throw new RuntimeException("HTTP error code : " + con.getResponseCode());
			}

			BufferedReader reader = new BufferedReader(new InputStreamReader((con.getInputStream())));

			String linha;
			StringBuffer buffer = new StringBuffer();
			while ((linha = reader.readLine()) != null) {
				buffer.append(linha);
			}
			System.out.println(buffer.toString());
			TypeToken<Room> token = new TypeToken<Room>() {};
			room = (Room) gson.fromJson(buffer.toString(),token.getType());
			
			con.disconnect();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		delay(_tempoSleep,"Dados da sala  {"+idSala+"} recuperado com sucesso!");
		return room;
	}

	public Agent recuperarAgent(int idAgente, int _tempoSleep) {
		int HTTP_COD_SUCESSO = 200;
		Gson gson = new Gson();
		Agent agent = new Agent();
		
		try {
			URL url = new URL("http://localhost:8080//agent/"+idAgente);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			if (con.getResponseCode() != HTTP_COD_SUCESSO) {
				throw new RuntimeException("HTTP error code : " + con.getResponseCode());
			}

			BufferedReader reader = new BufferedReader(new InputStreamReader((con.getInputStream())));

			String linha;
			StringBuffer buffer = new StringBuffer();
			while ((linha = reader.readLine()) != null) {
				buffer.append(linha);
			}
			System.out.println(buffer.toString());
			TypeToken<Agent> token = new TypeToken<Agent>() {};
			agent = (Agent) gson.fromJson(buffer.toString(),token.getType());
			
			con.disconnect();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		delay(_tempoSleep,"Dados do agente  {"+idAgente+"} recuperado com sucesso!");
		return agent;
	}
}
