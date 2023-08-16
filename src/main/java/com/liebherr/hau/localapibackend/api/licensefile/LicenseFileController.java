package com.liebherr.hau.localapibackend.api.licensefile;

import com.liebherr.hau.localapibackend.api.licensefile.dto.request.LicenseFileRequest;
import com.liebherr.hau.localapibackend.utils.KeyIVGeneratorAES;
import com.liebherr.hau.localapibackend.utils.PemUtils;
import com.liebherr.hau.localapibackend.utils.ReadPemKeyPairUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class LicenseFileController {

    @Autowired
    LicenseFileService licenseFileService;

    @Autowired
    KeyIVGeneratorAES generatorAES;

    @Autowired
    ReadPemKeyPairUtils pemKeyPairUtils;

    @Autowired
    PemUtils pemUtils;

    private static final Logger LOGGER = LoggerFactory.getLogger(LicenseFileController.class);

    @PostMapping(value = "/license")
    @ResponseBody
    public ResponseEntity<Resource> createLicenseFile(@RequestBody LicenseFileRequest licenseFileRequest) throws Exception {
        return licenseFileService.makeLicense(licenseFileRequest);
    }

    @GetMapping(value = "/license")
    @ResponseBody
    public ResponseEntity<Resource> getLicenseFile(@RequestParam(name = "id") String id) throws Exception {
        return licenseFileService.getLicenseResourceFromDb(id);
    }


    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) throws MalformedURLException {

        final Path root = Paths.get("");
        Path file = root.resolve(filename);
        Resource resource = new UrlResource(file.toUri());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
    }
}
