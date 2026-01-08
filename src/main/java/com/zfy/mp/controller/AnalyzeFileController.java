package com.zfy.mp.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.zfy.mp.domain.dto.bwcj.qxRecord;
import com.zfy.mp.domain.dto.bwcj.tsOrder;
import com.zfy.mp.domain.response.ResponseResult;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Log4j2
@RestController
@RequestMapping("/file")
public class AnalyzeFileController {


    @PostMapping("/analyze")
    @PreAuthorize("hasAnyAuthority('all')")
    public ResponseResult<Resource> analyzeFile(@RequestParam("files") List<MultipartFile> files) throws IOException {
        for (MultipartFile file: files) {
            // 直接使用文件输入流
            ExcelReader reader = ExcelUtil.getReader(file.getInputStream());
            String originalFilename = file.getOriginalFilename();

            List<Map<String, Object>> readAll = reader.readAll();
            String fileType = "";
            if (originalFilename.contains("堂食")) {
                fileType = "tsOrder";
            } else if (originalFilename.contains("外卖")) {
                fileType = "wmOrder";
            } else {
                fileType = "qxRecord";
            }
            log.info(readAll);
        }
        return null;
    }
}
