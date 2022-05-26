package com.example.natatorium.utils;


import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

public class MyExcelUtil {

    public static void exportExcel(HttpServletResponse response, List<?> list,String FName) throws IOException {

        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(list,true);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8");
        String fileName = URLEncoder.encode(FName, "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
        ServletOutputStream out = response.getOutputStream();
        writer.flush(out,true);
        writer.close();
        IoUtil.close(out);
    }

    public static <T> List<T> importExcel(MultipartFile file,Class<T> clazz) throws IOException{
        InputStream in = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(in);
        return reader.readAll(clazz);
    }
}
