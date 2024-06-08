package com.lam.Controller;

import com.lam.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
public class UploadFile {
    static List<File> getUploadDirectory() throws FileNotFoundException {
        File targetPath = new File(ResourceUtils.getURL("classpath:").getPath());
        if (!targetPath.exists()) {
            targetPath = new File("");
        }
        String resourcesPath = System.getProperty("user.dir") + "/src/main/resources/static";
        File path = new File(resourcesPath);
        File upload = new File(path.getAbsolutePath(), "upload");
        File uploadTarget = new File(targetPath.getAbsolutePath(), "static/upload");
        //System.out.println(uploadTarget);
        if (!upload.exists()) {
            upload.mkdirs();
        }
        if (!uploadTarget.exists()) {
            uploadTarget.mkdirs();
        }
        List<File> files = new ArrayList<File>();
        //编译前的文件路径
        files.add(upload);
        //编译后的文件路径
        files.add(uploadTarget);
        return files;
    }

    /*
     * 上傳單文件的業務邏輯是上傳了圖片后返回圖片地址讓前端保存，添加商品的時候一起把圖片地址和商品信息一起提交，然後保存到數據庫中。
     * */
    @PostMapping("/api/upload")
    public String upload(MultipartFile myFile) {
        String filePath = "";
        if (!myFile.isEmpty()) {
            try {
//                文件名
                String filename = myFile.getOriginalFilename();
                filename = UUID.randomUUID() + filename.substring(filename.lastIndexOf("."));
                //文件保存路径
                List<File> files = getUploadDirectory();
                File curFile = new File(files.get(0), filename);
                //System.out.println(curFile);
                myFile.transferTo(curFile);
                FileCopyUtils.copy(curFile, new File(files.get(1), filename));
                log.info("check as follows");
                //  System.out.println(files.get(1));
                filePath = "http://localhost:8080/upload/" + filename;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //返回文件路径 http://localhost:8080/upload/73427238-9861-439e-be1b-b2e94b043263.jpg
        return filePath;
    }

    //多文件上傳
    /*
     * 多文件上傳的業務邏輯是把商品id和圖片地址一起寫入服務器
     *
     * */
    @PostMapping("/api/uploads")
    public Result upload(@RequestPart MultipartFile[] files) {
        // String filePath = "";
       // System.out.println("pd_id is " + pd_id);
        // System.out.println(files.length);
        if (files.length == 0) {
            return Result.error("文件为空，提交失败");
        }
        List<String> fileNameList = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                //獲取原來的文件名
                String filename = file.getOriginalFilename();
                //拿到文件名後綴
                filename = UUID.randomUUID() + filename.substring(filename.lastIndexOf("."));
                //文件保存路径
                List<File> fileSave = getUploadDirectory();
                File curFile = new File(fileSave.get(0), filename);
                file.transferTo(curFile);
                FileCopyUtils.copy(curFile, new File(fileSave.get(1), filename));
                //添加文件名字
                fileNameList.add(filename);
            } catch (Exception e) {
                log.info("保存失敗{}", e.getMessage());

            }

        }
        //返回文件路径 文件回寫
        return Result.success(fileNameList);
    }

}
