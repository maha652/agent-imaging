package fr.gustaveroussy.imaging;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.pixelmed.dicom.Attribute;
import com.pixelmed.dicom.AttributeList;
import com.pixelmed.dicom.AttributeTag;
import com.pixelmed.dicom.DicomException;
import com.pixelmed.dicom.TagFromName;
import com.pixelmed.display.SourceImage;
import com.pixelmed.dicom.DicomInputStream;
import com.pixelmed.dicom.PersonNameAttribute;

import fr.gustaveroussy.imaging.services.DemoDicom;

@SpringBootApplication
public class ImagingApplication implements CommandLineRunner {
	int arg ;
	 


	public static final Logger logger = LoggerFactory.getLogger(ImagingApplication.class);
	
	private  AttributeList attributeList = new AttributeList() ;
	
	@Value("${path.to.dc.file}")
	private  String filePath ; 
	
	@Value("${path.to.write.file}")
	private  String writeFile ;



	
	
	
	
	public static void main(String[] args) {
		SpringApplication.run(ImagingApplication.class, args);
		
	}

	@Override
	public void run(String... args) throws Exception {
		
	
		 attributeList.read(new File(filePath));

	     Map<String, String> metaData = readMetadata();
	     for (Map.Entry<String, String> entry :metaData.entrySet()) {
	                System.out.println(entry.getKey()+" : "+entry.getValue());
	           
	     }   
	     Filewriter fw =  new Filewriter ();
	     fw.exportToFile(writeFile,metaData.toString()); 
	     
	    
	     
	     }
	
	
	 
	    private   Map<String, String> readMetadata() throws DicomException {
	        Map<String, String> metaData = new LinkedHashMap<>();
	        metaData.put("Patient Name", getTagInformation(TagFromName.PatientName));
	        metaData.put("Patient ID", getTagInformation(TagFromName.PatientID));
	        metaData.put("Modality", getTagInformation(TagFromName.Modality));
	        metaData.put("Photometric Interpretation", getTagInformation(TagFromName.PhotometricInterpretation));
	        metaData.put("Image type ", getTagInformation(TagFromName.ImageType ));
	        metaData.put("otherPatientNames ", getTagInformation(TagFromName.OtherPatientNames  ));
	        metaData.put("PatientAge", getTagInformation(TagFromName.PatientAge  ));
	        metaData.put("PatientBirthDate", getTagInformation(TagFromName.PatientBirthDate  ));
	        metaData.put("PatientBirthDateAlternativeCalendar", getTagInformation(TagFromName.PatientBirthDateInAlternativeCalendar ));
	        metaData.put("PatientBodyMassIndex", getTagInformation(TagFromName.PatientBodyMassIndex ));
	        metaData.put("PatientAlternativeCalendar", getTagInformation(TagFromName.PatientAlternativeCalendar ));
	        metaData.put("PatientBreedCodeSequence", getTagInformation(TagFromName.PatientBreedCodeSequence ));
	        metaData.put("PatientBreedDescription", getTagInformation(TagFromName.PatientBreedDescription ));
	        metaData.put("PatientComments ", getTagInformation(TagFromName.PatientComments ));
	        metaData.put("PatientDeathDateInAlternativeCalendar", getTagInformation(TagFromName.PatientDeathDateInAlternativeCalendar ));
	        metaData.put("PatientIdentityRemoved ", getTagInformation(TagFromName.PatientIdentityRemoved ));
	        metaData.put("PatientOrientation", getTagInformation(TagFromName.PatientOrientation ));
	        metaData.put("PatientPosition", getTagInformation(TagFromName.PatientPosition));
	        metaData.put("PatientSex", getTagInformation(TagFromName.PatientSex ));
	        metaData.put("PatientSexNeutered", getTagInformation(TagFromName.PatientSexNeutered ));
	        metaData.put("PatientSize", getTagInformation(TagFromName.PatientSize ));
	        metaData.put("PatientSizeCodeSequence", getTagInformation(TagFromName.PatientSizeCodeSequence ));
	        metaData.put("PatientSpeciesCodeSequence", getTagInformation(TagFromName.PatientSpeciesCodeSequence ));
	        metaData.put("PatientSpeciesDescription ", getTagInformation(TagFromName.PatientSpeciesDescription ));
	        metaData.put("PatientState", getTagInformation(TagFromName.PatientState ));
	        metaData.put("PatientWeight", getTagInformation(TagFromName.PatientWeight ));
	        
	     
	        
	        
	        attributeList.entrySet().forEach(e -> {
	        	System.out.println(e.getKey().toString(attributeList.getDictionary()));
	        	System.out.println(e.getValue().toString());
	        });
	        

	    	   Attribute patientNameAttr = new PersonNameAttribute(TagFromName.PatientName);
		        patientNameAttr.addValue("ANONYMIZED");
		        attributeList.put(TagFromName.PatientName,patientNameAttr);
	      
	        
	      
	       return metaData;  
	   
	    }
	    
	    
	  
	    
	    
	    
	    
	        private  String getTagInformation(AttributeTag tag) {
		        return Attribute.getDelimitedStringValuesOrDefault(attributeList, tag, "NOT FOUND");
	
	        
	        }	

	        
	            
	    
	        
	        
	        
	}


