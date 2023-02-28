package fr.gustaveroussy.imaging.services;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.pixelmed.dicom.DicomException;

@SpringBootTest
public class DicomAnonymizerTest {
	
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
}
