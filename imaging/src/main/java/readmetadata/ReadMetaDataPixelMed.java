package readmetadata;
import com.pixelmed.dicom.*;
import com.pixelmed.display.SourceImage;

import fr.gustaveroussy.imaging.ImagingApplication;
import fr.gustaveroussy.imaging.services.DemoDicom;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;


public class ReadMetaDataPixelMed {
	
	public static final Logger logger = LoggerFactory.getLogger(ReadMetaDataPixelMed.class);
	
	private static AttributeList attributeList = new AttributeList() ;
	public static void main(String[] args)throws DicomException, IOException  {
		
		/* String dcmFilePath = "C:\\Users\\m_graa\\Desktop\\stage_bioinfo\\1.3.12.2.1107.5.2.18.42239.2019080716095943021807871.dcm";
	        
	     readAttributes(dcmFilePath); */
		
		attributeList.read(new File("C:\\Users\\m_graa\\Desktop\\stage_bioinfo\\1.3.12.2.1107.5.2.18.42239.2019080716095943021807871.dcm"));

	     Map<String, String> metaData = readMetadata();
	     for (Map.Entry<String, String> entry :metaData.entrySet()) {
	                System.out.println(entry.getKey()+" : "+entry.getValue());
	           
	     }   
	     Filewriter fw =  new Filewriter ();
	     fw.exportToFile("C:\\Users\\m_graa\\Desktop\\stage_bioinfo\\metadata.txt",metaData.toString());
	     
	     }
	
	 
	    private static void readAttributes(String dcmFilePath) throws DicomException, IOException {
	        attributeList.read(new File(dcmFilePath));
	    }
	 
	    private static Map<String, String> readMetadata() throws DicomException {
	        Map<String, String> metaData = new LinkedHashMap<>();
	        metaData.put("Patient Name", getTagInformation(TagFromName.PatientName));
	        metaData.put("Patient ID", getTagInformation(TagFromName.PatientID));
	        metaData.put("Transfer Syntax", getTagInformation(TagFromName.TransferSyntaxUID));
	        metaData.put("SOP Class", getTagInformation(TagFromName.SOPClassUID));
	        metaData.put("Modality", getTagInformation(TagFromName.Modality));
	        metaData.put("Samples Per Pixel", getTagInformation(TagFromName.SamplesPerPixel));
	        metaData.put("Photometric Interpretation", getTagInformation(TagFromName.PhotometricInterpretation));
	        metaData.put("Pixel Spacing", getTagInformation(TagFromName.PixelSpacing));
	        metaData.put("Bits Allocated", getTagInformation(TagFromName.BitsAllocated));
	        metaData.put("Bits Stored", getTagInformation(TagFromName.BitsStored));
	        metaData.put("High Bit", getTagInformation(TagFromName.HighBit));
	        SourceImage img = new com.pixelmed.display.SourceImage(attributeList);
	        metaData.put("Number of frames", String.valueOf(img.getNumberOfFrames()));
	        metaData.put("Width", String.valueOf(img.getWidth()));
	        metaData.put("Height", String.valueOf(img.getHeight()));
	        metaData.put("Is Grayscale", String.valueOf(img.isGrayscale()));
	        metaData.put("Pixel Data present", String.valueOf(!getTagInformation(TagFromName.PixelData).isEmpty()));
	        return metaData;
	        
	        
	    }
	    
		private static String getTagInformation(AttributeTag tag) {
	        return Attribute.getDelimitedStringValuesOrDefault(attributeList, tag, "NOT FOUND");
	    }
	
		
		
		    	
		

	
		
		
		    	
		   
		


}
