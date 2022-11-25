package fr.gustaveroussy.imaging;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class Filewriter {
	
	public void   exportToFile(String filePath , String content) throws IOException {
    	
    	FileWriter fw =new FileWriter(new File (filePath));
    	fw.write(content);
    	fw.flush();
    	fw.close();
    	
    	
    	}
	
	public void  exportToFile(String filePath , Map<String,String> content) throws IOException {
    	
    	FileWriter fw =new FileWriter(new File (filePath));
    	//iterate over Map
    	//fw.write(content);
    	
    	
    	fw.flush();
    	fw.close();
    	
    	
    	}

	
	
	
	
	

}
