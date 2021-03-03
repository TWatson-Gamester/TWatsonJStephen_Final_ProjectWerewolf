package models;

public class Roles {

    private RoleName name;
    private int balanceNumber;

    public RoleName getName() {
        return name;
    }

    public int getBalanceNumber() {
        return balanceNumber;
    }


    public void setName(RoleName name) {
        if(name != null) {
            this.name = name;
        }else{
            throw new IllegalArgumentException("This role is not currently supported.");
        }
    }

    public void setBalanceNumber(int balanceNumber) {
        if(balanceNumber > -10 && balanceNumber < 10) {
            this.balanceNumber = balanceNumber;
        }else{
            throw new IllegalArgumentException("That is not a valid balance number.");
        }
    }

    @Override
    public String toString(){
        return "" + getName();
    }
}
