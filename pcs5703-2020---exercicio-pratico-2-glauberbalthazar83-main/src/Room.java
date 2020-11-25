import java.util.ArrayList;

public class Room {
	private int id;
	private String name;
	private ArrayList<Agent> agents = new ArrayList<Agent>();
	private ArrayList<String> messages =  new ArrayList<String>();
	
	public int getID() {
		return id;
	}
	public void setID(int iD) {
		id = iD;
	}
	public String getNome() {
		return name;
	}
	public void setNome(String nome) {
		this.name = nome;
	}
	
	public String toString() {
		return "Sala -  Nome: "+name+", ID: "+id;
	}
	
	public void setAgentToRoom(Agent agent) {
		agents.add(agent);
	}
	public ArrayList<Agent> getListaAgents(){
		return agents;
	}
	public Agent removeAgentList(Agent a) {
		int posicao = agents.indexOf(a);
		Agent agent = agents.get(posicao);
		agents.remove(posicao);
		return agent;
	}
	
	public Agent removeAgentList() {
		Agent agent = agents.get(0);
		agents.remove(0);
		return agent;
	}
	
	public String printListaAgents() {
		String msg = "Sala Nome: "+name+", ID: "+id;
		System.out.println(msg);
		for(Agent a : agents) {
			System.out.println("Agente: nome: "+a.getNome()+", ID: "+a.getID());
			msg += "\n"+ "Agente: nome: "+a.getNome()+", ID: "+a.getID();
		}
		return msg;
	}
	public ArrayList<Agent> getAgents() {
		return agents;
	}
	public void setAgents(ArrayList<Agent> agents) {
		this.agents = agents;
	}
	public ArrayList<String> getMessages() {
		return messages;
	}
	public void setMessages(ArrayList<String> messages) {
		this.messages = messages;
	}
}
