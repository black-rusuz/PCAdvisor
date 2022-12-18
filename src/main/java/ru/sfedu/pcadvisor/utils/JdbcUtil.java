package ru.sfedu.pcadvisor.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.sfedu.pcadvisor.model.bean.Cpu;
import ru.sfedu.pcadvisor.model.bean.Motherboard;
import ru.sfedu.pcadvisor.model.bean.Order;
import ru.sfedu.pcadvisor.model.bean.Ram;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings("Unchecked")
public class JdbcUtil {

    // COMMON

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String tablePrefix = "T_";
    private static final String columnPrefix = "C_";

    private static <T> LinkedHashMap<String, Object> getMap(T bean) {
        return objectMapper.convertValue(bean, LinkedHashMap.class);
    }

    public static <T> String getTableName(Class<T> type) {
        return tablePrefix + type.getSimpleName();
    }

    public static String getColumnName(String key) {
        return columnPrefix + key;
    }

    // CREATE TABLES

    private static final String SQL_CREATE_TABLE_IF_NOT_EXISTS = "CREATE TABLE IF NOT EXISTS %s (%s);";

    private static final String ID = "id";
    private static final String COLUMN_PRIMARY_KEY = " PRIMARY KEY";
    private static final String COLUMN_TYPE_LONG = " LONG";
    private static final String COLUMN_TYPE_STRING = " VARCHAR";
    private static final String COLUMN_TYPE_INT = " INTEGER";
    private static final String COLUMN_TYPE_DOUBLE = " NUMERIC";

    public static <T> String createTable(T bean) {
        LinkedHashMap<String, Object> map = getMap(bean);
        return String.format(SQL_CREATE_TABLE_IF_NOT_EXISTS, getTableName(bean.getClass()), mapToColumns(map));
    }

    private static String mapToColumns(LinkedHashMap<String, Object> map) {
        return map.entrySet().stream()
                .map(JdbcUtil::mapper)
                .collect(Collectors.joining(SQL_COMMA));
    }

    private static String mapper(Map.Entry<String, Object> entry) {
        if (entry.getKey().equals(ID))
            return getColumnName(entry.getKey()) + COLUMN_TYPE_LONG + COLUMN_PRIMARY_KEY;
        else if (entry.getValue().getClass() == Integer.class)
            return getColumnName(entry.getKey()) + COLUMN_TYPE_INT;
        else if (entry.getValue().getClass() == Double.class)
            return getColumnName(entry.getKey()) + COLUMN_TYPE_DOUBLE;
        else
            return getColumnName(entry.getKey()) + COLUMN_TYPE_STRING;
    }

    // COMMANDS

    private static final String SELECT_ALL_FROM_TABLE = "SELECT * FROM %s;";
    private static final String SELECT_FROM_TABLE_BY_ID = "SELECT * FROM %s WHERE " + getColumnName(ID) + " = %d;";
    private static final String INSERT_INTO_TABLE_VALUES = "INSERT INTO %s VALUES (%s);";
    private static final String DELETE_FROM_TABLE_BY_ID = "DELETE FROM %s WHERE " + getColumnName(ID) + " = %d;";
    private static final String UPDATE_TABLE_SET_BY_ID = "UPDATE %s SET %s WHERE " + getColumnName(ID) + " = %d;";

    // SQL

    public static <T> String selectAllFromTable(Class<T> type) {
        String tableName = JdbcUtil.getTableName(type);
        return String.format(SELECT_ALL_FROM_TABLE, tableName);
    }

    public static <T> String selectFromTableById(Class<T> type, long id) {
        String tableName = JdbcUtil.getTableName(type);
        return String.format(SELECT_FROM_TABLE_BY_ID, tableName, id);
    }

    public static <T> String insertIntoTableValues(T bean) {
        String tableName = JdbcUtil.getTableName(bean.getClass());
        LinkedHashMap<String, Object> map = getMap(bean);
        return String.format(INSERT_INTO_TABLE_VALUES, tableName, mapToValues(map));
    }

    public static <T> String deleteFromTableById(T bean, long id) {
        String tableName = JdbcUtil.getTableName(bean.getClass());
        return String.format(DELETE_FROM_TABLE_BY_ID, tableName, id);
    }

    public static <T> String updateTableSetById(T bean, long id) {
        String tableName = JdbcUtil.getTableName(bean.getClass());
        LinkedHashMap<String, Object> map = getMap(bean);
        return String.format(UPDATE_TABLE_SET_BY_ID, tableName, mapToKeyValues(map), id);
    }

    // HELPERS

    private static final String SQL_COMMA = ", ";
    private static final String SQL_VALUE_WRAPPER = "'%s'";
    private static final String SQL_KEY_VALUE_WRAPPER = "%s = '%s'";

    private static String innerMapToString(LinkedHashMap<String, Object> map) {
        return map.values().stream().map(Object::toString).collect(Collectors.joining(Constants.FIELDS_DELIMITER));
    }

    private static String innerListToString(ArrayList<LinkedHashMap<String, Object>> list) {
        return list.stream().map(JdbcUtil::innerMapToString).collect(Collectors.joining(Constants.BEANS_DELIMITER));
    }

    private static String toValues(Object value) {
        String valueString = value.toString();
        if (value instanceof LinkedHashMap valueMap) valueString = innerMapToString(valueMap);
        if (value instanceof ArrayList valueMap) valueString = innerListToString(valueMap);
        return String.format(SQL_VALUE_WRAPPER, valueString);
    }

    private static String toKeyValues(Map.Entry<String, Object> entry) {
        Object value = entry.getValue();
        String valueString = value.toString();
        if (value instanceof LinkedHashMap valueMap) valueString = innerMapToString(valueMap);
        if (value instanceof ArrayList valueMap) valueString = innerListToString(valueMap);
        return String.format(SQL_KEY_VALUE_WRAPPER, getColumnName(entry.getKey()), valueString);
    }

    private static String mapToValues(LinkedHashMap<String, Object> map) {
        return map.values().stream()
                .map(JdbcUtil::toValues)
                .collect(Collectors.joining(SQL_COMMA));
    }

    private static String mapToKeyValues(LinkedHashMap<String, Object> map) {
        return map.entrySet().stream()
                .map(JdbcUtil::toKeyValues)
                .collect(Collectors.joining(SQL_COMMA));
    }

    // READ

    public static <T> List<T> readData(Class<T> type, ResultSet rs) throws SQLException {
        List list = new ArrayList<>();
        if (type == Cpu.class) list = readCpu(rs);
        if (type == Motherboard.class) list = readMotherboard(rs);
        if (type == Order.class) list = readOrder(rs);
        if (type == Ram.class) list = readRam(rs);
        return list;
    }

    private static List<Cpu> readCpu(ResultSet rs) throws SQLException {
        List<Cpu> list = new ArrayList<>();
        while (rs.next()) {
            Cpu bean = new Cpu();
            bean.setId(rs.getLong(1));
            bean.setName(rs.getString(2));
            bean.setPrice(rs.getDouble(3));
            bean.setFrequency(rs.getInt(4));
            bean.setCoreCount(rs.getInt(5));
            bean.setSocket(rs.getString(6));
            list.add(bean);
        }
        return list;
    }

    private static List<Motherboard> readMotherboard(ResultSet rs) throws SQLException {
        List<Motherboard> list = new ArrayList<>();
        while (rs.next()) {
            Motherboard bean = new Motherboard();
            bean.setId(rs.getLong(1));
            bean.setName(rs.getString(2));
            bean.setPrice(rs.getDouble(3));
            bean.setSocket(rs.getString(4));
            bean.setDdrVersion(rs.getInt(5));
            list.add(bean);
        }
        return list;
    }

    private static List<Order> readOrder(ResultSet rs) throws SQLException {
        List<Order> list = new ArrayList<>();
        while (rs.next()) {
            Order bean = new Order();
            bean.setId(rs.getLong(1));
            bean.setName(rs.getString(2));
            bean.setParts(PartsConverter.fromString(rs.getString(3)));
            bean.setTotalPrice(rs.getDouble(4));
            list.add(bean);
        }
        return list;
    }

    private static List<Ram> readRam(ResultSet rs) throws SQLException {
        List<Ram> list = new ArrayList<>();
        while (rs.next()) {
            Ram bean = new Ram();
            bean.setId(rs.getLong(1));
            bean.setName(rs.getString(2));
            bean.setPrice(rs.getDouble(3));
            bean.setVolumeGb(rs.getInt(4));
            bean.setDdrVersion(rs.getInt(5));
            list.add(bean);
        }
        return list;
    }
}
