package com.example.demo.car.helper;

import com.example.demo.car.entity.Car;
import com.example.demo.manufacturer.entity.Manufacturer;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class EXCELExporter {

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Car> carList;

    public EXCELExporter(List<Car> carList) {
        this.carList = carList;
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("Cars");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row,0, "ID",style);
        createCell(row, 1,"Type",style);
        createCell(row, 2,"Manufacturer",style);
        createCell(row, 3,"Door",style);
        createCell(row, 4,"Prod Year",style);
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if(value instanceof Long){
            cell.setCellValue((Long) value);
        }else if (value instanceof String){
            cell.setCellValue( (String) value);
        }else if(value instanceof Manufacturer){
            cell.setCellValue(((Manufacturer) value).getName());
        }else if(value instanceof Double){
            cell.setCellValue((Double) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (Car car : carList) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row,0, car.getId(),style);
            createCell(row, 1,car.getName(),style);
            createCell(row, 2,car.getManufacturer(),style);
            createCell(row, 3,car.getDoors(),style);
            createCell(row, 4,car.getProdYear(),style);

        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();
    }
}
