package org.lts.lego_teh_set.commands.container;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.util.ClassUtils;

import java.io.*;

/// {@link RedisSerializer} implementation for {@link AbstractContainer}
///
/// @see RedisSerializer
/// @see AbstractContainer
public class AbstractContainerRedisSerializer implements RedisSerializer<AbstractContainer> {
    private final Logger LOG = LoggerFactory.getLogger(AbstractContainerRedisSerializer.class);

    @Override
    public boolean canSerialize(Class<?> type) {
        return ClassUtils.isAssignable(this.getTargetType(), type);
    }

    @Override
    public Class<?> getTargetType() {
        return AbstractContainer.class;
    }

    @Override
    public byte[] serialize(AbstractContainer value) throws SerializationException {
        var bos = new ByteArrayOutputStream();

        try (var out = new ObjectOutputStream(bos)) {
            out.writeObject(value);
            out.flush();
            return bos.toByteArray();
        } catch (IOException ex) {
            LOG.error("ContainerValueRedisSerializer serialize exception: {}", ex.getMessage());
            throw new UncheckedIOException(ex);
        }
    }

    @Override
    public AbstractContainer deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null) return null;

        var bis = new ByteArrayInputStream(bytes);

        try (var in = new ObjectInputStream(bis)) {
            return (AbstractContainer) in.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            LOG.error("ContainerValueRedisSerializer deserialize exception: {}", ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

}
