package com.wymm.easyexcelsample._9image;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelWriter;
import lombok.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.junit.platform.commons.util.StringUtils;

import java.io.FileOutputStream;
import java.util.*;
import java.util.function.Consumer;

public class CustomExcelPicUtil {
    
    /**
     * @description 读取Excel中的图片 返回sheet下标和对应图片的集合
     * @author OnCloud9
     * @date 2024/3/20 13:39
     * @params ExcelReader excelReader
     * @return Map<Integer, List<ExcelPic>>
     */
    public static Map<Integer, List<ExcelPic>> getExcelPic(ExcelReader excelReader) {
        Workbook workbook = excelReader.getWorkbook();
        if (Objects.isNull(workbook)) return null;
        boolean isXlsx = workbook instanceof XSSFWorkbook;
        boolean isXls = workbook instanceof HSSFWorkbook;
        if (isXlsx) return getExcelPicByXssfType((XSSFWorkbook) workbook, null);
        else if (isXls) return getExcelPicByHssfType((HSSFWorkbook) workbook, null);
        return null;
    }
    
    /**
     * @description
     * @author OnCloud9
     * @date 2024/3/20 14:12
     * @params
     * @return
     */
    public static Map<Integer, List<ExcelPic>> getExcelPic(Workbook workbook, Consumer<ExcelPic> consumer) {
        if (Objects.isNull(workbook)) return null;
        boolean isXSSF = workbook instanceof XSSFWorkbook;
        boolean isHSSF = workbook instanceof HSSFWorkbook;
        if (isXSSF) return getExcelPicByXssfType((XSSFWorkbook) workbook, consumer);
        else if (isHSSF) return getExcelPicByHssfType((HSSFWorkbook) workbook, consumer);
        return null;
    }
    
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static final class ExcelPic {
        private Integer sheetIdx;
        private String suffix;
        private String mimeType;
        private byte[] data;
        private Integer rowIdx;
        private Integer colIdx;
    }
    
    /**
     * @description XSSF工作簿读取图片
     * @author OnCloud9
     * @date 2024/3/20 13:57
     * @params
     * @return
     */
    private static Map<Integer, List<ExcelPic>> getExcelPicByXssfType(XSSFWorkbook xssfWorkbook, Consumer<ExcelPic> excelPicConsumer) {
        int numberOfSheets = xssfWorkbook.getNumberOfSheets();
        Map<Integer, List<ExcelPic>> picMap = new HashMap<>();
        
        for (int xssfSheetIdx = 0; xssfSheetIdx < numberOfSheets; xssfSheetIdx++) {
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(xssfSheetIdx);
            XSSFDrawing drawingPatriarch = xssfSheet.getDrawingPatriarch();
            if (Objects.isNull(drawingPatriarch)) {
                picMap.put(xssfSheetIdx, Collections.emptyList());
                continue;
            }
            List<XSSFShape> shapes = drawingPatriarch.getShapes();
            List<ExcelPic> excelPicList = new ArrayList<>(shapes.size());
            for (XSSFShape xssfShape : shapes) {
                XSSFPicture xssfPicture = (XSSFPicture) xssfShape;
                XSSFClientAnchor clientAnchor = xssfPicture.getClientAnchor();
                XSSFPictureData xssfPictureData = xssfPicture.getPictureData();
                
                String fileExtension = xssfPictureData.suggestFileExtension();
                byte[] data = xssfPictureData.getData();
                String mimeType = xssfPictureData.getMimeType();
                
                short col2 = clientAnchor.getCol2();
                int row2 = clientAnchor.getRow2();
                ExcelPic build = ExcelPic.builder()
                        .sheetIdx(xssfSheetIdx)
                        .suffix(fileExtension)
                        .mimeType(mimeType)
                        .data(data)
                        .rowIdx(row2)
                        .colIdx((int) col2)
                        .build();
                if (Objects.nonNull(excelPicConsumer)) excelPicConsumer.accept(build);
                excelPicList.add(build);
            }
            picMap.put(xssfSheetIdx, excelPicList);
        }
        return picMap;
    }
    
    /**
     * @description HSSF工作簿读取图片
     * @author OnCloud9
     * @date 2024/3/20 13:58
     * @params
     * @return
     */
    private static Map<Integer, List<ExcelPic>> getExcelPicByHssfType(HSSFWorkbook hssfWorkbook, Consumer<ExcelPic> excelPicConsumer) {
        int numberOfSheets = hssfWorkbook.getNumberOfSheets();
        Map<Integer, List<ExcelPic>> picMap = new HashMap<>();
        
        for (int hssfSheetIdx = 0; hssfSheetIdx < numberOfSheets; hssfSheetIdx++) {
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(hssfSheetIdx);
            HSSFPatriarch drawingPatriarch = hssfSheet.getDrawingPatriarch();
            if (Objects.isNull(drawingPatriarch)) {
                picMap.put(hssfSheetIdx, Collections.emptyList());
                continue;
            }
            List<HSSFShape> hssfShapeList = drawingPatriarch.getChildren();
            List<ExcelPic> excelPicList = new ArrayList<>(hssfShapeList.size());
            for (HSSFShape hssfShape : hssfShapeList) {
                HSSFPicture hssfPicture = (HSSFPicture) hssfShape;
                HSSFClientAnchor clientAnchor = hssfPicture.getClientAnchor();
                HSSFPictureData hssfPictureData = hssfPicture.getPictureData();
                
                String fileExtension = hssfPictureData.suggestFileExtension();
                byte[] data = hssfPictureData.getData();
                String mimeType = hssfPictureData.getMimeType();
                
                short col2 = clientAnchor.getCol2();
                int row2 = clientAnchor.getRow2();
                
                ExcelPic build = ExcelPic.builder()
                        .sheetIdx(hssfSheetIdx)
                        .suffix(fileExtension)
                        .mimeType(mimeType)
                        .data(data)
                        .rowIdx(row2)
                        .colIdx((int) col2)
                        .build();
                
                if (Objects.nonNull(excelPicConsumer)) excelPicConsumer.accept(build);
                excelPicList.add(build);
            }
            picMap.put(hssfSheetIdx, excelPicList);
        }
        return picMap;
    }
    
    /**
     * @description 根据文件路径和图片字节输出
     * @author OnCloud9
     * @date 2024/3/20 13:56
     * @params
     * @return
     */
    @SneakyThrows
    public static void writePicByteTo(byte[] picBytes, String outPutPath) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(outPutPath);
            fos.write(picBytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (Objects.nonNull(fos)) fos.close();
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * @description 将图片写入到Excel中
     * @author OnCloud9
     * @date 2024/3/20 16:40
     * @params
     * @return
     */
    @SneakyThrows
    public static void writePicToExcel(ExcelWriter excelWriter, List<ExcelPicWrite> excelPicWriteList) {
        Workbook workbook = excelWriter.getWorkbook();
        CreationHelper creationHelper = workbook.getCreationHelper();
        Sheet sheet;
        for (ExcelPicWrite picWrite : excelPicWriteList) {
            /* 判断是否提供sheet名称，不提供默认写入到第一个，遍历时不一定存在，所以要判断，不存在时创建出来 */
            String sheetName = picWrite.getSheetName();
            if (StringUtils.isBlank(sheetName)) sheet = workbook.getSheetAt(0);
            else {
                sheet = workbook.getSheet(sheetName);
                if (Objects.isNull(sheet)) sheet = workbook.createSheet(sheetName);
            }
            
            /* 获取图片内容，写入工作簿中 */
            byte[] picContent = picWrite.getData();
            Integer picType = picWrite.getPicType();
            int picIdx = workbook.addPicture(picContent, picType);
            
            /* 设置图片存放的位置 */
            Integer rowIdx = picWrite.getRowIdx();
            Integer colIdx = picWrite.getColIdx();
            ClientAnchor clientAnchor = creationHelper.createClientAnchor();
            clientAnchor.setRow1(rowIdx);
            clientAnchor.setRow2(rowIdx + 1);
            clientAnchor.setCol1(colIdx);
            clientAnchor.setCol2(colIdx + 1);
            
            /* 图片绘制渲染 */
            Drawing<?> drawingPatriarch = sheet.createDrawingPatriarch();
            Picture picture = drawingPatriarch.createPicture(clientAnchor, picIdx);
        }
    }
    
    /**
     * @description 图片类型翻译
     * @author OnCloud9
     * @date 2024/3/20 17:46
     * @params
     * @return
     */
    public static int picTypeTranslate(String picType) {
        if (StringUtils.isBlank(picType)) return Workbook.PICTURE_TYPE_JPEG;
        switch (picType) {
            case "png":
            case "PNG":
                return Workbook.PICTURE_TYPE_PNG;
            case "bmp":
            case "BMP":
                return Workbook.PICTURE_TYPE_DIB;
            default:
            case "jpeg":
            case "JPEG":
            case "jpg":
            case "JPG":
                return Workbook.PICTURE_TYPE_JPEG;
        }
    }
    
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static final class ExcelPicWrite {
        private String sheetName;  /* 导出创建时依照sheetName名称为标识，如果不写则默认放到第一个sheet页 */
        private byte[] data;  /* 图片字节数组 这里不关心图片输入的方式 */
        private Integer rowIdx; /* 定位的单元格行 */
        private Integer colIdx; /* 定位的单元格列 */
        private Integer picType; /* 图片类型 见上面翻译方法 */
    }
}