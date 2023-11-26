package fr.gustaveroussy.imaging.services;



import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.data.VR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AnonymisationDicomService {

    private static final Logger logger = LoggerFactory.getLogger(AnonymisationDicomService.class);

    public Attributes anonymizePatientAttributes(Attributes attributes, String dicomAnnonyFileName) {
        for (int tag : attributes.tags()) {
            String tagValueAnonym = attributes.getString(tag);

            logger.debug("Tag ID_anonym : {}", tag);
            if (tag == Tag.PatientName) {
                attributes.setString(Tag.PatientName, VR.PN, "ANONYME");
                logger.info(tagValueAnonym);
            } else if (tag == Tag.PatientID) {
                attributes.setString(Tag.PatientID, VR.LO, "NIP");
                logger.info(tagValueAnonym);
            } else if (tag == Tag.PatientBirthDate) {
                attributes.setString(Tag.PatientBirthDate, VR.DA, "BirthDate");
                logger.info(tagValueAnonym);
            } else if (tag == Tag.PatientAddress) {
                attributes.setString(Tag.PatientAddress, VR.LO, "street/country");
                logger.info(tagValueAnonym);
            } else if (tag == Tag.PatientSex) {
                attributes.setString(Tag.PatientSex, VR.CS, "M/F");
                logger.info(tagValueAnonym);
            } else if (tag == Tag.PatientAge) {
                attributes.setString(Tag.PatientAge, VR.AS, "Age");
                logger.info(tagValueAnonym);
            } else if (tag == Tag.InstitutionName) {
                attributes.setString(Tag.InstitutionName, VR.LO, "HOSPITAL");
                logger.info(tagValueAnonym);
            }

            logger.debug("anonymisation : {}", tagValueAnonym);
        }

        return attributes;
    }
}
