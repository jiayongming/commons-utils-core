package com.commons.files;

import com.google.common.collect.Maps;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;

@Log4j2
public class ReadExcel {

    @Test
    public void main() {
        try {

            Map<String, String> banksMap = Maps.newLinkedHashMap();

            InputStream inp = new FileInputStream("D:\\eclipse20150831\\workspace\\excelXML\\resources\\user_address-0902.xlsx");
            Workbook wb = WorkbookFactory.create(inp);
            int numberOfSheets = wb.getNumberOfSheets();
            log.info("工作表个数为：" + numberOfSheets);
            Sheet sheet = wb.getSheetAt(0); // 第一个工作表
            // 获得总列数
            int coloumNum = sheet.getRow(0).getPhysicalNumberOfCells();
            log.info("获得总列数" + coloumNum);
            // 获得总行数
            int rowNum = sheet.getLastRowNum();
            log.info("获得总行数" + rowNum);

			/* Cell cell54 = sheet.getRow(31).getCell(1); */// 获得第32行的第2个单元格
            for (int i = 1; i <= rowNum; i++) {
                Cell cell1 = sheet.getRow(i).getCell(0); // 第一列单元格的值
                Cell cell3 = sheet.getRow(i).getCell(3); // 第三列单元格的值
                log.info(i + "===" + cell1.getStringCellValue().replace("\"", "") + "===" + cell3.getStringCellValue().replace("\"", ""));
                banksMap.put(cell1.getStringCellValue().replace("\"", ""), cell3.getStringCellValue().replace("\"", ""));
            }

            writeXML(banksMap);
            log.info("生成xml成功");

        } catch (Exception e) {
            log.error("test error:",e);
        }
    }

    public static void writeXML(Map<String, String> banksInfoMap) throws Exception {


        log.info(banksInfoMap.size());
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();

        // 构建XML中的节点
        Element bean = doc.createElement("bean");
        bean.setAttribute("id", "banksInfoMap");
        bean.setAttribute("class", "java.util.HashMap");
        Element constructor_arg = doc.createElement("constructor-arg");
        Element map = doc.createElement("map");
        map.setAttribute("key-type", "java.lang.String");
        map.setAttribute("value-type", "java.lang.String");

        Set<String> keySet = banksInfoMap.keySet();
        int i = 0;
        for (String key : keySet) {
            Object value = banksInfoMap.get(key);
            Element entry = doc.createElement("entry");
            entry.setAttribute("key", StringUtils.trimToEmpty(key));
            entry.setAttribute("value", StringUtils.trimToEmpty(value.toString()));
            map.appendChild(entry);
            i++;
        }
        log.info(i);
        // 按顺序添加各个节点
        doc.appendChild(bean);
        bean.appendChild(constructor_arg);
        constructor_arg.appendChild(map);

        Transformer t = TransformerFactory.newInstance().newTransformer();
        // 设置换行和缩进
        t.setOutputProperty(OutputKeys.INDENT, "yes");
        t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        t.setOutputProperty(OutputKeys.METHOD, "xml");
        t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        t.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(new File("D:\\eclipse20150831\\workspace\\excelXML\\resources\\banksInfo.xml"))));

    }

    public static void main(String[] args) {

    }
}
