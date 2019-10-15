package com.navya.mpipoc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.navya.mpipoc.entities.Patient;
import com.navya.mpipoc.utils.ExportCsv;

public interface PatientRepository extends CrudRepository<Patient, String> {

	 @Query(value="select p.enterprise_id, pmr.medical_record_number, pmr.first_name, pmr.last_name, pmr.social_security_number, pmr.source, a.address_line1, a.address_line2, a.city, a.state, a.zip_code from patient p INNER JOIN patient_member_record pmr ON p.enterprise_id = pmr.enterprise_id_fk INNER JOIN address a ON a.address_id = pmr.address_address_id WHERE p.enterprise_id = :enterprise_id", nativeQuery = true)
	 public List<ExportCsv> find(@Param("enterprise_id") String enterprise_id);
	
}
