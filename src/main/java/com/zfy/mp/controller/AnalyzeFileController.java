package com.zfy.mp.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.zfy.mp.common.utils.FileUploadUtils;
import com.zfy.mp.domain.dto.bwcj.Order;
import com.zfy.mp.domain.dto.bwcj.Record;
import com.zfy.mp.enums.UploadEnum;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Tag(name = "文件处理")
@Log4j2
@RestController
@RequestMapping("/file")
public class AnalyzeFileController {


    @Resource
    private FileUploadUtils fileUploadUtils;

    @PostMapping("/analyze")
    @PreAuthorize("hasAnyAuthority('all')")
    public ResponseEntity<ByteArrayResource> analyzeFile(@RequestParam("files") List<MultipartFile> files) throws Exception {

        System.out.println("----------------开始处理文件-------------------");

        String regex = "[^a-zA-Z\\u4e00-\\u9fa5]";

        List<Order> orderList = null;
        List<Record> recordList = null;
        MultiValueMap<String, String> diffMap = new LinkedMultiValueMap<>();
        for (MultipartFile file : files) {
            // 直接使用文件输入流
            ExcelReader reader = ExcelUtil.getReader(file.getInputStream());
            String originalFilename = file.getOriginalFilename();

            List<Map<String, Object>> readAll = reader.readAll();
            if (originalFilename.contains("出料")) {
                orderList = IntStream.range(0, readAll.size())
                        .mapToObj(i -> {
                            Map<String, Object> item = readAll.get(i);
                            Order order = new Order();
                            order.setColumn(Long.valueOf(i) + 2);
                            order.setPfmc(item.get("配方名称").toString());
                            order.setWlmc(item.get("物料名称").toString().replaceAll(regex, ""));
                            order.setClkssj(DateUtil.parse(item.get("出料开始时间").toString()));
                            return order;
                        })
                        .collect(Collectors.toList());
            } else if (originalFilename.contains("记录")) {
                recordList = IntStream.range(0, readAll.size())
                        .mapToObj(i -> {
                            Map<String, Object> item = readAll.get(i);
                            Record record = new Record();
                            record.setWlmc(item.get("效期名称").toString().replaceAll(regex, ""));
                            record.setDysj(DateUtil.parse(item.get("打印时间").toString()));
                            record.setDqsj(DateUtil.parse(item.get("到期时间").toString()));
                            return record;
                        })
                        .collect(Collectors.toList());
            } else {
                readAll.stream().forEach(item -> {
                    String key = item.get("出料物料名称").toString().replaceAll(regex, "");
                    String value = item.get("记录物料名称").toString().replaceAll(regex, "");
                    diffMap.add(key, value);
                });
            }
        }
//        举例：第一款A茶汤制备时间是一点，过期时间三点；第二款A茶汤制备时间是四点，过期时间是五点。这中间有一杯饮品，茶饮机扫码出料时间是三点半，那么3点到四点之间因为第一款茶汤过期了，不能用了，且三点到四点之间没有制备新的茶汤，所以筛选出来三点半这杯饮品用了过期物料，有问题
        if (ObjectUtil.isEmpty(diffMap) || ObjectUtil.isEmpty(orderList) || ObjectUtil.isEmpty(recordList)) {
            return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN)
                    .body(new ByteArrayResource("请上传正确的文件".getBytes()));
        }
        List<Order> useValidOrders = new ArrayList<>();
        for (Order order : orderList) {
            Date wl_use_startTime = order.getClkssj();
            boolean isValid = false; // 标记该订单是否使用了有效物料
            boolean materialExists = false; // 标记物料是否存在于物料表中
            for (Record record : recordList) {
                if (order.getWlmc().equals(record.getWlmc()) ||
                        ifDiff(diffMap, order.getWlmc(), record.getWlmc())) {
                    materialExists = true; // 找到了该物料
                    // 匹配对应物料
                    Date wl_make_startTime = record.getDysj();
                    Date wl_valid_endTime = record.getDqsj();
                    // 检查使用时间是否在有效范围内：制作时间 <= 使用时间 <= 到期时间
                    if (wl_use_startTime.after(wl_make_startTime) &&
                            wl_use_startTime.before(wl_valid_endTime)) {
                        // 如果物料在有效期内使用，则标记为有效，并跳出内层循环
                        isValid = true;
                        break;
                    }
                }
            }
            // 只有当订单没有使用任何有效期内的物料时，才认为是使用了过期物料
            if (materialExists && !isValid) {
                useValidOrders.add(order);
            }
        }

        if (useValidOrders.size() > 0) {
            // 创建ExcelWriter
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();


            try (ExcelWriter writer = ExcelUtil.getWriter(true)) { // true表示自动创建sheet
                // 设置表头
                writer.addHeaderAlias("column", "序号");
                writer.addHeaderAlias("pfmc", "配方名称");
                writer.addHeaderAlias("wlmc", "物料名称");
                writer.addHeaderAlias("clkssj", "出料开始时间");

                // 写入数据
                writer.write(useValidOrders, true); // true表示包含表头

                // 将数据写入输出流
                writer.flush(outputStream);

                String uploadPath = fileUploadUtils.uploadWithOutputStream(UploadEnum.BWCJ_EXCEL,"test.xlsx", MediaType.MULTIPART_FORM_DATA_VALUE, outputStream.size(),writer);
                // todo 文件地址保存到数据库， 返回下载uri
                log.info("文件：{}的文件路径：{}", "过期文件", uploadPath);

                // 创建资源
                byte[] bytes = outputStream.toByteArray();
                ByteArrayResource resource = new ByteArrayResource(bytes);
                System.out.println("----------------处理结束-------------------");

                // 返回响应
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=useInvalidOrders.xlsx")
                        .body(resource);
            }
        }
        System.out.println("----------------处理结束-------------------");
        // 如果没有数据，返回空响应或提示信息
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(new ByteArrayResource("没有发现使用过期物料的订单".getBytes()));
    }
    private Boolean ifDiff(MultiValueMap<String, String> diffMap, String key, String value) {
        if (diffMap.get(key) == null) return false;
        List<String> list = diffMap.get(key);
        for (String item: list) {
            if (item.equals(value)) {
                return true;
            }
        }
        return false;
    }
}
