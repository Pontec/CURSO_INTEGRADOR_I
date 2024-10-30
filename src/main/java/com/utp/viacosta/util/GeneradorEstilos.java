package com.utp.viacosta.util;

import lombok.Builder;
import org.apache.commons.codec.binary.Hex;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@Builder
public class GeneradorEstilos {
    private short colorDefecto;
    private XSSFColor colorPersonalizado;
    private FillPatternType tipoPatron;
    private XSSFFont fuente;
    private String formato;
    private HorizontalAlignment alineacionHorizontal;
    private VerticalAlignment alineacionVertical;
    private BorderStyle bordeArriba;
    private BorderStyle bordeAbajo;
    private BorderStyle bordeDerecho;
    private BorderStyle bordeIzquierdo;

    public static class GeneradorEstilosBuilder {
        public GeneradorEstilosBuilder colorPersonalizado(String hexColor) {
            try {
                byte[] rgb = Hex.decodeHex(hexColor);
                this.colorPersonalizado = new XSSFColor(rgb);
            } catch (Exception e) {
                throw new RuntimeException("Error al decodificar el color hexadecimal", e);
            }
            return this;
        }
    }

    public XSSFCellStyle build(XSSFWorkbook libro) {
        XSSFCellStyle estilosCelda = libro.createCellStyle();
        if (this.colorDefecto != 0) {
            estilosCelda.setFillForegroundColor(colorDefecto);
        }
        if (this.colorPersonalizado != null) {
            estilosCelda.setFillForegroundColor(colorPersonalizado);
        }
        if (this.tipoPatron != null) {
            estilosCelda.setFillPattern(tipoPatron);
        }
        if (this.fuente != null) {
            estilosCelda.setFont(fuente);
        }
        if (this.formato != null) {
            estilosCelda.setDataFormat(libro.createDataFormat().getFormat(formato));
        }
        if (this.alineacionHorizontal != null) {
            estilosCelda.setAlignment(alineacionHorizontal);
        }
        if (this.alineacionVertical != null) {
            estilosCelda.setVerticalAlignment(alineacionVertical);
        }
        if (this.bordeArriba != null) {
            estilosCelda.setBorderTop(bordeArriba);
        }
        if (this.bordeAbajo != null) {
            estilosCelda.setBorderBottom(bordeAbajo);
        }
        if (this.bordeDerecho != null) {
            estilosCelda.setBorderRight(bordeDerecho);
        }
        if (this.bordeIzquierdo != null) {
            estilosCelda.setBorderLeft(bordeIzquierdo);
        }
        return estilosCelda;
    }
}
