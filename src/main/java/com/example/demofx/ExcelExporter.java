package com.example.demofx;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelExporter {
 public static void exportToExcel(TableView<Book> table_view, String filepath){
     try (Workbook workbook = new XSSFWorkbook()){
         Sheet sheet = workbook.createSheet("Data");
         ObservableList<TableColumn<Book, ?>> columns = table_view.getColumns();
         ObservableList<Book> data = table_view.getItems();

         // Creating header row
         Row headerRow = sheet.createRow(0);
         for(int i=0; i < columns.size(); i++){
             TableColumn<Book, ?> column = columns.get(i);
             Cell cell = headerRow.createCell(i);
             cell.setCellValue(column.getText());
         }

         // Creating data rows
         for(int i =0; i< data.size(); i++) {
             Book book = data.get(i);
             Row row = sheet.createRow(i + 1);
             for (int j = 0; j < columns.size(); j++) {
                 TableColumn<Book, ?> column = columns.get(j);
                 Cell cell = row.createCell(j);
                 cell.setCellValue(column.getCellObservableValue(book).getValue().toString());
             }
         }

             // Auto-size columns
             for(int i = 0; i < columns.size(); i++){
                 sheet.autoSizeColumn(i);
             }

             // Saving workbook to a file
             try(FileOutputStream fileOut = new FileOutputStream(filepath)){
                 workbook.write(fileOut);
             }
     } catch (IOException e){
         e.printStackTrace();
     }
 }
}
