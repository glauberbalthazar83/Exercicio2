import java.util.ArrayList;
import java.util.List;

public class Plan {
	private List<Action> actions = new ArrayList<>();

	public Plan() {
	}

	public Plan(String agentID, List<Action> actions) {
		this.actions = actions;
	}

	public List<Action> getActions() {
		return this.actions;
	}

	public void setActions(List<Action> actions) {
		this.actions = actions;
	}

	public void addAction(Action action) {
		this.actions.add(action);
	}
}
