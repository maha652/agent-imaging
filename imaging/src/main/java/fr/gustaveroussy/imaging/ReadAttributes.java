package fr.gustaveroussy.imaging;

import java.io.File;
import java.io.IOException;

import com.pixelmed.dicom.AttributeList;
import com.pixelmed.dicom.DicomException;

public class ReadAttributes {

	AttributeList attributeList = new AttributeList();
	public void  exportToFile(String dcmFilePath ) throws IOException, DicomException {
		
		
		 attributeList.read(new File(dcmFilePath));
		
		
	}
	
	
	
	
	
	
	
}
