package com.navya.mpipoc.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.navya.mpipoc.entities.Patient;
import com.navya.mpipoc.utils.ExportCsv;

public interface PatientService {
	
		public void savePatientData(MultipartFile file, HttpServletRequest request) throws IOException;
	
		public Optional<Patient> getPatientRecords(String enterpriseID) throws IOException;	

		public Iterable<Patient> getAllPatientsRecords() throws IOException;    
		
		public void deletePatientRecord(String enterpriseId) throws IOException;
		
		public List<ExportCsv> exportPatientRecordsCsv(String enterprise_id) throws IOException;
	
}
