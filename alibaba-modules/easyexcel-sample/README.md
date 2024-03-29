# EasyExcel
## 写入
- write() - 写文件
- excludeColumnFiledNames() - 传入忽略字段
- includeColumnFiledNames() - 传入仅要导出字段
- @ExcelProperty - 不连续的index会有空列 和 不连续的order不会有空列
- HyperlinkData - 超链接
- CommentData - 备注
- FormulaData - 公式
- WriteCellStyle - 指定单个单元格的样式
- RichTextStringData - 单个单元格多种样式
- @HeadRowHeight - 自定义标题行高
- @ContentRowHeight - 自定义内容行高
- @ColumnWidth - 自定义列宽
- @HeadStyle - 自定义标题头样式
- @HeadFontStyle - 自定义标题头字体样式
- @ContentFontStyle - 自定义内容样式
- head() - 设置动态表头
- registerWriteHandler() - 注册拦截器
- LongestMatchColumnWidthStyleStrategy - 自动列宽，不是很准确，但是可以作为默认使用
## 读取
- PageReadListener - 分页处理批处理数据
- @ExcelProperty(index = 0) - 设置字段和excel列对应关系
- ReadSheet 和 ExcelReader#read() - 使用 ReadSheet 和 ExcelReader#read() 读取多个 sheet
- @DateTimeFormat - 格式化日期
- @NumberFormat - 格式化数字
- EasyExcel#headRowNumber() - 设置标题行数
- invokeHead() - 读取表头数据
- extra() - 读取额外信息（批注、超链接、合并单元格）
- CellData - 读取更加详细的单元格信息，例如公式
- onException - 处理异常，该方法抛出异常的
- 使用 Map 存储数据，可不创建对象
- doReadSync() - 同步读取，即读取所有数据返回