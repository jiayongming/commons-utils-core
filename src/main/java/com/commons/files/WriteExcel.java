package com.commons.files;

import com.google.common.collect.Maps;
import lombok.Cleanup;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

@Slf4j
public class WriteExcel {

	/**
	 * 输出文件的位置
	 */
	@Getter @Setter
	private String filePath = "/Program Files/mysGithub/commons-utils-core/src/main/resources/source.txt" ;

	/**
	 * excel文件的位置
	 */
	@Getter @Setter
	private String excelPath = "/2017工作日历.xls" ;

	/**
	 * 工作表的个数，默认为0
	 */
	@Getter @Setter
	private int sheetOrder = 0 ;

	/**
	 * 输出文件格式，默认UTF-8
	 */
	@Getter @Setter
	private String encode = "UTF-8" ;

	public void mainsssssss() {

		try {
			final InputStream resourceAsStream = WriteExcel.class.getResourceAsStream(excelPath);
			Workbook wb = WorkbookFactory.create(resourceAsStream);
			int numberOfSheets = wb.getNumberOfSheets();
			log.info("工作表个数为:" + numberOfSheets);

			Sheet sheet = wb.getSheetAt(sheetOrder); // 第一个工作表
			// 获得总列数
			int coloumNum = sheet.getRow(0).getPhysicalNumberOfCells();
			log.info("获得总列数:" + coloumNum);

			// 获得总行数
			int rowNum = sheet.getLastRowNum();
			log.info("获得总行数:" + rowNum);

			Map<String, String> bankEncrypt = Maps.newLinkedHashMapWithExpectedSize(400) ;
			for (int i = 1; i <= rowNum ; i++) {
				final String key = sheet.getRow(i).getCell(1).getStringCellValue();
				final String value = sheet.getRow(i).getCell(2).getStringCellValue();
				if (value.equalsIgnoreCase("是")){
					int add = 1 ;

					while(true){
						if (i+add <= rowNum){
							final String stringCellValue = sheet.getRow(i+add).getCell(2).getStringCellValue();
							if (stringCellValue.equalsIgnoreCase("是")){
								bankEncrypt.put(key,sheet.getRow(i+add).getCell(1).getStringCellValue()) ;
								break;
							} else {
								add ++ ;
							}
						} else {
							bankEncrypt.put(key,"-1") ;
							break;
						}
					}

				} else if (value.equalsIgnoreCase("否")){
					int add = 1 ;
					outer : while (true){
						if (i+add <= rowNum){
							final String stringCellValue = sheet.getRow(i+add).getCell(2).getStringCellValue();
							if (stringCellValue.equalsIgnoreCase("是")){
								while(true){
									if (i+add+1 <= rowNum){
										final String stringCellValue1 = sheet.getRow(i+add+1).getCell(2).getStringCellValue();
										if (stringCellValue1.equalsIgnoreCase("是")){
											bankEncrypt.put(key,sheet.getRow(i+add+1).getCell(1).getStringCellValue()) ;
											break outer ;
										} else {
											add ++ ;
										}
									} else {
										bankEncrypt.put(key,"-1") ;
										break outer;
									}

								}
							} else {
								add ++ ;
							}
						} else {
							bankEncrypt.put(key,"-1") ;
							break;
						}
					}
				}
			}

			String content = "<entry key=\"{0}\" value=\"{1}\" />" ;
			Set<Entry<String, String>> entrySet = bankEncrypt.entrySet();

			for (Entry<String, String> entry : entrySet) {
				String replace = StringUtils.replace(content, "{0}", entry.getKey());
				final String result = StringUtils.replace(replace, "{1}", entry.getValue());
				writeNIO(result) ;
			}

		} catch (Exception e) {
			log.error("read excel error:",e);
		}
	}

	@SneakyThrows
	public void writeNIO(String content) {
		@Cleanup FileOutputStream fos = null;
		try {

			fos = new FileOutputStream(new File(filePath));
			FileChannel channel = fos.getChannel();
			ByteBuffer src = Charset.forName(encode).encode(content);

			// 字节缓冲的容量和limit会随着数据长度变化，不是固定不变的
			log.info("初始化容量和limit：" + src.capacity() + "," + src.limit());
			int length = 0;

			while ((length = channel.write(src)) != 0) {
                /*
                 * 注意，这里不需要clear，将缓冲中的数据写入到通道中后 第二次接着上一次的顺序往下读
                 */
				log.info("写入长度:" + length);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public static void main(String[] args) {
		final WriteExcel writeExcel = new WriteExcel();
		writeExcel.mainsssssss();

	}
}