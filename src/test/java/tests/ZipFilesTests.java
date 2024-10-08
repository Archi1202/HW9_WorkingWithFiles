package tests;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ZipFilesTests {
    private ClassLoader cl = ZipFilesTests.class.getClassLoader();
    PDF pdfFile = null;
    XLS excelFile = null;
    CSVReader csvFile = null;

    @Test
    @DisplayName("Check that in PDFFiles.zip has relevant file to the Washington University and verify data in it")
    void pdfVerificationDataTest() throws Exception {
        try (ZipInputStream zipFile = new ZipInputStream(
                cl.getResourceAsStream("PDFFiles.zip")
        )) {
            ZipEntry entry;

            while ((entry = zipFile.getNextEntry()) != null) {
                if (entry.getName().contains("Eurasian")) {
                    pdfFile = new PDF(zipFile);
                    break;
                }
            }

            assertThat(pdfFile.text).contains("edu.enu.kz");
            assertThat(pdfFile.numberOfPages).isEqualTo(40);
        }
    }
    @Test
    @DisplayName("Verify that in XLSFile.zip has relevant xls file and check data in it")
    void xlsDataVerificationTest() throws Exception {
        try (ZipInputStream zipFile = new ZipInputStream(
                cl.getResourceAsStream("XLSFile.zip")
        )) {
            ZipEntry entry;

            while ((entry = zipFile.getNextEntry()) != null) {
                if (entry.getName().contains(".xls")) {
                    excelFile = new XLS(zipFile);
                    break;
                }
            }
            String firstSheetName = excelFile.excel.getSheetName(0);
            assertThat(firstSheetName).isEqualTo("Example Test");
            String urlCell = excelFile.excel.getSheetAt(2).getRow(9).getCell(0).getStringCellValue();
            assertThat(urlCell).contains("www.cmu.edu");
        }
    }
    @Test
    @DisplayName("Verify customer file data in CSVFile.zip")
    void csvFileVerificationTest() throws Exception {
        try (ZipInputStream zipFile = new ZipInputStream(
                cl.getResourceAsStream("CSVFile.zip")
        )) {
            ZipEntry entry;

            while ((entry = zipFile.getNextEntry()) != null) {
                if (entry.getName().contains(".csv")) {
                    csvFile = new CSVReader(new InputStreamReader(zipFile));
                    break;
                }
            }
            List<String[]> csvCustomer = csvFile.readAll();
            assertThat(csvCustomer.getFirst())
                        .containsExactlyInAnyOrder(
                                "Subscription Date","Website","Index","Customer Id",
                                "Last Name","Company","City","First Name","Country","Phone 1","Phone 2","Email");
            assertThat(csvCustomer.get(3)).contains("http://www.lawrence.com/","+1-539-402-0259");
            assertThat(csvCustomer.get(49)).hasSize(12);
        }
    }
}
