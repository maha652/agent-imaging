package fr.gustaveroussy.imaging.services;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class ExtractMetadataDicomService {
	 @Autowired
	 @Value("${path.to.input.folder}")
	 private String dicomFolder;

	 
	 
    
   private static final Logger logger = LoggerFactory.getLogger(ExtractMetadataDicomService.class);

    public void writeAttributesToFile(Attributes beforeAnonymisation, String fileName, String baseFolderName) {
        logger.debug("Writing attributes to file: {}",dicomFolder );

        try (FileWriter fileWriter = new FileWriter(baseFolderName + File.separator + fileName)) {
            String[] labels = {"datasetName", "organisationName", "datasetIds", "projectName", "protocolName"};
            String[] values = new String[5];
            values[0] = "Dicom_metadata";
            values[3] = "Imaging";

            for (int tag : beforeAnonymisation.tags()) {
                switch (tag) {
                    case Tag.PatientID:
                        values[2] = beforeAnonymisation.getString(tag);
                        break;
                    case Tag.InstitutionName:
                        values[1] = beforeAnonymisation.getString(tag);
                        break;
                    case Tag.StudyDescription:
                        values[4] = beforeAnonymisation.getString(tag);
                        break;
                    default:
                        break;
                }
            }

            fileWriter.write(String.join(";", labels));
            fileWriter.write(System.lineSeparator());

            fileWriter.write(String.join(";", values));
            
            
        } catch (IOException e) {
            logger.error("Error writing attributes to file: {}", fileName, e);
            e.printStackTrace();
        }
    }
}