
 public class Vector2D implements IVector {
    protected double x;
    protected double y;
    protected double z = 0;

        public Vector2D(double x, double y)
        {
            this.x = x;
            this.y = y;
        }

        @Override
        public double cdot(IVector other) {
            if (other instanceof Vector2D) {
                Vector2D otherVector = (Vector2D) other;
                return this.x * otherVector.x + this.y * otherVector.y;
            }
            throw new IllegalArgumentException("The provided IVector is not an instance of Vector2D.");
        }

        @Override
        public double abs(){
            return Math.sqrt(x*x+y*y);
        }

        @Override
        public double[] getComponents(){
            return new double[] { x, y };
        }
        public double getX() {
            return x;
        }
        public double getY() {
            return y;
        }

    public void add(Vector2D other) {
        this.x += other.x;
        this.y += other.y;
    }

    public void scale(double scaleFactor) {
        this.x *= scaleFactor;
        this.y *= scaleFactor;
    }

    public void reflect(boolean reflectX, boolean reflectY) {
        if (reflectX) this.x = -this.x;
        if (reflectY) this.y = -this.y;
    }

    public Vector2D subtract(Vector2D other) {
        return new Vector2D(this.x - other.x, this.y - other.y);
    }
}
