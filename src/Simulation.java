import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Simulation {
    private List<Movable> movables;
    private int width;
    private int height;

    // Constructor that as parametrs takes width and height of area
    public Simulation(int width, int height) {
        this.width = width;
        this.height = height;
        this.movables = new ArrayList<>();
        initializeRandomMovables();
    }

    // method for start our simulation that initialize from 1 to 51 random Persons
    private void initializeRandomMovables() {
        Random random = new Random();
        int numberOfMovables = random.nextInt(3000) + 1;
        for (int i = 0; i < numberOfMovables; i++) {
            movables.add(new Person(new Vector2D(random.nextInt(width), random.nextInt(height))));
        }
    }

    // Temporary method that displays the board

    
    // public void display_board() {
    //     // Initialize the board
    //     char[][] board = new char[width][height];
    //     for (int i = 0; i < width; i++) {
    //         for (int j = 0; j < height; j++) {
    //             board[i][j] = '.';
    //         }
    //     }
    
    //     // Place movables on the board
    //     for (Movable m : movables) {
    //         if (m instanceof Person) { // Check if the movable is a Person
    //             Person p = (Person) m;
    //             int x = (int) p.getPosition().getX();
    //             int y = (int) p.getPosition().getY();
    //             if (x >= 0 && x < width && y >= 0 && y < height) {
    //                 if (p.isInfected) {
    //                     if (p.hasSymptoms) {
    //                         board[x][y] = '-'; // Infected with symptoms
    //                     } else {
    //                         board[x][y] = '+'; // Infected without symptoms
    //                     }
    //                 } else {
    //                     board[x][y] = '*'; // Not infected or immune
    //                 }
    //             }
    //         }
    //     }
    
    //     for (int i = 0; i < width; i++) {
    //         for (int j = 0; j < height; j++) {
    //             System.out.print(board[i][j]);
    //         }
    //         System.out.println();
    //     }
    //     System.out.println(getPopulationNum());
    // }
    

    private boolean handleBoundaryCollision(Movable m) {
        if (m instanceof Person) {
            Person p = (Person) m;
            Vector2D position = p.getPosition();
            Vector2D velocity = p.getVelocity();
    
            boolean atBoundaryX = position.getX() <= 0 || position.getX() >= width;
            boolean atBoundaryY = position.getY() <= 0 || position.getY() >= height;
    
            if (atBoundaryX || atBoundaryY) {
                if (shouldReflect()) {
                    velocity.reflect(atBoundaryX, atBoundaryY);
                    return false; 
                } else {
                    return true; 
                }
            }
        }
        return false; 
    }
    

    private boolean shouldReflect() {
        Random random = new Random();
        return random.nextInt(2) == 0; 
    }

    // 1 simulate step
    public void simulateStep() {
        List<Movable> toRemove = new ArrayList<>();

        for (Movable m : movables) {
            if (m instanceof Person) {
                Person person = (Person) m;
                person.updatePosition();
                person.updateInfection(); 

                for (Movable other : movables) {
                    if (other instanceof Person && other != m) {
                        person.checkAndInfect((Person) other);
                    }
                }
            }

            if (handleBoundaryCollision(m)) {
                toRemove.add(m);
            }
        }

        movables.removeAll(toRemove);
        checkAndAddNewPersons();
    }

    public void addNewPersons() {
        movables.add(new NewPerson(width, height));
    }

    public void checkAndAddNewPersons() {
            int populationThreshold = 1000; 
            if (movables.size() < populationThreshold) {
                int numberOfNewPersons = populationThreshold - movables.size();
                for (int i = 0; i < numberOfNewPersons; i++) {
                    movables.add(new NewPerson(width, height));
                }
        }
    }

    public Integer getPopulationNum() {
        return movables.size();
    }

    public List<Movable> getMovables() {
        return movables;
    }

    public int getNumInfected() {
        int count = 0;
        for (Movable m : movables) {
            if (m instanceof Person && ((Person) m).isInfected) {
                count++;
            }
        }
        return count;
    }

    public int getNumImmuned() {
        int count = 0;
        for (Movable m : movables) {
            if (m instanceof Person && ((Person) m).isImmune) {
                count++;
            }
        }
        return count;
    }
}