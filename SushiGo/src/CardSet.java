import java.util.ArrayList;

public class CardSet {
    private int size;
    private ArrayList<Card> content;
    private int indiValue;
    private boolean activeWasabi;
    private int makiVal;


    public static void main(String[] args) {
        CardSet demo = new CardSet();
        demo.AddToSet(new Card("Wasabi"));
        demo.AddToSet(new Card("Squid"));
        demo.AddToSet(new Card("Tempura"));
        demo.AddToSet(new Card("Tempura"));
        demo.AddToSet(new Card("Dumpling"));
        demo.AddToSet(new Card("Pudding"));
        demo.AddToSet(new Card("1 Maki Roll"));
        demo.EvalSet();
        System.out.println("Set size is "+demo.size+" cards, worth "+demo.indiValue+" points.");
        demo.TakeOut(4);
        demo.EvalSet();
        System.out.println("Set size is "+demo.size+" cards, worth "+demo.indiValue+" points.");
        
    }
    public CardSet() {
        size = 0;
        content = new ArrayList<Card>();
        indiValue = 0;
    }

    public void AddToSet(Card inserting) {
        ++size;
        if(inserting.getCardType() == 10){
            activeWasabi = true;
        }
        if((inserting.getCardType() > 5 && inserting.getCardType() < 9) && activeWasabi){
            activeWasabi = false;
            inserting.Wasabied();
        }
        content.add(inserting);
    }

    public int ScoreSet() {
        int runningScore = 0;
        runningScore = runningScore + TempuraCheck() + SashimiCheck() + DumplingCheck() + BaseContentValue();
        return runningScore;
    }

    public void EvalSet(){
        indiValue = ScoreSet();
        makiVal = makiSum();
    }

    private int makiSum() {
        int out = 0;
        for(int i = 0; i < size; ++i){
            out = out + content.get(i).getMakiVal();
        }
        return out;
    }
    private int BaseContentValue() {
        int out = 0;
        for(int i = 0; i < size; ++i){
            out = out + content.get(i).getValue();
        }
        return out;
    }

    private int DumplingCheck() {
        int key = CardTypeCount(2);
        switch (key) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 3;
            case 3:
                return 6;
            case 4: 
                return 10;
            default:
                return 15;
        }
    }

    private int SashimiCheck() {
        int key = CardTypeCount(1);
        switch (key % 3) {
            case 0:
                return (10 * key) / 3;
            case 1:
                return (10 * (key - 1)) / 3;
            case 2:
                return (10 * (key - 2)) / 3;
        }
        return 0;
    }

    private int TempuraCheck() {
        int key = CardTypeCount(0);
        switch (key % 2) {
            case 0:
                return (5 * key) / 2;
            case 1:
                return (5 * (key - 1)) / 2;
        }
        return 0;
    }

    public int CardTypeCount(int cardType) {
        int out = 0;
        for (int i = 0; i < size; ++i) {
            if (content.get(i).getCardType() == cardType) {
                ++out;
            }
        }
        return out;
    }

    public boolean isEmpty(){
        return content.isEmpty();
    }

    public Card TakeOut(int index){
        --size;
        Card out = content.remove(index);
        out.resetWasabi();
        return out;
    }

    public Card TakeOut(String name){
        for(int i = 0; i < size; ++i){
            if(content.get(i).toString().equals(name)){
                return TakeOut(i);
            }
        }
        return null;
    }

    public int getMakiVal() {
        return makiVal;
    }

    public int getIndiValue() {
        return indiValue;
    }

    public int getSize() {
        return size;
    }

    public String cardName(int index){
        return content.get(index).toString();
    }

    public Card peek(int index){
        return content.get(index);
    }

    public Card lastCardAdded(){
        return peek(size - 1);
    }

    public void ListCard(){
        for(int i = 0; i < content.size(); ++i){
            System.out.println(i + ": "+ content.get(i).toString());
        }
    }

    public boolean SetHasCard(Card checkFor){
        for(int i = 0; i < size; ++i){
            if(checkFor.equals(content.get(i))){
                return true;
            }
        }
        return false;
    }
}
