package rip.hippo.parcel.packet.generate;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.bukkit.Bukkit;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;
import rip.hippo.parcel.loader.StubClassLoader;
import rip.hippo.parcel.packet.Packet;
import rip.hippo.parcel.packet.annotation.EnumWrapper;
import rip.hippo.parcel.packet.annotation.GetterField;
import rip.hippo.parcel.packet.annotation.SetterField;
import rip.hippo.parcel.packet.impl.in.PacketPlayInAbilities;
import rip.hippo.parcel.packet.impl.in.PacketPlayInArmAnimation;
import rip.hippo.parcel.packet.impl.in.PacketPlayInBlockDig;
import rip.hippo.parcel.packet.impl.in.PacketPlayInChat;
import rip.hippo.parcel.packet.impl.out.*;
import rip.hippo.parcel.util.UnsafeUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import static org.objectweb.asm.Opcodes.*;

/**
 * @author Hippo
 * @version 1.0.0, 12/2/20
 * @since 1.0.0
 */
public enum PacketFactory {
    ;

    private static final String VERSION = "v" + Bukkit.getServer().getClass().getName().replaceAll("[^((\\d+)_(\\d+)_R(\\d+))]", "");
    private static final BiMap<String, Class<? extends Packet>> RAW_TO_WRAPPER_MAP = HashBiMap.create();

    private static final Map<Class<? extends Packet>, PacketFunction> PACKET_INSTANCE_FUNCTION_MAP = new HashMap<>();
    private static final StubClassLoader CLASS_LOADER = new StubClassLoader();

    @SuppressWarnings("unchecked")
    public static <T extends Packet> T from(Object raw) {
        String rawClassName = raw.getClass().getSimpleName();
        Class<? extends Packet> packetClass = RAW_TO_WRAPPER_MAP.get(rawClassName);

        if (packetClass == null) {
            return null;
        }

        PacketFunction packetFunction = getPacketFunction(packetClass, rawClassName);

        return (T) packetFunction.apply(raw);
    }

    public static <T extends Packet> T create(Class<T> packetClass) {
        BiMap<Class<? extends Packet>, String> inverse = RAW_TO_WRAPPER_MAP.inverse();
        String rawClassName = inverse.get(packetClass);
        String fullRawClassName = String.format("net.minecraft.server.%s.%s", VERSION, rawClassName);
        try {
            Object raw = UnsafeUtil.getUnsafe().allocateInstance(Class.forName(fullRawClassName));
            PacketFunction packetFunction = getPacketFunction(packetClass, rawClassName);
            return packetClass.cast(packetFunction.apply(raw));
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private static void ensureWrapperPacket(Class<? extends Packet> packetClass, Class<?> rawClass) {
        String rawClassName = rawClass.getSimpleName();

        String packetWrapperInternal = Type.getInternalName(packetClass);
        String unsafeUtilInternal = Type.getInternalName(UnsafeUtil.class);


        Map<String, Long> fieldOffsetMap = new HashMap<>();
        Map<String, Class<?>> fieldTypeMap = new HashMap<>();
        for (Field field : rawClass.getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers())) {
                fieldOffsetMap.put(field.getName(), UnsafeUtil.getUnsafe().objectFieldOffset(field));
                fieldTypeMap.put(field.getName(), field.getType());
            }
        }

        ClassNode classNode = new ClassNode();
        classNode.visit(V1_8, ACC_PUBLIC | ACC_FINAL, String.format("rip/hippo/parcel/generated/wrappers/%s", rawClassName), null, "java/lang/Object", new String[]{packetWrapperInternal});

        FieldNode rawPacketField = new FieldNode(ACC_PUBLIC, "raw", "Ljava/lang/Object;", null, null);
        classNode.fields.add(rawPacketField);

        MethodNode constructorMethodNode = new MethodNode(ACC_PUBLIC, "<init>", "(Ljava/lang/Object;)V", null, null);
        constructorMethodNode.instructions = new InsnList();
        constructorMethodNode.instructions.add(new VarInsnNode(ALOAD, 0));
        constructorMethodNode.instructions.add(new MethodInsnNode(INVOKESPECIAL, "java/lang/Object", "<init>", "()V"));
        constructorMethodNode.instructions.add(new VarInsnNode(ALOAD, 0));
        constructorMethodNode.instructions.add(new VarInsnNode(ALOAD, 1));
        constructorMethodNode.instructions.add(new FieldInsnNode(PUTFIELD, String.format("rip/hippo/parcel/generated/wrappers/%s", rawClassName), "raw", "Ljava/lang/Object;"));
        constructorMethodNode.instructions.add(new InsnNode(RETURN));
        classNode.methods.add(constructorMethodNode);

        MethodNode toRawMethodNode = new MethodNode(ACC_PUBLIC, "toRaw", "()Ljava/lang/Object;", null, null);
        toRawMethodNode.instructions = new InsnList();
        toRawMethodNode.instructions.add(new VarInsnNode(ALOAD, 0));
        toRawMethodNode.instructions.add(new FieldInsnNode(GETFIELD, String.format("rip/hippo/parcel/generated/wrappers/%s", rawClassName), "raw", "Ljava/lang/Object;"));
        toRawMethodNode.instructions.add(new InsnNode(ARETURN));
        classNode.methods.add(toRawMethodNode);


        for (Method method : packetClass.getDeclaredMethods()) {
            GetterField getterField = method.getAnnotation(GetterField.class);
            SetterField setterField = method.getAnnotation(SetterField.class);

            if (getterField != null) {
                long fieldOffset = fieldOffsetMap.get(getterField.name());

                MethodNode methodNode = new MethodNode(ACC_PUBLIC, method.getName(), Type.getMethodDescriptor(method), null, null);
                methodNode.instructions = new InsnList();
                methodNode.instructions.add(new MethodInsnNode(INVOKESTATIC, unsafeUtilInternal, "getUnsafe", "()Lsun/misc/Unsafe;"));
                methodNode.instructions.add(new VarInsnNode(ALOAD, 0));
                methodNode.instructions.add(new FieldInsnNode(GETFIELD, String.format("rip/hippo/parcel/generated/wrappers/%s", rawClassName), "raw", "Ljava/lang/Object;"));
                methodNode.instructions.add(new LdcInsnNode(fieldOffset));
                String integerMethod = null;
                switch (getterField.type()) {
                    case BOOLEAN:
                        integerMethod = "getBoolean";
                        break;
                    case BYTE:
                        integerMethod = "getByte";
                        break;
                    case SHORT:
                        integerMethod = "getShort";
                        break;
                    case CHAR:
                        integerMethod = "getChar";
                        break;
                    case INT:
                        integerMethod = "getInt";
                        break;
                    case LONG:
                        methodNode.instructions.add(new MethodInsnNode(INVOKEVIRTUAL, "sun/misc/Unsafe", "getLong", "(Ljava/lang/Object;J)J"));
                        methodNode.instructions.add(new InsnNode(LRETURN));
                        break;
                    case DOUBLE:
                        methodNode.instructions.add(new MethodInsnNode(INVOKEVIRTUAL, "sun/misc/Unsafe", "getDouble", "(Ljava/lang/Object;J)D"));
                        methodNode.instructions.add(new InsnNode(DRETURN));
                        break;
                    case OBJECT:
                        EnumWrapper annotation = method.getAnnotation(EnumWrapper.class);
                        methodNode.instructions.add(new MethodInsnNode(INVOKEVIRTUAL, "sun/misc/Unsafe", "getObject", "(Ljava/lang/Object;J)Ljava/lang/Object;"));
                        if (annotation != null) {
                            methodNode.instructions.add(new TypeInsnNode(CHECKCAST, "java/lang/Enum"));
                            methodNode.instructions.add(new MethodInsnNode(INVOKEVIRTUAL, "java/lang/Enum", "name", "()Ljava/lang/String;"));
                            methodNode.instructions.add(new MethodInsnNode(INVOKESTATIC, Type.getInternalName(annotation.value()), "valueOf", String.format("(Ljava/lang/String;)%s", Type.getDescriptor(annotation.value()))));
                        } else {
                            methodNode.instructions.add(new TypeInsnNode(CHECKCAST, Type.getInternalName(method.getReturnType())));
                        }
                        methodNode.instructions.add(new InsnNode(ARETURN));
                }

                if (integerMethod != null) {
                    methodNode.instructions.add(new MethodInsnNode(INVOKEVIRTUAL, "sun/misc/Unsafe", integerMethod, String.format("(Ljava/lang/Object;J)%s", Type.getDescriptor(method.getReturnType()))));
                    methodNode.instructions.add(new InsnNode(IRETURN));
                }

                classNode.methods.add(methodNode);
            } else if (setterField != null) {
                long fieldOffset = fieldOffsetMap.get(setterField.name());

                MethodNode methodNode = new MethodNode(ACC_PUBLIC, method.getName(), Type.getMethodDescriptor(method), null, null);
                methodNode.instructions = new InsnList();
                methodNode.instructions.add(new MethodInsnNode(INVOKESTATIC, unsafeUtilInternal, "getUnsafe", "()Lsun/misc/Unsafe;"));
                methodNode.instructions.add(new VarInsnNode(ALOAD, 0));
                methodNode.instructions.add(new FieldInsnNode(GETFIELD, String.format("rip/hippo/parcel/generated/wrappers/%s", rawClassName), "raw", "Ljava/lang/Object;"));
                methodNode.instructions.add(new LdcInsnNode(fieldOffset));


                switch (setterField.type()) {
                    case BOOLEAN:
                        methodNode.instructions.add(new VarInsnNode(ILOAD, 1));
                        methodNode.instructions.add(new MethodInsnNode(INVOKEVIRTUAL, "sun/misc/Unsafe", "putBoolean", "(Ljava/lang/Object;JZ)V"));
                        break;
                    case BYTE:
                        methodNode.instructions.add(new VarInsnNode(ILOAD, 1));
                        methodNode.instructions.add(new MethodInsnNode(INVOKEVIRTUAL, "sun/misc/Unsafe", "putByte", "(Ljava/lang/Object;JB)V"));
                        break;
                    case SHORT:
                        methodNode.instructions.add(new VarInsnNode(ILOAD, 1));
                        methodNode.instructions.add(new MethodInsnNode(INVOKEVIRTUAL, "sun/misc/Unsafe", "putShort", "(Ljava/lang/Object;JS)V"));
                        break;
                    case CHAR:
                        methodNode.instructions.add(new VarInsnNode(ILOAD, 1));
                        methodNode.instructions.add(new MethodInsnNode(INVOKEVIRTUAL, "sun/misc/Unsafe", "putChar", "(Ljava/lang/Object;JC)V"));
                        break;
                    case INT:
                        methodNode.instructions.add(new VarInsnNode(ILOAD, 1));
                        methodNode.instructions.add(new MethodInsnNode(INVOKEVIRTUAL, "sun/misc/Unsafe", "putInt", "(Ljava/lang/Object;JI)V"));
                        break;
                    case LONG:
                        methodNode.instructions.add(new VarInsnNode(LLOAD, 1));
                        methodNode.instructions.add(new MethodInsnNode(INVOKEVIRTUAL, "sun/misc/Unsafe", "putLong", "(Ljava/lang/Object;JJ)V"));
                        break;
                    case DOUBLE:
                        methodNode.instructions.add(new VarInsnNode(DLOAD, 1));
                        methodNode.instructions.add(new MethodInsnNode(INVOKEVIRTUAL, "sun/misc/Unsafe", "putDouble", "(Ljava/lang/Object;JD)V"));
                        break;
                    case OBJECT:
                        EnumWrapper annotation = method.getAnnotation(EnumWrapper.class);
                        Class<?> parameter = method.getParameterTypes()[0];
                        methodNode.instructions.add(new VarInsnNode(ALOAD, 1));
                        if (annotation != null) {
                            methodNode.instructions.add(new MethodInsnNode(INVOKEVIRTUAL, "java/lang/Enum", "name", "()Ljava/lang/String;"));
                            methodNode.instructions.add(new MethodInsnNode(INVOKESTATIC, Type.getInternalName(fieldTypeMap.get(setterField.name())), "valueOf", String.format("(Ljava/lang/String;)%s", Type.getDescriptor(annotation.value()))));
                        } else {
                            methodNode.instructions.add(new TypeInsnNode(CHECKCAST, Type.getInternalName(parameter)));
                        }
                        methodNode.instructions.add(new MethodInsnNode(INVOKEVIRTUAL, "sun/misc/Unsafe", "putObject", "(Ljava/lang/Object;JLjava/lang/Object;)V"));
                }
                methodNode.instructions.add(new InsnNode(RETURN));
                classNode.methods.add(methodNode);
            }
        }

        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        classNode.accept(classWriter);
        byte[] classBytes = classWriter.toByteArray();
        CLASS_LOADER.createClass(classNode.name.replace('/', '.'), classBytes);
    }

    private static synchronized PacketFunction getPacketFunction(Class<? extends Packet> packetClass, String rawClassName) {
        return PACKET_INSTANCE_FUNCTION_MAP.computeIfAbsent(packetClass, ignored -> {
            String packetFunctionInternal = Type.getInternalName(PacketFunction.class);
            String packetInternalName = Type.getInternalName(Packet.class);

            ClassNode classNode = new ClassNode();
            classNode.visit(V1_8, ACC_PUBLIC | ACC_FINAL, String.format("rip/hippo/parcel/generated/functions/%s", rawClassName), null, "java/lang/Object", new String[]{packetFunctionInternal});

            MethodNode constructorMethodNode = new MethodNode(ACC_PUBLIC, "<init>", "()V", null, null);
            constructorMethodNode.instructions = new InsnList();
            constructorMethodNode.instructions.add(new VarInsnNode(ALOAD, 0));
            constructorMethodNode.instructions.add(new MethodInsnNode(INVOKESPECIAL, "java/lang/Object", "<init>", "()V"));
            constructorMethodNode.instructions.add(new InsnNode(RETURN));

            MethodNode applyMethodNode = new MethodNode(ACC_PUBLIC, "apply", String.format("(Ljava/lang/Object;)L%s;", packetInternalName), null, null);
            applyMethodNode.instructions = new InsnList();
            applyMethodNode.instructions.add(new TypeInsnNode(NEW, String.format("rip/hippo/parcel/generated/wrappers/%s", rawClassName)));
            applyMethodNode.instructions.add(new InsnNode(DUP));
            applyMethodNode.instructions.add(new VarInsnNode(ALOAD, 1));
            applyMethodNode.instructions.add(new MethodInsnNode(INVOKESPECIAL, String.format("rip/hippo/parcel/generated/wrappers/%s", rawClassName), "<init>", "(Ljava/lang/Object;)V"));
            applyMethodNode.instructions.add(new InsnNode(ARETURN));

            classNode.methods.add(constructorMethodNode);
            classNode.methods.add(applyMethodNode);

            ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
            classNode.accept(classWriter);

            byte[] classBytes = classWriter.toByteArray();

            try {
                Class<?> dynamicClass = CLASS_LOADER.createClass(classNode.name.replace('/', '.'), classBytes);
                return (PacketFunction) dynamicClass.newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static void unsafePut(Class<? extends Packet> packetClass) {
        RAW_TO_WRAPPER_MAP.put(packetClass.getSimpleName(), packetClass);
    }

    static {
        unsafePut(PacketPlayInChat.class);
        unsafePut(PacketPlayInAbilities.class);
        unsafePut(PacketPlayInArmAnimation.class);
        unsafePut(PacketPlayInBlockDig.class);
        unsafePut(PacketPlayOutAnimation.class);
        unsafePut(PacketPlayOutSpawnEntity.class);
        unsafePut(PacketPlayOutScoreboardDisplayObjective.class);
        unsafePut(PacketPlayOutScoreboardObjective.class);
        unsafePut(PacketPlayOutScoreboardScore.class);
        unsafePut(PacketPlayOutScoreboardTeam.class);
        unsafePut(PacketPlayOutPlayerInfo.class);

        for (Map.Entry<String, Class<? extends Packet>> stringClassEntry : RAW_TO_WRAPPER_MAP.entrySet()) {
            String simpleName = stringClassEntry.getKey();
            Class<? extends Packet> packetWrapperClass = stringClassEntry.getValue();
            try {
                ensureWrapperPacket(packetWrapperClass, Class.forName("net.minecraft.server." + VERSION + "." + simpleName));
                getPacketFunction(packetWrapperClass, simpleName);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
