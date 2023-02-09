package dcm4che3_anonymisation;

import java.io.File;
import java.io.IOException;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.io.DicomInputStream;


public class Anonymisation {
	
	public static void main(String[] args) throws IOException {
	    String filePath = "path/to/dicom/file.dcm";
	    File file = new File(filePath);

	    DicomInputStream dis = new DicomInputStream(file);
	    Attributes attrs = dis.readDataset(-1, -1);

	    System.out.println("Patient Name: " + attrs.getString(Tag.PatientName));

	   
	    attrs.setString(Tag.PatientName, null, "ANONYMIZED");

	
	    File anonymizedFile = new File("path/to/anonymized/dicom/file.dcm");
	    dis.writeDataset(anonymizedFile, attrs);
	  }

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
