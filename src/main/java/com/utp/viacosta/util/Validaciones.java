package com.utp.viacosta.util;

public class Validaciones {
    // 1. Validar que todos los campos requeridos tengan información
    public static boolean esCampoRequerido(String valor) {
        return valor != null && !valor.trim().isEmpty();
    }

    // 2. Validar que se restrinja la captura según tipo de dato
    public static boolean esNumerico(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return false;
        }
        return valor.matches("\\d+");
    }

    public static boolean esTexto(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return false;
        }
        return valor.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+");
    }

    public static boolean esDecimal(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return false;
        }
        return valor.matches("\\d+(\\.\\d+)?");
    }

    // 3. Validar el acceso y los permisos a la pantalla por tipo de usuario
    public static boolean validarPermisos(String tipoUsuario, String pantalla) {
        if ("ADMIN".equals(tipoUsuario)) {
            return true;
        } else return "USUARIO".equals(tipoUsuario) && !pantalla.equals("Configuración");
    }

    // 4. Validar longitud de los campos textos y especiales
    public static boolean validarLongitud(String valor, int maxLongitud) {
        return valor != null && valor.length() <= maxLongitud;
    }

    // 5. Validar formato de captura en los campos especiales
    public static boolean validarFormatoEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    public static boolean validarFormatoTelefono(String telefono) {
        if (telefono == null || telefono.trim().isEmpty()) {
            return false;
        }
        return telefono.matches("\\+?[0-9]{7,15}");
    }

    // 6. Validar los rangos de valores aceptables en un campo
    public static boolean estaEnRango(int valor, int min, int max) {
        return valor >= min && valor <= max;
    }

    public static boolean estaEnRango(double valor, double min, double max) {
        return valor >= min && valor <= max;
    }
}
