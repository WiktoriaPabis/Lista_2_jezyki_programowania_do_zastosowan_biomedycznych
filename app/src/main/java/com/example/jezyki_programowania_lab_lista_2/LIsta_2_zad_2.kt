package com.example.jezyki_programowania_lab_lista_2

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

/**
 * @author Wiktoria Pabiś
 *
 * Klasa reprezentująca sekwencję DNA.
 *
 * @property    identifier  unikalny identyfikator sekwencji
 * @property    data        sekwencja DNA
 * @property    valid_Chars zbiór prawidłowych znaków dla sekwencji DNA
 * @property    length1     długość sekwencji DNA
 *
 * @constructor                         tworzy obiekt DNAsequence na podstawie podanych parametrów przez użytkownika
 *                                      oraz wyliczonej długości sekwencji oraz definiuje zbiór dozwolonych
 *                                      znaków w sekwencji
 * @param   number                      liczba porządkowa sekwencji
 * @param   DNA                         sekwencja znaków w nici kodującej DNA
 * @throws  IllegalArgumentException    w przypadku, gdy podane są znaki niedozwolone w nici DNA
 */
class DNASequence(number:Int, DNA: String) {
    var identifier: Int
    var data: String
    val valid_Chars: Set<Char>
    var length1: Int

    init {
        identifier = number
        valid_Chars = setOf('A', 'T', 'C', 'G')
        for (elem in DNA) {
            if (!valid_Chars.contains(elem)) {
                throw IllegalArgumentException("Nieprawidłowe wartości dla sekwencji DNA")
            }
        }
        data = DNA
        length1 = DNA.length
    }

    /**
     * @author  Wiktoria Pabiś
     *
     * Metoda służąca do wyświetlania wartości identifier oraz nici DNA.
     *
     * @return      zwraca ciąg znaków, string, który zawiera informację o identifier oraz nici DNA
     */
    fun DNAtext(): String {
        var Text: String = "> $identifier\n $data"

        return Text
    }

    /**
     * @author  Wiktoria Pabiś
     *
     * Metoda zmienia zasadę na zadanej pozycji w sekwencji DNA na zasadę podaną jako value.
     *
     * @param   position                    pozycja, na której znajduje się wartość do zmiany
     * @param   value                       wartość, która zostanie wpisana w miejsce konkretnej pozycji
     *
     * @throws  IllegalArgumentException    w przypadku, gdy podany jest nieprawidłowy znak
     * @throws  IllegalArgumentException    w przypadku, gdy podana jest liczba wykraczająca poza
     *                                      zakres nici DNA
     */
    fun mutate(position: Int, value: Char) {
        var dataTab = data.toCharArray()
        if (!valid_Chars.contains(value)) {
            throw IllegalArgumentException("Nieprawidłowa wartość dla sekwencji DNA")
        }
        if (dataTab.size < position){
            throw IllegalArgumentException("Liczba wykraczająca poza zakres nici DNA")
        }
        dataTab[position - 1] = value
        data = String(dataTab)
    }

    /**
     * @author Wiktoria Pabiś
     *
     * Metoda wyszukująca motyw w sekwencji DNA.
     *
     * @param   motif                   motyw, który ma zostać znaleziony w sekwencji
     * @return                          lista pozycji, na których znajduje się podany motyw
     * @throws IllegalArgumentException w przypadku, gdy podano nieprawidłową wartość dla sekwencji DNA
     */
    fun findMotif(motif: Char): MutableList<Int> {
        if (!valid_Chars.contains(motif)){
            throw IllegalArgumentException("Nieprawidłowe wartości dla sekwencji DNA")
        }
        var listOfNucleotides: MutableList<Int> = mutableListOf()
        for ((index, elem) in data.withIndex()) {
            if (elem == motif) {
                listOfNucleotides.add(index + 1)
            }
        }

        return listOfNucleotides
    }

    /**
     * @author  Wiktoria Pabiś
     *
     * Funkcja zamienia sekwencję nici kodującej DNA w kierunku od 5' do 3' na jej komplementarną
     * sekwencję nici matrycowej DNA w kierunku od 3' do 5'.
     * Kod komplementarnej nici matrycowej jest tworzony poprzez zamianę każdej zasady na jej
     * komplementarną zasadę według reguł: A -> T, T -> A, C -> G, G -> C.
     *
     * @return                              zwraca ciąg znaków DNA_mat reprezentujący komplementarny
     *                                      kod DNA nici kodującej
     * @throws  IllegalArgumentException    w przypadku, gdy podana zasada nie istnieje
     */
    fun Complement(): String {
        var DNA_mat: String = ""
        for (nucleotide in data) {
            when (nucleotide) {
                'A' -> DNA_mat = DNA_mat + 'T'
                'T' -> DNA_mat = DNA_mat + 'A'
                'C' -> DNA_mat = DNA_mat + 'G'
                'G' -> DNA_mat = DNA_mat + 'C'

                else -> {
                    throw IllegalArgumentException("Podano nieprawidłowe wartości")
                }
            }
        }

        DNA_mat = DNA_mat.reversed()

        return DNA_mat
    }

    /**
     * @author  Wiktoria Pabiś
     *
     * Funkcja przyjmuje sekwencję nici matrycowej DNA w kierunku od 3' do 5' i zwraca obiekt, zawierający
     * jej komplementarną sekwencję cząsteczki RNA w kierunku od 5' do 3'.
     * Kod komplementarnej sekwencji łańcucha RNA jest tworzony poprzez zamianę każdej zasady na jej
     * komplementarną zasadę według reguł: A -> U, T -> A, C -> G, G -> C.
     *
     * @param   DNA_matryc                  ciąg znaków reprezentujący sekwencję nici matrycowej DNA,
     *                                      który ma zostać przekształcony na odpowiadający mu łańcuch RNA
     * @return                              zwraca obiekt klasy RNAsequence
     * @throws  IllegalArgumentException    w przypadku, gdy podana zasada nie istnieje
     */
    fun transcribe(DNA_matryc: String): RNASequence {
        var RNA: String = ""
        var DNA_matryc_1 = DNA_matryc.reversed()
        for (zasada in DNA_matryc_1) {
            when (zasada) {
                'A' -> RNA = RNA + 'U'
                'T' -> RNA = RNA + 'A'
                'C' -> RNA = RNA + 'G'
                'G' -> RNA = RNA + 'C'

                else -> {
                    throw IllegalArgumentException("Podano nieprawidłowe wartości")
                }
            }
        }

        return RNASequence(identifier, RNA)
    }
}

/**
 * @author Wiktoria Pabiś
 *
 * Klasa reprezentująca sekwencję RNA.
 *
 * @property    identifier  unikalny identyfikator sekwencji
 * @property    data        sekwencja RNA
 * @property    valid_Chars zbiór prawidłowych znaków dla sekwencji RNA
 * @property    length1     długość sekwencji RNA
 * @property    codonsMap   mapa zawierająca trójki kodonów i odpowiadające im aminokwasy
 *
 * @constructor                         tworzy obiekt RNAsequence na podstawie podanych parametrów przez użytkownika
 *                                      oraz wyliczonej długości sekwencji oraz definiuje zbiór dozwolonych
 *                                      znaków w sekwencji, tworzy mapę kodonów
 * @param   number                      liczba porządkowa sekwencji
 * @param   RNA                         sekwencja znaków w nici RNA
 * @throws  IllegalArgumentException    w przypadku, gdy podane są znaki niedozwolone w nici RNA
 */
class RNASequence(number:Int, RNA: String){
    var identifier:Int
    var data:String
    val valid_Chars:Set<Char>
    var length1:Int
    val codonsMap: Map<String, String>

    init {
        identifier = number
        valid_Chars = setOf('A','U','C','G')
        for (elem in RNA){
            if (!valid_Chars.contains(elem)){
                throw IllegalArgumentException("Nieprawidłowe wartości dla sekwencji RNA")
            }
        }
        data = RNA
        length1 = RNA.length
        codonsMap = this.codons()
    }

    /**
     * @author  Wiktoria Pabiś
     *
     * Metoda służąca do wyświetlania wartości identifier oraz nici RNA.
     *
     * @return      zwraca ciąg znaków, string, który zawiera informację o identifier oraz nici RNA
     */
    fun RNAtext(): String{
        var Text1:String = "> $identifier\n $data"

        return Text1
    }

    /**
     * @author  Wiktoria Pabiś
     *
     * Metoda zmienia zasadę na zadanej pozycji w sekwencji RNA na zasadę podaną jako value.
     *
     * @param   position                    pozycja, na której znajduje się wartość do zmiany
     * @param   value                       wartość, która zostanie wpisana w miejsce konkretnej pozycji
     *
     * @throws  IllegalArgumentException    w przypadku, gdy podany jest nieprawidłowy znak
     * @throws  IllegalArgumentException    w przypadku, gdy podana jest liczba wykraczająca poza
     *                                      zakres nici RNA
     */
    fun mutate(position:Int, value:Char){
        var dataTab = data.toCharArray()
        if (!valid_Chars.contains(value)){
            throw IllegalArgumentException("Nieprawidłowa wartość dla sekwencji RNA")
        }
        if (dataTab.size < position){
            throw IllegalArgumentException("Liczba wykraczająca poza zakres nici RNA")
        }
        dataTab[position - 1] = value
        data = String(dataTab)
    }

    /**
     * @author Wiktoria Pabiś
     *
     * Metoda wyszukująca motyw w sekwencji RNA. Motyw składa się z trzech znaków, zatem jest typu string.
     *
     * @param   motif                       motyw, który ma zostać znaleziony w sekwencji
     * @return                              lista pozycji, na których znajduje się podany motyw
     * @throws  IllegalArgumentException    w przypadku, gdy podano nieprawidłową ilość zasad azotowych
     *                                      dla sekwencji RNA
     * @throws  IllegalArgumentException    w przypadku, gdy podano nieprawidłową wartość dla sekwencji RNA
     */
    fun findMotif(motif: String): MutableList<Int>{
        if (!valid_Chars.contains(motif[0]) || !valid_Chars.contains(motif[1]) ||
            !valid_Chars.contains(motif[2]) ){
            throw IllegalArgumentException("Nieprawidłowe wartości dla sekwencji RNA")
        }
        var listOfNucleotides: MutableList<Int> = mutableListOf()
        var counter: Int = 0
        if (data.length % 3 != 0){
            throw IllegalArgumentException("Nieprawidłowa liczba zasad azotowych, proszę o wprowadzenie ilości " +
                    "zasad azotowych podzielną przez liczbę 3")
        }
        for ((index, elem) in data.withIndex()){
            if (counter == 2){
                if (elem == motif[2] && data[index - 1] == motif[1] && data[index - 2] == motif[0]){
                    listOfNucleotides.add((index + 1) / 3)
                }
                counter = -1
            }
            counter++
        }

        return listOfNucleotides
    }

    /**
     * @author  Wiktoria Pabiś
     *
     * Funkcja zwraca mapę, w której kluczem jest kodon RNA, natomiast wartością odpowiadający
     * mu aminokwas.
     * Mapa zawiera zestawienie kodonów RNA, a także aminokwasy im odpowiadające, według standardowego
     * kodu genetycznego.
     *
     * @return      mapa kodonów RNA, wraz z odpowiadającymi im aminokwasami
     */
    fun codons():Map<String, String>{
        var codonsMap = mapOf(
//      Mapa amonikwasów wygenerowana za pomocą modelu językowego
            // Alanina
            "GCU" to "Ala", "GCC" to "Ala", "GCA" to "Ala", "GCG" to "Ala",
            // Cysteina
            "UGU" to "Cys", "UGC" to "Cys",
            // Kwas asparaginowy
            "GAU" to "Asp", "GAC" to "Asp",
            // Kwas glutaminowy
            "GAA" to "Glu", "GAG" to "Glu",
            // Fenyloalanina
            "UUU" to "Phe", "UUC" to "Phe",
            // Glicyna
            "GGU" to "Gly", "GGC" to "Gly", "GGA" to "Gly", "GGG" to "Gly",
            // Histydyna
            "CAU" to "His", "CAC" to "His",
            // Izoleucyna
            "AUU" to "Ile", "AUC" to "Ile", "AUA" to "Ile",
            // Lizyna
            "AAA" to "Lys", "AAG" to "Lys",
            // Leucyna
            "UUA" to "Leu", "UUG" to "Leu", "CUU" to "Leu", "CUC" to "Leu", "CUA" to "Leu", "CUG" to "Leu",
            // Metionina lub kodon rozpoczynający transkrypcję
            "AUG" to "Met",
            // Asparagina
            "AAU" to "Asn", "AAC" to "Asn",
            // Prolina
            "CCU" to "Pro", "CCC" to "Pro", "CCA" to "Pro", "CCG" to "Pro",
            // Glutamina
            "CAA" to "Gln", "CAG" to "Gln",
            // Arginina
            "CGU" to "Arg", "CGC" to "Arg", "CGA" to "Arg", "CGG" to "Arg", "AGA" to "Arg", "AGG" to "Arg",
            // Seryna
            "UCU" to "Ser", "UCC" to "Ser", "UCA" to "Ser", "UCG" to "Ser", "AGU" to "Ser", "AGC" to "Ser",
            // Treonina
            "ACU" to "Thr", "ACC" to "Thr", "ACA" to "Thr", "ACG" to "Thr",
            // Walina
            "GUU" to "Val", "GUC" to "Val", "GUA" to "Val", "GUG" to "Val",
            // Tryptofan
            "UGG" to "Trp",
            // Tyrozyna
            "UAU" to "Tyr", "UAC" to "Tyr",
            // Kodony STOP
            "UAA" to "Ter", "UAG" to "Ter", "UGA" to "Ter"
        )

        return codonsMap
    }

    /**
     * @author  Wiktoria Pabiś
     *
     * Funkcja przekształca sekwencję nici RNA i zwraca obiekt klasy ProteinSequence.
     * Tłumaczy sekwencję mRNA na sekwencję aminokwasów za pomocą dostarczonej mapy kodonów.
     * Założeniem funkcji jest, że pracuje ona na liczbie zasad azotowych podzielnych przez 3.
     *
     * @return                              lista aminokwasów, które odpowiadają sekwencji mRNA,
     *                                      z tym założeniem, że podana sekwencja zaiwera podzielną
     *                                      przez 3 liczbę zasad azotowych
     * @throws  IllegalArgumentException    w przypadku, gdy liczba zasad azotowych nie jest podzielna
     *                                      przez 3 oraz w przypadku, gdy nie występuje kodon STOP lub
     *                                      rozpoczynający
     */
    fun transcribe(): ProteinSequence{
        var listAmino:MutableList<String> = mutableListOf()
        var tempCodon:String = ""
        var startTransl:Boolean = false
        var stopTransl:Boolean = false
        var tempListOfCodons:MutableList<String> = mutableListOf()
        var count:Int = 0
        if (data.length % 3 != 0){
            throw IllegalArgumentException("Nieprawidłowa liczba zasad azotowych, proszę o wprowadzenie ilości " +
                    "zasad azotowych podzielną przez liczbę 3")
        }
        for (elem in data){
            tempCodon = tempCodon + elem
            if (count == 2){
                if (startTransl){
                    if (tempCodon == "UAA" || tempCodon == "UAG" || tempCodon == "UGA"){
                        stopTransl = true
                        tempListOfCodons.add(tempCodon)
                    }
                }
                if ((tempCodon == "AUG" || startTransl) && !stopTransl){
                    tempListOfCodons.add(tempCodon)
                    startTransl = true
                }

                tempCodon = ""
//                chce znowu miec count 0 w kolejnej iteracji petli
                count = -1
            }
            count++
        }
        for (elem in tempListOfCodons){
//            elem to klucz, chce dodac value czyli np. Ala
            listAmino.add(codonsMap[elem].toString())
        }
        if (!startTransl){
            throw IllegalArgumentException ("Brak kodonu rozpoczynającego")
        }
        if (!stopTransl){
            throw IllegalArgumentException ("Brak kodonu STOP")
        }

        return ProteinSequence(identifier, listAmino)
    }
}

/**
 * @author Wiktoria Pabiś
 *
 * Klasa reprezentująca sekwencję protein.
 *
 * @property    identifier              unikalny identyfikator sekwencji
 * @property    amino                   sekwencja protein
 * @property    valid_Chars             zbiór prawidłowych znaków dla sekwencji protein
 * @property    length1                 długość sekwencji protein
 * @property    aminoFASTAmap           mapa aminokwasów potrzebna do stworzenia formatu FASTA
 *
 * @constructor                         tworzy obiekt ProteinSequence na podstawie podanych parametrów przez użytkownika
 *                                      oraz wyliczonej długości sekwencji oraz definiuje zbiór dozwolonych
 *                                      stringów w sekwencji
 * @param   number                      liczba porządkowa sekwencji
 * @param   protein                     lista znaków protein
 * @throws  IllegalArgumentException    w przypadku, gdy podane są znaki niedozwolone dla protein
 */
class ProteinSequence(number:Int, protein: MutableList<String>){
    var identifier:Int
    var amino:MutableList<String>
    val valid_Chars:Set<String>
    var length1:Int
    val aminoFASTAmap: Map<String, String>

    init {
        identifier = number
        valid_Chars = setOf( "Ala", "Cys", "Asp", "Glu", "Phe", "Gly", "His", "Ile", "Lys", "Leu",
            "Met", "Asn", "Pro", "Gln", "Arg", "Ser", "Thr", "Val", "Trp", "Tyr", "Ter")
        for (elem in protein){
            if (!valid_Chars.contains(elem)){
                throw IllegalArgumentException("Nieprawidłowe wartości dla sekwencji protein")
            }
        }
        amino = protein
        length1 = amino.size
        aminoFASTAmap = this.FASTAmap()
    }

    /**
     * @author  Wiktoria Pabiś
     *
     * Metoda służąca do wyświetlania wartości identifier oraz protein.
     *
     * @return      zwraca ciąg znaków, string, który zawiera informację o identifier oraz proteinach
     */
    fun Proteintext(): String{
        var Text1:String = "> $identifier\n ${amino.toString()}"

        return Text1
    }

    /**
     * @author  Wiktoria Pabiś
     *
     * Metoda służąca do wyświetlania wartości identifier oraz protein w formacie FASTA.
     *
     * @return      zwraca ciąg znaków, string, który zawiera informację o identifier oraz proteinach
     */
    fun ProteintextFASTA(): String{
        var Text1:String = "> $identifier\n"
        for (elem in amino){
            Text1 = Text1 + aminoFASTAmap[elem]
        }

        return Text1
    }

    /**
     * @author  Wiktoria Pabiś
     *
     * Funkcja zwraca mapę, w której kluczem jest skrót aminokwasu, natomiast wartością odpowiadająca
     * mu litera w formacie FASTA.
     *
     * @return      mapa liter, wraz z odpowiadającymi im aminokwasami
     */
    fun FASTAmap(): Map<String, String>{
        val aminoMap = mapOf(
            "Ala" to "A",
            "Asx" to "B",
            "Cys" to "C",
            "Asp" to "D",
            "Glu" to "E",
            "Phe" to "F",
            "Gly" to "G",
            "His" to "H",
            "Ile" to "I",
            "Xle" to "J",
            "Lys" to "K",
            "Leu" to "L",
            "Met" to "M",
            "Asn" to "N",
            "Pyl" to "O",
            "Pro" to "P",
            "Gln" to "Q",
            "Arg" to "R",
            "Ser" to "S",
            "Thr" to "T",
            "Sec" to "U",
            "Val" to "V",
            "Trp" to "W",
            "Tyr" to "Y",
            "Glx" to "Z",
            "Ter" to "*"
        )
        return aminoMap
    }

    /**
     * @author  Wiktoria Pabiś
     *
     * Metoda zmienia proteinę na zadanej pozycji w sekwencji protein na proteinę podaną jako value.
     *
     * @param   position                    pozycja, na której znajduje się wartość do zmiany
     * @param   value                       wartość, która zostanie wpisana w miejsce konkretnej pozycji
     *
     * @throws  IllegalArgumentException    w przypadku, gdy podana jest nieprawidłowa proteina
     * @throws  IllegalArgumentException    w przypadku, gdy podana jest liczba wykraczająca poza
     *                                      zakres listy protein
     */
    fun mutate(position:Int, value:String){
        if (!valid_Chars.contains(value)){
            throw IllegalArgumentException("Nieprawidłowa wartość dla sekwencji protein")
        }
        if (amino.size < position){
            throw IllegalArgumentException("Liczba wykraczająca poza zakres nici RNA")
        }
        amino[position - 1] = value
    }

    /**
     * @author Wiktoria Pabiś
     *
     * Metoda wyszukująca motyw w sekwencji protein.
     *
     * @param   motif                   motyw, który ma zostać znaleziony w sekwencji
     * @return                          lista pozycji, na których znajduje się podany motyw
     * @throws IllegalArgumentException w przypadku, gdy podano nieprawidłową wartość dla sekwencji protein
     */
    fun findMotif(motif: String): MutableList<Int>{
        if (!valid_Chars.contains(motif)){
            throw IllegalArgumentException("Nieprawidłowe wartości dla sekwencji protein")
        }
        var listOfNucleotides: MutableList<Int> = mutableListOf()
        var counter: Int = 0
        for ((index, elem) in amino.withIndex()){
            if (elem == motif){
                listOfNucleotides.add((index + 1))
            }
        }

        return listOfNucleotides
    }
}

/**
 * @author Wiktoria Pabiś
 * Główna funkcja programu.
 * Prezentuje sposób działania programu na przykładzie jednej nici.
 */
fun main(){
    var DNAseqNew = DNASequence(1, "ATGAGGAAAGGGAAATAA")
    println("................................................")
    println("Wyświetlenie w formie tekstowej w formacie FASTA")
    println(DNAseqNew.DNAtext())
    DNAseqNew.mutate(6,'A')
    println("................................................")
    println("Wyświetlenie sekwencji DNA po zmianie")
    println(DNAseqNew.DNAtext())
    var newList = DNAseqNew.findMotif('A')
    println("................................................")
    println("Wyświetlenie pozycji zadanego motywu w sekwencji DNA w kierunku od 5' do 3'")
    println(newList.toString())
    var newDNAmat = DNAseqNew.Complement()
    println("................................................")
    println("Wyświetlenie komplementarnej matrycowej nici DNA w kierunku od 3' do 5' do nici " +
            "kodującej DNA")
    println(newDNAmat)
    var newRNA = DNAseqNew.transcribe(newDNAmat)
    println("................................................")
    println("Wyświetlenie komplementarnej nici RNA do nici matrycowej DNA ")
    println(newRNA.RNAtext())
    newRNA.mutate(6,'U')
    println("................................................")
    println("Wyświetlenie sekwencji RNA po zmianie")
    println(newRNA.RNAtext())
    var RNAlist = newRNA.findMotif("AAA")
    println("................................................")
    println("Wyświetlenie pozycji zadanego motywu w sekwencji RNA ")
    println(RNAlist.toString())
    var proteins = newRNA.transcribe()
    println("................................................")
    println("Wyświetlenie komplementarnej do RNA sekwencji protein ")
    println(proteins.Proteintext())
    proteins.mutate(2, "Cys")
    println("................................................")
    println("Wyświetlenie sekwencji protein po zmianie")
    println(proteins.Proteintext())
    var proteinlist = proteins.findMotif("Lys")
    println("................................................")
    println("Wyświetlenie pozycji zadanego motywu w sekwencji protein ")
    println(proteinlist.toString())
    println("................................................")
    println("Wyświetlenie sekwencji protein w formacie FASTA ")
    println(proteins.ProteintextFASTA())


}
class DNASequenceTest {

    @Test
    fun Constructor(){
        val validDNA = DNASequence(2, "AAAGGGCCTC")
        assertEquals("AAAGGGCCTC", validDNA.data, "Nieprawidłowe " +
                "utworzenie obiektu klasy")

        val validDNA1 = DNASequence(2, "AAAGGGCCTC")
        assertEquals(2, validDNA.identifier, "Nieprawidłowe " +
                "utworzenie obiektu klasy")

        assertFailsWith<IllegalArgumentException>("Niepoprawnie obsłużony wyjątek"){
            DNASequence(1, "AAAGTTWRA")
        }
    }

    @Test
    fun DNAtext(){
        val DNAseq = DNASequence(1, "ATGAGGAAAGGGAAATAA")
        assertEquals("> 1\n ATGAGGAAAGGGAAATAA", DNAseq.DNAtext(), "Nieprawidłowe" +
                "wyświetlenie nici DNA")
    }

    @Test
    fun Mutate(){
        val DNAseq = DNASequence(1, "ATGAGGAAA")
        DNAseq.mutate(6,'A')
        assertEquals("ATGAGAAAA", DNAseq.data, "Nieprawidłowe działanie " +
                "metody mutate")
    }

    @Test
    fun Mutate1(){
        val DNAseq = DNASequence(1, "ATGAGGAAA")
        assertFailsWith<IllegalArgumentException>("Niepoprawnie obsłużony wyjątek"){
            DNAseq.mutate(6,'W')
        }
    }

    @Test
    fun Mutate2(){
        val DNAseq = DNASequence(1, "ATGAGGAAA")
        assertFailsWith<IllegalArgumentException>("Niepoprawnie obsłużony wyjątek"){
            DNAseq.mutate(24,'G')
        }
    }

    @Test
    fun findMotif(){
        val DNAseq = DNASequence(1, "ATGAGGAAA")
        val newList = DNAseq.findMotif('A')
        assertEquals("[1, 4, 7, 8, 9]", newList.toString())
    }

    @Test
    fun findMotif2(){
        val DNAseq = DNASequence(1, "ATGAGGAAA")
        assertFailsWith<IllegalArgumentException>("Niepoprawnie obsłużony wyjątek"){
            DNAseq.findMotif('B')
        }
    }

    @Test
    fun Complement(){
        val DNAseq = DNASequence(1, "ATGAGGAAAGGGAAA")
        val newDNAmat = DNAseq.Complement()
        assertEquals("TTTCCCTTTCCTCAT", newDNAmat, "Niepoprawnie utworzona nić komplementarna")
    }

    @Test
    fun Transcribe(){
        val DNAseq = DNASequence(1, "ATGAGGAAG")
        val newDNAmat = DNAseq.Complement()
        val newRNA = DNAseq.transcribe(newDNAmat)
        assertEquals("AUGAGGAAG", newRNA.data, "Niepoprawna transkrypcja")
    }
}

class RNASequenceTest {

    @Test
    fun Constructor(){
        val validRNA = RNASequence(2, "AAAGGGCCU")
        assertEquals("AAAGGGCCU", validRNA.data, "Nieprawidłowe " +
                "utworzenie obiektu klasy")

        val validRNA1 = RNASequence(2, "AAAGGGCCUC")
        assertEquals(2, validRNA1.identifier, "Nieprawidłowe " +
                "utworzenie obiektu klasy")

        assertFailsWith<IllegalArgumentException>("Niepoprawnie obsłużony wyjątek"){
            RNASequence(1, "AAAGUUWR")
        }
    }

    @Test
    fun RNAtext(){
        val RNAseq = RNASequence(1, "AGAGGAAAGGGAAAA")
        assertEquals("> 1\n AGAGGAAAGGGAAAA", RNAseq.RNAtext(), "Nieprawidłowe" +
                "wyświetlenie nici RNA")
    }

    @Test
    fun Mutate(){
        val RNAseq = RNASequence(1, "AUGAGGAAA")
        RNAseq.mutate(6,'A')
        assertEquals("AUGAGAAAA", RNAseq.data, "Nieprawidłowe działanie " +
                "metody mutate")
    }

    @Test
    fun Mutate1(){
        val RNAseq = RNASequence(1, "AUGAGGAAA")
        assertFailsWith<IllegalArgumentException>("Niepoprawnie obsłużony wyjątek"){
            RNAseq.mutate(6,'W')
        }
    }

    @Test
    fun Mutate2(){
        val RNAseq = RNASequence(1, "AUGAGGAAA")
        assertFailsWith<IllegalArgumentException>("Niepoprawnie obsłużony wyjątek"){
            RNAseq.mutate(24,'G')
        }
    }

    @Test
    fun findMotif(){
        val RNAseq = RNASequence(1, "AUGAGGAAA")
        val newList = RNAseq.findMotif("AUG")
        assertEquals("[1]", newList.toString(), "Nieprawidłowo obliczona pozycja")
    }

    @Test
    fun findMotif2(){
        val RNAseq = RNASequence(1, "AUGAGGAAA")
        assertFailsWith<IllegalArgumentException>("Niepoprawnie obsłużony wyjątek"){
            RNAseq.findMotif("B")
        }
    }

    @Test
    fun findMotif3(){
        val RNAseq = RNASequence(1, "AUGAGGAAAG")
        assertFailsWith<IllegalArgumentException>("Niepoprawnie obsłużony wyjątek"){
            RNAseq.findMotif("AAA")
        }
    }

    @Test
    fun Transcribe(){
        val RNAseq = RNASequence(1, "AUGAGGAAGUAA")
        val newProtein = RNAseq.transcribe()
        assertEquals("[Met, Arg, Lys, Ter]", newProtein.amino.toString(), "Niepoprawna transkrypcja")
    }

    @Test
    fun Transcribe1(){
        val RNAseq = RNASequence(1, "AGGAAGUAA")
        assertFailsWith<IllegalArgumentException>("Brak kodonu start"){
            RNAseq.transcribe()
        }
    }

    @Test
    fun Transcribe2(){
        val RNAseq = RNASequence(1, "AUGAGGAAG")
        assertFailsWith<IllegalArgumentException>("Brak kodonu stop"){
            RNAseq.transcribe()
        }
    }
}

class ProteinSequenceTest {

    @Test
    fun Constructor(){
        val validprotein = ProteinSequence(2, mutableListOf("Met", "Arg", "Lys", "Ter"))
        assertEquals("[Met, Arg, Lys, Ter]", validprotein.amino.toString(), "Nieprawidłowe " +
                "utworzenie obiektu klasy")

        val validprotein1 = ProteinSequence(2, mutableListOf("Met", "Arg", "Lys", "Ter"))
        assertEquals(2, validprotein1.identifier, "Nieprawidłowe " +
                "utworzenie obiektu klasy")

        assertFailsWith<IllegalArgumentException>("Niepoprawnie obsłużony wyjątek"){
            ProteinSequence(1, mutableListOf("Met", "Arg", "Asa", "Lys", "Ter"))
        }
    }

    @Test
    fun proteintext(){
        val proteinseq = ProteinSequence(1, mutableListOf("Met", "Arg", "Lys", "Ter"))
        assertEquals("> 1\n [Met, Arg, Lys, Ter]", proteinseq.Proteintext(), "Nieprawidłowe" +
                "wyświetlenie protein")
    }

    @Test
    fun proteintextFASTA(){
        val proteinseq = ProteinSequence(1, mutableListOf("Met", "Arg", "Lys", "Ter"))
        assertEquals("> 1\nMRK*", proteinseq.ProteintextFASTA(), "Nieprawidłowe" +
                "wyświetlenie protein w formacie FASTA")
    }

    @Test
    fun Mutate(){
        val proteinseq = ProteinSequence(1, mutableListOf("Met", "Arg", "Lys", "Ter"))
        proteinseq.mutate(2,"Lys")
        assertEquals("[Met, Lys, Lys, Ter]", proteinseq.amino.toString(), "Nieprawidłowe działanie " +
                "metody mutate")
    }

    @Test
    fun Mutate1(){
        val proteinseq = ProteinSequence(1, mutableListOf("Met", "Arg", "Lys", "Ter"))
        assertFailsWith<IllegalArgumentException>("Niepoprawnie obsłużony wyjątek"){
            proteinseq.mutate(3,"Was")
        }
    }

    @Test
    fun Mutate2(){
        val proteinseq = ProteinSequence(1, mutableListOf("Met", "Arg", "Lys", "Ter"))
        assertFailsWith<IllegalArgumentException>("Niepoprawnie obsłużony wyjątek"){
            proteinseq.mutate(24,"Lys")
        }
    }

    @Test
    fun findMotif(){
        val proteinseq = ProteinSequence(1, mutableListOf("Met", "Arg", "Lys", "Ter"))
        val newList = proteinseq.findMotif("Arg")
        assertEquals("[2]", newList.toString(), "Nieprawidłowo obliczona pozycja")
    }

    @Test
    fun findMotif2(){
        val proteinseq = ProteinSequence(1, mutableListOf("Met", "Arg", "Lys", "Ter"))
        assertFailsWith<IllegalArgumentException>("Niepoprawnie obsłużony wyjątek"){
            proteinseq.findMotif("Byh")
        }
    }

}



