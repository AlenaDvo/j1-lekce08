package cz.czechitas.lekce8.losovani;

import java.util.Optional;
import java.util.stream.Stream;

public class AplikaceLosovani {

    public static void main(String[] args) {
        new AplikaceLosovani().run();
    }

    public void run() {
        LosovaciZarizeni losovaciZarizeni = new LosovaciZarizeni();
        System.out.println("Losování prvního tahu Sportky…");
        System.out.println();
        Stream<Integer> prvniTah = losovaciZarizeni.losovatSportkuHlavniTah();
        this.vypsatTazenaCisla("prvním", prvniTah);

        System.out.println();
        System.out.println("Losování druhého tahu Sportky…");
        System.out.println();
        Stream<Integer> druhyTah = losovaciZarizeni.losovatSportkuHlavniTah();
        this.vypsatTazenaCisla("druhém", druhyTah);

        System.out.println();
        System.out.println("Losování Šance…");
        System.out.println();
        Stream<Integer> sance = losovaciZarizeni.losovatSanci();
        System.out.println("V Šanci byla tažena čísla:");
        sance.forEachOrdered(this::vypsatVylosovaneCislo);

//        // zkouseni Optional
//        Optional<Integer> first = sance.findFirst();
//        if (first.isPresent()) {
//            Integer cislo = first.get();
//            System.out.println("Na kotce padlo cislo: " + cislo);
//        } else {
//            System.out.println("Kostka se ztratila!");
//        }
//
//        Integer cislo = first.orElse(6);
//
//        Optional<String> email = Optional.ofNullable(6);
//        // konec zkouseni Optional

        // zkouseni Streamu
//        private void test() {
//            Osoba osoba = new Osoba();
//            Adresa adresa = osoba.getAdresa();
//            Mesto mesto = adresa.getMesto();
//            int pocetObyvatel = mesto.getPocetObyvatel();
//
////            fluent zapis
//            int pocetObyvatel = new Osoba().getAdresa().getMesto().getPocetObyvatel();
//        }
        // konec zkouseni Streamu


        System.out.println();
        System.out.println("Výhercům gratulujeme.");
    }

    /**
     * Vypíše všechna vylosovaná čícsla.
     */
    public void vypsatTazenaCisla(String poradi, Stream<Integer> vylosovanaCisla) {
        System.out.printf("V %s tahu Sportky byla tažena čísla:", poradi).println();
        vylosovanaCisla.forEachOrdered(this::vypsatVylosovaneCislo);
    }

    /**
     * Vypíše vylosované číslo na nový řádek s odrážkou.
     *
     * @param cislo Vylosované číslo k vypsání.
     */
    private void vypsatVylosovaneCislo(int cislo) {
        System.out.printf("• %d", cislo)
                .println();
    }

}
