package com.navya.mpipoc.services;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.navya.mpipoc.entities.Address;
import com.navya.mpipoc.entities.Patient;
import com.navya.mpipoc.entities.PatientMemberRecord;
import com.navya.mpipoc.repository.PatientRepository;
import com.navya.mpipoc.utils.ExportCsv;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

@Service
public class PatientServiceImpl implements PatientService {
	
	private static final Logger log = LogManager.getLogger(PatientServiceImpl.class);

	@Autowired
	private PatientRepository pr;

	@Transactional
	public void savePatientData(MultipartFile file, HttpServletRequest request) throws IOException {

		String rootPath = request.getSession().getServletContext().getRealPath("/");
		File dir = new File(rootPath + File.separator + "uploadedfile");
		if (!dir.exists()) {
			dir.mkdirs();
		}

		File serverFile = new File(dir.getAbsolutePath() + File.separator + file.getOriginalFilename());

		try {
			try (InputStream is = file.getInputStream();
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile))) {
				int i;
				// write file to server
				while ((i = is.read()) != -1) {
					stream.write(i);
				}
				stream.flush();
			}
		} catch (IOException e) {
			log.error("Exception while reading uploaded file : " + e.getMessage());
		}
		log.info("Uploaded File Temp Path:: {} " + serverFile.getAbsoluteFile().toPath().toString());
		try (Reader reader = Files.newBufferedReader(serverFile.getAbsoluteFile().toPath());
				CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();) {
			
			// Reading Records One by One in a String array
			String[] nextRecord;

			while ((nextRecord = csvReader.readNext()) != null) {

				Patient p = new Patient(nextRecord[0], new PatientMemberRecord(nextRecord[1], nextRecord[2],
						nextRecord[3], nextRecord[4], nextRecord[5],
						new Address(nextRecord[6], nextRecord[7], nextRecord[8], nextRecord[9], nextRecord[10])));

				pr.save(p);
			}
		} catch (Exception ex) {
			log.error("Error while reading CSV and inserting to Database:: {}", ex.getMessage());
		}
	}

	@Override
	public Optional<Patient> getPatientRecords(String enterpriseID) {

		return pr.findById(enterpriseID);
	}

	@Override
	public Iterable<Patient> getAllPatientsRecords() {

		return pr.findAll();
	}

	@Override
	@Transactional
	public void deletePatientRecord(String enterpriseId) {

		pr.deleteById(enterpriseId);

	}

	@Override
	public List<ExportCsv> exportPatientRecordsCsv(String enterprise_id) {

		return pr.find(enterprise_id);

	}

}
