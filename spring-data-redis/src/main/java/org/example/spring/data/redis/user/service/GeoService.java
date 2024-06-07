package org.example.spring.data.redis.user.service;

import jakarta.annotation.Resource;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Redis 的地理空间（Geo）功能允许您存储地理空间数据（例如，城市或商店的位置）并对这些数据执行操作（例如，查找附近的地点或计算两点之间的距离）。
 * 这些功能基于 Redis 的有序集合（sorted set）数据结构
 * 位置服务：查找附近的商店、餐馆、加油站等。
 * 距离计算：计算两个地理位置之间的距离。
 * 地理围栏：检测某个点是否在指定的地理区域内。
 *
 * @Description
 * @Author SangY
 * @Date 2024/6/7 13:20
 **/
@Service
public class GeoService {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    // 添加地理位置
    public void addLocation(String key, Point point, String name) {
        GeoOperations<String, String> geoOps = redisTemplate.opsForGeo();
        geoOps.add(key, point, name);
    }

    // 获取地理位置
    public Point getLocation(String key, String name) {
        GeoOperations<String, String> geoOps = redisTemplate.opsForGeo();
        List<Point> points = geoOps.position(key, name);
        return points.isEmpty() ? null : points.get(0);
    }

    // 计算两个地点之间的距离
    public Distance getDistance(String key, String name1, String name2) {
        GeoOperations<String, String> geoOps = redisTemplate.opsForGeo();
        return geoOps.distance(key, name1, name2, RedisGeoCommands.DistanceUnit.KILOMETERS);
    }

    // 查找某个地点附近的地点
    public List<GeoResult<RedisGeoCommands.GeoLocation<String>>> findNearby(String key, Point point, double radius) {
        GeoOperations<String, String> geoOps = redisTemplate.opsForGeo();
        Circle circle = new Circle(point, new Distance(radius, Metrics.KILOMETERS));
        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs().includeDistance().sortAscending();
        return geoOps.radius(key, circle, args).getContent();
    }

    // 批量添加地理位置
    public void addLocations(String key, Map<String, Point> locations) {
        GeoOperations<String, String> geoOps = redisTemplate.opsForGeo();
        locations.forEach((name, point) -> geoOps.add(key, point, name));
    }

    public void deleteKey(String key) {
        redisTemplate.delete(key);
    }
}
