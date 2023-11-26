import java.util.Random;

public class Person implements Movable {
    protected Vector2D position;
    protected Vector2D velocity;
    protected boolean isImmune;
    protected boolean isInfected;
    protected boolean hasSymptoms;
    protected double infectionDuration;

    // Constructor that create person with position 
    public Person(Vector2D position) {
        this.position = position;
        this.velocity = generateRandomVelocity();

        this.isImmune = false; // Initially, no one is immune
        this.isInfected = false; // Initially, no one is infectious
        this.hasSymptoms = false; // Initially, no one has symptoms
        this.infectionDuration = 0; // Duration of infection
    }

    public void infect(boolean withSymptoms) {
        if (!isImmune) {
            isInfected = true;
            hasSymptoms = withSymptoms;
            infectionDuration = 20 + new Random().nextInt(11); // Infection lasts 20-30 seconds
        }
    }

    public void updateInfection() {
        if (isInfected) {
            infectionDuration -= 1.0 / 25;
            if (infectionDuration <= 0) {
                isInfected = false;
                isImmune = true; // Person becomes immune after infection
            }
        }
    }

        // Method to check and infect based on proximity and infection status
        public void checkAndInfect(Person other) {
            if (this.isInfected && !other.isImmune && !other.isInfected) {
                double distance = this.position.subtract(other.position).abs();
                if (distance <= 2) { // If within 2 meters
                    boolean infect = new Random().nextBoolean(); // 50% chance of infection from asymptomatic
                    if (this.hasSymptoms || infect) {
                        other.infect(this.hasSymptoms);
                    }
                }
            }
        }

    // method that generates random velocity <cos * speed; sin * speed >
    private Vector2D generateRandomVelocity() {
        Random random = new Random();
        double angle = random.nextDouble() * 2 * Math.PI;
        double speed = random.nextDouble() * 2.5;
        return new Vector2D(Math.cos(angle) * speed, Math.sin(angle) * speed);
    }

    // method that updates position
    @Override
    public void updatePosition() {
        this.position.add(this.velocity);
    }

    //method that randomize velocity
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
