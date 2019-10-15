package com.navya.mpipoc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.navya.mpipoc.entities.Patient;
import com.navya.mpipoc.services.PatientService;
import com.navya.mpipoc.utils.ExportCsv;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

@Controller
public class AppController {

	private static final Logger log = LogManager.getLogger(AppController.class);

	@Autowired
	private PatientService ps;

	@GetMapping(value = "/")
	public String index() {
		return "redirect:index.html";
	}

	@RequestMapping(value = "/importPatientData", method = RequestMethod.POST)
	public String setPatientDataInDB(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes,
			HttpServletRequest request) throws IOException {

		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
			return "redirect:/uploadStatus";
		}

		try {

			ps.savePatientData(file, request);

		} catch (IOException e) {
			System.out.println("error while reading csv and put to db : " + e.getMessage());
		}

		redirectAttributes.addFlashAttribute("message",
				"You successfully uploaded '" + file.getOriginalFilename() + "' data to DB");

		return "redirect:/uploadStatus";

	}

	@GetMapping("/uploadStatus")
	public String uploadStatus() {
		return "uploadStatus";
	}

	@GetMapping(path = "/getAllPatientsRecords")
	@ResponseBody
	public Iterable<Patient> getAllPatientsRecords() throws IOException {

		return ps.getAllPatientsRecords();

	}

	@GetMapping(path = "/getPatientRecords")
	@ResponseBody
	public Optional<Patient> getPatientRecords(@RequestParam String enterpriseId) throws IOException {

		return ps.getPatientRecords(enterpriseId);

	}

	@DeleteMapping(path = "/deletePatientRecord/{enterpriseId}")
	@ResponseBody
	public String deletePatientRecord(@PathVariable("enterpriseId") String enterpriseId) throws IOException {

		try {

			ps.deletePatientRecord(enterpriseId);

			return "Successfully Deleted record: " + enterpriseId;

		} catch (Exception e) {

			return "Exception while deleting record:" + enterpriseId + "::" + e.getMessage();

		}
	}

	@GetMapping(path = "/exportPatientRecordsToCsv")
	@ResponseBody
	public void getAllPatientsRecordsAsCSV(@RequestParam String enterpriseId,
			HttpServletRequest request, HttpServletResponse response) throws IOException {

		DateTimeFormatter timeStampPattern = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

		String rootPath = request.getSession().getServletContext().getRealPath("/");
		File dir = new File(rootPath + File.separator + "uploadedfile");
		if (!dir.exists()) {
			dir.mkdirs();
		}

		File serverFile = new File(dir.getAbsolutePath() + File.separator + "PatientRecords_" + enterpriseId);

		response.setContentType("text/csv");
		response.setHeader("Content-Disposition", "attachment; filename=\"PatientRecords_for_" + enterpriseId + "_"
				+ timeStampPattern.format(java.time.LocalDateTime.now()) + ".csv\"");

		try (FileWriter writer = new FileWriter(serverFile.getAbsolutePath().toString())) {

			// Arrange column name as provided in below array.
			String[] columns = new String[] { "enterprise_id", "source", "medical_record_number", "first_name",
					"last_name", "social_security_number", "address_line1", "address_line2", "city", "state",
					"zip_code" };

			writer.append(StringUtils.join(columns, ",") + "\n");

			List<ExportCsv> exp = ps.exportPatientRecordsCsv(enterpriseId);

			// Create Mapping Strategy to arrange the column name in order
			ColumnPositionMappingStrategy<ExportCsv> mappingStrategy = new ColumnPositionMappingStrategy<ExportCsv>();
			mappingStrategy.setType(ExportCsv.class);
			mappingStrategy.setColumnMapping(columns);

			// Creating StatefulBeanToCsv object
			StatefulBeanToCsvBuilder<ExportCsv> builder = new StatefulBeanToCsvBuilder<ExportCsv>(writer);

			StatefulBeanToCsv<ExportCsv> beanWriter = builder.withMappingStrategy(mappingStrategy).build();

			// Write list to StatefulBeanToCsv object
			beanWriter.write(exp);

			// closing the writer object -- using try with resource which automatically
			// closing writer object.
			// writer.close();

		} catch (Exception e) {
			log.error("Exception while writing to CSV :: {}", e.getMessage());
		} finally {
			try (FileInputStream is = new FileInputStream(serverFile.getAbsolutePath())) {
				OutputStream outputStream = response.getOutputStream();
				byte[] buffer = new byte[4096];
				int length;
				while ((length = is.read(buffer)) > 0) {
					outputStream.write(buffer, 0, length);
				}
				outputStream.flush();
				outputStream.close();

			} catch (Exception e) {
				log.error("Exception while downloading CSV :: {}", e.getMessage());
			}
		}
	}

}
