package com.navya.mpipoc.utils;

/**
 * This interface is used to write to CSV (Spring Data)
 */
public interface ExportCsv {

	String getEnterprise_id();

	String getSource();

	String getMedical_record_number();

	String getFirst_name();

	String getLast_name();

	String getSocial_security_number();

	String getAddress_line1();

	String getAddress_line2();

	String getCity();

	String getState();

	String getZip_code();

}
