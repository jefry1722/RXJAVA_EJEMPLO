package org.example;

import io.reactivex.Observable;

import java.util.Arrays;
import java.util.List;

class Trabajador {
    private String nombre;
    private int horasTrabajadas;
    private int valorHora;

    public Trabajador(String nombre, int horasTrabajadas, int valorHora) {
        this.nombre = nombre;
        this.horasTrabajadas = horasTrabajadas;
        this.valorHora = valorHora;
    }

    public int getHorasTrabajadas() {
        return horasTrabajadas;
    }

    public String getNombre() {
        return nombre;
    }

    public int getValorHora() {
        return valorHora;
    }
}

public class Main {
    public static void main(String[] args) {
        // Cada trabajador trabaja cierta cantidad de horas y por hora se les paga un valor que varia según el perfil, se quiere calcular cuanto han
        // generado los trabajadores que han trabajado más de 20 horas y se les dará un bono en base al total del pago final de todos
        List<Trabajador> trabajadores = Arrays.asList(
                new Trabajador("Pedro", 21, 6),
                new Trabajador("Juan", 20, 5),
                new Trabajador("Carolina", 45, 15),
                new Trabajador("Santiago", 55, 11),
                new Trabajador("Juana", 5, 5),
                new Trabajador("Liliana", 19, 8)
        );
        Observable.fromIterable(trabajadores)
                .filter(trabajador -> trabajador.getHorasTrabajadas() >= 20) //Se filtran los trabajadores que han trabajado más de 20 horas
                .doOnNext(trabajador -> System.out.println(trabajador.getNombre())) // Se imprimen los nombres de los trabajadores que cumplen el filtro
                .map(trabajador -> trabajador.getHorasTrabajadas() * trabajador.getValorHora()) // Se calcula el total de pago al trabajador por las horas trabajadas
                .reduce((totalPagoTrabajadorA, totalPagoTrabajadorB) -> totalPagoTrabajadorA + totalPagoTrabajadorB)// Se suma el valor total de lo que se debe de pagar
                .doAfterSuccess(pagoTotal -> System.out.println("PAGO TOTAL " + pagoTotal)) // Se imprime el total a pagar luego de que fuera exitoso el subscribe del observador
                .map(pagoTotal -> pagoTotal * 0.1) // Finalmente se toma el 10% del valor final para pagarle adicional esto a los trabajadores que trabajaron más de 20 horas
                .subscribe(bono -> System.out.println("BONO FINAL " + bono));
    }
}