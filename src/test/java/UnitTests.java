// Title:   UnitTests.java
// Author:  Jacob Bello
// Course:  CST 336
// Date:    10/11/2024
// Abstract: This class tests our classes and creates an object of Daily plan with a specified work order text file.

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
// Reference: https://www.youtube.com/watch?v=vZm0lHciFsQ

class UnitTests {

    @Test
     void validLicense() {
        DailyPlan dp1 = new DailyPlan("resources/workOrder1.txt");
        assertEquals( "\nAll drivers have valid licenses.\n",dp1.checkLicense());
    }


    @Test
    void invalidLicense(){
        DailyPlan dp2 = new DailyPlan("resources/workOrder3.txt");
        assertNotEquals("\nAll drivers have valid licenses.\n",dp2.checkLicense());
    }

    @Test
    void senorityCheck(){
        DailyPlan dp3 = new DailyPlan("resources/workOrder2.txt");
        assertEquals("Seniority check passed", dp3.seniorityCheck());
    }

    @Test
    void orderFulfilled(){
        DailyPlan dp4 = new DailyPlan("resources/workOrder2.txt");
        assertEquals("Order fulfilled \n", dp4.orderFulfilled());
    }

    @Test
    void notEnoughCars(){
        DailyPlan dp5 = new DailyPlan("resources/workOrder4.txt");
        assertEquals("Order not fulfilled: \n- Not enough street legal cars.\n", dp5.orderFulfilled());
    }

    @Test
    void notEnoughTrucks(){
        DailyPlan dp5 = new DailyPlan("resources/workOrder5.txt");
        assertEquals("Order not fulfilled: \n- Not enough street legal trucks.\n", dp5.orderFulfilled());
    }
}



