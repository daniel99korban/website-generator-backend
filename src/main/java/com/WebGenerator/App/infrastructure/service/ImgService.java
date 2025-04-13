package com.WebGenerator.App.infrastructure.service;

import com.WebGenerator.App.domain.model.Img;
import com.WebGenerator.App.domain.model.WebSite;
import com.WebGenerator.App.infrastructure.repository.ImgRepository;
import io.imagekit.sdk.ImageKit;
import io.imagekit.sdk.models.FileCreateRequest;
import io.imagekit.sdk.models.results.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class ImgService {
    @Autowired
    private ImgRepository imgRepository;

    @Autowired
    private ImageKit imageKit;

    public Img create(Img img){
        return imgRepository.save(img);
    }

    public String uplodImg(MultipartFile file) {
        try {

            byte[] fileBytes = file.getBytes();
            String base64File = Base64.getEncoder().encodeToString(fileBytes);

            String contentType = file.getContentType();
            String base64WithPrefix = "data:" + contentType + ";base64," + base64File;

            FileCreateRequest request = new FileCreateRequest(base64WithPrefix, file.getOriginalFilename());
            request.setPrivateFile(false);

            Result result = imageKit.upload(request);
            if (result == null) {
                throw new RuntimeException("Upload falhou.");
            }

            List<Map<String, String>> transformation = new ArrayList<>();

            Map<String, Object> options = new HashMap<>();
            options.put("urlEndpoint", result.getUrl());
            options.put("path", result.getFilePath());
            options.put("transformation", transformation);

            if (result.getUrl() == null) {
                throw new RuntimeException("Upload falhou ou URL da imagem não retornada.");
            }
            return result.getUrl();

        } catch (IOException e) {
            e.printStackTrace();
            return "Erro ao ler o arquivo: " + e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao enviar imagem: " + e.getMessage();
        }
    }
}
