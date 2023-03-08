package fr.gustaveroussy.imaging;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.io.DicomInputStream;
import org.dcm4che3.io.DicomOutputStream;
import org.springframework.stereotype.Service;

import com.pixelmed.dicom.DicomException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@SpringBootTest
class ImagingApplicationTests {
	public static final Logger logger = LoggerFactory.getLogger(ImagingApplication.class);
	@Autowired
	ImagingApplication imagingApplication ; 

	@Test
    public  void  testImagApp() throws IOException, DicomException {
		 // Chemin du fichier DICOM source d'origine
        File inputFile = new File("C:\\Users\\m_graa\\Desktop\\stage_bioinfo\\dicom1.dcm");

        // Chemin du fichier DICOM anonymisée 
        File outputFile = new File("C:\\Users\\m_graa\\Desktop\\stage_bioinfo\\dicom_annony.dcm");

     
   
    }
	
	public void anonymizeDicomFile(File inputFile, File outputFile) throws IOException {

		
		

		
        try (DicomInputStream dicomInputStream = new DicomInputStream(new FileInputStream((inputFile)))) {
        	
        	Attributes attributes = dicomInputStream.readFileMetaInformation();
            anonymizePatientAttributes(attributes);
            attributes = filterAttributes(attributes);
            try (DicomOutputStream dos = new DicomOutputStream(new FileOutputStream(outputFile), null)) {
                dos.writeDataset(attributes, dicomInputStream.readFileMetaInformation());
            }
        }
    }

   

	private void anonymizePatientAttributes(Attributes attributes) {
        attributes.setString(Tag.PatientName, null);
        attributes.setString(Tag.PatientID, null);
        attributes.setString(Tag.PatientBirthDate, null);
        attributes.setString(Tag. Modality, null);
        attributes.setString(Tag. PhotometricInterpretation, null);
        attributes.setString(Tag. ImageType, null);
        attributes.setString(Tag. OtherPatientNames, null);
        attributes.setString(Tag.PatientAge , null);
        attributes.setString(Tag.PatientBirthDate , null);
        attributes.setString(Tag.PatientBirthDateInAlternativeCalendar , null);
        attributes.setString(Tag.PatientBodyMassIndex , null);
        attributes.setString(Tag.PatientAlternativeCalendar , null);
        attributes.setString(Tag.PatientBreedCodeSequence , null);
        attributes.setString(Tag.PatientBreedDescription , null);
        attributes.setString(Tag.PatientComments , null);
        attributes.setString(Tag.PatientDeathDateInAlternativeCalendar , null);
        attributes.setString(Tag.PatientIdentityRemoved , null);
        attributes.setString(Tag.PatientOrientation , null);
        attributes.setString(Tag.PatientPosition, null);
        attributes.setString(Tag.PatientSex, null);
        attributes.setString(Tag.PatientSexNeutered , null);
        attributes.setString(Tag. PatientSize, null);
        attributes.setString(Tag.PatientSizeCodeSequence , null);
        attributes.setString(Tag.PatientSpeciesCodeSequence , null);
        attributes.setString(Tag.PatientSpeciesDescription , null);
        attributes.setString(Tag.PatientState , null);
        attributes.setString(Tag.PatientWeight , null);   
        
    }
	
	

    private Attributes filterAttributes(Attributes attributes) {
        Attributes filteredAttributes = new Attributes();
        filteredAttributes.addAll(attributes, false);
        filteredAttributes.remove(Tag.StudyInstanceUID);
        filteredAttributes.remove(Tag.SeriesInstanceUID);
        filteredAttributes.remove(Tag.SOPClassUID);
        filteredAttributes.remove(Tag.ImagePosition );
        filteredAttributes.remove(Tag.PixelData  );
      
        return filteredAttributes;
    }
}

	
	
	
	
	
	
	
	
	
	
	


