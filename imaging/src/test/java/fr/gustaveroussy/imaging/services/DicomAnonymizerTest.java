

package fr.gustaveroussy.imaging.services;

import java.io.File;
import java.io.IOException;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.data.VR;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.pixelmed.dicom.AttributeList;
import com.pixelmed.dicom.DicomException;
import com.pixelmed.dicom.TagFromName;
import com.pixelmed.dicom.TransferSyntax;

@SpringBootTest
public class DicomAnonymizerTest {
	public static final Logger logger = LoggerFactory.getLogger(DicomAnonymizer.class);
	
	@Autowired
	DicomAnonymizer dicomAnonymizer;
	
	@Test
    public  void testAnon() throws IOException, DicomException {

        // Chemin du dossier source contenant les fichiers DICOM à copier
        File sourceDirectory = new File("C:\\Users\\m_graa\\Desktop\\stage_bioinfo\\source_dicom");

        // Chemin du dossier de destination où la copie anonymisée sera enregistrée
        File destinationDirectory = new File("C:\\Users\\m_graa\\Desktop\\stage_bioinfo\\destination_dicom");

        
        
    }
	private Attributes anonymizePatientAttributes(Attributes attributes ) {
    	logger.debug("Anonymizing patient attributes...");
    	
    	
    	for (int tag : attributes.tags()) {
    		
    		logger.debug(" tag : {}" , tag);
    		
    	    VR vr = attributes.getVR(tag); 
    	    
    	    
    	if (tag == Tag.StudyInstanceUID || tag == Tag.SeriesInstanceUID || tag == Tag.SOPClassUID || 
    			     tag == Tag.ImagePositionPatient || tag == Tag.PixelData || tag == 0x00210011 || tag ==0x0028 || tag == 0x7FE0 || tag == 0x0002  ) {
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



