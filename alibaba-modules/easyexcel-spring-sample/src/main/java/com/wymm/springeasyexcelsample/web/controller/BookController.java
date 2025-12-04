package com.wymm.springeasyexcelsample.web.controller;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.wymm.springeasyexcelsample.core.excel.strategy.ObjectAndListMergeStrategy;
import com.wymm.springeasyexcelsample.core.util.ExcelUtils;
import com.wymm.springeasyexcelsample.web.entity.Book;
import com.wymm.springeasyexcelsample.web.entity.excel.BookColorStyleExcel;
import com.wymm.springeasyexcelsample.web.entity.excel.BookExcel;
import com.wymm.springeasyexcelsample.web.service.api.BookService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * (Book)表控制层
 *
 * @since 2022-08-10 15:03:14
 */
@Slf4j
@RestController
@RequestMapping("book")
public class BookController {
    @Resource
    private BookService bookService;
    @Resource
    private HttpServletResponse response;
    @Resource
    private HttpServletRequest request;
    
    /**
     * 分页查询所有数据
     *
     * @return 所有数据
     */
    @GetMapping
    public List<Book> list() {
        return this.bookService.list();
    }
    
    /**
     * 简单的下载示例
     */
    @GetMapping("/download")
    public void download() throws IOException {
        ExcelUtils.initResponse(response, "书籍导出列表", ExcelTypeEnum.XLSX);
        
        List<BookExcel> data = this.bookService.findBookExcel();
        
        EasyExcel.write(response.getOutputStream())
                .sheet("模板")
                .head(BookExcel.class)
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .doWrite(data);
    }
    
    /**
     * 简单的上传示例
     *
     * @param file 上传文件
     */
    @PostMapping("/upload")
    public void upload(@RequestParam("file") MultipartFile file) throws IOException {
        List<BookExcel> data = EasyExcel.read(file.getInputStream())
                .head(BookExcel.class)
                .doReadAllSync();
        
        bookService.upload(data);
    }
    
    /**
     * 下载合并Excel文件
     */
    @GetMapping("/downloadMerge")
    public void downloadMerge() throws IOException {
        ExcelUtils.initResponse(response, "书籍导出列表", ExcelTypeEnum.XLSX);
        
        List<BookExcel> data = this.bookService.findBookExcel();
        
        ObjectAndListMergeStrategy<BookExcel> mergeStrategy = new ObjectAndListMergeStrategy<>(data, BookExcel::getBookType);
        mergeStrategy.includeMergeColumnFiledNames("bookType");
        
        EasyExcel.write(response.getOutputStream())
                .sheet("模板")
                .head(BookExcel.class)
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .registerWriteHandler(mergeStrategy)
                .doWrite(data);
    }
    
    /**
     * 下载动态生成下拉列表的导入模板
     * <p>
     * 需配置模板下拉框如下：
     * 1、公式->名称管理器->新建
     * 名称：bookType
     * 引用位置：=OFFSET(meta!$A$2,,,SUMPRODUCT(N(LEN(meta!$A:$A)>0))-1,)
     * 2、选中需要设置下拉框的单元格，数据->有效性
     * 条件：序列
     * 来源：=bookType
     */
    @GetMapping("/downloadTemplate")
    public void downloadTemplate() throws IOException {
        this.bookService.downloadTemplate(response);
    }
    
    
    /**
     * 导出根据属性值不同具有不同颜色的单元格
     * <p>
     * 使用 {@link WriteCellData}
     */
    @GetMapping("/downloadColColor")
    public void downloadColColor() throws IOException {
        ExcelUtils.initResponse(response, "书籍导出列表", ExcelTypeEnum.XLSX);
        
        List<BookColorStyleExcel> data = this.bookService.findBookColorStyleExcel();
        
        EasyExcel.write(response.getOutputStream())
                .inMemory(true)
                .sheet("模板")
                .head(BookColorStyleExcel.class)
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .doWrite(data);
    }
    
    
    /**
     * 上传示例（包含图片）
     *
     * @param file 上传文件
     */
    @PostMapping("/uploadExcelImage")
    public void uploadExcelImage(@RequestParam("file") MultipartFile file) throws IOException {
        List<BookExcel> data = EasyExcel.read(file.getInputStream())
                .head(BookExcel.class)
                .registerReadListener(new ImageReadListener<BookExcel>(file) {
                    
                    @Override
                    public void invoke(BookExcel data, AnalysisContext context, Map<String, PictureData> picMap, Integer sheetNo, Integer rowIndex) {
                        // sheetNo_rowIndex_colIndex
                        String picKey = sheetNo + "_" + rowIndex + "_" + 5;
                        if (picMap.containsKey(picKey)) {
                            PictureData pictureData = picMap.get(picKey);
                            // 设置图片
                            data.setPic(pictureData.getData());
                        }
                    }
                })
                .doReadAllSync();
        
//        bookService.upload(data);
        System.out.println();
    }
}

