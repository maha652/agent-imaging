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

	static final Logger logger = LoggerFactory.getLogger(ImagingApplication.class);
	

	

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
		
			writeAttributesToFile( attributes, fileName);
			anonymizePatientAttributes(attributes);
		     
			logger.info("exporting anon dicom to output file: {}", outputFile);

		    try (DicomOutputStream dos = new DicomOutputStream(new FileOutputStream(outputFile), "TEST")) {
            	 
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
    		
        	logger.debug("current tag : {}",tag);

    		
    	    VR vr = attributes.getVR(tag); 
    	    
    	    
    	if (tag == Tag.StudyInstanceUID || tag == Tag.SeriesInstanceUID || tag == Tag.SOPClassUID || 
    			     tag == Tag.ImagePositionPatient || tag == Tag.PixelData ) {
    			     continue;
    			}
    	else if (tag == Tag.TransferSyntaxUID   ) {
    	 
        		logger.debug("TransferSyntaxUID ignored : {}",tag);

    	        continue;
			 }    
    		
    	    
    	    else if (vr != null && vr == (VR.PN)) { 
    	    
    	    	attributes.setString(tag, VR.PN, "ANONYME");
    	    	
    	    } else if (vr != null && (vr == (VR.DA) || vr.equals(VR.DT))) { 
    	    	attributes.setString(tag, vr, "19000101");
    	    } else if (vr != null && vr ==(VR.CS)  ) { 
    	    	attributes.setString(tag, vr, "");
    	    } else if (vr != null && vr ==(VR.SH)) {
    	    	attributes.setString(tag, vr, "");
    	    } else if (vr != null &&  vr ==(VR.LO)) { 
    	    	attributes.setString(tag, vr, "");
    	    } else if (vr != null && vr ==(VR.SQ)) {
    	    	attributes.remove(tag);
    	    } else if (vr != null && (vr ==(VR.OW) || vr ==(VR.OF) || vr == (VR.OB) || vr ==(VR.UN)) ) { 
    	    	attributes.remove(tag); 
    	    
    	    }
    	    
    	    
    	    
    	}
    	return ;
    }
    	
    
    
    
    
    
    
    	    
  
	
	
	
	
}
	
	














	



























