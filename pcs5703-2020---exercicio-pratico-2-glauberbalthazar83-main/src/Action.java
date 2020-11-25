import java.io.Serializable;

public class Action implements Serializable {
	private String actionType = "";
	private Object actionParameter = new Object();

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public Object getActionParameter() {
		return actionParameter;
	}

	public void setActionParameter(Object actionParameter) {
		this.actionParameter = actionParameter;
	}

}
