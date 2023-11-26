package fr.gustaveroussy.imaging.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import fr.gustaveroussy.imaging.services.AnonymisationDicomService;
import fr.gustaveroussy.imaging.services.ExtractMetadataDicomService;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.io.DicomInputStream;
import org.dcm4che3.io.DicomOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Controller

public class ImagingController {

    @Autowired
    private AnonymisationDicomService anonymisationDicomService;

    @Autowired
    private ExtractMetadataDicomService extractMetadataDicomService;

    @Value("${path.to.input.folder}")
    private String dicomFolder;

    

  
    public String processDicomFiles() {
        File dicomFolderFile = new File(dicomFolder);

        if (!dicomFolderFile.exists() || !dicomFolderFile.isDirectory()) {
            return "Invalid DICOM folder path: " + dicomFolder;
        }

        File[] dicomFiles = dicomFolderFile.listFiles((dir, name) -> name.toLowerCase().endsWith(".dcm"));

        if (dicomFiles == null || dicomFiles.length == 0) {
            return "No DICOM files found in the folder: " + dicomFolder;
        }

        for (File dicomFile : dicomFiles) {
            try {
                processDicomFile(dicomFile);
            } catch (IOException e) {
                e.printStackTrace();
                return "Error processing DICOM files.";
            }
        }

        return "DICOM files processed successfully.";
    }

private  void processDicomFile(File originalDicomFile) throws FileNotFoundException, IOException {
    	
    	
        String baseFileName = getBaseFileName(originalDicomFile.getName());
        String baseFolderName = baseFileName;
        String attributsBeforeAnonymisationFileName = baseFileName + "_before.csv";
        String dicomAnnonyFileName = baseFileName + "_anonyme.dcm";
       
  
        File baseFolder = new File(baseFolderName);
        if (!baseFolder.exists()) {
            baseFolder.mkdirs();
        }
    
    	 Attributes attributes = loadAttributesFromFile(originalDicomFile);
         extractMetadataDicomService.writeAttributesToFile(attributes, attributsBeforeAnonymisationFileName, baseFolderName);
         anonymisationDicomService.anonymizePatientAttributes(attributes,dicomAnnonyFileName);
         try (DicomInputStream dicomInputStream = new DicomInputStream(new FileInputStream(originalDicomFile));
        	  DicomOutputStream dicomOutputStream = new DicomOutputStream(new File(baseFolderName, dicomAnnonyFileName))) {
              Attributes outattributes = dicomInputStream.readFileMetaInformation();
       
              outattributes = attributes.createFileMetaInformation(outattributes.getString(Tag.TransferSyntaxUID));
        	 dicomOutputStream.writeDataset(outattributes, attributes);
         }     
              
}
    
private String getBaseFileName(String fileName) {
    int lastDotIndex = fileName.lastIndexOf('.');
    if (lastDotIndex != -1) {
        return fileName.substring(0, lastDotIndex);
    }
    return fileName;
}

private Attributes loadAttributesFromFile(File dicomFile) throws FileNotFoundException, IOException  {
try (FileInputStream fileInputStream = new FileInputStream(dicomFile)) {
    DicomInputStream dicomInputStream = new DicomInputStream(fileInputStream);
    Attributes attributes = dicomInputStream.readDataset();
    dicomInputStream.close();
    return attributes;
}
}
}












