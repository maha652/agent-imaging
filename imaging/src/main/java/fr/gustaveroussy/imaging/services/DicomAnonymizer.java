package fr.gustaveroussy.imaging.services;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pixelmed.dicom.AttributeList;
import com.pixelmed.dicom.DicomException;
import com.pixelmed.dicom.TagFromName;
import com.pixelmed.dicom.TransferSyntax;

@Service
public class DicomAnonymizer {

	public static final Logger logger = LoggerFactory.getLogger(DicomAnonymizer.class);
	
    /**
     * Copie les fichiers DICOM du dossier source vers le dossier de destination.
     
     */
    public  void copyDicomFiles(File source, File destination) throws IOException {
        // Création du dossier de destination s'il n'existe pas encore
        if (!destination.exists()) {
            destination.mkdirs();
        }

        // Parcours des fichiers DICOM du dossier source
        for (File file : source.listFiles()) {
            if (file.getName().endsWith(".dcm")) {
                // Copie du fichier dans le dossier de destination
                File destFile = new File(destination, file.getName());
                java.nio.file.Files.copy(file.toPath(), destFile.toPath());
            }
        }
    }

    /**
     * Anonymise les fichiers DICOM dans le dossier spécifié.
     *  directory le dossier contenant les fichiers DICOM à anonymiser
     *  DicomException en cas d'erreur lors de l'anonymisation des fichiers
     * 
     */
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











