package fr.gustaveroussy.imaging.controller;



import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.annotation.PostConstruct;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.data.VR;
import org.springframework.stereotype.Controller;



@Controller public class ImagingController {
	
	
//  methods definition  //	
	@PostConstruct
	public void afficherMessage() {
	    System.out.println("L'application a démarré !");
	}
	

	public void writeAttributesToFile(Attributes beforeanonymisation, String fileName) {
		
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
	
	
	
	
	  public void anonymizePatientAttributes(Attributes attributes) {
	    
	    	Attributes anonymizedAttributes = new Attributes();
	    	anonymizedAttributes.addAll(attributes, false);
	    	
	    	for (int tag : attributes.tags()) {
	    		
	    
	    		
	    	    VR vr = attributes.getVR(tag); 
	    	    
	    	    
	    	if (tag == Tag.StudyInstanceUID || tag == Tag.SeriesInstanceUID || tag == Tag.SOPClassUID || 
	    			     tag == Tag.ImagePositionPatient || tag == Tag.PixelData ) {
	    			     continue;
	    			}
	    	else if (tag == Tag.TransferSyntaxUID   ) {
	    	
				 
	    	        continue;
				 }    
	    	    
	    	    else if( (vr != null && vr == (VR.PN))) { 
	    	    
	    	    	attributes.setString(tag, VR.PN, "ANONYME");
	    	    	
	    	    	
	    	    } else if ((vr != null && vr.equals(VR.DA) || vr.equals(VR.DT))) { 
	    	    	attributes.setString(tag, vr, "19000101");
	    	    	
	    	    } else if ((vr != null && vr.equals(VR.CS)  )) { 
	    	    	attributes.setString(tag, vr, "");
	    	    
	    	    } else if ((vr != null && vr.equals(VR.SH))) {
	    	    	attributes.setString(tag, vr, "");
	    	    
	    	    } else if ((vr != null &&  vr.equals(VR.LO)) ){ 
	    	    	attributes.setString(tag, vr, "");
	    	    
	    	    } else if ((vr != null && vr.equals(VR.SQ))) {
	    	    	attributes.remove(tag);
	    	 
	    	    } else if ((vr != null && vr.equals(VR.OW) || vr.equals(VR.OF) || vr.equals(VR.OB) || vr.equals(VR.UN))) { 
	    	    	attributes.remove(tag); 
	    	 
	    	    
	    	    }
	    	    
	      
	    	    
	    	}
	    	
	    	
	    	
	    	
	    }
	
	
	
}
 
 
 





	
	 






	


	
	

	
	
	
	
	
	




