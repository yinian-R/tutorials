package com.wymm.easyexcelsample;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.merge.LoopMergeStrategy;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wymm.easyexcelsample.excel.DemoData;
import com.wymm.easyexcelsample.excel.TestFileUtil;
import com.wymm.easyexcelsample.excel.merger.Book;
import com.wymm.easyexcelsample.excel.merger.BookExcel;
import com.wymm.easyexcelsample.excel.merger.BookType;
import com.wymm.easyexcelsample.excel.merger.strategy.MergeStrategy;
import com.wymm.easyexcelsample.excel.write.DemoMergeData;
import org.junit.jupiter.api.Test;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class _5MergeTest {
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    /**
     * 合并单元格
     * 方法1 使用{@link DemoMergeData}
     * 方法2 创建一个merge策略并注册
     */
    @Test
    public void mergeWrite() {
        // 方法1 注解
        String fileName = TestFileUtil.getPath() + "mergeWrite" + System.currentTimeMillis() + ".xlsx";
        // 在DemoStyleData里面加上ContentLoopMerge注解
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, DemoMergeData.class)
                .sheet("模板")
                .doWrite(data());
        
        // 方法2 自定义合并单元格策略
        fileName = TestFileUtil.getPath() + "mergeWrite" + System.currentTimeMillis() + ".xlsx";
        // 每隔2行会合并 把eachColumn 设置成 3 也就是我们数据的长度，所以就第一列会合并。当然其他合并策略也可以自己写
        LoopMergeStrategy loopMergeStrategy = new LoopMergeStrategy(2, 0);
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, DemoData.class)
                .registerWriteHandler(loopMergeStrategy)
                .sheet("模板")
                .doWrite(data());
    }
    
    /**
     * 自定义合并策略：适合对象中嵌套列表的结构合并导出
     */
    @Test
    void customMergeStrategy() throws JsonProcessingException {
        List<BookType> data = data();
        System.out.println(objectMapper.writeValueAsString(data));
        
        MergeStrategy mergeStrategy = new MergeStrategy();
        mergeStrategy.addIgnoreMergeRow("bookId", "bookName", "author");
        int dataIndex = 0;
        List<BookExcel> exportList = new ArrayList<>();
        for (BookType bookType : data) {
            List<Book> books = bookType.getBooks();
            if (ObjectUtils.isEmpty(books)) {
                BookExcel bookExcel = toExcelModel(bookType, null);
                exportList.add(bookExcel);
                mergeStrategy.addMergeRow(bookExcel.getBookTypeId(), dataIndex++, 1);
            } else {
                for (Book book : books) {
                    BookExcel bookExcel = toExcelModel(bookType, book);
                    exportList.add(bookExcel);
                    mergeStrategy.addMergeRow(bookExcel.getBookTypeId(), dataIndex++, books.size());
                }
            }
        }
        String path = System.getProperty("user.dir") + File.separator + "target" + File.separator;
        String fileName = path + "merge" + System.currentTimeMillis() + ".xlsx";
        
        EasyExcel.write(fileName, BookExcel.class)
                .registerWriteHandler(mergeStrategy)
                .sheet("模板").doWrite(exportList);
    }
    
    private BookExcel toExcelModel(BookType bookType, Book book) {
        BookExcel bookExcel = new BookExcel();
        bookExcel.setBookTypeId(bookType.getBookTypeId());
        bookExcel.setBookType(bookType.getBookType());
        if (book != null) {
            bookExcel.setBookId(book.getBookId());
            bookExcel.setBookName(book.getBookName());
            bookExcel.setAuthor(book.getAuthor());
        }
        return bookExcel;
    }
    
    
    private List<BookType> data() {
        List<BookType> list = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            BookType bookType = new BookType();
            bookType.setBookTypeId((long) i);
            bookType.setBookType("分类名称 " + i);
            bookType.setBooks(new ArrayList<>());
            for (int j = 0; j < i; j++) {
                Book book = new Book();
                book.setBookId((long) j);
                book.setBookName("书名" + i + "-" + j);
                book.setAuthor("作者" + i + "-" + j);
                
                bookType.getBooks().add(book);
            }
            list.add(bookType);
        }
        return list;
    }
    
}
