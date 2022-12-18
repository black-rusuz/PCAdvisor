package ru.sfedu.pcadvisor.model.bean;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.ElementListUnion;
import ru.sfedu.pcadvisor.utils.PartConverter;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Element
public class Order implements Serializable {
    @Attribute
    @CsvBindByPosition(position = 0)
    private long id;

    @Element
    @CsvBindByPosition(position = 1)
    private String name = "";

    @ElementListUnion({
            @ElementList(entry = "Part", inline = true, required = false, type = Part.class),
            @ElementList(entry = "Cpu", inline = true, required = false, type = Cpu.class),
            @ElementList(entry = "Ram", inline = true, required = false, type = Ram.class),
            @ElementList(entry = "Motherboard", inline = true, required = false, type = Motherboard.class),
    })
    @CsvCustomBindByPosition(position = 2, converter = PartConverter.class)
    private List<Part> parts = List.of();

    @CsvBindByPosition(position = 3)
    double totalPrice;

    public Order() {
    }

    public Order(long id, String name, List<Part> parts, double totalPrice) {
        setId(id);
        setName(name);
        setParts(parts);
        setTotalPrice(totalPrice);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order order)) return false;
        return getId() == order.getId()
                && Double.compare(order.getTotalPrice(), getTotalPrice()) == 0
                && Objects.equals(getName(), order.getName())
                && Objects.equals(getParts(), order.getParts());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getParts());
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", parts=" + getParts() +
                ", totalPrice=" + getTotalPrice() +
                '}';
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

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
