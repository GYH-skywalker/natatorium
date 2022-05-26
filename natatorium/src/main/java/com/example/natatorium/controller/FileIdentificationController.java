package com.example.natatorium.controller;

import com.example.natatorium.common.base.BaseController;
import com.example.natatorium.entity.FileIdentification;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* @author GuYuHang
* @description 针对表【file_identification】的Controller
* @createDate 2022-04-14 12:52:05
*/
@RestController
@RequestMapping("/fileIdentity")
public class FileIdentificationController extends BaseController<FileIdentification> {

}