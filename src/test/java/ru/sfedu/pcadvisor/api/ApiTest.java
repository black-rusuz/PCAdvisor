package ru.sfedu.pcadvisor.api;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sfedu.pcadvisor.utils.TestData;


public class ApiTest extends TestData {
    AbstractDataProvider dp = new DataProviderXml();

    @Test
    void test() {
    }

    @BeforeEach
    void setUp() {
        dp.insertCpu(c1);
        dp.insertCpu(c2);

        dp.insertMotherboard(m1);
        dp.insertMotherboard(m2);

        dp.insertRam(r1);
        dp.insertRam(r2);

        dp.insertOrder(o1);
        dp.insertOrder(o2);
    }

    @AfterEach
    void tearDown() {
        dp.deleteCpu(c1.getId());
        dp.deleteCpu(c2.getId());

        dp.deleteMotherboard(m1.getId());
        dp.deleteMotherboard(m2.getId());

        dp.deleteRam(r1.getId());
        dp.deleteRam(r2.getId());

        dp.deleteOrder(o1.getId());
        dp.deleteOrder(o2.getId());
    }
}
