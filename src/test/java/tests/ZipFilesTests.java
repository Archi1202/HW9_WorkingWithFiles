package tests;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ZipFilesTests {
    private ClassLoader cl = ZipFilesTests.class.getClassLoader();
    PDF pdfFile = null;
    XLS excelFile = null;
    CSVReader csvFile = null;

    @Test
    @DisplayName("Check that in PDFFiles.zip we ca find file related to the Washington University and verify data in it")
    void pdfVerificationDataTest() throws Exception {
        try (ZipInputStream zipFile = new ZipInputStream(
                cl.getResourceAsStream("PDFFiles.zip")
        )) {
            ZipEntry entry;

            while ((entry = zipFile.getNextEntry()) != null) {
                if (entry.getName().contains("Washington")) {
                    pdfFile = new PDF(zipFile);
                    break;
                }
            }

            assertThat(pdfFile.text).contains("admissions.wustl.edu");
            assertThat(pdfFile.numberOfPages).isEqualTo(7);
        }
    }
}
