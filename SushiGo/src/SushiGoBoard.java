import java.util.Stack;

public class SushiGoBoard {
    private Player[] cast;
    private Stack<Card> deck;
    private Stack<Card> discard;

    public static void main(String[] args) {
        SushiGoBoard demo = new SushiGoBoard();
    }

    public SushiGoBoard() {
        cast = new Player[5];
        cast[0] = new Player("Takumi");
        cast[1] = new Player("Tohru");
        cast[2] = new Player("Nanami");
        cast[3] = new Player("Miku");
        cast[4] = new Player("Hanako");
        deck = new Stack<Card>();
        AddMultiToDeck("Tempura", 14);
        AddMultiToDeck("Sashimi", 14);
        AddMultiToDeck("Dumpling", 14);
        AddMultiToDeck("1 Maki Roll", 6);
        AddMultiToDeck("2 Maki Roll", 12);
        AddMultiToDeck("3 Maki Roll", 8);
        AddMultiToDeck("Squid", 5);
        AddMultiToDeck("Salmon", 10);
        AddMultiToDeck("Egg", 5);
        AddMultiToDeck("Pudding", 10);
        AddMultiToDeck("Wasabi", 6);
        AddMultiToDeck("Chopsticks", 4);
        ShuffleDeck(10);
        discard = new Stack<Card>();
        Play();
    }

    public SushiGoBoard(int playerCount) {
        cast = new Player[playerCount];
    }

    private void Play() {
        Deal();
        Round();
        EndRound();
        Deal();
        Round();
        EndRound();
        Deal();
        Round();
        EndRound();
        Pudding();
        Results();
    }

    private void Results() {
        System.out.println("Results: ");
        for(int i = 0; i < cast.length; ++i){
            System.out.println(cast[i].toString() + " - "+ cast[i].getScore());
        }
    }

    private void Pudding() {
        int maxPudding = 0;
        int playersReachedMax = cast.length;
        int leastPudding = 10;
        int playersReachedLeast = cast.length;
        for(int i = 0; i < cast.length; ++i){
            int numberOfPuddings = cast[i].PuddingsAccumulated();
            System.out.println(cast[i].toString() + " had "+ cast[i].PuddingsAccumulated() + " puddings.");
            
            if(numberOfPuddings == maxPudding){
                ++playersReachedMax;
            }
            if(numberOfPuddings == leastPudding){
                ++playersReachedLeast;
            }
            if(numberOfPuddings > maxPudding){
                maxPudding = numberOfPuddings;
                playersReachedMax = 1;
            }
            if(numberOfPuddings < leastPudding){
                leastPudding = numberOfPuddings;
                playersReachedLeast = 1;
            }
            
        }
        for(int i = 0; i < cast.length; ++i){
            if(cast[i].PuddingsAccumulated() == maxPudding){
                cast[i].AddToScore(6 / playersReachedMax);
            }
            if(cast[i].PuddingsAccumulated() == leastPudding){
                cast[i].AddToScore(-6 / playersReachedLeast);
            }
        }
    }

    private void EndRound() {
        for(int i = 0; i < cast.length; ++i){
            cast[i].GradeScoring();
        }
        int makiMax = 0;
        int maki2ndMax = 0;
        for(int i = 0; i < cast.length; ++i){
            if(cast[i].ShowMaki() > makiMax){
                maki2ndMax = makiMax;
                makiMax = cast[i].ShowMaki();
            }
        }
        int tieFor1st = 1;
        for(int i = 0; i < cast.length; ++i){
            if(cast[i].ShowMaki() == makiMax){
                ++tieFor1st;
            }
        }
        int tieFor2nd = 1;
        for(int i = 0; i < cast.length; ++i){
            if(cast[i].ShowMaki() == maki2ndMax){
                ++tieFor2nd;
            }
        }
        for(int i = 0 ; i < cast.length; ++i){
            cast[i].RecordRoundScore();
            if(cast[i].ShowMaki() == makiMax){
                cast[i].AddToScore(6 / tieFor1st);
            }
            if(cast[i].ShowMaki() == maki2ndMax){
                cast[i].AddToScore(3 / tieFor2nd);
            }
            
            System.out.println(cast[i].toString() + " scored to "+ cast[i].getScore() + " using: ");
            for(int j = 0; j < cast[i].getScoring().getSize(); ++j){
                System.out.println(cast[i].getScoring().cardName(j));
            }
            System.out.println("/////////////////////////////");
        }
        for(int i = 0; i < cast.length; ++i){
            cast[i].HoldPudding();
            while(!cast[i].IsScoringEmpty()){
                discard.push(cast[i].TakeFromScoring(0));
            }
        }
    }

    private void Round() {
        for(int i = 0; i < cast.length; ++i){
            cast[i].HandToScore();
        }
        for(int i = 0; i < cast.length; ++i){
            cast[i].LastCardAddedToScore();
        }
        CardSet temp = cast[0].getHand();
        for(int i = 1; i < cast.length; ++i){
            cast[i - 1].setHand(cast[i].getHand());
        }
        cast[cast.length - 1].setHand(temp);
        System.out.println("-------------------------");
        if(!cast[0].getHand().isEmpty()){
            Round();
        }
    }

    private void Deal() {
        switch (cast.length) {
            case 2:
                for (int i = 0; i < 10; ++i) {
                    for (int j = 0; j < cast.length; ++j) {
                        cast[j].DealIntoHand(deck.pop());
                    }
                }
                break;
            case 3:
                for (int i = 0; i < 9; ++i) {
                    for (int j = 0; j < cast.length; ++j) {
                        cast[j].DealIntoHand(deck.pop());
                    }
                }
                break;
            case 4:
                for (int i = 0; i < 8; ++i) {
                    for (int j = 0; j < cast.length; ++j) {
                        cast[j].DealIntoHand(deck.pop());
                    }
                }
                break;
            case 5:
                for (int i = 0; i < 7; ++i) {
                    for (int j = 0; j < cast.length; ++j) {
                        cast[j].DealIntoHand(deck.pop());
                    }
                }
        }
    }

    public void AddMultiToDeck(int type, int number) {
        if (number != 0) {
            deck.push(new Card(type));
            AddMultiToDeck(type, --number);
        }
    }

    public void AddMultiToDeck(String type, int number) {
        if (number != 0) {
            deck.push(new Card(type));
            AddMultiToDeck(type, --number);
        }
    }

    public void ShuffleDeck(int times) {
        Stack<Card> A = new Stack<Card>();
        Stack<Card> B = new Stack<Card>();
        while (!deck.isEmpty()) {
            double randValue = Math.random();
            if (randValue > 0.5) {
                A.push(deck.pop());
            } else {
                B.push(deck.pop());
            }
        }
        while (!A.isEmpty()) {
            deck.push(A.pop());
        }
        while (!B.isEmpty()) {
            deck.push(B.pop());
        }
        if (times != 0) {
            ShuffleDeck(--times);
        }
    }
}
