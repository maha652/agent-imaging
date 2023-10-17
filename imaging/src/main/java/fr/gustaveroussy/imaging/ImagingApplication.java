package fr.gustaveroussy.imaging;
import org.dcm4che3.io.DicomInputStream;
import org.dcm4che3.io.DicomOutputStream;
import org.dcm4che3.data.VR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.util.UIDUtils;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication

public class ImagingApplication implements CommandLineRunner {

public static final Logger logger = LoggerFactory.getLogger(ImagingApplication.class);

		@Value("${path.to.input.file}")
		private  String OriginalDicomFile ; 
			
		
		@Value("${path.to.output.file}")
		private  String AnonymDicomFile ;
		
		
		@Value("${path.to.fileName}")
		private  String fileName  ;
		
		
		@Value("${path.to.test}")
		private  String test  ;
		
		
  public static void main(String[] args) {	
  logger.info("Starting the Imaging Application...");  

SpringApplication.run(ImagingApplication.class, args);
		
	}
@Override

public void run(String... args) throws Exception {
	     
	      logger.info("Processing input file: {}", OriginalDicomFile);
	           
		  try (DicomInputStream dicomInputStream = new DicomInputStream(new FileInputStream((OriginalDicomFile)))) {
			  
			Attributes attributes = dicomInputStream.readFileMetaInformation();
			Attributes beforeanonymisation = dicomInputStream.readDataset(); 
            AddSampleIdToDicom(attributes);
			writeAttributesToFile(beforeanonymisation, attributes , fileName );
			anonymizePatientAttributes(attributes); 
		
				        
	
			
			
			}
          
		  }
		  

	        


private void AddSampleIdToDicom(Attributes attributes) {
	logger.debug("Add Sample ID");

   
 
    String uniqueIdentifier = UIDUtils.createUID();
    System.out.println("SampleId généré : " + uniqueIdentifier);
    try {
  
    	attributes.setString(0x00210011, VR.LO, uniqueIdentifier );
     
     
     
    } catch (Exception e) {
       
        logger.error("Erreur lors de l'ajout de l'attribut personnalisé 'SampleID' : " + e.getMessage());
    }
    

} 



private void writeAttributesToFile(Attributes beforeanonymisation, Attributes attributes,String fileName ) {
    logger.debug("Writing attributes to file: {}", fileName);
    try (FileWriter fileWriter = new FileWriter(fileName)) {
        int[] tagsToExtract = {
            Tag.PatientName,
            Tag.InstitutionName,
            Tag.StudyDescription,  
            0x00210011
         
        };
        
   
        
        for (int tag : beforeanonymisation.tags()) {
            if (contains(tagsToExtract, tag)) {
                String tagValue = beforeanonymisation.getString(tag);
                
                logger.debug("Tag ID : {}", tag);
                
               
                String label = "";
                if (tag == Tag.PatientName) {
                    label = "PatientName";
                } else if (tag == Tag.InstitutionName) {
                    label = "organisation";
                } else if (tag == Tag.StudyDescription) {
                    label = "technology";
                }
                else if (tag == 0x00210011) {
                    label = "SampleId";
                }
               
                
                
                

      
                fileWriter.write(label + ": " + tagValue);
                
                fileWriter.write(System.lineSeparator());
               
           
                
                
            }
            
           
        }
       String sampleIDValue = attributes.getString(0x00210011);
        if (sampleIDValue != null) {
            fileWriter.write("SampleID: " + sampleIDValue);
            fileWriter.write(System.lineSeparator());
        }
      
        
    } catch (IOException e) {
        logger.error("Error writing attributes to file: {}", fileName, e);
        e.printStackTrace();
    }
}






private boolean contains(int[] array, int value) {
    for (int element : array) {
        if (element == value) {
            return true;
        }
    }
    return false;
}







 
   



private Attributes anonymizePatientAttributes(Attributes attributes ) {
    	logger.debug("Anonymizing patient attributes...");
    	
    	
    	for (int tag : attributes.tags()) {
    		
    		logger.debug(" tag : {}" , tag);
    		
    	    VR vr = attributes.getVR(tag); 
    	    
    	    
    	if (tag == Tag.StudyInstanceUID || tag == Tag.SeriesInstanceUID || tag == Tag.SOPClassUID || 
    			     tag == Tag.ImagePositionPatient || tag == Tag.PixelData || tag == 0x00210011 ) {
    			     continue;
    			}
    	else if (tag == Tag.TransferSyntaxUID   ) {
    		logger.debug(" transferSyntaxUID_tag : {}" , tag);
    		
    		
			 
    	        continue;
			 }  
    	
	
  
    	    
    	    else if( (vr != null && vr == (VR.PN))) { 
    	    
    	    	attributes.setString(tag, VR.PN, "ANONYME");
    	    	logger.debug(" VR.PN : {}" , tag);
    	    	
    	    } else if ((vr != null && vr.equals(VR.DA) || vr.equals(VR.DT))) { 
    	    	attributes.setString(tag, vr, "19000101");
    	    	logger.debug(" VR.DA : {}" , tag);
    	    } else if ((vr != null && vr.equals(VR.CS)  )) { 
    	    	attributes.setString(tag, vr, "");
    	    	logger.debug(" VR.CS : {}" , tag);
    	    } else if ((vr != null && vr.equals(VR.SH))) {
    	    	attributes.setString(tag, vr, "");
    	    	logger.debug(" VR.SH : {}" , tag);
    	    } else if ((vr != null &&  vr.equals(VR.LO)) ){ 
    	    	attributes.setString(tag, vr, "");
    	    	logger.debug(" VR.LO : {}" , tag);
    	    } else if ((vr != null && vr.equals(VR.SQ))) {
    	    	attributes.remove(tag);
    	    	logger.debug(" VR.SQ : {}" , tag);
    	    } else if ((vr != null && vr.equals(VR.OW) || vr.equals(VR.OF) || vr.equals(VR.OB) || vr.equals(VR.UN))) { 
    	    	attributes.remove(tag); 
    	    	logger.debug(" VR.OW, OF, OB ,UN : {}" , tag);
    	    
    	    }
    	    
    	logger.debug("anonymisation" , tag);
 
    	
    	    
    	}
    	
    
        return (attributes ) ;
        
    
      	
    	
}
    
    
     
    
}      
	
	











	



























