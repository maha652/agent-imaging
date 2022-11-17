package ReadMetaDataPixelMed;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.management.AttributeList;

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
 
    private static Map<String, String> readMetadata() {
		// TODO Auto-generated method stub
		return null;
	}

	private static void readAttributes(String dcmFilePath)  {
        attributeList.read(new File(dcmFilePath));
    }
 
