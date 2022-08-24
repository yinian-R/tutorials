package com.wymm.easyexcelsample;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.merge.LoopMergeStrategy;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wymm.easyexcelsample.excel.DemoData;
import com.wymm.easyexcelsample.excel.TestFileUtil;
import com.wymm.easyexcelsample.excel.merger.Book;
import com.wymm.easyexcelsample.excel.merger.BookExcel;
import com.wymm.easyexcelsample.excel.merger.BookType;
import com.wymm.easyexcelsample.excel.merger.strategy.ObjectAndListMergeStrategy;
import com.wymm.easyexcelsample.excel.write.DemoMergeData;
import org.junit.jupiter.api.Test;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 合并单元格
 */
public class _5MergeTest {
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    /**
     * 合并单元格（固定合并行数）
     * 方法1 使用{@link DemoMergeData}
     * 方法2 创建一个merge策略并注册
     */
    @Test
    public void simple() {
        // 方法1 注解
        String fileName = TestFileUtil.getPath() + "mergeWrite" + System.currentTimeMillis() + ".xlsx";
        // 在DemoStyleData里面加上ContentLoopMerge注解
        EasyExcel.write(fileName, DemoMergeData.class)
                .sheet("模板")
                .doWrite(data());
        
        // 方法2 自定义合并单元格策略
        fileName = TestFileUtil.getPath() + "mergeWrite" + System.currentTimeMillis() + ".xlsx";
        // 每隔2行会合并 把eachColumn 设置成 3 也就是我们数据的长度，所以就第一列会合并。当然其他合并策略也可以自己写
        LoopMergeStrategy loopMergeStrategy = new LoopMergeStrategy(2, 0);
        EasyExcel.write(fileName, DemoData.class)
                .registerWriteHandler(loopMergeStrategy)
                .sheet("模板")
                .doWrite(data());
    }
    
    /**
     * 自定义合并策略：适合对象中嵌套列表的结构合并导出
     */
    @Test
    void customMergeStrategy() {
        List<BookType> data = data();
        List<BookExcel> exportList = toBookExcelList(data);
        
        ObjectAndListMergeStrategy mergeStrategy = new ObjectAndListMergeStrategy();
        mergeStrategy.ignoreMergeColumnFiledNames("bookId", "bookName", "author");
        mergeStrategy.addMergeRow(exportList);
        
        String fileName = TestFileUtil.getPath() + "merge" + System.currentTimeMillis() + ".xlsx";
        EasyExcel.write(fileName, BookExcel.class)
                .registerWriteHandler(mergeStrategy)
                .sheet("模板")
                .doWrite(exportList);
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
    
    private List<BookExcel> toBookExcelList(List<BookType> list) {
        List<BookExcel> exportList = new ArrayList<>();
        if (!ObjectUtils.isEmpty(list)) {
            for (BookType bookType : list) {
                List<Book> books = bookType.getBooks();
                if (ObjectUtils.isEmpty(books)) {
                    BookExcel bookExcel = toExcelModel(bookType, null);
                    bookExcel.setUnique(bookType.getBookTypeId());
                    bookExcel.setMergeRowNum(1);
                    exportList.add(bookExcel);
                } else {
                    for (Book book : books) {
                        BookExcel bookExcel = toExcelModel(bookType, book);
                        bookExcel.setUnique(bookType.getBookTypeId());
                        bookExcel.setMergeRowNum(books.size());
                        exportList.add(bookExcel);
                    }
                }
            }
        }
        return exportList;
    }
    
    private List<BookType> data() {
        List<BookType> list = new ArrayList<>();
        for (int i = 0; i <= 5; i++) {
            BookType bookType = new BookType();
            bookType.setBookTypeId((long) i);
            bookType.setBookType("分类名称 " + i);
            bookType.setBooks(new ArrayList<>());
            for (int j = 0; j <= i; j++) {
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
