package com.example.nmax.controller;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.PriorityQueue;

@RestController
@RequestMapping("/api")
public class NumberController {

  @PostMapping("/findNthMax")
  public int findNthMax(@RequestParam String filePath, @RequestParam int n) throws IOException {
    FileInputStream file = new FileInputStream(new File(filePath));
    Workbook workbook = new XSSFWorkbook(file);
    Sheet sheet = workbook.getSheetAt(0);

    PriorityQueue<Integer> minHeap = new PriorityQueue<>();

    for (Row row : sheet) {
      for (Cell cell : row) {
        if (cell.getCellType() == CellType.NUMERIC) {
          int num = (int) cell.getNumericCellValue();
          minHeap.add(num);
          if (minHeap.size() > n) {
            minHeap.poll();
          }
        }
      }
    }

    workbook.close();
    file.close();

    return minHeap.peek();
  }
}