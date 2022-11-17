package fr.gustaveroussy.imaging.services;

import java.io.File;
import java.io.IOException;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.BulkData;
import org.dcm4che3.data.Tag;
import org.dcm4che3.data.VR;
import org.dcm4che3.data.ValueSelector;
import org.dcm4che3.io.DicomInputStream;
import org.dcm4che3.io.DicomInputStream.IncludeBulkData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DemoDicom {

	private static final Logger logger = LoggerFactory.getLogger(DemoDicom.class);

	public void demo(String dicomFilePath) {
		try (DicomInputStream din = new DicomInputStream(new File(dicomFilePath));){
		    
			//attributes
			din.setIncludeBulkData(IncludeBulkData.URI);
		    Attributes attr=din.readDataset();
		    logger.info( attr.toString() );
		    
		    //Image
		    Object pixeldata1 = attr.getValue(Tag.PixelData);
		    BulkData bulkData1 = (BulkData) pixeldata1;
		    logger.info("bulkData:"+ bulkData1.longLength() );
            
		    logger.info( "#############################");
		    
		    //get a value
		    ValueSelector vs = ValueSelector.valueOf("DicomAttribute[@privateCreator='ELCINT1' and @tag='00E10024']/Value[@number='1']");
	        Attributes attrs = new Attributes(2);
	        attrs.setBytes("ELCINT1", 0x00E10024, VR.UN, new byte[]{89, 32});
	        logger.info(vs.selectStringValue(attrs, null));
	        
	        
	        
		}
		catch (IOException e) {
		    e.printStackTrace();
		}
	}

}








