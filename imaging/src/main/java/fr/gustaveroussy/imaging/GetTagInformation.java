package fr.gustaveroussy.imaging;

import com.pixelmed.dicom.Attribute;
import com.pixelmed.dicom.AttributeList;
import com.pixelmed.dicom.AttributeTag;

public class GetTagInformation {
	public static String getTagInformation(AttributeTag tag) {
		AttributeList attributeList = new AttributeList();
       
		return Attribute.getDelimitedStringValuesOrDefault(attributeList, tag, "NOT FOUND");
    }

}
