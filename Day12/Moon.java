import java.util.Objects;

public class Moon {
    private int xVel = 0;
    private int yVel = 0;
    private int zVel = 0;
    private int xPos;
    private int yPos;
    private int zPos;

    public Moon(int xPos, int yPos, int zPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.zPos = zPos;
    }

    public void increaseVelocity(int dx,int dy,int dz){
        this.xVel += dx;
        this.yVel += dy;
        this.zVel += dz;
    }

    public void updatePos(){
        xPos += xVel;
        yPos += yVel;
        zPos += zVel;
    }
    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public int getzPos() {
        return zPos;
    }

    public int getxVel() {
        return xVel;
    }

    public int getyVel() {
        return yVel;
    }

    public int getzVel() {
        return zVel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Moon moon = (Moon) o;
        return xVel == moon.xVel &&
                yVel == moon.yVel &&
                zVel == moon.zVel &&
                xPos == moon.xPos &&
                yPos == moon.yPos &&
                zPos == moon.zPos;
    }

    @Override
    public int hashCode() {
        return Objects.hash(xVel, yVel, zVel, xPos, yPos, zPos);
    }
}
