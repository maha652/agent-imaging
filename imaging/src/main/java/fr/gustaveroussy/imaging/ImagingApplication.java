package fr.gustaveroussy.imaging;
import org.dcm4che3.io.DicomInputStream;

import org.dcm4che3.data.VR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;

import org.dcm4che3.io.DicomOutputStream;




import java.io.BufferedWriter;

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


	private static final Object[] String = null;
	

	

		@Value("${path.to.input.file}")
		private  String inputFile ; 
		
		
	
		
		@Value("${path.to.output.file}")
		private  String outputFile ;
		
		
		@Value("${path.to.fileName}")
		private  String fileName  ;
		
		
		


  public static void main(String[] args) {	
  logger.info("Starting the Imaging Application...");  

SpringApplication.run(ImagingApplication.class, args);
		
	}
@Override

public void run(String... args) throws Exception {
	     
	      logger.info("Processing input file: {}", inputFile);
		  try (DicomInputStream dicomInputStream = new DicomInputStream(new FileInputStream((inputFile)))) {
			
			Attributes attributes = dicomInputStream.readFileMetaInformation();
			Attributes beforeanonymisation = dicomInputStream.readFileMetaInformation();
		
			writeAttributesToFile( beforeanonymisation, fileName);
			anonymizePatientAttributes(attributes);
		
		    try (DicomOutputStream dos = new DicomOutputStream(new FileOutputStream(outputFile), "tsuid")) {
            	 
            	 dos.writeDataset( attributes, dicomInputStream.readFileMetaInformation());
             }
             
             
		  }
             
	        
} 

private void writeAttributesToFile(Attributes beforeanonymisation, String fileName) {
	logger.debug("Writing attributes to file: {}", fileName);
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
        for (int tag : beforeanonymisation.tags()) {
            VR vr = beforeanonymisation.getVR(tag);
            String value = beforeanonymisation.getString(tag);
            String line = tag + " " + vr + " " + value;
            writer.write(line);
            writer.newLine();
        }
    } catch (IOException e) {
    	logger.error("Error writing attributes to file: {}", fileName, e);
        e.printStackTrace();
    }
}
    



    private void anonymizePatientAttributes(Attributes attributes) {
    	logger.debug("Anonymizing patient attributes...");
    	Attributes anonymizedAttributes = new Attributes();
    	anonymizedAttributes.addAll(attributes, false);
    	
    	for (int tag : attributes.tags()) {
    		
    		logger.debug(" tag : {}" , tag);
    		
    	    VR vr = attributes.getVR(tag); 
    	    
    	    
    	if (tag == Tag.StudyInstanceUID || tag == Tag.SeriesInstanceUID || tag == Tag.SOPClassUID || 
    			     tag == Tag.ImagePositionPatient || tag == Tag.PixelData ) {
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
    	return ;
    	
    	
    	
    }
    
  
    	
    
    
}
	
	














	



























