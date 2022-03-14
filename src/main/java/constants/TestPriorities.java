package constants;

/**
 * One thing to bare in mind when using with TestNG:
 *
 *      "The LOWER the number, the HIGHER the priority"
 *
 * The range between low and high priority level is arbitrary.
 * You can set any values but is good to leave some space between
 * priority levels. If you decide to have another one (e.g. 11 or -3)
 * you have some room to define another custom levels.
 *
 * Priority level MEDIUM is actually redundant in sense of use because
 * it responds to default priority. It serves only as landmark.
 *
 * * * * * * * * * * * * * * * * * * * * * * * *
 *              EXAMPLE OF USE:                *
 * * * * * * * * * * * * * * * * * * * * * * * *
 *
 *      @Test( priority = HIGH)
 *      public void testPriorityExample(){...}
 *
 *      @Test( priority = LOW)
 *      public void testPriorityExample(){...}
 *
 */
public interface TestPriorities {

    int HIGH = -20;

    int MEDIUM = 0;

    int LOW = 20;

}