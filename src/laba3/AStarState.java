package laba3;

import java.util.HashMap;
import java.util.Map;

/**
 * Этот класс хранит базовое состояние, необходимое для алгоритма A* для вычисления
 * пути по карте. Это состояние включает коллекцию "открытых путевых точек" и
 * другую коллекцию "закрытых путевых точек". Кроме того, этот класс обеспечивает
 * основные операции, необходимые алгоритму поиска пути A* для выполнения его
 * обработки.
 **/
public class AStarState {
    /** Это ссылка на карту, по которой перемещается алгоритм A*. **/
    private Map2D map;
    private HashMap<Location,Waypoint> openWaypoints = new HashMap();
    private HashMap<Location,Waypoint> closeWaypoints = new HashMap();

    /**
     * Инициализирует новый объект состояния для использования алгоритма поиска пути A*.
     **/
    public AStarState(Map2D map) {
        if (map == null)
            throw new NullPointerException("map cannot be null");
        this.map = map;
    }

    /** Возвращает карту, по которой перемещается навигатор A*. **/
    public Map2D getMap() {
        return map;
    }

    /**
     * Этот метод сканирует все открытые путевые точки и возвращает путевую точку с
     * минимальной общей стоимостью. Если открытых путевых точек нет, этот метод
     * возвращает null.
     **/
    public Waypoint getMinOpenWaypoint() {
        Waypoint result = null;
        for (Map.Entry<Location, Waypoint> entry : openWaypoints.entrySet()) {
            if (result == null) {
                result = entry.getValue();
            }
            if (result.getTotalCost() > (entry.getValue()).getTotalCost()) {
                result = entry.getValue();
            }
        }
        return result;
    }

    /**
     * Этот метод добавляет путевую точку в коллекцию "открытые путевые точки" (или
     * потенциально обновляет уже имеющуюся путевую точку). Если в новом
     * местоположении путевых точек еще нет открытой путевой точки, то новая путевая
     * точка просто добавляется в коллекцию. Однако если в местоположении новой
     * путевой точки уже есть путевая точка, то новая путевая точка заменяет старую
     * только в том случае, если значение "предыдущей стоимости" новой путевой точки
     * меньше значения "предыдущей стоимости" текущей путевой точки.
     **/
    public boolean addOpenWaypoint(Waypoint newWP) {
        if (openWaypoints.containsKey(newWP.getLocation())) {
            if (openWaypoints.get(newWP.getLocation()).getRemainingCost() > newWP.getRemainingCost()) {
                openWaypoints.put(newWP.getLocation(), newWP);
                return true;
            }
        }
        else {
            openWaypoints.put(newWP.getLocation(), newWP);
            return true;
        }
        return false;
    }

    /** Возвращает текущее количество открытых путевых точек. **/
    public int numOpenWaypoints() {
        return openWaypoints.size();
    }

    /**
     * Этот метод перемещает путевую точку в указанном месте из открытого списка в
     * закрытый.
     **/
    public void closeWaypoint(Location loc) {
        Waypoint newWP = openWaypoints.get(loc);
        openWaypoints.remove(loc);
        closeWaypoints.put(loc,newWP);
    }

    /**
     * Возвращает true, если коллекция закрытых путевых точек содержит путевую точку
     * для указанного местоположения.
     **/
    public boolean isLocationClosed(Location loc) {
        return closeWaypoints.containsKey(loc);
    }
}