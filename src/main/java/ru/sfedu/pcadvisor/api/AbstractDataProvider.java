package ru.sfedu.pcadvisor.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.pcadvisor.model.HistoryContent;
import ru.sfedu.pcadvisor.model.bean.Cpu;
import ru.sfedu.pcadvisor.model.bean.Motherboard;
import ru.sfedu.pcadvisor.model.bean.Order;
import ru.sfedu.pcadvisor.model.bean.Ram;
import ru.sfedu.pcadvisor.utils.ConfigurationUtil;
import ru.sfedu.pcadvisor.utils.Constants;
import ru.sfedu.pcadvisor.utils.MongoUtil;
import ru.sfedu.pcadvisor.utils.ReflectUtil;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("UnusedReturnValue")
public abstract class AbstractDataProvider {
    protected final Logger log = LogManager.getLogger(this.getClass());

    private boolean MONGO_ENABLE = false;
    private String MONGO_ACTOR = "";

    public AbstractDataProvider() {
        try {
            MONGO_ENABLE = Boolean.parseBoolean(ConfigurationUtil.getConfigurationEntry(Constants.MONGO_ENABLE));
            MONGO_ACTOR = ConfigurationUtil.getConfigurationEntry(Constants.MONGO_ACTOR);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    // ABSTRACT GENERICS

    protected abstract <T> List<T> getAll(Class<T> type);

    protected abstract <T> T getById(Class<T> type, long id);

    protected abstract <T> long insert(Class<T> type, T bean);

    protected abstract <T> boolean delete(Class<T> type, long id);

    protected abstract <T> boolean update(Class<T> type, T bean);

    // SERVICE

    protected void sendLogs(String methodName, Object bean, boolean result) {
        HistoryContent historyContent = new HistoryContent(UUID.randomUUID(),
                this.getClass().getSimpleName(),
                LocalDateTime.now().toString(),
                MONGO_ACTOR,
                methodName,
                MongoUtil.objectToString(bean),
                result);
        if (MONGO_ENABLE) MongoUtil.saveToLog(historyContent);
    }

    protected <T> boolean hasSavedId(Class<T> type, long id) {
        T oldBean = getById(type, id);
        return ReflectUtil.getId(oldBean) != 0;
    }

    protected <T> String getNotFoundMessage(Class<T> type, long id) {
        return String.format(Constants.NOT_FOUND, type.getSimpleName(), id);
    }

    // USE CASES

    // TODO:

    // CRUD

    public List<Cpu> getCpus() {
        return getAll(Cpu.class);
    }

    public Cpu getCpu(long id) {
        return getById(Cpu.class, id);
    }

    public long insertCpu(Cpu cpu) {
        return insert(Cpu.class, cpu);
    }

    public boolean deleteCpu(long id) {
        return delete(Cpu.class, id);
    }

    public boolean updateCpu(Cpu cpu) {
        return update(Cpu.class, cpu);
    }


    public List<Motherboard> getMotherboards() {
        return getAll(Motherboard.class);
    }

    public Motherboard getMotherboard(long id) {
        return getById(Motherboard.class, id);
    }

    public long insertMotherboard(Motherboard motherboard) {
        return insert(Motherboard.class, motherboard);
    }

    public boolean deleteMotherboard(long id) {
        return delete(Motherboard.class, id);
    }

    public boolean updateMotherboard(Motherboard motherboard) {
        return update(Motherboard.class, motherboard);
    }


    public List<Order> getOrders() {
        return getAll(Order.class);
    }

    public Order getOrder(long id) {
        return getById(Order.class, id);
    }

    public long insertOrder(Order order) {
        return insert(Order.class, order);
    }

    public boolean deleteOrder(long id) {
        return delete(Order.class, id);
    }

    public boolean updateOrder(Order order) {
        return update(Order.class, order);
    }


    public List<Ram> getRams() {
        return getAll(Ram.class);
    }

    public Ram getRam(long id) {
        return getById(Ram.class, id);
    }

    public long insertRam(Ram ram) {
        return insert(Ram.class, ram);
    }

    public boolean deleteRam(long id) {
        return delete(Ram.class, id);
    }

    public boolean updateRam(Ram ram) {
        return update(Ram.class, ram);
    }
}
