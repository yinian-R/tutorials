package com.wymm.springeasyexcelsample.core.util;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.map.MapUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillWrapper;
import javax.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class ExcelUtils {
    
    public static final String SHEET_META_DEFAULT_NAME = "meta";
    
    /**
     * 初始化 HttpServletResponse（ContentType、CharacterEncoding、Header）
     *
     * @param response response
     * @param fileName 文件名
     * @param suffix   文件后缀
     */
    @SneakyThrows(UnsupportedEncodingException.class)
    public static void initResponse(HttpServletResponse response, String fileName, ExcelTypeEnum suffix) {
        fileName = ExcelUtils.fileNameJoinDateTime(fileName);
        fileName = String.format("%s%s", URLEncoder.encode(fileName, "UTF-8"), suffix.getValue());
        
        // 根据实际的文件类型找到对应的 contentType
        String contentType = MediaTypeFactory.getMediaType(fileName)
                .map(MediaType::toString)
                .orElse("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setContentType(contentType);
        response.setCharacterEncoding("UTF-8");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName);
    }
    
    /**
     * @param fileName fileName
     * @return fileName-yyyyMMddHHmmss
     */
    public static String fileNameJoinDateTime(String fileName) {
        return fileName + "-" + LocalDateTime.now().format(DatePattern.PURE_DATETIME_FORMATTER);
    }
    
    public static List<HashMap<String, String>> buildDictList(List<String> list) {
        return list.stream()
                .map(item -> MapUtil.of("name", item))
                .collect(Collectors.toList());
    }
    
    /**
     * 导出模板（填充字典）
     * 在 sheet name = meta 的 sheet 填充单元格
     *
     * @param filePath  文件路径
     * @param fileName  文件名称（不包含后缀）
     * @param excelType 文件类型（后缀）
     * @param dictMap   字典 key:字典名称 value: 字典列表
     * @param response  HttpServletResponse
     */
    public static void fillDictTemplate(String filePath, String fileName, ExcelTypeEnum excelType, Map<String, List<HashMap<String, String>>> dictMap, HttpServletResponse response)
            throws IOException {
        ExcelWriter excelWriter = null;
        try {
            initResponse(response, fileName, excelType);
            
            InputStream stream = ResourceUtil.getStream(filePath);
            excelWriter = EasyExcel.write(response.getOutputStream())
                    .withTemplate(stream)
                    .build();
            WriteSheet metaSheet = EasyExcel.writerSheet(SHEET_META_DEFAULT_NAME)
                    .build();
            
            // 写入字典
            for (String key : dictMap.keySet()) {
                List<HashMap<String, String>> dictDTOS = dictMap.get(key);
                excelWriter.fill(new FillWrapper(key, dictDTOS), metaSheet);
            }
        } finally {
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
    }
}
