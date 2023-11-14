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

String baseFileName = UIDUtils.createUID();
String baseFolderName = baseFileName;
String attributsBeforeAnonymisationFileName = baseFileName + "_before.csv";
String dicomAnnonyFileName = baseFileName + ".dcm";
String attributs_apres_anonymisation = baseFileName + "_after.csv";

		@Value("${path.to.input.file}")
		private  String originalDicomFile ; 
				
		public static void main(String[] args) {	
			logger.info("Starting the Imaging Application...");  

			SpringApplication.run(ImagingApplication.class, args);
		
		}

		@Override
		public void run(String... args) throws Exception {
	     
			logger.info("Processing input file: {}", originalDicomFile);
	           
			File baseFolder = new File(baseFolderName);
	        if (!baseFolder.exists()) {
	            baseFolder.mkdirs();
	        }

	
			try (DicomInputStream dicomInputStream = new DicomInputStream(new FileInputStream(originalDicomFile));
					DicomOutputStream dicomOutputStream = new DicomOutputStream(new File(baseFolderName,dicomAnnonyFileName));) {
				
				
				
			    Attributes fmi = dicomInputStream.readFileMetaInformation();
			    Attributes dataset = dicomInputStream.readDataset();
	
				writeAttributesToFile(dataset, dataset , attributsBeforeAnonymisationFileName );

			    anonymizePatientAttributes(dataset ,  dicomAnnonyFileName);
			    fmi = dataset.createFileMetaInformation(fmi.getString(Tag.TransferSyntaxUID));
			    dicomOutputStream.writeDataset(fmi, dataset);
	            logger.info("Sample ID ajouté : {}", dataset.getString(0x00210011));
	            writeAttributesToFile(dataset, dataset , attributs_apres_anonymisation );
			}
	
			
			
			
					
			
} 

		
		private void writeAttributesToFile(Attributes beforeanonymisation, Attributes attributes, String fileName) {
		    logger.debug("Writing attributes to file: {}", fileName);
            
		    try (FileWriter fileWriter = new FileWriter(baseFolderName + File.separator + fileName)) {
		        String[] labels = {"datasetName","organisation", "patientId","projectName","technicalPlatform", "technology"};
		        String[] values = new String[6];
		        values[0] = "Dicom_metadata"; 
		        values[3] = "Service imagerie diagnostique"; 
		        values[4] = "imagerie"; 


		        for (int tag : beforeanonymisation.tags()) {
		        	
		        	 
		            switch (tag) {
		          
		                case Tag.PatientID:
		                    values[2] = beforeanonymisation.getString(tag);
		                    break;
		                case Tag.InstitutionName:
		                    values[1] = beforeanonymisation.getString(tag);
		                    break;
		                case Tag.StudyDescription:
		                    values[5] = beforeanonymisation.getString(tag);
		                    break;
		                default:
		                
		                    break;
		            }
		        }

		      
		        fileWriter.write(String.join(";", labels));
		        fileWriter.write(System.lineSeparator());

		      
		        fileWriter.write(String.join(";", values));
		    } catch (IOException e) {
		        logger.error("Error writing attributes to file: {}", fileName, e);
		        e.printStackTrace();
		    }
		}




 private Attributes anonymizePatientAttributes(Attributes attributes , String anonymDicomFile) {
	
	 
		 
for (int tag : attributes.tags()) {  
	String tagValueanonym = attributes.getString(tag);

	 logger.debug("Tag ID_anonym : {}", tag);
	 if (tag == Tag.PatientName) {
    	attributes.setString(Tag.PatientName , VR.PN, "ANONYME");
    	logger.info(tagValueanonym);
    	
   	
     } else if (tag == Tag.PatientID) {
    	attributes.setString(Tag.PatientID, VR.LO, "NIP");
    	logger.info(tagValueanonym);
      }
	 
	 
     else if (tag == Tag.PatientBirthDate) {
     	attributes.setString(Tag.PatientBirthDate, VR.DA, "BirthDate");
     	logger.info(tagValueanonym);
       }
	 
     else if (tag == Tag.PatientAddress) {
      	attributes.setString(Tag.PatientAddress, VR.LO, "street/country");
      	logger.info(tagValueanonym);
        }
  
     else if (tag == Tag.PatientSex) {
       	attributes.setString(Tag.PatientSex, VR.CS, "M/F");
       	logger.info(tagValueanonym);
         }
	 
     else if (tag == Tag.PatientAge) {
        	attributes.setString(Tag.PatientAge, VR.AS, "Age");
        	logger.info(tagValueanonym);
          }
	 
     else if (tag == Tag.InstitutionName) {
     	attributes.setString(Tag.InstitutionName, VR.LO, "HOSPITAL");
     	logger.info(tagValueanonym);
       }
	
    
    logger.debug("anonymisation : {}" , tagValueanonym);
  
   
   
}
	

	 return attributes; 
	
}   

}
















