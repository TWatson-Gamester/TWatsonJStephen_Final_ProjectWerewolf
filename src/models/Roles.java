package models;

public class Roles {

    private RoleName name;
    private int balanceNumber;
    private boolean ability1 = true;
    private boolean ability2 = true;

    public RoleName getName() {
        return name;
    }

    public int getBalanceNumber() {
        return balanceNumber;
    }

    public boolean isAbility1() {
        return ability1;
    }

    public boolean isAbility2() {
        return ability2;
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

    public void setAbility1(boolean ability1) {
        this.ability1 = ability1;
    }

    public void setAbility2(boolean ability2) {
        this.ability2 = ability2;
    }

    @Override
    public String toString(){
        return "" + getName();
    }
}
