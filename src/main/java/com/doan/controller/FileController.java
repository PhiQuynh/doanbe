package com.doan.controller;

import com.doan.entity.FileInfo;
import com.doan.payload.ResponseMessage;
import com.doan.repository.FileInfoRepository;
import com.doan.service.FileDBServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/QLCSVC/api/file")
public class FileController {

    private final FileDBServiceImpl fileDBService;

    private final FileInfoRepository infoRepository;

    @GetMapping("/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = fileDBService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
    //
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseMessage> uploadFile(@RequestPart("file") MultipartFile file) {
        String message = "";
        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType("MediaType.ALL");
        try {
            fileDBService.save(file);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @GetMapping("/files")
    public ResponseEntity<List<FileInfo>> getListFiles() {

        return new ResponseEntity<>(infoRepository.findAll(), HttpStatus.OK);

//        List<FileInfo> fileInfos = fileDBService.loadAll().map(path -> {
//            String filename = path.toFile().getName();
//            String url = MvcUriComponentsBuilder
//                    .fromMethodName(FileController.class, "getFile", path.getFileName().toString()).build().toString();
//
//            return new FileInfo(filename, url);
//        }).collect(Collectors.toList());
//
//        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }

}