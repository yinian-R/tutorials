package com.wymm.easyexcelsample;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.format.NumberFormat;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.converters.DefaultConverterLoader;
import com.alibaba.excel.enums.CellExtraTypeEnum;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson.JSON;
import com.wymm.easyexcelsample.excel.TestFileUtil;
import com.wymm.easyexcelsample.excel.read.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * 写入示例
 */
@Slf4j
public class _6ReadTest {
    
    /**
     * 使用监听器读
     * 实体类中的字段顺序需跟 Excel 列相同
     */
    @Test
    public void simpleRead() {
        // 写法1：不用额外写一个DemoDataListener
        String fileName = TestFileUtil.getPath() + "read" + File.separator + "demo.xlsx";
        EasyExcel.read(fileName, DemoData.class, new PageReadListener<DemoData>(dataList -> {
            for (DemoData demoData : dataList) {
                log.info("读取到一条数据{}", JSON.toJSONString(demoData));
            }
        })).sheet().doRead();
        
        // 写法2：
        // 匿名内部类 不用额外写一个DemoDataListener
        fileName = TestFileUtil.getPath() + "read" + File.separator + "demo.xlsx";
        EasyExcel.read(fileName, DemoData.class, new ReadListener<DemoData>() {
            /**
             * 单次缓存的数据量
             */
            public static final int BATCH_COUNT = 100;
            /**
             *临时存储
             */
            private List<DemoData> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
            
            @Override
            public void invoke(DemoData data, AnalysisContext context) {
                cachedDataList.add(data);
                if (cachedDataList.size() >= BATCH_COUNT) {
                    saveData();
                    // 存储完成清理 list
                    cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
                }
            }
            
            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
                saveData();
            }
            
            /**
             * 加上存储数据库
             */
            private void saveData() {
                log.info("{}条数据，开始存储数据库！", cachedDataList.size());
                log.info("存储数据库成功！");
            }
        }).sheet().doRead();
        
        // 有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
        // 写法3：创建 ReadListener 实现类
        fileName = TestFileUtil.getPath() + "read" + File.separator + "demo.xlsx";
        EasyExcel.read(fileName, DemoData.class, new DemoDataListener()).sheet().doRead();
        
        // 写法4：使用 ExcelReader 和监听器
        fileName = TestFileUtil.getPath() + "read" + File.separator + "demo.xlsx";
        // 一个文件一个reader
        ExcelReader excelReader = null;
        try {
            excelReader = EasyExcel.read(fileName, DemoData.class, new DemoDataListener()).build();
            // 构建一个sheet 这里可以指定名字或者no
            ReadSheet readSheet = EasyExcel.readSheet(0).build();
            excelReader.read(readSheet);
        } finally {
            if (excelReader != null) {
                // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
                excelReader.finish();
            }
        }
    }
    
    /**
     * 指定列的下标或者列名
     * 若实体类字段顺序和 Excel 不同可通过 @ExcelProperty(index = ) 设置，从左侧 0 递增
     */
    @Test
    public void indexOrNameRead() {
        String fileName = TestFileUtil.getPath() + "read" + File.separator + "demo.xlsx";
        EasyExcel.read(fileName, IndexOrNameData.class, new IndexOrNameDataListener()).sheet().doRead();
    }
    
    /**
     * 读多个或者全部sheet,这里注意一个sheet不能读取多次，多次读取需要重新读取文件
     */
    @Test
    public void repeatedRead() {
        String fileName = TestFileUtil.getPath() + "read" + File.separator + "demo.xlsx";
        // 读取全部sheet
        // 这里需要注意 DemoDataListener的doAfterAllAnalysed 会在每个sheet读取完毕后调用一次。然后所有sheet都会往同一个DemoDataListener里面写
        EasyExcel.read(fileName, DemoData.class, new DemoDataListener()).doReadAll();
        
        // 读取部分sheet
        fileName = TestFileUtil.getPath() + "read" + File.separator + "demo.xlsx";
        ExcelReader excelReader = null;
        try {
            excelReader = EasyExcel.read(fileName).build();
            
            // 这里为了简单 所以注册了 同样的head 和Listener 自己使用功能必须不同的Listener
            ReadSheet readSheet1 =
                    EasyExcel.readSheet(0).head(DemoData.class).registerReadListener(new DemoDataListener()).build();
            ReadSheet readSheet2 =
                    EasyExcel.readSheet(1).head(DemoData.class).registerReadListener(new DemoDataListener()).build();
            // 这里注意 一定要把sheet1 sheet2 一起传进去，不然有个问题就是03版的excel 会读取多次，浪费性能
            excelReader.read(readSheet1, readSheet2);
        } finally {
            if (excelReader != null) {
                // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
                excelReader.finish();
            }
        }
    }
    
    /**
     * 日期、数字或者自定义格式转换读
     * 默认读的转换器{@link DefaultConverterLoader#loadDefaultReadConverter()}
     * 创建excel对应的实体对象 参照{@link ConverterData}.里面可以使用注解{@link DateTimeFormat}、{@link NumberFormat}或者自定义注解
     */
    @Test
    public void converterRead() {
        String fileName = TestFileUtil.getPath() + "read" + File.separator + "demo.xlsx";
        EasyExcel.read(fileName, ConverterData.class, new ConverterDataListener())
                // 这里注意 我们也可以registerConverter来指定自定义转换器， 但是这个转换变成全局了， 所有java为string,excel为string的都会用这个转换器。
                // 如果就想单个字段使用请使用 @ExcelProperty(converter = CustomStringStringConverter.class)
                // .registerConverter(new CustomStringStringConverter())
                // 读取sheet
                .sheet()
                .doRead();
    }
    
    /**
     * 多行标题读
     * 设置headRowNumber参数，然后读。 这里要注意headRowNumber如果不指定， 会根据你传入的class的{@link ExcelProperty#value()}里面的表头的数量来决定行数，
     * 如果不传入class则默认为1.当然你指定了headRowNumber不管是否传入class都是以你传入的为准。
     */
    @Test
    public void complexHeaderRead() {
        String fileName = TestFileUtil.getPath() + "read" + File.separator + "demo.xlsx";
        EasyExcel.read(fileName, DemoData.class, new DemoDataListener())
                .sheet()
                // 这里可以设置1，因为头就是一行。如果多行头，可以设置其他值。不传入也可以，因为默认会根据DemoData 来解析，他没有指定头，也就是默认1行
                .headRowNumber(1)
                .doRead();
    }
    
    /**
     * 读取表头数据
     * 监听器实现 invokeHead 方法读取头数据
     */
    @Test
    public void headerRead() {
        String fileName = TestFileUtil.getPath() + "read" + File.separator + "demo.xlsx";
        EasyExcel.read(fileName, DemoData.class, new DemoHeadDataListener()).sheet().doRead();
    }
    
    /**
     * 额外信息（批注、超链接、合并单元格信息读取）
     * 由于是流式读取，没法在读取到单元格数据的时候直接读取到额外信息，所以只能最后通知哪些单元格有哪些额外信息
     */
    @Test
    public void extraRead() {
        String fileName = TestFileUtil.getPath() + "read" + File.separator + "extra.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet
        EasyExcel.read(fileName, DemoExtraData.class, new DemoExtraListener())
                // 需要读取批注 默认不读取
                .extraRead(CellExtraTypeEnum.COMMENT)
                // 需要读取超链接 默认不读取
                .extraRead(CellExtraTypeEnum.HYPERLINK)
                // 需要读取合并单元格信息 默认不读取
                .extraRead(CellExtraTypeEnum.MERGE)
                .sheet()
                .doRead();
    }
    
    /**
     * 读取公式和单元格类型
     */
    @Test
    public void cellDataRead() {
        String fileName = TestFileUtil.getPath() + "read" + File.separator + "cellDataDemo.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet
        EasyExcel.read(fileName, CellDataReadDemoData.class, new CellDataDemoHeadDataListener())
                .sheet()
                .doRead();
    }
    
    /**
     * 数据转换等异常处理
     */
    @Test
    public void exceptionRead() {
        String fileName = TestFileUtil.getPath() + "read" + File.separator + "demo.xlsx";
        EasyExcel.read(fileName, ExceptionDemoData.class, new DemoExceptionListener())
                .sheet()
                .doRead();
    }
    
    /**
     * 不创建对象的读，使用 Map 接收数据
     */
    @Test
    public void noModelRead() {
        String fileName = TestFileUtil.getPath() + "read" + File.separator + "demo.xlsx";
        EasyExcel.read(fileName, new NoModelDataListener()).sheet().doRead();
    }
    
    /**
     * 同步的返回结果
     * 不推荐使用，因为会把数据放到内存里面
     */
    @Test
    public void synchronousRead() {
        String fileName = TestFileUtil.getPath() + "read" + File.separator + "demo.xlsx";
        List<DemoData> list = EasyExcel.read(fileName)
                .head(DemoData.class)
                .sheet()
                .doReadSync();
        for (DemoData data : list) {
            log.info("读取到数据:{}", JSON.toJSONString(data));
        }
        
        // 这里 也可以不指定class，返回一个list，然后读取第一个sheet 同步读取会自动finish
        List<Map<Integer, String>> listMap = EasyExcel.read(fileName)
                .sheet()
                .doReadSync();
        for (Map<Integer, String> data : listMap) {
            // 返回每条数据的键值对 表示所在的列 和所在列的值
            log.info("读取到数据:{}", JSON.toJSONString(data));
        }
    }
    
}
