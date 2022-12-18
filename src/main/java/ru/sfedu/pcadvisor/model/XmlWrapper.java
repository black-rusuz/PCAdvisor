package ru.sfedu.pcadvisor.model;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.ElementListUnion;
import org.simpleframework.xml.Root;
import ru.sfedu.pcadvisor.model.bean.*;

import java.io.Serializable;
import java.util.List;

@Root(name = "List")
public class XmlWrapper<T> implements Serializable {

    @ElementListUnion({
            @ElementList(inline = true, required = false, type = Cpu.class),
            @ElementList(inline = true, required = false, type = Motherboard.class),
            @ElementList(inline = true, required = false, type = Order.class),
            @ElementList(inline = true, required = false, type = Part.class),
            @ElementList(inline = true, required = false, type = Ram.class),
    })
    private List<T> list;

    public XmlWrapper() {
    }

    public XmlWrapper(List<T> list) {
        setList(list);
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
