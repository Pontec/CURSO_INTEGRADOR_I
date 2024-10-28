package com.utp.viacosta.util;

import com.utp.viacosta.agregates.dto.DetalleBoletaDTO;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.*;

import javax.swing.filechooser.FileSystemView;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FxmlReportes {

    public void generarReportes(List<DetalleBoletaDTO> listaReportes){

        List<DetalleBoletaDTO> ventas = listaReportes;
        System.out.println(ventas);
        Field[] campos = DetalleBoletaDTO.class.getDeclaredFields();

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Reporte de Ventas");

        XSSFFont fuenteCabecera = GeneradorFuentes.builder()
                .nombreFuente("Arial")
                .tamanoFuente((short) 10)
                .conNegrita(true)
                .colorPersonalizado("ffffff")
                .build().build(workbook);


        XSSFFont fuenteContenido = GeneradorFuentes.builder()
                .nombreFuente("Arial")
                .tamanoFuente((short) 10)
                .build().build(workbook);

        XSSFCellStyle estiloCabecera = GeneradorEstilos.builder()
                .colorPersonalizado("0062c4")
                .tipoPatron(FillPatternType.SOLID_FOREGROUND)
                .alineacionHorizontal(HorizontalAlignment.CENTER)
                .bordeArriba(BorderStyle.THIN)
                .bordeAbajo(BorderStyle.THIN)
                .bordeDerecho(BorderStyle.THIN)
                .bordeIzquierdo(BorderStyle.THIN)
                .fuente(fuenteCabecera)
                .build().build(workbook);

        XSSFCellStyle estiloContenido = GeneradorEstilos.builder()
                .colorPersonalizado("ffffff")
                .tipoPatron(FillPatternType.SOLID_FOREGROUND)
                .alineacionHorizontal(HorizontalAlignment.CENTER)
                .bordeArriba(BorderStyle.THIN)
                .bordeAbajo(BorderStyle.THIN)
                .bordeDerecho(BorderStyle.THIN)
                .bordeIzquierdo(BorderStyle.THIN)
                .fuente(fuenteContenido)
                .build().build(workbook);

        XSSFCellStyle estilosFecha = GeneradorEstilos.builder()
                .colorPersonalizado("ffffff")
                .tipoPatron(FillPatternType.SOLID_FOREGROUND)
                .alineacionHorizontal(HorizontalAlignment.CENTER)
                .bordeArriba(BorderStyle.THIN)
                .bordeAbajo(BorderStyle.THIN)
                .bordeDerecho(BorderStyle.THIN)
                .bordeIzquierdo(BorderStyle.THIN)
                .fuente(fuenteContenido)
                .formato("dd/MM/yyyy")
                .build().build(workbook);

        XSSFRow fila = null;
        XSSFCell celda = null;

        for (int i = 0; i < ventas.size() ; i++) {
            if (i == 0){
                fila = sheet.createRow(i);
                for (int j = 0; j < campos.length; j++) {
                    celda = fila.createCell(j);
                    celda.setCellValue(campos[j].getName().toUpperCase());
                    celda.setCellStyle(estiloCabecera);
                }
            }
            DetalleBoletaDTO venta = ventas.get(i);
            List<Object> datos = venta.obtenerDatos();

            fila = sheet.createRow(i+1);
            for (int a = 0; a < datos.size() ; a++) {
                celda = fila.createCell(a);
                if (datos.get(a) instanceof  String){
                    celda.setCellValue((String)datos.get(a));
                    celda.setCellStyle(estiloContenido);
                }
                if (datos.get(a) instanceof  Integer){
                    celda.setCellValue((Integer)datos.get(a));
                    celda.setCellStyle(estiloContenido);
                }
                if (datos.get(a) instanceof  Double){
                    celda.setCellValue((Double)datos.get(a));
                    celda.setCellStyle(estiloContenido);
                }
                if (datos.get(a) instanceof LocalDate){
                    celda.setCellValue((LocalDate)datos.get(a));
                    celda.setCellStyle(estilosFecha);
                }
                if (datos.get(a) instanceof LocalTime){
                    celda.setCellValue(datos.get(a).toString());
                    celda.setCellStyle(estiloContenido);
                }

                sheet.autoSizeColumn(a);
            }
        }
        
        try {
            String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String path = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
            String ruta = path + "\\ReporteVentas" + time + ".xlsx";
            OutputStream output = new FileOutputStream(ruta);
            workbook.write(output);
            output.close();
            workbook.close();
            System.out.println("Reporte generado en: "+ruta);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al generar el reporte");
        }

    }

}
