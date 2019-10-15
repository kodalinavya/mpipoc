package com.navya.mpipoc.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="patient_member_record")
public class PatientMemberRecord implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name="source")
	private String source;

	@Id
	@Column(name="medical_record_number")
	private String medicalRecordNumber;

	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="social_security_number")
	private String socialSecurityNumber;

	@OneToOne(cascade = CascadeType.ALL)
	private Address address;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="enterprise_id_fk", nullable=false, insertable=true)
	@JsonBackReference
	private Patient patientReference;

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getMedicalRecordNumber() {
		return medicalRecordNumber;
	}

	public void setMedicalRecordNumber(String medicalRecordNumber) {
		this.medicalRecordNumber = medicalRecordNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSocialSecurityNumber() {
		return socialSecurityNumber;
	}

	public void setSocialSecurityNumber(String socialSecurityNumber) {
		this.socialSecurityNumber = socialSecurityNumber;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Patient getPatientReference() {
		return patientReference;
	}

	public void setPatientReference(Patient patientReference) {
		this.patientReference = patientReference;
	}
	
	public PatientMemberRecord() {
		
	}
	
	public PatientMemberRecord(String source, String mrn, String fName, String lName, String ssn, Address add) {
        this.source = source;
        this.medicalRecordNumber = mrn;
        this.firstName = fName;
        this.lastName = lName;
        this.socialSecurityNumber = ssn;
        this.address = add;
        this.address.setPatientMemberRecord(this);
    }

	@Override
	public String toString() {
		return "PatientMemberRecord [source=" + source + ", medicalRecordNumber=" + medicalRecordNumber + ", firstName="
				+ firstName + ", lastName=" + lastName + ", socialSecurityNumber=" + socialSecurityNumber + ", address="
				+ address + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((medicalRecordNumber == null) ? 0 : medicalRecordNumber.hashCode());
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
		PatientMemberRecord other = (PatientMemberRecord) obj;
		if (medicalRecordNumber == null) {
			if (other.medicalRecordNumber != null)
				return false;
		} else if (!medicalRecordNumber.equals(other.medicalRecordNumber))
			return false;
		return true;
	}

}
