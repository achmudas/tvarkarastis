package lt.kutkaitis.pamokutvarkarastis.client;

import lt.kutkaitis.pamokutvarkarastis.client.returned.Data;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ExcelReaderServiceAsync {

	void returnData(AsyncCallback<Data> callback);

}
