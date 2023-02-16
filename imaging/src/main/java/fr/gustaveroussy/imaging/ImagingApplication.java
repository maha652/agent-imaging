//package //
package fr.gustaveroussy.imaging;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.pixelmed.dicom.DicomException;
import com.pixelmed.dicom.DicomInputStream;
import com.pixelmed.dicom.AttributeList;



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
import com.pixelmed.dicom.TransferSyntax;
import com.pixelmed.display.SourceImage;
import com.pixelmed.dicom.DicomInputStream;
import com.pixelmed.dicom.PersonNameAttribute;

import fr.gustaveroussy.imaging.services.DemoDicom;

@SpringBootApplication

// classe principale // 


public class ImagingApplication implements CommandLineRunner {
	 


	public static final Logger logger = LoggerFactory.getLogger(ImagingApplication.class);

	private static final Object[] String = null;
	
	private  AttributeList attributeList = new AttributeList() ;
	
	@Value("${path.to.dc.file}")
	private  String filePath ; 
	
	@Value("${path.to.write.file}")
	private  String writeFile ;
	
	 @Value ("path.to.directory")
		private String directoryName ;
		

	
	
	
	
	
	
	
	// fonction principale main ( point d'entrée du programme ) //

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

	
	
// acceder à une liste de dicom dans un fichier //	
	
/* Cet exemple utilise la méthode listFiles() de la classe File pour obtenir une liste de tous les fichiers DICOM dans un dossier donné. 
 * La boucle for parcourt ensuite chaque fichier, lit son contenu en utilisant DicomInputStream et ajoute les attributs à une liste d'AttributeList.
 */
	
	
 public void Trouver(String[] args) {
	 
	 File directory = new File(directoryName); 
	 
	File[] files = directory.listFiles((dir , name) -> name.endsWith(".dcm")); 


	ArrayList<AttributeList> attributeLists = new ArrayList<>();

	for (File file : files) {
	    try {
	        DicomInputStream din = new DicomInputStream(file);
	        AttributeList attributeList = new AttributeList();
	        attributeList.read(din);
	        din.close();
	        attributeLists.add(attributeList);
	    } catch (IOException | DicomException e) {
	        e.printStackTrace(); 
	    }
	    
	    
	    // copier les DICOM , avoir le file des DICOM copiés , et travailler sur ces copies//
	    
	  // Accédez aux données DICOM ( deja copiés)  ici en utilisant attributeLists , modifier(anonymiser et transférer les copies) //
	
 }
 }
 
 
 
 
 
 
 
 
 
 
 // methode_lecture_d'un_seul_dicom_
		 
	    private   Map<String, String> readMetadata() throws DicomException, IOException {
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
	        metaData.put("PatientWeight", getTagInformation(TagFromName.PatientWeight  ));
	       
	     
	       
	     /*   attributeList.entrySet().forEach(e -> {
	        	System.out.println(e.getKey().toString());
	        	System.out.println(e.getValue().toString());
	        });   */
	        
// modifier la variable au niveau du DICOM d'origine //
	    	   Attribute patientNameAttr = new PersonNameAttribute(TagFromName.PatientName);
		        patientNameAttr.addValue("ANONYMIZED");
		        attributeList.put(TagFromName.PatientName,patientNameAttr);
		        
		     // Save anonymized DICOM file
			    File anonymizedFile = new File("C:\\Users\\m_graa\\Desktop\\stage_bioinfo\\anonym.txt");
			    
			    
			    
			    attributeList.write(anonymizedFile  ,  TransferSyntax.ExplicitVRLittleEndian, true, true);
		        
		        
		        
		        
		        
		        
		        
		     
   	
		        return metaData; 
		      
		        }     
	 
		
	        private  String getTagInformation(AttributeTag tag) {
		        return Attribute.getDelimitedStringValuesOrDefault(attributeList, tag, "NOT FOUND");
	
	        
	        }	

	        
	       	        
	}


