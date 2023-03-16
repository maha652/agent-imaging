package fr.gustaveroussy.imaging.controller;



import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.VR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;



@Controller public class ImagingController {
	
	

	static final Logger logger = LoggerFactory.getLogger(ImagingController.class);	
	
	 
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
		    	
		        e.printStackTrace();
		    }
		}
		
		
}

	
	 

	


	
	

	
	
	
	
	
	




