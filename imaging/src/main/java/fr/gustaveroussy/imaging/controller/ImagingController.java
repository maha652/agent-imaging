
package fr.gustaveroussy.imaging.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


import fr.gustaveroussy.imaging.ImagingApplication;

@Controller
public class ImagingController {

    @Autowired
    private ImagingApplication imagingApplication;

    public void yourControllerMethod() throws Exception  {
        String dicomFilePath = "/chemin/vers/votre/fichier.dcm"; 
        imagingApplication.run(dicomFilePath);
    }
}