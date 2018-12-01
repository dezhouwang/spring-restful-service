package com.simple.spring.simpleservices;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class Controller {

	@Autowired
	UserRepository userRepo;

	Services services = new Services();

	// Output JSONObject of users' salary based on query parameters
	@RequestMapping(value = "/users", produces = "application/json")
	public String outputUser(@RequestParam(name = "min", required = false, defaultValue = "0") String min,
			@RequestParam(name = "max", required = false, defaultValue = "4000") String max, Model model) {
		model.addAttribute("min", min);
		model.addAttribute("max", max);

		double minimum = 0;// default min salary
		double maximum = 4000;// default max salary

		try {
			minimum = Double.parseDouble(min);
		} catch (NumberFormatException e) {
			// min input is invalid, fallback to 0
		}

		try {
			maximum = Double.parseDouble(max);
		} catch (NumberFormatException e) {
			// max input is invalid, fallback to 4000
		}

		return services.getUsers(userRepo, minimum, maximum);

	}

	// Manually load all csv files in '/db/' directory into DB
	@RequestMapping(value = "/load")
	public String populateDB() throws Exception {
		services.loadCSV(userRepo);
		return "[Loaded csv files from '/db/.*' ] <br> <a href='/users'>Click to see users</a>";
	}

	// Upload csv file into '/db/' directory, follow by loading it into the DB
	@RequestMapping(value = "/upload")
	public String uploadCSV(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes)
			throws Exception {

		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
			return "[Failed] Empty File!  <br> <a href='/index.html'>Return<a>";
		}

		try {

			// Get the file and save it somewhere
			byte[] bytes = file.getBytes();
			Path path = Paths.get(".//db//" + file.getOriginalFilename());
			Files.write(path, bytes);

			redirectAttributes.addFlashAttribute("message",
					"You successfully uploaded '" + file.getOriginalFilename() + "'");

		} catch (IOException e) {
			e.printStackTrace();
		}

		return "[Successfully] uploaded '" + file.getOriginalFilename() + "' <br> <a href='/users'>Click to see users<a>";
	}

}