package com.example.natatorium.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.natatorium.common.Result;
import com.example.natatorium.common.base.BaseController;
import com.example.natatorium.entity.FileIdentification;
import com.example.natatorium.entity.Files;
import com.example.natatorium.service.IFileIdentificationService;
import com.example.natatorium.service.IFilesService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.*;

/**
* @author GuYuHang
* @description 针对表【files】的Controller
* @createDate 2022-04-14 14:15:54
*/
@RestController
@RequestMapping("/files")
public class FilesController extends BaseController<Files> {

    @Resource
    private IFilesService iFilesService;

    @Resource
    private IFileIdentificationService iFiService;

    @Value("${files.upload.path}")
    private String filesUploadPath;

    @Value("${serverLocation.url}")
    private String serverLocation;

    @PostMapping("/upload")
    @PreAuthorize("hasAuthority('sys:files')")
    public Result upload(@RequestBody MultipartFile mFile) throws IOException {
        String fullFileName = mFile.getOriginalFilename();
        String prefixName = mFile.getName();
        String type = FileUtil.extName(fullFileName);
        long size = mFile.getSize();
        String fileUUID = UUID.randomUUID().toString();
        String fileMD5 = SecureUtil.md5(mFile.getInputStream());
        File filePath = new File(filesUploadPath);
        if(!filePath.exists()){
            filePath.mkdirs();
            System.out.println(filePath.getPath());
        }

        QueryWrapper<FileIdentification> fiqw = new QueryWrapper<>();
        fiqw.eq("file_md5",fileMD5);
        FileIdentification FI = iFiService.getOne(fiqw);
        if(FI == null){
            File file = new File(filesUploadPath+fileMD5+ StrUtil.DOT+type);
            mFile.transferTo(file);
            FI = new FileIdentification();
            FI.setFileUrl(file.getPath());
            FI.setFileMd5(fileMD5);
            iFiService.save(FI);
        }
        Files dbFile = new Files();
        FileIdentification existFile =  iFiService.getOne(fiqw);
        dbFile.setUuid(fileUUID);
        dbFile.setFullName(fullFileName);
        dbFile.setType(type);
        dbFile.setSize((double) (size /1024));
        dbFile.setIdentificationId(existFile.getId());
        dbFile.setRelativeUrl(serverLocation+fileMD5+StrUtil.DOT+type);
//        SimpleDateFormat dateFormat = new
        dbFile.setCreateTime(new Timestamp(new Date().getTime()).toString());
        iFilesService.save(dbFile);
        Map<String,Object> fileInfo = new HashMap<>();
        fileInfo.put("uuid",fileUUID);
        fileInfo.put("fileUrl",serverLocation+fileMD5+StrUtil.DOT+type);
        return Result.success(fileInfo);
    }

    @DeleteMapping("/delFile/{uuid}")
    public Result delFile(@PathVariable String uuid){
        iFilesService.del(uuid);
        return Result.success();
    }

    @GetMapping("/download/{uuid}")
    public void downloadFile(@PathVariable String uuid,
                               HttpServletResponse response) throws IOException{
        Files fileInfo = iFilesService.findFIlePathByUUID(uuid);
        File file = new File(fileInfo.getFileIdentification().getFileUrl());
        ServletOutputStream os = response.getOutputStream();
        response.setHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode(fileInfo.getFullName(),"UTF-8"));
//        response.setHeader("Access-Control-Expose-Headers","Content-Disposition");
        response.setContentType("application/octet-stream");
        os.write(FileUtil.readBytes(file));
        os.flush();
        os.close();
    }

    @GetMapping("/filePath/{uuid}")
    @PreAuthorize("hasAuthority('sys:fileList')")
    public Result getPath(@PathVariable String uuid){
        LambdaQueryWrapper<Files> lqw = new LambdaQueryWrapper();
        lqw.eq(Files::getUuid,uuid).select(Files::getRelativeUrl);
        return Result.success(iFilesService.getOne(lqw).getRelativeUrl());
    }
}