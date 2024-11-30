package com.utp.viacosta.util;

import com.utp.viacosta.modelo.EmpleadoModelo;

public class AuthLogin {
    private static EmpleadoModelo empleadoActivo;

    public static void setEmpleadoActivo(EmpleadoModelo empleado) {
        empleadoActivo = empleado;
    }

    public static EmpleadoModelo getEmpleadoActivo() {
        return empleadoActivo;
    }

    public static void logout() {
        empleadoActivo = null;
    }
}
