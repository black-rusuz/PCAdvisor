package ru.sfedu.pcadvisor.model;

import com.opencsv.bean.CsvBindByPosition;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.ElementListUnion;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Element
public class Computer implements Serializable {
    @Attribute
    @CsvBindByPosition(position = 0)
    private long id;

    @Element
    @CsvBindByPosition(position = 1)
    private String name;

    @ElementListUnion({
            @ElementList(entry = "Part", inline = true, required = false, type = Part.class),
            @ElementList(entry = "Cpu", inline = true, required = false, type = Cpu.class),
            @ElementList(entry = "Ram", inline = true, required = false, type = Ram.class),
            @ElementList(entry = "Motherboard", inline = true, required = false, type = Motherboard.class),
    })
    @CsvBindByPosition(position = 2)
    private List<Part> parts;

    public Computer() {
    }

    public Computer(long id, String name, List<Part> parts) {
        setId(id);
        setName(name);
        setParts(parts);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Computer computer)) return false;
        return getId() == computer.getId()
                && Objects.equals(getName(), computer.getName())
                && Objects.equals(getParts(), computer.getParts());
    }

    @Override
    public String toString() {
        return "Computer{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", parts=" + getParts() +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getParts());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Part> getParts() {
        return parts;
    }

    public void setParts(List<Part> parts) {
        this.parts = parts;
    }
}
