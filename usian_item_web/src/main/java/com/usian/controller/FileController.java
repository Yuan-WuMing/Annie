package com.usian.controller;

import com.usian.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("file")
public class FileController {

    /*@Autowired
    private */

    @RequestMapping("upload")
    public Result upload(MultipartFile file){
        if (file != null && file.getSize()>0){
            String filename = file.getOriginalFilename();
            String substring = filename.substring(filename.lastIndexOf("."));
            String fileName = UUID.randomUUID()+substring;
            File file1 = new File("D:\\Wuming\\"+fileName);
            try {
                file.transferTo(file1);
                return Result.ok("http://image.usian.com/"+fileName);
            } catch (IOException e) {
                e.printStackTrace();
                return Result.error("突突突");
            }
        }
        return Result.error("上传失败");
    }
}
