package test;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;

import com.pixelmed.dicom.Attribute;
import com.pixelmed.dicom.AttributeList;
import com.pixelmed.dicom.DicomException;
import com.pixelmed.dicom.DicomInputStream;
import com.pixelmed.dicom.PersonNameAttribute;
import com.pixelmed.dicom.SpecificCharacterSet;
import com.pixelmed.dicom.StringAttribute;
import com.pixelmed.dicom.TagFromName;
import com.pixelmed.dicom.TransferSyntax;



public class DicomReadAnonymize {
	
	private static final String AttributeList = null;
	

	public static void main(String[] args) throws IOException, DicomException {
	
		String filePath = "C:\\\\Users\\\\m_graa\\\\Desktop\\\\stage_bioinfo\\\\1.3.12.2.1107.5.2.18.42239.2019080716095943021807871.dcm";
	    File file = new File(filePath);


	
	

	    DicomInputStream dis = new DicomInputStream(file);
	    AttributeList attrs = new AttributeList();
	    attrs.read(dis);

	    // Print patient name attribute //
	    System.out.println("Patient Name: " + 
	        attrs.get(TagFromName.PatientName).getSingleStringValueOrEmptyString());

	    // Anonymize patient name attribute //
	
	    Attribute patientNameAttr = new PersonNameAttribute(TagFromName.PatientName);
	    patientNameAttr.addValue("ANONYMIZED");
	    attrs.put(patientNameAttr);
	    
	    
	 // Save anonymized DICOM file
	    File anonymizedFile = new File("C:\\Users\\m_graa\\Desktop\\stage_bioinfo\\anonym.txt");
	    
	    
	    
	    attrs.write(anonymizedFile  ,  TransferSyntax.ExplicitVRLittleEndian, true, true);

	    

	    
	}
	
}
	    
	    
	    
	    
	    
	    
	    	    
	  


