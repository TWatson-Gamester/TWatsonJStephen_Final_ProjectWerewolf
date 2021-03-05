package models;

public class Players {

    private int seatNumber;
    private Roles currentRole;
    private boolean isDead = false;
    private boolean openGrave;
    private boolean isVillage;
    private boolean isCult = false;
    private boolean hasWon = false;
    private boolean isLovers = false;

    public boolean isCult() {
        return isCult;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public Roles getCurrentRole() {
        return currentRole;
    }

    public boolean isDead() {
        return isDead;
    }

    public boolean getOpenGrave() {
        return openGrave;
    }

    public boolean isVillage() {
        return isVillage;
    }

    public boolean hasWon() {
        return hasWon;
    }

    public boolean isLovers() {
        return isLovers;
    }


    public void setCult(boolean cult) {
        isCult = cult;
    }

    public void setSeatNumber(int seatNumber) {
        if(seatNumber > 0 && seatNumber < 21){
        this.seatNumber = seatNumber;
        }else{
            throw new IllegalArgumentException("This is not a valid seat.");
        }
    }

    public void setCurrentRole(Roles currentRole) {
        if(currentRole != null) {
            this.currentRole = currentRole;
        }else{
            throw new IllegalArgumentException("This role is not currently supported.");
        }
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public void setOpenGrave(boolean openGrave) {
        this.openGrave = openGrave;
    }

    public void setVillage(boolean village) {
        isVillage = village;
    }

    public void setWon(boolean hasWon) {
        this.hasWon = hasWon;
    }

    public void setLovers(boolean lovers) {
        isLovers = lovers;
    }
}
