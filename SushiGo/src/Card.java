public class Card {
    private int cardType;    
    private int value;    
    private String name;
    private int makiVal;



    public static void main(String[] args) {
        Card demo1 = new Card(3);
        System.out.println(demo1.toString());
        System.out.println("Worth: "+demo1.value);
        
        Card demo2 = new Card("Salmon");
        System.out.println(demo2.toString());
        System.out.println("Worth: "+demo2.value);
        System.out.println("Cards Match? "+ demo1.equals(demo2));
    }

    public Card(int type){
        cardType = type;
        value = chooseVal();
        name = toString();
        makiVal = makiAssign();
    }

    private int makiAssign() {
        switch(cardType){
            case 3:
            return 1;
            case 4:
            return 2;
            case 5:
            return 3;
        }
        return 0;
    }

    public Card(String name){
        int key = -1;
        for(int i = 0; i < 12; ++i){
            Card test = new Card(i);
            if(test.toString().equals(name)){
                key = i;
            }
        }
        cardType = key;
        value = chooseVal();
        this.name = name;
        makiVal = makiAssign();
    }

    private int chooseVal() {
        switch(cardType){
            case 8:
            return 1;
            case 7:
            return 2;
            case 6: 
            return 3;
        }
        return 0;
    }

    public String toString(){
        switch(cardType){
            case 0:
            return "Tempura";
            case 1:
            return "Sashimi";
            case 2: 
            return "Dumpling";
            case 3:
            return "1 Maki Roll";
            case 4:
            return "2 Maki Roll";
            case 5:
            return "3 Maki Roll";
            case 6:
            return "Squid";
            case 7:
            return "Salmon";
            case 8:
            return "Egg";
            case 9:
            return "Pudding";
            case 10: 
            return "Wasabi";
            case 11:
            return "Chopsticks";
        }
        return "Nothing";
    }

    public void Wasabied(){
        value = 3 * value;
    }

    public void resetWasabi(){
        value = chooseVal();
    }

    public boolean equals(Card comparedTo){
        if(cardType != comparedTo.cardType){
            return false;
        }
        return true;
    }

    public int getCardType() {
        return cardType;
    }

    public int getValue() {
        return value;
    }

    public int getMakiVal() {
        return makiVal;
    }
}
