package com.utp.viacosta.util;

import lombok.Builder;
import org.apache.commons.codec.binary.Hex;
import org.apache.poi.ss.usermodel.FontUnderline;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@Builder
public class GeneradorFuentes {
    private String nombreFuente;
    private boolean conNegrita;
    private boolean conItalica;
    private short tamanoFuente;
    private short colorDefecto;
    private XSSFColor colorPersonalizado;
    private FontUnderline tipoUnderline;

    public static class GeneradorFuentesBuilder {
        public GeneradorFuentes.GeneradorFuentesBuilder colorPersonalizado(String hexColor) {
            try {
                byte[] rgb = Hex.decodeHex(hexColor);
                this.colorPersonalizado = new XSSFColor(rgb);
            } catch (Exception e) {
                throw new RuntimeException("Error al decodificar el color hexadecimal", e);
            }
            return this;
        }
    }

    public XSSFFont build(XSSFWorkbook libro) {
        XSSFFont fuente = libro.createFont();

        if (nombreFuente != null) {
            fuente.setFontName(nombreFuente);
        }
        if (conNegrita) {
            fuente.setBold(true);
        }
        if (conItalica) {
            fuente.setItalic(true);
        }
        if (tamanoFuente != 0) {
            fuente.setFontHeightInPoints(tamanoFuente);
        }
        if (colorDefecto != 0) {
            fuente.setColor(colorDefecto);
        }
        if (colorPersonalizado != null) {
            fuente.setColor(colorPersonalizado);
        }
        if (tipoUnderline != null) {
            fuente.setUnderline(tipoUnderline);
        }

        return fuente;
    }
}

