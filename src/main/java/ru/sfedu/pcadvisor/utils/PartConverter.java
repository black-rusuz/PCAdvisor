package ru.sfedu.pcadvisor.utils;

import com.opencsv.bean.AbstractBeanField;
import ru.sfedu.pcadvisor.model.bean.Cpu;
import ru.sfedu.pcadvisor.model.bean.Motherboard;
import ru.sfedu.pcadvisor.model.bean.Part;
import ru.sfedu.pcadvisor.model.bean.Ram;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PartConverter extends AbstractBeanField<Part, String> {
    private static final String fieldsDelimiter = Constants.FIELDS_DELIMITER;
    private static final String beansDelimiter = Constants.BEANS_DELIMITER;

    public static Part fromString(String string) {
        String[] parsed = string.split(fieldsDelimiter);
        return switch (parsed.length) {
            // TODO: args
            case (5) -> new Cpu();
            case (6) -> new Motherboard();
            case (7) -> new Ram();
            default -> new Part() {
            };
        };
    }

    public static String toString(Object object) {
        Part Part = (Part) object;
        List<Object> params = new ArrayList<>(List.of(Part.getId(), Part.getName(), Part.getPrice()));

        if (object instanceof Cpu cpu) {
            params.add(cpu.getFrequency());
            params.add(cpu.getCoreCount());
            params.add(cpu.getSocket());
        } else if (object instanceof Motherboard motherboard) {
            params.add(motherboard.getSocket());
            params.add(motherboard.getDdrVersion());
        } else if (object instanceof Ram ram) {
            params.add(ram.getVolumeGb());
            params.add(ram.getDdrVersion());
        }

        return params.stream().map(Object::toString).collect(Collectors.joining(fieldsDelimiter));
    }

    @Override
    public Part convert(String string) {
        return fromString(string);
    }

    @Override
    public String convertToWrite(Object object) {
        return toString(object);
    }
}
