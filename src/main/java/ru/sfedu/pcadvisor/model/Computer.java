package ru.sfedu.pcadvisor.model;

import com.opencsv.bean.CsvBindByPosition;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

import java.io.Serializable;
import java.util.Objects;

@Element
public class Computer implements Serializable {
    @Attribute
    @CsvBindByPosition(position = 0)
    private long id;

    @Element
    @CsvBindByPosition(position = 1)
    private Motherboard motherboard;

    @Element
    @CsvBindByPosition(position = 2)
    private Cpu cpu;

    @Element
    @CsvBindByPosition(position = 3)
    private Ram ram;

    @Attribute
    @CsvBindByPosition(position = 4)
    private int memoryGb;

    public Computer() {
    }

    public Computer(long id, Motherboard motherboard, Cpu cpu, Ram ram, int memoryGb) {
        setId(id);
        setMotherboard(motherboard);
        setCpu(cpu);
        setRam(ram);
        setMemoryGb(memoryGb);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Computer computer)) return false;
        return getId() == computer.getId() && getMemoryGb() == computer.getMemoryGb() && Objects.equals(getMotherboard(), computer.getMotherboard()) && Objects.equals(getCpu(), computer.getCpu()) && Objects.equals(getRam(), computer.getRam());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getMotherboard(), getCpu(), getRam(), getMemoryGb());
    }

    @Override
    public String toString() {
        return "Computer{" +
                "id=" + getId() +
                ", motherboard=" + getMotherboard() +
                ", cpu=" + getCpu() +
                ", ram=" + getRam() +
                ", memoryGb=" + getMemoryGb() +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Motherboard getMotherboard() {
        return motherboard;
    }

    public void setMotherboard(Motherboard motherboard) {
        this.motherboard = motherboard;
    }

    public Cpu getCpu() {
        return cpu;
    }

    public void setCpu(Cpu cpu) {
        this.cpu = cpu;
    }

    public Ram getRam() {
        return ram;
    }

    public void setRam(Ram ram) {
        this.ram = ram;
    }

    public int getMemoryGb() {
        return memoryGb;
    }

    public void setMemoryGb(int memoryGb) {
        this.memoryGb = memoryGb;
    }
}
