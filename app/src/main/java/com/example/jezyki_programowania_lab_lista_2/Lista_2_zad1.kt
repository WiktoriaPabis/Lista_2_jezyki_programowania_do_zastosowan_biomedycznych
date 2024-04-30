package com.example.jezyki_programowania_lab_lista_2

import kotlin.math.abs
import kotlin.math.pow
import org.junit.Test
import kotlin.IllegalArgumentException
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith


/**
 * @author   Wiktoria Pabiś
 *
 * Klasa reprezentująca wielomian dowolnego stopnia.
 *
 * @property listOfFactors           mutowalna lista zawierająca współczynniki wielomianu typu double
 *
 * @constructor                      tworzy obiekt Wielomian na podstawie mutowalnej listy współczynników
 * @param   listOfValue              lista zawierająca współczynniki wielomianu
 * @throws  IllegalArgumentException w przypadku, gdy lista współczynników jest pusta
 */
class Wielomian(listOfValue: MutableList<Double>){
    var listOfFactors: MutableList<Double> = mutableListOf()

    init {
        if (listOfValue.isEmpty()) {
            throw IllegalArgumentException("Próba niepoprawnego utworzenia wielomianu")
        }
        var i = 0
        while (listOfValue[i] == 0.0){
            listOfValue.removeFirst()
        }
        listOfFactors = listOfValue
    }

    /**
     * @author  Wiktoria Pabiś
     *
     * Metoda obliczająca stopień wielomianu.
     *
     * @return  stopień wielomianu, obliczony za pomocą rozmiaru listy współczynników
     */
    fun StopienWielomianu(): Int{
        return listOfFactors.size-1

    }


    /**
     * @author  Wiktoria Pabiś
     *
     * Metoda tworząca tekstową reprezentację wielomianu. Zawiera mutowalne pola temp_Factor, które
     * zawiera wywołanie metody StopienWielomianu, resultOfPolynomial, zawierający początek
     * zapisu wzoru wielomianu oraz firstElem typu Boolean.
     *
     * @return tekstowa reprezentacja wielomianu typu string
     */
    fun TekstWielomian(): String{
        var temp_Factor = StopienWielomianu()
        var resultOfPolynomial = "W(x)= "
        var firstElem: Boolean = true
        for ((index , elem) in listOfFactors.withIndex()){
            if(index == listOfFactors.size-1) {
                if (elem == 0.0){
                    break
                }
                if (elem > 0.0){
                    resultOfPolynomial = resultOfPolynomial + '+' + elem
                } else
                    resultOfPolynomial = resultOfPolynomial + elem
                break
            }
            if (elem != 0.0){
                if (elem > 0.0) {
                    if (firstElem) {
                        firstElem = false
                    } else {
                        resultOfPolynomial = resultOfPolynomial + '+'
                    }
                }
                if (firstElem) {
                    firstElem = false
                }
                resultOfPolynomial = resultOfPolynomial + elem
                if (index == listOfFactors.size-2){
                    resultOfPolynomial = resultOfPolynomial + 'x'
                } else
                    resultOfPolynomial = resultOfPolynomial + "x^$temp_Factor"
            }
            temp_Factor--
        }

        return resultOfPolynomial
    }

    /**
     * @author  Wiktoria Pabiś
     *
     * Przeciążony operator wywołania oblicza wartość wielomianu dla danego argumentu,zawiera mutowalną zmienną
     * result typu Double oraz tempFactor1, który zawiera informację o stoniu wielomianu, wywołaując
     * metodę StopienWielomianu.
     *
     * @param   argument1   argument, dla którego obliczana jest wartość wielomianu
     * @return              wartość wielomianu dla danego argumentu
     */
    operator fun invoke(argument1: Double): Double{
        var result: Double = 0.0
        var tempFactor1 = StopienWielomianu()
        for (elem in listOfFactors){
            result = result + argument1.pow(tempFactor1)*elem

            tempFactor1--
        }

        return result
    }

    /**
     * @author  Wiktoria Pabiś
     *
     * Przeciążony operator dodawania dodaje dwa wielomiany, zawarte są w nim zmienne: listW, który jest mutowalną
     * listą typu Double, degreeW1, czyli bieżący wielomian oraz degree W2, który zostaje dodany
     * w wyniku przeciążenia. Zmienna difference jest typu Int i zawiera informację o różnicy stopni
     * wielomianu.
     * Operator przeciążenia początkowo sprawdzq czy stopnie wielomianu są sobie równe, jeśli nie
     * to, sprawdza, który wielomian ma większy stopień, następnie odpowiednio dodaje do siebie
     * wielomiany, w zależności od wyniku.
     * Wynik zostaje zapisany w nowej zmiennej polynomial3.
     *
     * @param   polynomial_2    drugi wielomian
     * @return                  nowy wielomian będący sumą dwóch wielomianów
     */
    operator fun plus(polynomial_2: Wielomian): Wielomian{
        var listW: MutableList<Double> = mutableListOf()
        var degreeW1 = this.StopienWielomianu()
        var degreeW2 = polynomial_2.StopienWielomianu()
        var difference: Int = abs(degreeW1 - degreeW2)
        if (degreeW1 != degreeW2){
            if (degreeW1 > degreeW2){
                listW = this.listOfFactors.subList(0, difference).toMutableList()
                for ( i in 0..degreeW2){
                    listW.add(this.listOfFactors[difference + i] + polynomial_2.listOfFactors[i])
                }
            } else {
                listW = polynomial_2.listOfFactors.subList(0, difference).toMutableList()
                for (i in 0..degreeW1) {
                    listW.add(polynomial_2.listOfFactors[difference + i] + this.listOfFactors[i])
                }
            }

        } else{
            for ( i in 0..degreeW1){
                listW.add(polynomial_2.listOfFactors[i] + this.listOfFactors[i])
            }
        }

        var polynomial3 = Wielomian(listW)
        return polynomial3
    }

    /**
     * @author  Wiktoria Pabiś
     *
     * Przeciążony operator złożony dodawania dodaje dwa wielomiany, zawarte są w nim zmienne: listW, który jest mutowalną
     * listą typu Double, degreeW1, czyli bieżący wielomian oraz degree W2, który zostaje dodany
     * w wyniku przeciążenia. Zmienna difference jest typu Int i zaiwera informację o różnicy stopni
     * wielomianu.
     * Operator przeciążenia początkowo sprawdzq czy stopnie wielomianu są sobie równe, jeśli nie
     * to, sprawdza, który wielomian ma większy stopień, następnie odpowiednio dodaje do siebie
     * wielomiany, w zależności od wyniku.
     * Wynik zostaje zapisany w pierwotnym wielomianie.
     *
     * @param   polynomial_2    drugi wielomian
     * @return                  nowy wielomian będący sumą dwóch wielomianów
     */
    operator fun plusAssign(polynomial_2: Wielomian){
        var listW: MutableList<Double> = mutableListOf()
        var degreeW1 = this.StopienWielomianu()
        var degreeW2 = polynomial_2.StopienWielomianu()
        var difference: Int = abs(degreeW1 - degreeW2)
        if (degreeW1 != degreeW2){
            if (degreeW1 > degreeW2){
                listW = this.listOfFactors.subList(0, difference).toMutableList()
                for ( i in 0..degreeW2){
                    listW.add(this.listOfFactors[difference + i] + polynomial_2.listOfFactors[i])
                }
            } else {
                listW = polynomial_2.listOfFactors.subList(0, difference).toMutableList()
                for (i in 0..degreeW1) {
                    listW.add(polynomial_2.listOfFactors[difference + i] + this.listOfFactors[i])
                }
            }

        } else{
            for ( i in 0..degreeW1){
                listW.add(polynomial_2.listOfFactors[i] + this.listOfFactors[i])
            }
        }

        this.listOfFactors = listW
    }

    /**
     * @author  Wiktoria Pabiś
     *
     * Przeciążony operator odejmowania odejmuje jeden wielomian od drugiego. Zawarte są w nim zmienne: listW, który jest mutowalną
     * listą typu Double, degreeW1, czyli bieżący wielomian oraz degree W2, który zostaje odjęty
     * w wyniku przeciążenia. Zmienna difference jest typu Int i zaiwera informację o różnicy stopni
     * wielomianu.
     * Operator przeciążenia początkowo sprawdzq czy stopnie wielomianu są sobie równe, jeśli nie
     * to, sprawdza, który wielomian ma większy stopień, następnie odpowiednio odejmuje do siebie
     * wielomiany, w zależności od wyniku.
     * Wynik zostaje zapisany w nowej zmiennej polynomial3.
     *
     * @param   polynomial_2    drugi wielomian
     * @return                  nowy wielomian będący różnicą dwóch wielomianów
     */
    operator fun minus(polynomial_2: Wielomian): Wielomian{
        var listW: MutableList<Double> = mutableListOf()
        var degreeW1 = this.StopienWielomianu()
        var degreeW2 = polynomial_2.StopienWielomianu()
        var difference: Int = abs(degreeW1 - degreeW2)
        if (degreeW1 != degreeW2){
            if (degreeW1 > degreeW2){
                listW = this.listOfFactors.subList(0, difference).toMutableList()
                for ( i in 0..degreeW2){
                    listW.add(this.listOfFactors[difference + i] - polynomial_2.listOfFactors[i])
                }
            } else {
                listW = polynomial_2.listOfFactors.subList(0, difference).toMutableList()
                for (i in 0..listW.size-1){
                    listW[i] *= -1.0
                }
                for (i in 0..degreeW1) {
                    listW.add(this.listOfFactors[i] - polynomial_2.listOfFactors[difference + i])
                }
            }

        } else{
            for ( i in 0..degreeW1){
                listW.add(this.listOfFactors[i] - polynomial_2.listOfFactors[i])
            }
        }

        var polynomial3 = Wielomian(listW)
        return polynomial3
    }

    /**
     * @author  Wiktoria Pabiś
     *
     * Przeciążony operator złożony odejmowania odejmuje jeden wielomian od drugiego. Zawarte są w nim zmienne: listW, który jest mutowalną
     * listą typu Double, degreeW1, czyli bieżący wielomian oraz degree W2, który zostaje odjęty
     * w wyniku przeciążenia. Zmienna difference jest typu Int i zaiwera informację o różnicy stopni
     * wielomianu.
     * Operator przeciążenia początkowo sprawdzq czy stopnie wielomianu są sobie równe, jeśli nie
     * to, sprawdza, który wielomian ma większy stopień, następnie odpowiednio odejmuje do siebie
     * wielomiany, w zależności od wyniku.
     * Wynik zostaje zapisany w pierwotnym wielomianie
     *
     * @param   polynomial_2    drugi wielomian
     * @return                  nowy wielomian będący różnicą dwóch wielomianów
     */
    operator fun minusAssign(polynomial_2: Wielomian){
        var listW: MutableList<Double> = mutableListOf()
        var degreeW1 = this.StopienWielomianu()
        var degreeW2 = polynomial_2.StopienWielomianu()
        var difference: Int = abs(degreeW1 - degreeW2)
        if (degreeW1 != degreeW2){
            if (degreeW1 > degreeW2){
                listW = this.listOfFactors.subList(0, difference).toMutableList()
                for ( i in 0..degreeW2){
                    listW.add(this.listOfFactors[difference + i] - polynomial_2.listOfFactors[i])
                }
            } else {
                listW = polynomial_2.listOfFactors.subList(0, difference).toMutableList()
                for (i in 0..listW.size-1){
                    listW[i] *= -1.0
                }
                for (i in 0..degreeW1) {
                    listW.add(this.listOfFactors[i] - polynomial_2.listOfFactors[difference + i])
                }
            }

        } else{
            for ( i in 0..degreeW1){
                listW.add(this.listOfFactors[i] - polynomial_2.listOfFactors[i])
            }
        }

        this.listOfFactors = listW
    }

    /**
     * @author  Wiktoria Pabiś
     *
     * Operator przeciążony mnożenia mnoży dwa wielomiany. Zmienna x zawiera informację o rozmiarze wielomianów.
     * Zmienna listW to mutowalna lista typu Double, zawierająca zmienną x.
     *
     * @param   polynomial_2    drugi wielomian
     * @return                  nowy wielomian będący iloczynem dwóch wielomianów
     */
    operator fun times(polynomial_2: Wielomian): Wielomian{
        var x = this.listOfFactors.size + polynomial_2.listOfFactors.size - 1
        var listW: MutableList<Double> = MutableList(x) {0.0}
        for ((index1, elem) in this.listOfFactors.withIndex()){
            for ((index2,elem2) in polynomial_2.listOfFactors.withIndex()){
                listW[index1 + index2] = listW[index1 + index2] + this.listOfFactors[index1] *
                        polynomial_2.listOfFactors[index2]
            }
        }

        return Wielomian(listW)
    }

    /**
     * @author  Wiktoria Pabiś
     *
     * Złożony operator przeciążenia mnoży dwa wielomiany. Zmienna x zawiera informację o rozmiarze wielomianów.
     * Zmienna listW to mutowalna lista typu Double, zawierająca zmienną x.
     *
     * @param   polynomial_2    drugi wielomian
     * @return                  nowy wielomian będący iloczynem dwóch wielomianów
     */
    operator fun timesAssign(polynomial_2: Wielomian){
        var x = this.listOfFactors.size + polynomial_2.listOfFactors.size - 1
        var listW: MutableList<Double> = MutableList(x) {0.0}
        for ((index1, elem) in this.listOfFactors.withIndex()){
            for ((index2,elem2) in polynomial_2.listOfFactors.withIndex()){
                listW[index1 + index2] = listW[index1 + index2] + this.listOfFactors[index1] *
                        polynomial_2.listOfFactors[index2]
            }
        }

        this.listOfFactors = listW
    }

}


/**
 * @author  Wiktoria Pabiś
 *
 * Funkcja main służąca do demonstracji operacji na wielomianach.
 *
 * Funkcja tworzy wielomian na podstawie listy wartości.
 * Funkcja oblicza stopień wielomianu oraz wyświetla jego tekstową reprezentację.
 * Funkcja oblicza wartość wielomianu dla określonej wartości.
 * Funkcja tworzy drugi wielomian.
 * Funkcja dodaje dwa wielomiany i wyświetla wynik.
 * Funkcja odejmuje drugi wielomian od pierwszego i wyświetla wynik.
 * Funkcja mnoży dwa wielomiany i wyświetla wynik.
 */
fun main(){
    var listOfValue:MutableList<Double> = mutableListOf(-4.0 ,2.0)
    var newPolynomial = Wielomian(listOfValue)
    var degreeOfPolynomial = newPolynomial.StopienWielomianu()
    println("................................................")
    println("Stopień wielomianu: $degreeOfPolynomial")
    println("................................................")
    println("Wielomian pierwszy: ")
    var textPolynomial1 = newPolynomial.TekstWielomian()
    println(textPolynomial1)
    var resultOfPolynomial = newPolynomial(9.0)
    println("................................................")
    println("Wynik wielomianu: $resultOfPolynomial")

    var Polynomial_2 = Wielomian(mutableListOf(0.0, 0.5, 2.0))
    println("................................................")
    println("Wielomian drugi: ")
    println(Polynomial_2.TekstWielomian())

    var addResult = newPolynomial + Polynomial_2
    println("................................................")
    println("Wynik doodawania: ${newPolynomial.TekstWielomian()} + ${Polynomial_2.TekstWielomian()}")
    println(addResult.TekstWielomian())

    var minusResult = newPolynomial - Polynomial_2
    println("................................................")
    println("Wynik odejmowania: ${newPolynomial.TekstWielomian()} - ${Polynomial_2.TekstWielomian()}")
    println(minusResult.TekstWielomian())

//    newPolynomial += Polynomial_2
//    println(newPolynomial.TekstWielomian())

//    newPolynomial -= Polynomial_2
//    println(newPolynomial.TekstWielomian())

    var multiplicationResult = newPolynomial * Polynomial_2
    println("................................................")
    println("Wynik mnożenia: ${newPolynomial.TekstWielomian()} * ${Polynomial_2.TekstWielomian()}")
    println(multiplicationResult.TekstWielomian())

//        newPolynomial *= Polynomial_2
//    println(newPolynomial.TekstWielomian())

}



class WielomianTest {

    @Test
    fun testConstructor(){
        val validPolynomial = Wielomian(mutableListOf(1.0, 2.0, 3.0))
        assertEquals(mutableListOf(1.0, 2.0, 3.0), validPolynomial.listOfFactors, "Niepoprawnie " +
                "utworzone współczynniki wielomianu")

        val zeroPolynomial = Wielomian(mutableListOf(0.0, 0.0, 2.0))
        assertEquals(mutableListOf(2.0), zeroPolynomial.listOfFactors, "Niepoprawnie " +
                "utworzone współczynniki wielomianu")

        assertFailsWith<IllegalArgumentException>("Niepoprawnie obsłużony wyjątek"){
            Wielomian(mutableListOf())
        }
    }

    @Test
    fun polynomialDegree(){
        val wielomian = Wielomian(mutableListOf(2.0, 3.0, 4.0))
        assertEquals(2, wielomian.StopienWielomianu(), "Niepoprawnie obliczony stopień" +
                "wielomianu")
    }

    @Test
    fun polynomialDegree1(){
        val wielomian = Wielomian(mutableListOf(2.0, 3.0, 0.0, 4.0))
        assertEquals(3, wielomian.StopienWielomianu(), "Niepoprawnie obliczony stopień" +
                "wielomianu")
    }

    @Test
    fun polynomialDegree2(){
        val wielomian = Wielomian(mutableListOf(0.0, 0.0, 2.0, 3.0, 0.0, 4.0))
        assertEquals(3, wielomian.StopienWielomianu(), "Niepoprawnie obliczony stopień" +
                "wielomianu")
    }

    @Test
    fun polynomialText(){
        val listOfValue: MutableList<Double> = mutableListOf(-4.0, 2.0, 0.0, 0.0)
        val wielomian = Wielomian(listOfValue)
        val tekstWielomianu = wielomian.TekstWielomian()
        assertEquals("W(x)= -4.0x^3+2.0x^2", tekstWielomianu, "Zły zapis wielomianu")
    }

    @Test
    fun resultOfPolynomailInvoke(){
        val listOfValue: MutableList<Double> = mutableListOf(-4.0, 2.0, 0.0, 0.0)
        val wielomian = Wielomian(listOfValue)
        val result = wielomian(2.0)
        assertEquals(-24.0, result, "Zły wynik wielomianu")
    }

    @Test
    fun polynomialAdd() {
        val wielomian1 = Wielomian(mutableListOf(-4.0, 2.0, 0.0, 0.0))
        val wielomian2 = Wielomian(mutableListOf(0.0, 0.5, 2.0))
        val wynikDodawania = wielomian1 + wielomian2
        assertEquals("W(x)= -4.0x^3+2.0x^2+0.5x+2.0", wynikDodawania.TekstWielomian(),
            "Niepoprawny wynik dodawania")
    }

    @Test
    fun polynomialMinus() {
        val wielomian1 = Wielomian(mutableListOf(-4.0, 2.0, 0.0, 0.0))
        val wielomian2 = Wielomian(mutableListOf(0.0, 0.5, 2.0))
        val wynikOdejmowania = wielomian1 - wielomian2
        assertEquals("W(x)= -4.0x^3+2.0x^2-0.5x-2.0", wynikOdejmowania.TekstWielomian(),
            "Niepoprawny wynik odejmowania")
    }

    @Test
    fun polynomialMultiplication() {
        val wielomian1 = Wielomian(mutableListOf(-4.0, 2.0, 0.0, 0.0))
        val wielomian2 = Wielomian(mutableListOf(0.0, 0.5, 2.0))
        val wynikMnozenia = wielomian1 * wielomian2
        assertEquals("W(x)= -2.0x^4-7.0x^3+4.0x^2", wynikMnozenia.TekstWielomian(),
            "Niepoprawny wynik mnożenia")
    }
}



