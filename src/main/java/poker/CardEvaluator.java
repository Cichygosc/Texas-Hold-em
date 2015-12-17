package poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

///////////////////////////////////////////////////////////
//////////////////PORADNIK ŻÓŁTODZIOBA/////////////////////
///////////////////////////////////////////////////////////
/*
1. Wszystkie poniższe algorytmy można napisać nie umiejąc programować.
2. Wymagana jest tylko znajomość pętli for oraz tworzenia zmiennych.
3. Oprócz tego potrzebujesz umiejętności logicznego myślenia oraz kartki papieru (lub kilku kartek) i długopisu.
4. Wszyskie metody działają tak samo. (biorą ostatnią kartę z listy i dobierają do niej cztery kolejne aby stworzyć daną kombinację)
5. Wyjątkiem od powyższej reguły są straight, flush oraz full.
6. Zacznij od przestudiowania co się zmieniło.
7. Nazwy metod ustal jakie chcesz.
8. Lista kroków dla każdego algorytmu jest na dole.
9. Każdy algorytm przestudiuj na kartce na dowolnym przykładzie.
10. Jeżeli obawiasz się że nie ogarniesz to weź największą książkę jaką masz w mieszkaniu (albo coś innego) i walnij się w łeb.
11. Jeżeli masz nadal jakieś obawy to wróć do punktu poprzedniego.
12. Najprawdopodobniej boli cię właśnie mocno głowa ale jesteś już zmotywowany do pracy.
13. Powodzenia.
*/


public class CardEvaluator {

	private static volatile CardEvaluator instance;
	private HashMap<Integer, Integer> valueMap;
	private HashMap<Integer, Integer> suitMap;
	
	private CardEvaluator()
	{}
	
	public BestHand checkCards(List<Card> cards)
	{
		BestHand hand = new BestHand();
		Collections.sort(cards);
		createValueMap(cards);

		//Musisz sprawdzić jaką kobinację ma gracz. (masz od tego metody is...)
		//Sprawdzanie zacznij od najmocniejszej kombinacji.
		//Jeżeli gracz ma np parę to wywołujesz metodę do znalezienia tej pary.
		//W pseudokodzie metoda ta wygląda mniej więcej tak:
		/*
		 if (Jest poker)
		 	listaKart = znajdźPoker;
		 	ranga = rangaPokera (w BestHand w komentarzu są rangi)
		 w przeciwnym wypadku if (Jest kareta)
		 	listaKart = znajdz karete;
		 	ranga = rangaKarety
		 w przciwnym wypadku if ....... 
		 */
		// Następnie obiektowi klasy BestHand ustawiasz listę kart oraz rangę
		// zwracasz ten obiekt
		// to tyle. Teraz pozostało napisać metody od znajdowania 5 kart.
		return hand;
	}
	
	/*
	 * Wyrzuciłem tu powtarzający się kod w poniższych metodach.
	 */
	private void createValueMap(List<Card> cards)
	{
		valueMap = new HashMap<Integer, Integer>();
		for (int i = 0; i < cards.size(); ++i)
		{
			Integer count = valueMap.get(cards.get(i).getNumber());
					if (count == null)
				valueMap.put(cards.get(i).getNumber(), 1);
			
			else valueMap.put(cards.get(i).getNumber(), count + 1);
		}
	}
	
	public boolean isStraightFlush(List<Card> cards)
	{
		if (isStraight(cards) && isFlush(cards))
			return true;
		return false;
	}
	
	public boolean isFourOfKind(List<Card> cards)
	{
		if (valueMap.containsValue(4))
			return true;
		return false;
	}
	
	public boolean isFullHouse(List<Card> cards)
	{
		if (valueMap.containsValue(3) && valueMap.containsValue(2))
			return true;
		return false;
	}
	
	/*
	 * 
	 */
	public boolean isStraight(List<Card>  cards)
	{
	    return false;
	}
	
	public boolean isFlush(List<Card> cards)
	{
		suitMap = new HashMap<Integer, Integer>();
		for (int i = 0; i < cards.size(); ++i)
		{
			Integer count = suitMap.get(cards.get(i).getSuit());
			
			if (count == null)
				suitMap.put(cards.get(i).getSuit(), 1);
			
			else suitMap.put(cards.get(i).getSuit(), count + 1);
		}
		
		if (suitMap.containsValue(5))
			return true;
		return false;
	}
	
	public boolean isThreeOfAKind(List<Card> cards)
	{
		if (valueMap.containsValue(3))
			return true;
		return false;
	}
	
	
	public boolean isTwoPair(List<Card> cards)
	{   
		
		Card tmpCard = null;
        int pairCount = 0;
        for (Card card : cards) {
            
            if (tmpCard != null && (tmpCard.getNumber() == card.getNumber())) {
                pairCount = pairCount + 1;
            }
            tmpCard = card;

        }
       
        if (pairCount == 2)
            return true;

        return false; 
	}
	
	public boolean isOnePair(List<Card> cards)
	{
		if (valueMap.containsValue(2))
			return true;
		return false;
	}
	
	/*
	 * Musisz stworzyć taką metodę dla każdej kombinacji.
	 * Jako parametr przyjmuje listę 7 kart.
	 * Zwraca listę 5 kart.
	 * 
	 * Przykładowy algorytm wybierający 5 najlepszych kart gdy nie ma żadnego układu (bierzemy poprostu 5 ostatnich kart)
	 */
	public List<Card> findHighCard(List<Card>cards)
	{
		List<Card> bestCards = new ArrayList<Card>();
		for (int i = cards.size() - 1; i >= 2; --i)
			bestCards.add(cards.get(i));
		return bestCards;
	}
	
	public static CardEvaluator getInstance()
	{
		if (instance == null)
		{
			synchronized(CardEvaluator.class)
			{
				if (instance == null)
				{
					instance = new CardEvaluator();
				}
			}
		}
		return instance;
	}
	
///////////////////////////////////////////////
////////////////ALGORYTMY//////////////////////
/////////////W WERSJI LIGHT////////////////////
///////////////////////////////////////////////
	
	
/*
/////////////PARA/////////////////////
 
 na początek przypomnę że karty są ustawione w kolejności od 'najsłabszej' do 'najlepszej'
 
 aby wyciągnąc pięc nalepszych kart potrzebujemy dwóch o tej samej wartości oraz 3 o najwyższych wartościach z pozostałych 5 kart
 jak zuważyłeś będziemy mięc wtedy najlepszą parę, teraz algorytm
 
 będziesz potrzebował kilku zmiennych pomocniczych
 boolean, int oraz listę nowych kart
 zmienna boolean będzie mówić czy znaleziono już parę 
 int będzie odpowiadać wartości karty dla której pary szukamy
 w liście kart znajdzie się 5 najlepszych kart (wśród którch jest para)
 
 boolean ustawiamy na false (jeszcze nie znaleźliśmy pary)
 int ustwiamy na wartośc ostatniej karty
 do nowej listy możemy dodać ostatnią kartę (nawet jeżeli nie jest to karta z pary to i tak znajdzie się w liście bo ma najwyższą wartość)
 
 OSTATNI INDEKS W LIŚCIE TO 6!!

tworzymy pętle od PRZED ostatniego elementu do elementu pierwszego  (pierwszy element nie ma indeksu 1)
-sprawdzamy czy para została już odnaleziona (jeżeli nie wiesz jak to sprawdzić to przeczytaj jeszcze raz od początku albo zrób se przerwę)
 	-jeżeli NIE została odnaleziona to sprawdzamy czy wartość której szukamy jest równa wartości sprawdzanej karty
 		-jeżeli wartości są równe to zmienną bool ustawiamy na true (udało się znaleźć parę) oraz dodajemy kartę do listy)
 		-jeżeli wartości nie są równe to do wartości poszukiwanej przypisujemy wartość karty sprawdznej i sprawdzamy czy na liście są mniej niż 3 karty
 			-jeżeli są mniej niż 3 karty to dodajemy kartę do listy
 	-jeżeli para została już znaleziona to sprawdzamy czy na liście jest mniej niż 5 kart
 		-jeżeli tak to dodajemy kartę 
 		-w przeciwnym wypadku wychodzimy z pętli (służy do tego polecenie break) (wychodzimy ponieważ mamy już 5 kart w liście)
 		
 		
 zwracamy wypełnioną listę 
 
 
 ////////KONIEC PARY////////////
 
 jeżeli udało ci się to ogarnąć to prawie każdy poniższy alogrytm zajmie ci 10 minut (wszystkie działają tak samo) :)
 
 
 ///////////DWIE PARY///////////
 
zadanie rozpoczynamy tak samo z tą różnicą że zmienną bool zamieniamy na int (będziemy zliczać ile już znaleźliśmy par)
w sumie inicjujemy dwie zmienne int oraz listę kart
do jednej zmiennej int przypisujemy 0 (mamy narazie 0 par)
do drugiej wartośc ostatniej karty (jest to wartość której szukamy)
do listy dodajemy ostatnią kartę

algorytm jest skróconą wersją poprzedniego

tworzymy pętle od PRZED ostatniego elementu do elementu pierwszego
-sprawdzamy czy dwie pary zostały już odnalezione (od tego ile mamy par masz zmienną int)
 	-jeżeli NIE zostały znalezione to sprawdzamy czy wartość której szukamy jest równa wartości sprawdzanej karty
 		-jeżeli wartości są równe to zmienną int zwiększamy (masz dwie zmienne ale chyba skapniesz się którą) oraz dodajemy kartę do listy)
 		-jeżeli wartości nie są równe to do wartości poszukiwanej przypisujemy wartość karty sprawdznej
 	-jeżeli dwie pary zostały już znalezione to sprawdzamy czy na liście jest mniej niż 5 kart
 		-jeżeli tak to dodajemy kartę 
 		-w przeciwnym wypadku wychodzimy z pętli
 		
 zwracamy listę
 
 
 ////////KONIEC DWÓCH PAR//////////////////
 
 ///////TRÓJKA///////////////
 
 algorytm jest mixem dwóch powyższych
 potrzebujesz tych samych zmiennych co do dwóch par 
 po raz ostatni napiszę do listy dodajemy ostatnią kartę 
 
 tworzymy pętle od PRZED ostatniego elementu do elementu pierwszego  
-sprawdzamy czy trójka została już odnaleziona
 	-jeżeli NIE została odnaleziona to sprawdzamy czy wartość której szukamy jest równa wartości sprawdzanej karty
 		-jeżeli wartości są równe to zmienną int zwiększamy oraz dodajemy kartę do listy)
 		-jeżeli wartości nie są równe to do wartości poszukiwanej przypisujemy wartość karty sprawdznej i sprawdzamy czy na liście są mniej niż 2 karty
 			-jeżeli są mniej niż 2 karty to dodajemy kartę do listy
 	-jeżeli trójka została już znaleziona to sprawdzamy czy na liście jest mniej niż 5 kart
 		-jeżeli tak to dodajemy kartę 
 		-w przeciwnym wypadku wychodzimy z pętli
 
 
 
 /////////////KONIEC TRÓJKI/////////////////

 jak już pewnie zauważyłeś robimy ciągle to samo tylko zmieniamy ilość kart do dobrania
 
 
 /////////////// STRAIGHT/////////////////
 
 Ten algorytm jest NAJPROSTSZY więc się go nie bój :)
 Właściwie to tylko seria ifów.
 
 Przypomnienie (uproszczenie tego jak wygląda strit)
 Strita będziemy mieć wtedy gdy natrafimy na 3 następujące po sobie karty. Dlaczego tylko 3? Jeżeli zadałeś sobie to pytanie to czytaj dalej.
 Możliwości strita (indeksy kart):
 
 0 1 2 3 4
 1 2 3 4 5
 2 3 4 5 6
 
 Jako iż mamy metodę isStraight, która nam zwróciła true wiemy że mamy strita (niewiemy gdzie się zaczyna)
 Jeżeli teraz sprawdzimy 3 pierwsze karty z powyższych kombinacji i okaże się że następują po sobie to znaczy że dwie kolejne muszą też następować po sobie.
 Jeżeli nie skumałeś powyższego zdania to nic się nie stało :)
 Napisz sobie na kartce kilka stritów i spójrz na indeksy tych kart.
 W sumie mamy tylko 3 możliwości strita więc wystarczą nam zwykłe ify.
 
 algorytm:
 sprawdzamy czy karty na indeksach 0, 1, 2 następują po sobie (nie pytaj jak to sprawdzić........ wystarczy dodać do wartości karty pierwszej 1 a do drugiej 2)
 jeżeli tak to do listy dodajemy karty na indeksach 0,1,2,3,4
 jeżeli nie to sprawdzamy czy karty na indeksach 1, 2, 3 następują po sobie
 jeżeli tak to do listy dodajemy karty na indeksach 1,2,3,4,5
 jeżeli nie to sprawdzamy czy karty na indeksach 2, 3, 4 następują po sobie
 jeżeli tak to do listy dodajemy karty na indeksach 2,3,4,5,6
 zwracamy listę kart
 
 EDIT
 powyższy algorytm powinien iść od końca!!!!!
 tzn. pierwsze sprawdzasz karty na pozycjach 2 3 4
 dlaczego?
 np dla 2 3 4 5 6 7 8
 stritem jest 4 5 6 7 8 a nie 2 3 4 5 6
 
 //////////KONIEC STRITA///////////////////
	
//////////////////FLUSH////////////////////
 
do napisania tutaj masz tylko jedną pętlę z jednym ifem więc jest jeszcze łatwiej niż przy stricie
jako iż w tym algorytmie używam mapy co wykracza poza wymagania przedstawione na górze napisałem kawałek za ciebie
jak już pewnie zuważyłeś dodałem dwie mapy na górze
teraz użyjemy tej drugiej
	
wiemy że mamy kolor ale nie wiemy jaki to kolor
tworzymy pętlę która będzie iterować nam przez całą mapę
jeżeli nie rozumiesz tego fragmentu to nic się złego nie dzieje
	
poniższy kod szuka nam koloru naszego flusha
	int suit = -1;
		for (Entry<Integer, Integer> entry : suitMap.entrySet()) {
	            if (entry.getValue().intValue() >= 5) {
		            suit = entry.getKey();
		            break;
	            }
		}
jako iż wiemy już jaki mają kolor nasze karty to
tworzymy pętlę od końca listy do początku
-jeżeli karta ma ten sam kolor co kolor który wyżej znależliśmy to dodajemy ją do listy i sprawdzamy czy mamy już 5 kart
-jeżeli mamy już 5 kart to wychodzimy z pętli

zwracamy listę kart
	
////////////////KONIEC FLUSHA/////////////////
	
jak już pewnie zuwżyłeś udało ci się napisać ponad połowę możliwości znając tylko pętle for (i używając trochę głowy)
jedziemy dalej
	
////////////////////MOCNY FULL////////////////////


full jest najtrudniejszym zadaniem ale nie znaczy to że jest trudny
jeżeli wpadaniesz na jakiś kultularny pomysł jak to zrobić bardziej zrozumiale to spróbuj go zaimplementować (albo daj znać może coś pomogę)

nie mam na chwilę prostego pomysłu więc spróbuj zrozumięc co się tu wyprawia
to będzie ostatni przkład z mapami, obiecuję

dla przypadku np 1 1 3 3 4 4 4
nasza hashamapa wygląda tak
Klucze:   1 3 4
Wartości: 2 2 3
jak więc można łatwo odczytać mamy 3 czwórki, dwie trójki i dwie dwójki
mocniejszą parą jest oczywiście para trójek
czyli wartość trójki to 4 (mamy 3 czwórki), wartość dwójki to trzy(mamy 2 trójki, jedynki są gorsze więc je odrzucamy)
mając taką wiedzę możemy zrobić tak
stworzyć pętlę od początku listy kart do końca
i sprawdzać czy wartość karty jest równa wartości naszej trójki lub dwójki
jeżeli tak to dodajemy ją do listy kart

pewnie odleciałeś właśnie w kosmos ale coś takiego zrobimy
aby skrócić twoje męki trochę napiszę 

value1 to będzie wartość naszej trójki
value2 to będzie wartość naszej dwójki
		int value1 = -1;
		int value2 = -1;
		for (Entry<Integer, Integer> entry : valueMap.entrySet()) {
	            if (entry.getValue().intValue() == 3) 
		            value1 = entry.getKey();
	            else if (entry.getValue().intValue() == 2 && entry.getValue().intValue > value2)
	            	value2 = entry.getKey();
		}
	powyższego kodu nie będę opisywał ale jak szczaiłeś o co w nim chodzi to się bardzo cieszę
	
znamy już wartość trójki i dwójki więc teraz twoja kolej
stwórz pętlę od początki do końca (albo na odwrót nie ma różnicy)
jeżeli wartość karty jest równa wartości trójki lub dwójki to dodaj ją do listy kart
	
	zwróć listę kart
	
	
//////////////////KONIEC FULLA (MOCNEGO)///////////////////////////////
	
	
//////////////////////////CZWÓRKA///////////////////////////////
	
czas na powrót do przeszłości 
jak pewnie zauważyłeś można to zrobić tak jak parę, trójkę bądź dwójkę
	
zrobimy to jeszcze prościej!!!!

tworzymy pętlę dla pierwszych czterech elementów. Dlaczego czterech? Ponieważ kareta porzebuje 4 elementów. Zaraz zobaczysz o co chodzi.
jeżeli wartość karty na pozycji i-tej jest równa karcie na pozycji i+1 oraz jest równa karcie na pozycji i+2 i jest równa karcie na pozycji i+3
to do listy kart dodaj te cztery karty. Sprawdzamy licznik pętli. Jeżeli i < 3 to do listy dodajemy kartę na pozycji ostatniej w przeciwnym wypdaku
dodajemy kartę na pozycji 2 (czyli trzecią kartę). 

Jeżeli nie wiesz dlaczego tak to weź kartę i wypisz kilka przykładów.

	
////////////////////KONIEC CZWÓRKI/////////////////////////////////	
	
Został ostatni pacjent

//////////////////POKER///////////////////////

użyjemy ty zabójczej kombinacji algorytmów na straighta oraz flusha (jak napewno pamiętasz nie były one trudne)
	
wpierw musimy znaleźć kolor naszego flusha 
z algorytmu na flusha chyba wiesz jak to zrobić (jak nie wiesz jak to zrobić to poprostu skopiuj, użyliśmy do tego map więc możesz 	nie ogarniać)

teraz tak jak dla strita sprawdzamy 3 ify z tym że musimy sprawdzać też kolor tych kart nie tylko wartości	
powinienieś sobie z tym poradzić (jest to tylko lekka modyfikacja)
	
///////////////KONIEC POKERA//////////////////
	
	*/
	
}
