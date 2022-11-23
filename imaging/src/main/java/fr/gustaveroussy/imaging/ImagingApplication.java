package fr.gustaveroussy.imaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import fr.gustaveroussy.imaging.services.DemoDicom;

@SpringBootApplication
public class ImagingApplication implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(ImagingApplication.class);
	
	@Autowired
	DemoDicom demoDicom;
	
	public static void main(String[] args) {
		SpringApplication.run(ImagingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {


		String dicomFilePath = "C:\\Users\\m_graa\\Desktop\\stage_bioinfo\\1.3.12.2.1107.5.2.18.42239.2019080716095943021807871.dcm";
		
		demoDicom.demo(dicomFilePath);
		
		
		
		logger.info("DONE");
	}


}


