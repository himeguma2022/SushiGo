import java.util.Scanner;
import java.util.Stack;

public class Player {
    private String name;
    private Stack<Card> puddingPile;
    private CardSet scoring;
    private CardSet hand;
    private int score;
    private int behavior;

    public Player() {
        name = "Takumi";
        puddingPile = new Stack<Card>();
        scoring = new CardSet();
        hand = new CardSet();
        score = 0;
        behavior = 0;
    }

    public Player(String name) {
        this.name = name;
        puddingPile = new Stack<Card>();
        scoring = new CardSet();
        hand = new CardSet();
        score = 0;
        behavior = 0;
    }

    public Player(String name, int behavior) {
        this.name = name;
        puddingPile = new Stack<Card>();
        scoring = new CardSet();
        hand = new CardSet();
        score = 0;
        this.behavior = behavior;
    }

    public void DealIntoHand(Card pop) {
        hand.AddToSet(pop);
    }

    public void HandToScore() {
        scoring.AddToSet(hand.TakeOut(chooseFromHand()));

    }

    private int chooseFromHand() {
        switch (behavior) {
            case -1:
                return UserChoose();
            default:
                if (Math.random() > 0.5 && scoring.SetHasCard(new Card("Chopsticks"))) {
                    UseChopsticks();
                }
                return 0;
        }
    }

    private int UserChoose() {
        System.out.println("Choose a card in your hand");
        if (scoring.SetHasCard(new Card("Chopsticks"))) {
            System.out.println("Type -1 to to use chopsticks!");
        }
        hand.ListCard();
        Scanner userInput = new Scanner(System.in);
        int choice = userInput.nextInt();
        if (choice > -1 && choice < hand.getSize()) {
            return choice;
        }
        if (choice == -1 && scoring.SetHasCard(new Card("Chopsticks"))) {
            UseChopsticks();
        }
        return UserChoose();
    }

    public CardSet getHand() {
        return hand;
    }

    public void setHand(CardSet hand) {
        this.hand = hand;
    }

    public void GradeScoring() {
        scoring.EvalSet();
    }

    public int ShowMaki() {
        return scoring.getMakiVal();
    }

    public void AddToScore(int amount) {
        score = score + amount;
    }

    public void RecordRoundScore() {
        score = score + scoring.getIndiValue();
    }

    public String toString() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public CardSet getScoring() {
        return scoring;
    }

    public void HoldPudding() {
        for (int i = scoring.getSize() - 1; i > -1; --i) {
            if (scoring.peek(i).getCardType() == 9) {
                puddingPile.push(scoring.TakeOut("Pudding"));
            }
        }
    }

    public Card TakeFromScoring(int index) {
        return scoring.TakeOut(index);
    }

    public Card TakeFromScoring(String target) {
        return scoring.TakeOut(target);
    }

    public boolean IsScoringEmpty() {
        return scoring.isEmpty();
    }

    public int PuddingsAccumulated() {
        return puddingPile.size();
    }

    public void LastCardAddedToScore() {
        System.out.println(name + " added " + scoring.lastCardAdded().toString());
    }

    public void UseChopsticks() {
        if (hand.getSize() > 1) {
            hand.AddToSet(scoring.TakeOut("Chopsticks"));
            System.out.println(name + " uses chopsticks!");
            switch (behavior) {
                case -1:
                    scoring.AddToSet(hand.TakeOut(UserChoose()));
                    break;
                default:
                    scoring.AddToSet(hand.TakeOut(0));
            }
        }
    }
}
