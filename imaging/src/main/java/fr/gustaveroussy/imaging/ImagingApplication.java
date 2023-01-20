package fr.gustaveroussy.imaging;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.pixelmed.dicom.Attribute;
import com.pixelmed.dicom.AttributeList;
import com.pixelmed.dicom.AttributeTag;
import com.pixelmed.dicom.DicomException;
import com.pixelmed.dicom.TagFromName;
import com.pixelmed.display.SourceImage;

import fr.gustaveroussy.imaging.services.DemoDicom;
import readmetadata.Filewriter;

@SpringBootApplication
public class ImagingApplication implements CommandLineRunner {


	public static final Logger logger = LoggerFactory.getLogger(ImagingApplication.class);
	
	private  AttributeList attributeList = new AttributeList() ;
	
	@Value("${path.to.dc.file}")
	private  String filePath ; 
	
	@Value("${path.to.write.file}")
	private  String writeFile ;
	
	
	
	public static void main(String[] args) {
		SpringApplication.run(ImagingApplication.class, args);
		
	}

	@Override
	public void run(String... args) throws Exception {
		
	
		attributeList.read(new File(filePath));

	     Map<String, String> metaData = readMetadata();
	     for (Map.Entry<String, String> entry :metaData.entrySet()) {
	                System.out.println(entry.getKey()+" : "+entry.getValue());
	           
	     }   
	     Filewriter fw =  new Filewriter ();
	     fw.exportToFile(writeFile,metaData.toString()); 
	     
	    
	     
	     }
	
	
	 
	    private   Map<String, String> readMetadata() throws DicomException {
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
	        metaData.put("Image type ", getTagInformation(TagFromName.ImageType ));
	        metaData.put("otherPatientNames ", getTagInformation(TagFromName.OtherPatientNames  ));
	        metaData.put("PatientAge", getTagInformation(TagFromName.PatientAge  ));
	        SourceImage img = new com.pixelmed.display.SourceImage(attributeList);
	        
	        metaData.put("Number of frames", String.valueOf(img.getNumberOfFrames()));
	        metaData.put("Width", String.valueOf(img.getWidth()));
	        metaData.put("Height", String.valueOf(img.getHeight()));
	        metaData.put("Is Grayscale", String.valueOf(img.isGrayscale()));
	        metaData.put("Pixel Data present", String.valueOf(!getTagInformation(TagFromName.PixelData).isEmpty()));
	        
	        return metaData;
	    }
	        private  String getTagInformation(AttributeTag tag) {
		        return Attribute.getDelimitedStringValuesOrDefault(attributeList, tag, "NOT FOUND");
		    }
	
			
		
		
	    	
			


		

	}


