package com.utp.viacosta.util;

import com.utp.viacosta.modelo.*;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.Leading;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.layout.properties.TextAlignment;
import lombok.RequiredArgsConstructor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@RequiredArgsConstructor
public class GenerarBoleto {

    public static void generarBoleto(ClienteModelo cliente, EmpleadoModelo empleado, ComprobanteModelo comprobante, AsignacionBusRutaModelo asignacionBusRuta, AsientoModelo asiento, DetalleBoletaModelo detalleBoleta) {
        try {
            EmpresaModelo empresa = empleado.getSede().getEmpresa();
            SedeModelo sede = empleado.getSede();
            RutaModelo ruta = asignacionBusRuta.getRutaAsignada();
            String filePath = comprobante.getNumeroComprobante()+".pdf";

            // Crear el documento PDF
            PdfWriter writer = new PdfWriter(filePath);
            PdfDocument pdf = new PdfDocument(writer);
            PageSize pageSize = new PageSize(283.5f, PageSize.A4.getHeight());
            pdf.setDefaultPageSize(pageSize);
            Document document = new Document(pdf);
            document.setMargins(20, 14.175f, 20, 14.175f);
            document.setProperty(Property.LEADING, new Leading(Leading.MULTIPLIED, 1.5f));

            // ENCABEZADO
            Paragraph header = new Paragraph(empresa.getRazonSocial())
                    .setFontSize(20)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(0);
            document.add(header);

            Paragraph ruc = new Paragraph("RUC: "+ empresa.getRuc())
                    .setFontSize(10)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(0);
            document.add(ruc);

            Paragraph terminalInfo = new Paragraph(empresa.getCiudad() + ", " + empresa.getDepartamento() + ", " + empresa.getPais())
                    .setFontSize(10)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(0);
            document.add(terminalInfo);

            Paragraph telefonoInfo = new Paragraph()
                    .add(new Text("Teléfono: ").setBold().setFontSize(10))
                    .add(new Text(empresa.getTelefono()).setFontSize(10))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(0);
            document.add(telefonoInfo);

            document.add(new Paragraph("---------------------------------------------------------------")
                    .setMarginBottom(0));
            Paragraph boletaInfo = new Paragraph("BOLETA DE VENTA ELECTRÓNICA "+ comprobante.getNumeroComprobante())
                    .setFontSize(10)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(0);
            document.add(boletaInfo);
            document.add(new Paragraph("---------------------------------------------------------------")
                    .setMarginBottom(0));

            Paragraph datosCliente = new Paragraph()
                    .add(new Text("DNI: ").setBold().setFontSize(10))
                    .add(new Text(cliente.getDni()+"\n").setFontSize(10))
                    .add(new Text("NOMBRE: ").setBold().setFontSize(10))
                    .add(new Text(cliente.getNombre()+" "+cliente.getApellido()).setFontSize(10))
                    .setMarginBottom(0);
            document.add(datosCliente);

            document.add(new Paragraph("---------------------------------------------------------------")
                    .setMarginBottom(0));
            Paragraph agenciaInfo = new Paragraph()
                    .add(new Text("Agencia: ").setBold().setFontSize(10))
                    .add(new Text(sede.getNombreSedes()+"\n").setFontSize(10))
                    .add(new Text("Dirección: ").setBold().setFontSize(10))
                    .add(new Text(sede.getDireccion()+"\n").setFontSize(10))
                    .add(new Text(sede.getDepartamento()+" "+sede.getCiudad()).setFontSize(10))
                    .setMarginBottom(0);
            document.add(agenciaInfo);

            document.add(new Paragraph("---------------------------------------------------------------")
                    .setMarginBottom(0));
            document.add(new Paragraph("DETALLE DE VENTA")
                    .setFontSize(10)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(0));
            document.add(new Paragraph("---------------------------------------------------------------")
                    .setMarginBottom(0));

            Paragraph detalleBoletaInfo = new Paragraph()
                    .add(new Text("1 PASAJERO\n").setBold().setFontSize(10))
                    .add(new Text("SERVICIO DE TRANSPORTE EN LA RUTA: "+ruta.getOrigen()+"-"+ruta.getDestino()+"\n").setBold().setFontSize(10))
                    .add(new Text("EMBARQUE: ").setBold().setFontSize(10))
                    .add(new Text(sede.getDireccion()+"\n").setFontSize(10))
                    .add(new Text("ASIENTO: ").setBold().setFontSize(10))
                    .add(new Text(asiento.getNumeroAsiento()+"\n").setFontSize(10))
                    .add(new Text("PASAJERO: ").setBold().setFontSize(10))
                    .add(new Text(cliente.getNombre()+" "+cliente.getApellido()+"\n").setFontSize(10))
                    .add(new Text("DNI: ").setBold().setFontSize(10))
                    .add(new Text(cliente.getDni()+"\n").setFontSize(10))
                    .add(new Text("FECHA VIAJE: ").setBold().setFontSize(10))
                    .add(new Text(detalleBoleta.getFechaViaje()+"\n").setFontSize(10))
                    .add(new Text("HORA VIAJE: ").setBold().setFontSize(10))
                    .add(new Text(detalleBoleta.getHoraViaje()+"\n").setFontSize(10))
                    .setMarginBottom(0);
            document.add(detalleBoletaInfo);

            document.add(new Paragraph("---------------------------------------------------------------")
                    .setMarginBottom(0));
            Paragraph operacionInfo = new Paragraph()
                    .add(new Text("OPERACIÓN NO GRAVADA: ").setBold().setFontSize(10))
                    .add(new Text(detalleBoleta.getPrecioTotal()+"\n").setFontSize(10))
                    .add(new Text("IGV (18%): ").setBold().setFontSize(10))
                    .add(new Text("0.00\n").setFontSize(10))
                    .add(new Text("TOTAL (S/): ").setBold().setFontSize(10))
                    .add(new Text(detalleBoleta.getPrecioTotal()+"\n").setFontSize(10))
                    .add(new Text("TIPO PAGO: ").setBold().setFontSize(10))
                    .add(new Text(detalleBoleta.getMetodoPago()+"\n").setFontSize(10))
                    .setMarginBottom(0);
            document.add(operacionInfo);
            document.add(new Paragraph("---------------------------------------------------------------")
                    .setMarginBottom(0));

            LocalDateTime time = LocalDateTime.now();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

            Paragraph info = new Paragraph()
                    .add(new Text("FECHA EMISIÓN: " + time.format(dateFormatter) + "\n").setBold().setFontSize(10))
                    .add(new Text("VENDEDOR: "+empleado.getNombre().toUpperCase()+" "+empleado.getApellido().toUpperCase()+"\n").setFontSize(10))
                    .add(new Text("HORA IMPRESIÓN " + time.format(timeFormatter) + "\n").setFontSize(10))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(0);
            document.add(info);

            document.close();
            System.out.println("Ticket generado exitosamente: " + filePath);
        } catch (FileNotFoundException e) {
            System.out.println("Error al crear el archivo PDF: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo PDF: " + e.getMessage());
        }
    }
}
