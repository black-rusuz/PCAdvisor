package ru.sfedu.pcadvisor.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import ru.sfedu.pcadvisor.model.bean.Cpu;
import ru.sfedu.pcadvisor.model.bean.Motherboard;
import ru.sfedu.pcadvisor.model.bean.Order;
import ru.sfedu.pcadvisor.model.bean.Ram;
import ru.sfedu.pcadvisor.utils.TestData;


public class TestArea extends TestData {
    protected final Logger log = LogManager.getLogger(TestArea.class);
    protected final AbstractDataProvider dp = new DataProviderXml();

    @Test
    void cpus() {
        log.info(dp.getCpus());

        Cpu c = c1;
        dp.insertCpu(c);
        log.info(dp.getCpus());

        Cpu cc = dp.getCpu(c.getId());
        log.info(dp.getCpus());

        Cpu ccc = dp.getCpu(321);
        log.info(ccc);

        cc.setName("CPU");
        dp.updateCpu(cc);
        log.info(dp.getCpus());

        dp.deleteCpu(cc.getId());
        log.info(dp.getCpus());
    }

    @Test
    void motherboards() {
        log.info(dp.getMotherboards());

        Motherboard m = m1;
        dp.insertMotherboard(m);
        log.info(dp.getMotherboards());

        Motherboard mm = dp.getMotherboard(m.getId());
        log.info(dp.getMotherboards());

        Motherboard mmm = dp.getMotherboard(321);
        log.info(mmm);

        mm.setDdrVersion(3);
        dp.updateMotherboard(mm);
        log.info(dp.getMotherboards());

        dp.deleteMotherboard(mm.getId());
        log.info(dp.getMotherboards());
    }

    @Test
    void rams() {
        log.info(dp.getRams());

        Ram r = r1;
        dp.insertRam(r);
        log.info(dp.getRams());

        Ram rr = dp.getRam(r.getId());
        log.info(dp.getRams());

        Ram rrr = dp.getRam(321);
        log.info(rrr);

        rr.setVolumeGb(32);
        dp.updateRam(rr);
        log.info(dp.getRams());

        dp.deleteRam(rr.getId());
        log.info(dp.getRams());
    }

    @Test
    void orders() {
        log.info(dp.getOrders());

        Order o = new Order(1245, "New Test Build", p1, 0);
        dp.insertOrder(o);
        log.info(dp.getOrders());

        Order oo = dp.getOrder(o.getId());
        log.info(oo);

        Order ooo = dp.getOrder(123123);
        log.info(ooo);

        oo.setName("Test Build");
        dp.updateOrder(oo);
        log.info(dp.getOrders());

        dp.deleteOrder(oo.getId());
        log.info(dp.getOrders());
    }
}
