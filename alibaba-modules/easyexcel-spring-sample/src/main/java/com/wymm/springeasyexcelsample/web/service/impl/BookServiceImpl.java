package com.wymm.springeasyexcelsample.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillWrapper;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wymm.springeasyexcelsample.core.util.ExcelUtils;
import com.wymm.springeasyexcelsample.web.dao.BookDao;
import com.wymm.springeasyexcelsample.web.entity.Book;
import com.wymm.springeasyexcelsample.web.entity.excel.BookColorStyleExcel;
import com.wymm.springeasyexcelsample.web.entity.excel.BookExcel;
import com.wymm.springeasyexcelsample.web.service.api.BookService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * (Book)表服务实现类
 *
 * @since 2022-08-10 15:03:14
 */
@Slf4j
@Service
public class BookServiceImpl extends ServiceImpl<BookDao, Book> implements BookService {
    
    @Resource
    private Validator validator;
    
    @Override
    public List<BookExcel> findBookExcel() {
        return BeanUtil.copyToList(this.list(), BookExcel.class);
    }
    
    @Override
    public void upload(List<BookExcel> data) {
        // check
        String tips = this.checkUploadData(data);
        log.warn("tips:{}", tips);
        
        // do something
        log.info("data:{}", data);
    }
    
    
    @Override
    public void downloadTemplate(HttpServletResponse response) throws IOException {
        ExcelUtils.initResponse(response, "书籍导入模板", ExcelTypeEnum.XLSX);
        
        InputStream stream = ResourceUtil.getStream("static" + File.separator + "book_upload_template.xlsx");
        ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream())
                .withTemplate(stream)
                .build();
        WriteSheet metaSheet = EasyExcel.writerSheet("meta").build();
        
        List<String> list = Stream.of("自然科学", "编程语言", "高中教材").collect(Collectors.toList());
        excelWriter.fill(new FillWrapper("bookType", ExcelUtils.buildDictList(list)), metaSheet);
        
        List<String> tags = Stream.of("快乐", "开心", "高兴", "兴奋", "激动", "热情", "新颖").collect(Collectors.toList());
        excelWriter.fill(new FillWrapper("tag", ExcelUtils.buildDictList(tags)), metaSheet);
        
        excelWriter.finish();
    }
    
    @Override
    public List<BookColorStyleExcel> findBookColorStyleExcel() {
        return this.list()
                .stream()
                .map(data -> {
                    
                    BookColorStyleExcel styleExcel = new BookColorStyleExcel();
                    styleExcel.setName(data.getName());
                    styleExcel.setAuthor(data.getAuthor());
                    styleExcel.setDescription(data.getDescription());
                    
                    WriteCellData<String> bookTypeCellData = new WriteCellData<>("分类样式");
                    bookTypeCellData.setType(CellDataTypeEnum.STRING);
                    String bookType = data.getBookType();
                    bookTypeCellData.setStringValue(bookType);
                    // 根据判断设置颜色
                    if ("计算机".equals(bookType)) {
                        WriteCellStyle writeCellStyle = new WriteCellStyle();
                        writeCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
                        writeCellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
                        bookTypeCellData.setWriteCellStyle(writeCellStyle);
                    }
                    styleExcel.setBookType(bookTypeCellData);
                    
                    return styleExcel;
                }).collect(Collectors.toList());
    }
    
    private String checkUploadData(List<BookExcel> data) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i <= data.size(); i++) {
            BookExcel excel = data.get(i - 1);
            Set<ConstraintViolation<BookExcel>> validate = validator.validate(excel);
            if (ObjectUtils.isNotEmpty(validate)) {
                String line = String.format("第【%s】条：", i);
                String message = validate.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining("；"));
                if (stringBuilder.length() > 0) {
                    stringBuilder.append("；");
                }
                stringBuilder.append(line);
                stringBuilder.append(message);
            }
        }
        return stringBuilder.toString();
    }
    
    
}

