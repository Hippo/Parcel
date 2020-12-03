package rip.hippo.parcel.loader;

import rip.hippo.parcel.plugin.ParcelPlugin;

/**
 * @author Hippo
 * @version 1.0.0, 12/3/20
 * @since 1.0.0
 */
public final class StubClassLoader extends ClassLoader {

    public StubClassLoader() {
        super(ParcelPlugin.class.getClassLoader());
    }

    public Class<?> createClass(String className, byte[] classBytes) {
        return super.defineClass(className, classBytes, 0, classBytes.length);
    }
}
