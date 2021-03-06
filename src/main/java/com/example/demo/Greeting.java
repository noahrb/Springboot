package com.example.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;

@Controller
public class Greeting {

	// Step1: Handle an http get to return a bash(plaintext) output
	@RequestMapping("/bash")
	public @ResponseBody String greeting() {
		String bashOutput = ""; // Insert bashScript here.
		return bashOutput;
	}

	// Step2: Handle an http get to grant cert & return cert as plaintext.
	// Helper method for Converting .pfx files to usable Strings.
	public static String convertPFXtoString(String path) throws IOException {
		File file = new File(path);
		byte[] bytesArray = new byte[(int) file.length()];
		FileInputStream fis = new FileInputStream(file);
		fis.read(bytesArray); // read file into bytes[]
		fis.close();
		String output = Base64.getEncoder().encodeToString(bytesArray);
		System.out.println(output);
		return output;
	}

	@RequestMapping("/cert")
	public @ResponseBody String getCert(@RequestParam("coreID") String coreid,
			@RequestParam("hostname") String hostname) throws IOException {

		System.out.println("BEFORE OVCM COMMAND");
		String ovcmCmd = "ovcm -issue -file " + coreid + ".pfx " + "-name " + hostname + " -pass glg";
		System.out.println("AFTER OVCM COMMAND");
		try {
			Runtime r = Runtime.getRuntime();
			Process p = r.exec(ovcmCmd);

			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				System.out.println(inputLine);
			}
			in.close();

		} catch (IOException e) {
			System.out.println(e);
		}

		System.out.println("AFTER TRY/CATCH && BEFORE HELPER CALL");
		return convertPFXtoString(coreid + ".pfx");

		// return result;
	}

	// Step3: Return necessary policies given a coreid.

	// GET for file download.
	@RequestMapping("/policy")
	public ResponseEntity<InputStreamResource> getPolicyReturn(@RequestParam("coreid") String coreid)
			throws IOException {
		String output = "";
		System.out.println("BEFORE GET POLICY");
		String ovcmCmd = "ovpolicy -list " + coreid;
		System.out.println("AFTER GET POLICY");
		try {
			Runtime r = Runtime.getRuntime();
			Process p = r.exec(ovcmCmd);

			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				System.out.println(inputLine);
			}
			in.close();
			output = inputLine;
		} catch (IOException e) {
			System.out.println(e);
		}
		// Insert complete list of policies and compare to see if the output of this
		// command contains the keywords(names) of the list of policies.

		// TODO: Possibly make a ArrayList<String, String> to firstly hold the name of
		// the policy, and the path the the policy resource in the workspace.
		ArrayList<String> policyList = new ArrayList<String>();
		ArrayList<String> newPolicies = new ArrayList<String>();
		for (int i = 0; i < policyList.size(); i++) {
			if (output.contains("INSERT FULL LIST OF POLICIES HERE")) {
				newPolicies.add(policyList.get(i));
			}
		}
		ResponseEntity<InputStreamResource> response = getFiles(newPolicies);
		return response;
	}

	// Helper method for GET file download.
	// TODO: Add policy group names and file paths.
	public ResponseEntity<InputStreamResource> getFiles(ArrayList<String> list) throws IOException {

		if (list.contains("INSERT POLICY HERE")) {

		}

		if (list.contains("INSERT POLICY HERE")) {

		}

		if (list.contains("INSERT POLICY HERE")) {

		}

		if (list.contains("INSERT POLICY HERE")) {

		}

		if (list.contains("INSERT POLICY HERE")) {

		}
		
		//TODO: Create a zip containing this list of policies and export it then 
		//TODO: read it with this new class path resource. \/
		ClassPathResource zipFile = new ClassPathResource("create_package.zip");

		return ResponseEntity.ok().contentLength(zipFile.contentLength())
				.contentType(MediaType.parseMediaType("application/zip"))
				.body(new InputStreamResource(zipFile.getInputStream()));
	}
	/*
	 * 
	 * if (list.contains("createpkg")) { ClassPathResource zipFile = new
	 * ClassPathResource("create_package.zip");
	 * 
	 * return ResponseEntity.ok().contentLength(zipFile.contentLength())
	 * .contentType(MediaType.parseMediaType("application/zip")) .body(new
	 * InputStreamResource(zipFile.getInputStream())); } if
	 * (list.contains("samplePolicies1")) { ClassPathResource zipFile = new
	 * ClassPathResource("INSERT PATH");
	 * 
	 * return ResponseEntity.ok().contentLength(zipFile.contentLength())
	 * .contentType(MediaType.parseMediaType("application/zip")) .body(new
	 * InputStreamResource(zipFile.getInputStream())); } if
	 * (list.contains("samplePolicies2")) { ClassPathResource zipFile = new
	 * ClassPathResource("INSERT PATH");
	 * 
	 * return ResponseEntity.ok().contentLength(zipFile.contentLength())
	 * .contentType(MediaType.parseMediaType("application/zip")) .body(new
	 * InputStreamResource(zipFile.getInputStream())); } if
	 * (list.contains("samplePolicies3")) { ClassPathResource zipFile = new
	 * ClassPathResource("INSERT PATH");
	 * 
	 * return ResponseEntity.ok().contentLength(zipFile.contentLength())
	 * .contentType(MediaType.parseMediaType("application/zip")) .body(new
	 * InputStreamResource(zipFile.getInputStream())); } else { ClassPathResource
	 * zipFile = new ClassPathResource("INSERT PATH");
	 * 
	 * return ResponseEntity.ok().contentLength(zipFile.contentLength())
	 * .contentType(MediaType.parseMediaType("application/zip")) .body(new
	 * InputStreamResource(zipFile.getInputStream())); } }
	 */

}

//
//
// EXTRA COMMANDS
//
//
// @RequestMapping("/certtest")
// public @ResponseBody String certTest() throws IOException {
// String output =
// convertPFXtoString("C:\\Users\\nbeverly\\Desktop\\test1.pfx");
// return output;
// }
// // id processing helper method
// public String getCertHelper(String input) {
//
// return input + " TEST WORKS WOO";
// }
// public static void enterKeyInCommand(Runtime r, Process p) {
// String ovcmCmd = "";
// try {
// p = r.exec(ovcmCmd);
// BufferedReader in = new BufferedReader(new
// InputStreamReader(p.getInputStream()));
// String inputLine;
// while ((inputLine = in.readLine()) != null) {
// System.out.println(inputLine);
// }
// in.close();
//
// } catch (IOException e) {
// System.out.println(e);
// }
// }

// // Sample id processing http GET
// @RequestMapping("/idprocess")
// public @ResponseBody String httpGETprocessID(@RequestParam("id") String
// itemid) {
// String response = processIdHelper(itemid);
// // Insert commands and Response here.
//
// return response;
// }
//
// // id processing helper method
// public String processIdHelper(String input) {
//
// return input + " TEST WORKS WOO";
// }
//
// // Sample GET for file download.
// @RequestMapping("/download")
// public ResponseEntity<InputStreamResource>
// sampleGETFileReturn(@RequestParam("file") String itemid)
// throws IOException {
// ResponseEntity<InputStreamResource> response = getFiles(itemid);
//
// return response;
// }
//
// // Sample helper method for GET file download.
// public ResponseEntity<InputStreamResource> getFiles(String input) throws
// IOException {
// if (input.equals("createpkg")) {
// ClassPathResource zipFile = new ClassPathResource("create_package.zip");
//
// return ResponseEntity.ok().contentLength(zipFile.contentLength())
// .contentType(MediaType.parseMediaType("application/zip"))
// .body(new InputStreamResource(zipFile.getInputStream()));
// }
//
// else {
// ClassPathResource zipFile = new ClassPathResource("create_package.zip");
//
// return ResponseEntity.ok().contentLength(zipFile.contentLength())
// .contentType(MediaType.parseMediaType("application/zip"))
// .body(new InputStreamResource(zipFile.getInputStream()));
// }
// }
//
// // Sample Http GET
// @RequestMapping("/sampleGET")
// public @ResponseBody String sampleGET(@RequestParam("param") String itemid) {
// String response = getterHelperMethod(itemid);
// // Insert commands and Response here.
//
// return response;
// }
//
// // Sample Http GET Helper method.
// public String getterHelperMethod(String input) {
//
// return input + " TEST WORKS WOO";
// }
//
// // Sample Http POST
// @RequestMapping(method = RequestMethod.POST, value = "/samplePOST")
// public @ResponseBody String samplePOST(@RequestBody String hostname) {
// String response = "";
// // Insert Commands and Response here.
//
// return response;
// }
// @RequestMapping(value = "/download", method = RequestMethod.GET)
// public void sampleGETFileReturn(@RequestParam("file") String itemid,
// HttpServletResponse response)
// throws IOException {
// if (itemid.equals("createpkg")) {
// File file = new File("/demo/src/main/resources/create_package.zip");
// InputStream zipStream = new FileInputStream(file);
//
// response.addHeader("Content-disposition",
// "attatchment;filename=create_package.zip");
// response.setContentType("application/zip");
//
// IOUtils.copy(zipStream, response.getOutputStream());
// response.flushBuffer();
// }
//
// else {
//
// }
// }

// public ResponseEntity<InputStreamResource> getFiles(String input) throws
// IOException { if(input.equals("createpkg")) { ClassPathResource zipFile = new
// ClassPathResource("create_package.zip");
//
// return ResponseEntity.ok().contentLength(zipFile.contentLength())
// .contentType(MediaType.parseMediaType("application/octlt-stream")) .body(new
// InputStreamResource(zipFile.getInputStream())); } else { ClassPathResource
// zipFile = new ClassPathResource("create_package.zip");
//
// return ResponseEntity.ok().contentLength(zipFile.contentLength())
// .contentType(MediaType.parseMediaType("application/octlt-stream")) .body(new
// InputStreamResource(zipFile.getInputStream())); } }

// @RequestMapping("/greeting")
// public @ResponseBody String greeting() {
// return "hello";
// }

// @RequestMapping(method = RequestMethod.POST, value = "/test")
// public @ResponseBody String testPost(@RequestBody String input) {
// return "You typed " + input + ".";
// }

// @RequestMapping(method = RequestMethod.POST, value = "/ping")
// public @ResponseBody String testPingPost(@RequestBody String hostname) {
// // String response = "Invalid Hostname";
//
// String ip = hostname;
// String pingResult = "";
//
// String pingCmd = "ping -c 5 " + ip;
// try {
// Runtime r = Runtime.getRuntime();
// Process p = r.exec(pingCmd);
//
// BufferedReader in = new BufferedReader(new
// InputStreamReader(p.getInputStream()));
// String inputLine;
// while ((inputLine = in.readLine()) != null) {
// System.out.println(inputLine);
// pingResult += inputLine + "\n";
// }
// in.close();
//
// } catch (IOException e) {
// System.out.println(e);
// }
// return pingResult;
// }
