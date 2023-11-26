import java.util.Random;

public class NewPerson extends Person {

    public NewPerson(int width, int height) {
        super(generateBoundaryPosition(width, height));
        Random rand = new Random(); 
        isInfected = rand.nextInt(100) < 10;
    }

    private static Vector2D generateBoundaryPosition(int width, int height) {
        Random random = new Random();
        int side = random.nextInt(4);
        double x = 0, y = 0;

        switch (side) {
            case 0:
                x = random.nextDouble() * width;
                y = 0;
                break;
            case 1:
                x = width;
                y = random.nextDouble() * height;
                break;
            case 2:
                x = random.nextDouble() * width;
                y = height;
                break;
            case 3:
                x = 0;
                y = random.nextDouble() * height;
                break;
        }
        return new Vector2D(x, y);
    }
}
