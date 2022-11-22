package readmetadata;
import com.pixelmed.dicom.*;
import com.pixelmed.display.SourceImage;
import java.io.BufferedReader;



import fr.gustaveroussy.rcp.model.RedcapAlterationImport;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.pixelmed.dicom.AttributeList;

public class ReadMetaDataPixelMed {
	
	
	 private static AttributeList attributeList = new AttributeList();

	public static void main(String[] args) {
		 String dcmFilePath = "C:\\Users\\m_graa\\Desktop\\stage_bioinfo\\1.3.12.2.1107.5.2.18.42239.2019080716095943021807871.dcm";
	        try {
	            readAttributes(dcmFilePath);
	            Map<String, String> metaData = readMetadata();
	            for (Map.Entry<String, String> entry :metaData.entrySet()) {
	                System.out.println(entry.getKey()+" : "+entry.getValue());
	            }
	        }catch (Exception e) {
	            System.out.println("Error due to: "+e.getMessage());
	        }
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
	
		
		   public class Filewriter { 
			   
			   private static void  Filewriter(String[] args) throws IOException {
		    	// TODO Auto-generated method stub
		    	FileWriter fw =new FileWriter(new File ("C:\\Users\\m_graa\\agent-imaging\\metadata.txt"));
		    	fw.write("metaData");
		    	fw.flush();
		    	fw.close();
		    	
		    	
		    	}
		    	
		   }
		
		
		
		
		
		
}
		
	  
		
		
		  
	 

	
	    	
	    

	    


