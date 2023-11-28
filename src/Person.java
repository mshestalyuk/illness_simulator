import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Person implements Movable {
    protected Vector2D position;
    protected Vector2D velocity;
    protected boolean isImmune;
    protected boolean isInfected;
    protected boolean hasSymptoms;
    protected double infectionDuration;
    private Map<Person, Double> closeContactTimeMap = new HashMap<>();

    public Person(Vector2D position) {
        this.position = position;
        this.velocity = generateRandomVelocity();

        this.isImmune = false;
        this.isInfected = false; 
        this.hasSymptoms = false; 
        this.infectionDuration = 0;
    }

    public void infect(boolean withSymptoms) {
        if (!isImmune) {
            isInfected = true;
            hasSymptoms = withSymptoms;
            infectionDuration = 20 + new Random().nextInt(11); 
        }
    }

    public void updateInfection() {
        if (isInfected) {
            infectionDuration -= 1.0 / 25;
            if (infectionDuration <= 0) {
                isInfected = false;
                isImmune = true;
            }
        }
    }


        public void checkAndInfect(Person other) {
            if (this.isInfected && !other.isImmune && !other.isInfected) {
                double distance = this.position.subtract(other.position).abs();
                if (distance <= 2) { 
                    closeContactTimeMap.putIfAbsent(other, 0.0);
                    closeContactTimeMap.put(other, closeContactTimeMap.get(other) + 25);
    
                    if (closeContactTimeMap.get(other) >= 3 * 25) { 
                        boolean infectSymptoms = new Random().nextBoolean();
                        if (infectSymptoms) {
                            other.infect(this.hasSymptoms);
                        }
                        else
                            other.infect(this.isInfected);
                    }
                } else {
                    closeContactTimeMap.remove(other);
                }
            }
        }

    private Vector2D generateRandomVelocity() {
        Random random = new Random();
        double angle = random.nextDouble() * 2 * Math.PI;
        double speed = random.nextDouble() * 2.5;
        return new Vector2D(Math.cos(angle) * speed, Math.sin(angle) * speed);
    }

    public void setRandomVelocity() {
        Random random = new Random();
        double angle = random.nextDouble() * 2 * Math.PI;
        double speed = random.nextDouble() * 0.25;
        velocity = new Vector2D(Math.cos(angle) * speed, Math.sin(angle) * speed);
    }
    @Override
    public void updatePosition() {
        this.position.add(this.velocity);
        setRandomVelocity();
    }

    @Override
    public void randomizeVelocity() {
        this.velocity = generateRandomVelocity();
    }

    public Vector2D getPosition() {
        return position;
    }

    public Vector2D getVelocity() {
        return velocity;
    }
}
