package lt.kutkaitis.pamokutvarkarastis.client;

import com.extjs.gxt.ui.client.data.BaseModel;

public class Students extends BaseModel{

	private static final long serialVersionUID = 1L;

	public Students() {
	}

	public Students(String name) {
		set("name", name);
	}

	public String getName() {
		return (String) get("name");
	}

	public String toString() {
		return getName();
	}
}
