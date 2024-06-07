package org.example.spring.data.redis;

import org.example.spring.data.redis.user.service.GeoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


/**
 *
 * @Description Geospatial数据结构应用
 * @Author SangY
 * @Date 2024/6/7 13:22
 **/
@SpringBootTest
public class GeoServiceTest {
    @Autowired
    private GeoService geoService;

    private final String key = "places";
    private final Point point1 = new Point(13.361389, 38.115556);
    private final Point point2 = new Point(15.087269, 37.502669);

    @BeforeEach
    public void setUp() {
        // 清除之前的测试数据
        geoService.deleteKey(key);
        geoService.addLocation(key, point1, "Palermo");
        geoService.addLocation(key, point2, "Catania");
    }

    // 自定义方法用于比较点
    private void assertPointsEqual(Point expected, Point actual) {
        assertEquals(expected.getX(), actual.getX(), 1.0E-5, "X coordinates should be within delta");
        assertEquals(expected.getY(), actual.getY(), 1.0E-5, "Y coordinates should be within delta");
    }

    // addLocation：添加一个地理位置。
    @Test
    public void testAddLocation() {
        Point point = new Point(13.583333, 37.316667);
        geoService.addLocation(key, point, "Agrigento");
        Point retrievedPoint = geoService.getLocation(key, "Agrigento");
        assertNotNull(retrievedPoint);
        assertPointsEqual(point, retrievedPoint);
    }

    // getLocation：获取一个地理位置。
    @Test
    public void testGetLocation() {
        Point retrievedPoint = geoService.getLocation(key, "Palermo");
        assertNotNull(retrievedPoint);
        assertPointsEqual(point1, retrievedPoint);
    }

    // getDistance：计算两个地理位置之间的距离。
    @Test
    public void testGetDistance() {
        Distance distance = geoService.getDistance(key, "Palermo", "Catania");
        assertNotNull(distance);
        assertTrue(distance.getValue() > 0);
    }

    // findNearby：查找某个位置附近的地点。
    @Test
    public void testFindNearby() {
        Point point = new Point(13.583333, 37.316667);
        geoService.addLocation(key, point, "Agrigento");
        List<GeoResult<RedisGeoCommands.GeoLocation<String>>> nearby = geoService.findNearby(key, point, 100);
        assertFalse(nearby.isEmpty());
    }

    // addLocations：批量添加地理位置
    @Test
    public void testAddLocations() {
        Map<String, Point> locations = Map.of(
                "Rome", new Point(12.496365, 41.902783),
                "Milan", new Point(9.1900, 45.4669)
        );
        geoService.addLocations(key, locations);
        assertPointsEqual(new Point(12.496365, 41.902783), geoService.getLocation(key, "Rome"));
        assertPointsEqual(new Point(9.1900, 45.4669), geoService.getLocation(key, "Milan"));
    }
}
