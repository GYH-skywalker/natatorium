package com.example.natatorium.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.natatorium.common.Constants;
import com.example.natatorium.common.base.BaseServiceImpl;
import com.example.natatorium.entity.FileIdentification;
import com.example.natatorium.entity.Files;
import com.example.natatorium.exception.serviceException;
import com.example.natatorium.service.IFileIdentificationService;
import com.example.natatorium.service.IFilesService;
import com.example.natatorium.mapper.FilesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

/**
* @author GuYuHang
* @description 针对表【files】的数据库操作Service实现
* @createDate 2022-04-14 14:15:54
*/
@Service
public class FilesServiceImpl extends BaseServiceImpl<FilesMapper, Files>
    implements IFilesService{
    @Resource
    private FilesMapper filesMapper;

    @Autowired
    private IFileIdentificationService iFileIdentificationService;

    @Value("${files.upload.path}")
    private String filesUploadPath;

    @Override
    public Files findFIlePathByUUID(String uuid){
        return filesMapper.findPathAndName(uuid);
    }

    @Override
    public boolean del(String uuid) {
        QueryWrapper<Files> qw = new QueryWrapper<>();
        qw.eq("uuid",uuid);
        //查出数据库中的该文件
        Files dbfile = getOne(qw);
        QueryWrapper<FileIdentification> fileMD5qw = new QueryWrapper<>();
        fileMD5qw.eq("id",dbfile.getIdentificationId());
        //查出该文件的md5以及路径
        FileIdentification fileMD5 = iFileIdentificationService.getOne(fileMD5qw);
        //删除数据库中该文件条目
        remove(qw);
        if(fileMD5 != null){
            QueryWrapper<Files> fdqw = new QueryWrapper<>();
            fdqw.eq("identification_id",dbfile.getIdentificationId());
            List<Files> sameFiles = list(fdqw);
            if(sameFiles.size() < 1){
                File realFile = new File(filesUploadPath + fileMD5.getFileMd5()+ StrUtil.DOT+dbfile.getType());
                if(realFile.exists()){
                    realFile.delete();
                }else {
                    remove(qw);
                    throw new serviceException(Constants.CODE_600,"文件不存在");
                }
                iFileIdentificationService.remove(fileMD5qw);
            }
        }
        return true;
    }
}




