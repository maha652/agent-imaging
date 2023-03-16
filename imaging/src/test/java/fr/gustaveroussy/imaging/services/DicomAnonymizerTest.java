

package fr.gustaveroussy.imaging.services;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.pixelmed.dicom.AttributeList;
import com.pixelmed.dicom.DicomException;
import com.pixelmed.dicom.TagFromName;
import com.pixelmed.dicom.TransferSyntax;

@SpringBootTest
public class DicomAnonymizerTest {
	public static final Logger logger = LoggerFactory.getLogger(DicomAnonymizer.class);
	
	@Autowired
	DicomAnonymizer dicomAnonymizer;
	
	@Test
    public  void testAnon() throws IOException, DicomException {

        // Chemin du dossier source contenant les fichiers DICOM à copier
        File sourceDirectory = new File("C:\\Users\\m_graa\\Desktop\\stage_bioinfo\\source_dicom");

        // Chemin du dossier de destination où la copie anonymisée sera enregistrée
        File destinationDirectory = new File("C:\\Users\\m_graa\\Desktop\\stage_bioinfo\\destination_dicom");

        
        // Copie des fichiers DICOM du dossier source vers le dossier de destination
        //dicomAnonymizer.copyDicomFiles(sourceDirectory, destinationDirectory);

        // Anonymisation des fichiers DICOM dans le dossier de destination
        //dicomAnonymizer.anonymizeDicomFiles(destinationDirectory);
   
    }



public  void anonymizeDicomFiles(File directory) throws DicomException, IOException {
    // Parcours des fichiers DICOM du dossier
    for (File file : directory.listFiles()) {
        if (file.getName().endsWith(".dcm")) {
        	logger.debug("file {}",file.getAbsolutePath());
            // Chargement du fichier DICOM dans un objet AttributeList
            AttributeList attributeList = new AttributeList();
            attributeList.read(file.getAbsolutePath());

            // Anonymisation des attributs
            attributeList.remove(TagFromName.PatientName);
            attributeList.remove(TagFromName.PatientID);
            attributeList.remove(TagFromName.PatientBirthDate);
            attributeList.remove(TagFromName.AccessionNumber);
            attributeList.remove(TagFromName.StudyDate);
            attributeList.remove(TagFromName.StudyTime);
            attributeList.remove(TagFromName.InstitutionName);
            attributeList.remove(TagFromName.ReferringPhysicianName);

            // Enregistrement des modifications dans le fichier DICOM //
           // attributeList.write //


            // Write the modified DICOM file to the destination folder
            
           // List<DicomFile> dicomFiles = dicomDir.find(DicomDirectory.getByFileSet);//
            
          /*  for (DicomFile dicomFile : dicomFiles) {
                dicomFile.write(dicomFile.getPath(), DicomOutputStream.WriteEncapsulated, false);
            }
            
            
            /* j'ai pas reussi a le faire en liste , j'ai testé pour un seul DICOM */
            

         File anonymizedFile = new File("C:\\Users\\m_graa\\Desktop\\stage_bioinfo\\destination_dicom\\dicom1.dcm");
            
            
            attributeList.write(anonymizedFile  ,  TransferSyntax.ExplicitVRLittleEndian, true, true);

            
            
            
            
            
            
            
        }
    }
}
}



