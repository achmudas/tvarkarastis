package lt.kutkaitis.pamokutvarkarastis.client;

import lt.kutkaitis.pamokutvarkarastis.client.returned.Data;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("excelReaderService")
public interface ExcelReaderService extends RemoteService{

	Data returnData();
	
	class instance {
		public static ExcelReaderServiceAsync getData() {
			return GWT.create(ExcelReaderService.class);
			
		}
	}
	
}
