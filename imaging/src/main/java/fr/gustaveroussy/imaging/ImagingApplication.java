//package //
/*
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


 
 @SpringBootApplication

//classe principale // 


public class ImagingApplication implements CommandLineRunner {
	 


	public static final Logger logger = LoggerFactory.getLogger(ImagingApplication.class);

	private static final Object[] String = null;
	
	private  AttributeList attributeList = new AttributeList() ;
	

	
	// chemin vers le fichier dicom //
	@Value("${path.to.dc.file}")
	private  String filePath ; 
	
	
	// chemin vers le nouveau file créé //
	
	@Value("${path.to.write.file}")
	private  String writeFile ;
	
	
	// chemin du dossier contenant les fichiers DICOM à copier //
	

	 @Value ("${path.to.Sourcedirectory}")
		private String SourceDirectory ;
	 
	 
	  // Chemin du dossier de destination où la copie anonymisée sera enregistrée //
	 
	 
	 @Value ("${path.to.Destinationdirectory}")
		private String DestinationDirectory ;
	 
	 	 
	 
	 
	 
 
	 
	 
	
	
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
	     
 
 // methode_lecture_d'un_seul_dicom_ //
		 
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
	       	metaData.put("PatientTransferSyntaxUID", getTagInformation(TagFromName.TransferSyntaxUID )); 
	        
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
	
*/


package fr.gustaveroussy.imaging;
import org.dcm4che3.io.DicomInputStream;
import org.dcm4che3.data.VR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.io.DicomInputStream;
import org.dcm4che3.io.DicomOutputStream;
import org.springframework.stereotype.Service;

import com.pixelmed.dicom.DicomException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.pixelmed.dicom.DicomException;

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

import com.pixelmed.dicom.PersonNameAttribute;
	
@SpringBootApplication

//classe principale // 


public class ImagingApplication implements CommandLineRunner {

public static final Logger logger = LoggerFactory.getLogger(ImagingApplication.class);


	private static final Object[] String = null;
	
	private  AttributeList attributeList = new AttributeList() ;
	
	
	//chemin vers le fichier dicom //
		@Value("${path.to.input.file}")
		private  String inputFile ; 
		
		
		// chemin vers le nouveau Dicom créé //
		
		@Value("${path.to.output.file}")
		private  String outputFile ;

	// Chemin du fichier DICOM source d'origine
	//  File inputFile = new File("C:\\Users\\m_graa\\Desktop\\stage_bioinfo\\dicom1.dcm"); //

	    // Chemin du fichier DICOM anonymisée 
	  //  File outputFile = new File("C:\\Users\\m_graa\\Desktop\\stage_bioinfo\\dicom_annony.dcm"); //

	
// fonction principale main ( point d'entrée du programme ) //

  public static void main(String[] args) {	
SpringApplication.run(ImagingApplication.class, args);
		
	}
@Override
	public void run(String... args) throws Exception {
		
	
		  try (DicomInputStream dicomInputStream = new DicomInputStream(new FileInputStream((inputFile)))) {
        	
        	Attributes attributes = dicomInputStream.readFileMetaInformation();
        	anonymizePatientAttributes(attributes);
        	 attributes = filterAttributes( attributes);
             try (DicomOutputStream dos = new DicomOutputStream(new FileOutputStream(outputFile), null)) {
                 dos.writeDataset(attributes, dicomInputStream.readFileMetaInformation());
             }
             
             
		  }
             
             
} 
             


    private void anonymizePatientAttributes(Attributes attributes) {
	
    	
    	for (int tag : attributes.tags()) {
        	
    	    VR vr = attributes.getVR(tag); 
    	    
    	    if (vr != null && vr.equals(VR.PN)) { // Anonymiser les données de type Person Name
    	    	attributes.setString(tag, VR.PN, "ANONYME");
    	    } else if (vr != null && vr.equals(VR.DA) || vr.equals(VR.DT)) { // Anonymiser les données de type Date ou Date/Time
    	    	attributes.setString(tag, vr, "19000101");
    	    } else if (vr != null && vr.equals(VR.CS)) { // Anonymiser les données de type Code String
    	    	attributes.setString(tag, vr, "");
    	    } else if (vr != null && vr.equals(VR.SH)) { // Anonymiser les données de type Short String
    	    	attributes.setString(tag, vr, "");
    	    } else if (vr != null && vr == null || vr.equals(VR.LO)) { // Anonymiser les données de type Long String
    	    	attributes.setString(tag, vr, "");
    	    } else if (vr != null && vr.equals(VR.SQ)) { // Supprimer les données de type Sequence
    	    	attributes.remove(tag);
    	    } else if (vr != null && vr.equals(VR.OW) || vr.equals(VR.OF) || vr.equals(VR.OB) || vr.equals(VR.UN)) { // Supprimer les données binaires
    	    	attributes.remove(tag);
    	    } 
    	}  	
    	
     	
    	
    	
	
}
	private Attributes filterAttributes(Attributes attributes) {
        Attributes filteredAttributes = new Attributes();
        filteredAttributes.addAll(attributes, false);
        filteredAttributes.remove(Tag.TransferSyntaxUID);
        filteredAttributes.remove(Tag.StudyInstanceUID);
        filteredAttributes.remove(Tag.SeriesInstanceUID);
        filteredAttributes.remove(Tag.SOPClassUID);
        filteredAttributes.remove(Tag.ImagePosition );
        filteredAttributes.remove(Tag.PixelData  );
      
        return filteredAttributes;
    } 

}


	



























