package com.example.demo.car.helper;


import com.example.demo.car.entity.Car;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class CSVHelper {

    public static ByteArrayInputStream tutorialsToCSV(List<Car> cars) {
        final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);
        //TODO megcsinálni a kurva getManufacturert() jóra
        //TODO elintézni hogy a kiratás rendesen történjen az excelbe
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
            for (Car car : cars) {
                List<Object> data = Arrays.asList(
                        String.valueOf(car.getId()),
                        String.valueOf(car.getName()) ,
                        String.valueOf(car.getManufacturer()),
                        String.valueOf(car.getDoors()),
                        String.valueOf( car.getProdYear())
                );

                csvPrinter.printRecord(data);
            }

            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
        }

        /**********************************************************************************************************/
    }

    private static void createHeader(XSSFSheet sheet, XSSFWorkbook workbook) {
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Type");
        headerRow.createCell(2).setCellValue("Manufacturer");
        headerRow.createCell(2).setCellValue("Doors");
        headerRow.createCell(2).setCellValue("Prod year");
    }
}
