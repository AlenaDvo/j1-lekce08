package cz.czechitas.lekce8.svatky;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Month;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.stream.Stream;

/**
 * Třída s informacemi o tom,kdo má kdy svátek.
 */
public class Svatky {
    private static final DateTimeFormatter MONTH_PARSER = DateTimeFormatter.ofPattern("d.M.");

    private static Svatek parseLine(String line) {
        String[] parts = line.split("\\s");
        assert parts.length == 3;
        return new Svatek(
                MonthDay.parse(parts[0], MONTH_PARSER),
                parts[1],
                Gender.valueOf(parts[2].toUpperCase(Locale.ROOT))
        );
    }

    public Stream<Svatek> nacistSeznamSvatku() {
        try {
            Path path = Paths.get(Svatky.class.getResource("svatky.txt").toURI());
            return Files.lines(path).map(Svatky::parseLine);
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Vrátí seznam všech svátků v daném měsíci.
     *
     * @param mesic Měsíc, pro který se mají svátky vypsat.
     * @return Stream svátků.
     */
    public Stream<Svatek> nacistSvatkyVMesici(Month mesic) {
        return nacistSeznamSvatku()
                .filter(svatek -> svatek.den().getMonth() == mesic);
    }

    /**
     * Vrátí den, kdy má dotyčné jméno svátek.
     *
     * @param jmeno
     * @return
     */
    public Stream<MonthDay> urcitDatumSvatku(String jmeno) {
        return nacistSeznamSvatku()
                .filter(svatek -> svatek.jmeno().equals(jmeno))
                .map(Svatek::den);
    }

    /**
     * Vrátí všechna jména mužů.
     *
     * @return Stream jmen.
     */
    public Stream<String> nacistSeznamSvatkuMuzu() {
        return nacistSeznamSvatku()
                .filter(svatek -> svatek.gender() == Gender.MUZ)
                .map(Svatek::jmeno);
    }

    /**
     * Vrátí všechna jména žen.
     *
     * @return Stream jmen.
     */
    public Stream<String> nacistSeznamSvatkuZen() {
        return nacistSeznamSvatku()
                .filter(svatek -> svatek.gender() == Gender.ZENA)
                .map(Svatek::jmeno);
    }

    /**
     * Vrátí jména, která mají v daný den svátek.
     *
     * @return Stream jmen.
     */
    public Stream<String> urcitSvatkyProDen(MonthDay den) {
        return nacistSeznamSvatku()
                .filter(svatek -> svatek.den().equals(den))
                .map(Svatek::jmeno);
    }

    /**
     * Vrátí ženská jména, která mají svátek v daném měsíci.
     *
     * @param mesic Vybraný měsíc.
     * @return Stream jmen.
     */
    public Stream<String> nacistZenskaJmenaVMesici(Month mesic) {
        return nacistSeznamSvatku()
                .filter(svatek -> svatek.den().getMonth().equals(mesic))
                .filter(svatek -> svatek.gender() == Gender.ZENA)
                .map(Svatek::jmeno);
    }

    /**
     * Vrátí počet mužů, kteří mají svátek 1. den v měsíci.
     *
     * @return Počet mužských jmen.
     */
    public long zjistitPocetMuzskychSvatkuPrvniho() {
        return nacistSeznamSvatku()
                .filter(svatek -> svatek.den().getDayOfMonth() == 1 && svatek.gender() == Gender.MUZ)
                .count();
    }

    /**
     * Vypíše do konzole seznam jmen, která mají svátek v listopadu.
     */
    public void vypsatJmenaListopad() {
//        System.out.println(
                nacistSeznamSvatku()
                .filter(svatek -> svatek.den().getMonth() == Month.NOVEMBER)
                .map(Svatek::jmeno)
                .forEach(System.out::println);
//        .forEach(jmeno -> System.out.println(jmeno));
//                .toList());
    }

    /**
     * Vypíše počet unikátních jmen v kalendáři.
     */
    public long zjistitPocetUnikatnichJmen() {
        return nacistSeznamSvatku()
                .map(Svatek::jmeno)
                .distinct()
                .count();
    }

    /**
     * Vrátí seznam jmen, která mají svátek v červnu – přeskočí prvních 10 jmen.
     *
     * @see Stream#skip(long)
     */
    public Stream<String> urcitJmenavCervnuVynechatPrvnichDeset() {
        return nacistSeznamSvatku()
                .filter(svatek -> svatek.den().getMonth() == Month.JUNE)
                .skip(10)
                .map(Svatek::jmeno);
    }

    /**
     * Vrátí seznam jmen, která mají svátek od 24. 12. včetně do konce roku.
     *
     * @see Stream#dropWhile(java.util.function.Predicate)
     */
    public Stream<String> urcitJmenaOdVanoc() {
        return nacistSeznamSvatku()
                .filter(svatek -> svatek.den().getMonth() == Month.DECEMBER)
                .filter(svatek -> svatek.den().getDayOfMonth() >= 24)
                .map(Svatek::jmeno);
    }
}
