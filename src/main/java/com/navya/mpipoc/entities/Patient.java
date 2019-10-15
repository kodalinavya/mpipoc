package com.navya.mpipoc.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "patient")
public class Patient implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "enterprise_id")
	private String enterpriseId;
	
	@OneToMany(mappedBy = "patientReference", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<PatientMemberRecord> memberRecords = new ArrayList();

	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public List<PatientMemberRecord> getMemberRecords() {
		return memberRecords;
	}

	public void setMemberRecords(List<PatientMemberRecord> memberRecords) {
		this.memberRecords = memberRecords;
	}
	
	public Patient() {
		
	}

	public Patient(String enterpriseId, PatientMemberRecord... memberRecords) {
        this.enterpriseId = enterpriseId;
        this.memberRecords = Stream.of(memberRecords).collect(Collectors.toList());
        this.memberRecords.forEach(x -> x.setPatientReference(this));
    }
	
	@Override
	public String toString() {
		return "Patient [enterpriseId=" + enterpriseId + ", memberRecords=" + memberRecords + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((enterpriseId == null) ? 0 : enterpriseId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Patient other = (Patient) obj;
		if (enterpriseId == null) {
			if (other.enterpriseId != null)
				return false;
		} else if (!enterpriseId.equals(other.enterpriseId))
			return false;
		return true;
	}

}
