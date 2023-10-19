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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication

public class ImagingApplication implements CommandLineRunner {

public static final Logger logger = LoggerFactory.getLogger(ImagingApplication.class);

		@Value("${path.to.input.file}")
		private  String originalDicomFile ; 
			
		
		@Value("${path.to.output.file}")
		private  String anonymDicomFile ;
		
		
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
	     
			logger.info("Processing input file: {}", originalDicomFile);
	           
			try (DicomInputStream dicomInputStream = new DicomInputStream(new FileInputStream((originalDicomFile)))) {
				Attributes attributes = dicomInputStream.readFileMetaInformation();
				Attributes beforeanonymisation = dicomInputStream.readDataset(); 
	            AddSampleIdToDicom(attributes);
				writeAttributesToFile(beforeanonymisation, attributes , fileName );
				anonymizePatientAttributes(attributes); 		
			}
		 
			try (DicomInputStream dicomInputStream = new DicomInputStream(new FileInputStream(originalDicomFile));
					DicomOutputStream dicomOutputStream = new DicomOutputStream(new File(anonymDicomFile));) {
				
			    Attributes fmi = dicomInputStream.readFileMetaInformation();
			    Attributes dataset = dicomInputStream.readDataset();
			    AddSampleIdToDicom(dataset);
			    anonymizePatientAttributes(dataset);
			    fmi = dataset.createFileMetaInformation(fmi.getString(Tag.TransferSyntaxUID));
			    dicomOutputStream.writeDataset(fmi, dataset);
	            logger.info("Sample ID ajouté : {}", dataset.getString(0x00210011));
	            writeAttributesToFile(dataset, dataset , test );
			}
					  
} 

		  
private void AddSampleIdToDicom(Attributes attributes) {
	logger.debug("Add Sample ID");

   
 
    String SampleId = UIDUtils.createUID();
    System.out.println("SampleId généré : " + SampleId);
    try {
  
    	attributes.setString(0x00210011, VR.LO, SampleId );
     
     
     
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



 private Attributes anonymizePatientAttributes(Attributes attributes) {
	
	 
		 
for (int tag : attributes.tags()) {  
	String tagValueanonym = attributes.getString(tag);

	 logger.debug("Tag ID_anonym : {}", tag);
	 if (tag == Tag.PatientName) {
    	attributes.setString(Tag.PatientName , VR.PN, "ANONYME");
    	logger.info(tagValueanonym);
    	
   	
     } else if (tag == Tag.PatientID) {
    	attributes.setString(Tag.PatientID, VR.LO, "190010000AA");
    	logger.info(tagValueanonym);
      }
	 
	 
     else if (tag == Tag.PatientBirthDate) {
     	attributes.setString(Tag.PatientBirthDate, VR.DA, "190010000AA");
     	logger.info(tagValueanonym);
       }
	 
     else if (tag == Tag.PatientAddress) {
      	attributes.setString(Tag.PatientAddress, VR.LO, "voie/rue/pays");
      	logger.info(tagValueanonym);
        }
  
    
	
    
    logger.debug("anonymisation : {}" , tagValueanonym);
  
   
   
}
	

	 return attributes; 
}   

}
















