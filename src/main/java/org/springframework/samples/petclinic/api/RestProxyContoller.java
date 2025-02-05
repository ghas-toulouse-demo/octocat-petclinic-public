package org.springframework.samples.petclinic.api;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RestProxyContoller {

	@Value("${api.key}")
	private String apiKey;

	@RequestMapping(value = "/service/", produces = "application/json")
	public String getDataFromUrl(@RequestParam("url") String url) {
		String result;
		try (InputStream in = new URL(url).openStream()) {
			result = new String(in.readAllBytes(), StandardCharsets.UTF_8);
		}
		catch (MalformedURLException e) {
			// raise an error if the url is not valid
			throw new IllegalArgumentException("Invalid url");
		}
		catch (IOException e) {
			throw new IllegalArgumentException("Could not read from url");

		}
		return result;
	}

}
