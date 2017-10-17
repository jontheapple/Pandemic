import java.util.*;

public class Pandemic {

	private HashMap<String, City> map;
	private ArrayList<PlayerCard> playerDeck;
	private ArrayList<PlayerCity> infectionDeck;
	private int infectionCounter; //The location of where the counter is, from 1 to 7
	private int infectionRate; //The actual infection rate
	private int outbreaks; //Number of outbreaks. Game is lost at 8 outbreaks
	private int researchCentersLeft; //Number of research centers not yet placed. Initializes to 6.
	private HashMap<Disease.Type, Disease> diseases;
	private ArrayList<Player> players;
	private long seed;
	private Random rng;

	public Pandemic(int numPlayers, int numEpidemics) {
		rng = new Random();
	
		//Initialize Board
		infectionRate = 2;
		infectionCounter = 1;
		outbreaks = 0;
		researchCentersLeft = 6;

		//Initialize Diseases
		diseases = new HashMap<Disease.Type, Disease>();
		for (Disease.Type t : Disease.Type.values()) {
			diseases.put(t, new Disease(t));
		}

		players = new ArrayList<Player>();
		for (int i = 0; i < numPlayers; i++) {
			players.add(new Player("Atlanta"));
		}
		initializeMap();
		initializePlayerDeck(numEpidemics);

		//Initialize Infection Deck
		infectionDeck = new ArrayList<PlayerCity>();
		for (String cityName : getCities()) {
			infectionDeck.add(new PlayerCity(cityName));
		}
	}

	private void initializeMap() {
		map = new HashMap<String, City>();

		City atlanta = new City("Atlanta", Disease.Type.BLUE);
		atlanta.addConnectedCity("Chicago");
		atlanta.addConnectedCity("Washington");
		atlanta.addConnectedCity("Miami");
		map.put("Atlanta", atlanta);

		map.put("Chicago", new City("Chicago", Disease.Type.BLUE));
		map.get("Chicago").addConnectedCity("Atlanta");
		// map.get("Chicago").addConnectedCity("Montreal");
		// map.get("Chicago").addConnectedCity("Mexico City");
		// map.get("Chicago").addConnectedCity("Los Angeles");
		// map.get("Chicago").addConnectedCity("San Francisco");

		map.put("Washington", new City("Washington", Disease.Type.BLUE));
		// map.get("Washington").addConnectedCity("Montreal");
		map.get("Washington").addConnectedCity("Atlanta");
		// map.get("Washington").addConnectedCity("New York");
		map.get("Washington").addConnectedCity("Miami");

		map.put("Miami", new City("Miami", Disease.Type.YELLOW));
		map.get("Miami").addConnectedCity("Atlanta");
		map.get("Miami").addConnectedCity("Washington");
		// map.get("Miami").addConnectedCity("Mexico City");
		// map.get("Miami").addConnectedCity("Bogota");
	}

	private void initializePlayerDeck(int numEpidemics) {
		playerDeck = new ArrayList<PlayerCard>();
		for (String cityName : getCities()) {
			playerDeck.add(new PlayerCity(cityName));
		}

		//Event Cards
		playerDeck.add(new PlayerEvent(PlayerEventType.AIRLIFT));
		playerDeck.add(new PlayerEvent(PlayerEventType.FORECAST));
		playerDeck.add(new PlayerEvent(PlayerEventType.GOVERNMENT_GRANT));
		playerDeck.add(new PlayerEvent(PlayerEventType.ONE_QUIET_NIGHT));
		playerDeck.add(new PlayerEvent(PlayerEventType.RESILIENT_POPULATION));

		//TODO: shuffle deck
		public void shuffle (ArrayList<PlayerCard> deck){
			int nextCardIndex = 0;
			int cardsToShuffle = deck.size();
			int cardsShuffled = 0;
			while (cardsToShuffle > 0){
				nextCardIndex = rng.nextInt() % cardsToShuffle;
				PlayerCard cardRemoved = deck.remove(nextCardIndex + cardsShuffled);
				deck.add(0, cardRemoved);
				cardsShuffled++;
				cardsToShuffle--;
			}
		}

		//TODO: put epidemic cards in evened out places
		for (int i = 0; i < numEpidemics; i++) {
			playerDeck.add(new PlayerEpidemic());
		}
	}

	int debugGetInfectionCounter() {
		return infectionCounter;
	}

	void debugSetInfectionCounter(int infectionCounter) {
		this.infectionCounter = infectionCounter;
	}

	int debugGetInfectionRate() {
		return infectionRate;
	}

	void debugSetInfectionRate(int infectionRate) {
		this.infectionRate = infectionRate;
	}

	int debugGetOutbreaks() {
		return outbreaks;
	}

	void debugSetOutbreaks(int outbreaks) {
		this.outbreaks = outbreaks;
	}

	int debugGetResearchCentersLeft() {
		return researchCentersLeft;
	}

	void debugSetResearchCentersLeft(int researchCenters) {
		this.researchCentersLeft = researchCenters;
	}

	// returns ArrayList of cities
	public ArrayList<String> getCities() {
		return new ArrayList<String>(map.keySet());
	}
	
	public void setSeed(long seed){
		this.seed = seed;
		rng.setSeed(seed);
	}
	
	void debugGetSeed(){
		return seed;
	}
}