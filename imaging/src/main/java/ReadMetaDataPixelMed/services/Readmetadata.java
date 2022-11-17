package ReadMetaDataPixelMed.services;

import java.util.LinkedHashMap;
import java.util.Map;

import com.pixelmed.dicom.AttributeTag;
import com.pixelmed.dicom.DicomException;
import com.pixelmed.dicom.TagFromName;
import com.pixelmed.display.SourceImage;

public class Readmetadata {
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

private static String getTagInformation(AttributeTag patientname) {
	// TODO Auto-generated method stub
	return null;
}
}
