package com.obstruct.clans.clans.codec;

import com.mongodb.BasicDBObject;
import dev.morphia.converters.BigDecimalConverter;
import dev.morphia.converters.SimpleValueConverter;
import dev.morphia.converters.TypeConverter;
import dev.morphia.mapping.MappedField;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.NumberConversions;

public class LocationConverter extends TypeConverter implements SimpleValueConverter {

    public LocationConverter() {
        super(Location.class);
    }

    @Override
    public Object decode(Class<?> aClass, Object o, MappedField mappedField) {
        BasicDBObject basicDBObject = (BasicDBObject) o;
        World world = Bukkit.getWorld(basicDBObject.getString("world"));
        double x = NumberConversions.toDouble(basicDBObject.getDouble("x"));
        double y = NumberConversions.toDouble(basicDBObject.getDouble("y"));
        double z = NumberConversions.toDouble(basicDBObject.getDouble("z"));
        float yaw = NumberConversions.toFloat(basicDBObject.getDouble("yaw"));
        float pitch = NumberConversions.toFloat(basicDBObject.getDouble("pitch"));
        return new Location(world, x, y, z, yaw, pitch);
    }

    @Override
    public Object encode(Object value, MappedField optionalExtraInfo) {
        Location location = (Location) value;
        BasicDBObject basicDBObject = new BasicDBObject();

        basicDBObject.put("world", location.getWorld().getName());
        basicDBObject.put("x", location.getX());
        basicDBObject.put("y", location.getY());
        basicDBObject.put("z", location.getZ());
        basicDBObject.put("yaw", location.getYaw());
        basicDBObject.put("pitch", location.getPitch());
        return super.encode(basicDBObject, optionalExtraInfo);
    }
}