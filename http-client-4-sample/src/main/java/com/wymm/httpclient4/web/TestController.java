package com.wymm.httpclient4.web;

import com.wymm.httpclient4.model.MultipartFileQO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class TestController {
    
    @GetMapping
    public void home() {
    }
    
    @GetMapping("/timeout5")
    public void timeout5() throws InterruptedException {
        TimeUnit.SECONDS.sleep(5);
    }
    
    @PostMapping("/form")
    public void formPost(@RequestParam Map<String, Object> params) {
        log.info(params.toString());
    }
    
    @PostMapping("/auth")
    public void authPost(@RequestParam Map<String, Object> params) {
        log.info(params.toString());
    }
    
    @PostMapping("/json")
    public void jsonPost(@RequestBody Map<String, Object> params) {
        log.info(params.toString());
    }
    
    @PostMapping("/multipart")
    public void multipartPost(@RequestParam Map<String, Object> params, @RequestParam("file") MultipartFile file) throws IOException {
        log.info(params.toString());
        log.info(file.getName());
    }
    
    @PostMapping(value = "/multiparts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void multiparts(StandardMultipartHttpServletRequest request) throws IOException {
        MultiValueMap<String, MultipartFile> multiFileMap = request.getMultiFileMap();
        Map<String, String[]> parameterMap = request.getParameterMap();
        
        log.info("parameterMap:{}", parameterMap);
        log.info("multiFileMap.keys:{}", multiFileMap.keySet());
    }
    
    @PostMapping(value = "/multiparts2", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void multiparts2(MultipartFileQO qo) throws IOException {
        log.info("qo:{}", qo);
    }
}
