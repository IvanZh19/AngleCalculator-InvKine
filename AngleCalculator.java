import java.util.Scanner;

public class AngleCalculator {
    //These values are in centimeters
    //Since not reading values from limelight, do not need
    //public static final double distanceBetweenPivotLimelight = 48.26;
    //public static final double distanceBetweenPivotLimelightZ = 0;
    public static final double kAnchorArmLength = 101.6;
    public static final double kFloatingArmLength = 40.64;
    // Roughly y0 and z0, should remeasure
    public static final double y0 = 127;
    public static final double z0 = 59.055;
    public static final double centimetersPerInch = 2.54;
    public static final double degreesPerRadian = 180/Math.PI;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("INCHES - enter y and z for desired position relative to the front lip of grid");
        double y = centimetersPerInch * scanner.nextDouble();
        double z = centimetersPerInch * scanner.nextDouble();

        double yactual = y-y0;
        double zactual = z+z0;

        double[] desiredAngles = calculateAngles(yactual, zactual);
        System.out.println("Angles in radians for anchorJoint and floatingJoint");
        System.out.println(desiredAngles[0]);
        System.out.println(desiredAngles[1]);

        System.out.println("Angles in degrees for anchorJoint and floatingJoint");
        System.out.println(degreesPerRadian * desiredAngles[0]);
        System.out.println(degreesPerRadian * desiredAngles[1]);
    }

// This method will be using dy and dz relative to pivot already, no limelight adjusting
    public static double[] calculateAngles(double dy, double dz) {
        double[] angles = new double[2];
        double distanceToObj = Math.sqrt(dy*dy + dz*dz);
        double alpha = Math.acos((kFloatingArmLength*kFloatingArmLength + distanceToObj*distanceToObj - kAnchorArmLength*kAnchorArmLength)/(2*kAnchorArmLength*distanceToObj));
        double gamma = Math.atan2(dy, dz);

        // Now fills the return array with the angles for L1 and L2 - WHICH ARE IN RADIANS
        angles[0] = alpha+gamma;
        angles[1] = Math.PI - Math.acos((kAnchorArmLength * kAnchorArmLength + kFloatingArmLength * kFloatingArmLength - distanceToObj * distanceToObj) / (2 * kAnchorArmLength * kFloatingArmLength));
        return angles;
    }
}
