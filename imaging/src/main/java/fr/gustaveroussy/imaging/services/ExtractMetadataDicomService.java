package fr.gustaveroussy.imaging.services;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.json.JSONArray;
import org.json.JSONObject;
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
        logger.debug("Writing attributes to file: {}", dicomFolder);

        try (FileWriter fileWriter = new FileWriter(baseFolderName + File.separator + fileName )) {

      
            JSONObject jsonObject = new JSONObject();

          
            jsonObject.put("organisationName", beforeAnonymisation.getString(Tag.InstitutionName));
            jsonObject.put("protocolName", beforeAnonymisation.getString(Tag.StudyDescription));
            jsonObject.put("datasetIds", new JSONArray());
            jsonObject.put("tpName", beforeAnonymisation.getString(Tag.Manufacturer));
            jsonObject.put("datasetName", "");
            jsonObject.put("biologicalCategoryName", beforeAnonymisation.getString(Tag.BodyPartExamined));   
            //jsonObject.put("biologicalCategoryName", "organe"); //
            jsonObject.put("projectName", "AnonymizedDicomImaging");

         
            JSONArray samplesArray = new JSONArray();
            JSONObject sampleObject = new JSONObject();
            sampleObject.put("patientName", beforeAnonymisation.getString(Tag.PatientID));
            sampleObject.put("patientNameType", "NOIGR");
            sampleObject.put("sampleNameType", "");
            sampleObject.put("sampleName", baseFolderName);
            sampleObject.put("sampleType", "");
            sampleObject.put("speciesName", "Homo sapiens");
            sampleObject.put("comment", "");
            sampleObject.put("patientAliases", new JSONArray());
            sampleObject.put("sampleAliases", new JSONArray());

      
            samplesArray.put(sampleObject);

    
            jsonObject.put("samples", samplesArray);

           
            fileWriter.write(jsonObject.toString());
            

        } catch (IOException e) {
            logger.error("Error writing attributes to file: {}", fileName, e);
            e.printStackTrace();
        }
    }
}
