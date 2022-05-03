package com.cs.pdb.controller;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import java.io.*;
import java.util.stream.Collectors;

@Controller
public class PDBController {
    @GetMapping("/pdb")
    @ResponseBody
    public String downloadPdb(String id) {
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("https://files.rcsb.org/download/" + id + ".pdb");
        try {
            HttpResponse response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() != 200) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "pdb file not found");
            }
            HttpEntity entity = response.getEntity();
            InputStream content = entity.getContent();
            File folder = new File("pdb/");
            if (!folder.exists()) {
                boolean mkdir = folder.mkdir();
                if (!mkdir) {
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "can not make pdb directory");
                }

            }
            File file = new File("pdb/" + id + ".pdb");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            int bytesRead;
            byte[] buffer = new byte[1024];
            while ((bytesRead = content.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }
            fileOutputStream.close();
            content.close();
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return id;
    }

    @GetMapping("viewer")
    public String view() {
        return "viewer";
    }

    @GetMapping("json")
    @ResponseBody
    public String getJson(String id) throws IOException {
        Runtime rt = Runtime.getRuntime();
        Process pr = rt.exec("./dnapro -i=pdb/" + id + ".pdb --json --interface");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(pr.getInputStream()));
        String res = bufferedReader.lines().collect(Collectors.joining());
        bufferedReader.close();
        return res;
    }
}
