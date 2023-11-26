package fr.gustaveroussy.imaging;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import fr.gustaveroussy.imaging.controller.ImagingController;

@SpringBootApplication
public class ImagingApplication implements CommandLineRunner {

    @Autowired
    private ImagingController imagingController;

    public static void main(String[] args) {
        SpringApplication.run(ImagingApplication.class, args);
    }

    @Override
    public void run(String... args) throws FileNotFoundException, IOException {
    
        String result = imagingController.processDicomFiles();
        System.out.println(result);
    }
}









