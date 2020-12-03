package rip.hippo.parcel.packet.generate;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;
import rip.hippo.parcel.loader.StubClassLoader;
import rip.hippo.parcel.packet.Packet;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static org.objectweb.asm.Opcodes.*;

/**
 * @author Hippo
 * @version 1.0.0, 12/2/20
 * @since 1.0.0
 */
public enum PacketFactory {
    ;

    private static final Map<String, Class<Packet>> RAW_TO_WRAPPER_MAP = Collections.unmodifiableMap(new HashMap<String, Class<Packet>>() {{

    }});

    private static final Map<Class<Packet>, PacketFunction> PACKET_INSTANCE_FUNCTION_MAP = new HashMap<>();

    private static final StubClassLoader CLASS_LOADER = new StubClassLoader();

    public static Packet create(Object raw) {
        String rawClassName = raw.getClass().getSimpleName();
        Class<Packet> packetClass = RAW_TO_WRAPPER_MAP.get(rawClassName);

        PacketFunction objectWrapperFunction = PACKET_INSTANCE_FUNCTION_MAP.computeIfAbsent(packetClass, ignored -> {
            String packetFunctionInternal = Type.getInternalName(PacketFunction.class);
            String packetInternalName = Type.getInternalName(Packet.class);

            ClassNode classNode = new ClassNode();
            classNode.visit(V1_8, ACC_PUBLIC | ACC_FINAL, String.format("rip/hippo/parcel/generated/functions/%s", rawClassName), null, "java/lang/Object", new String[] {packetFunctionInternal});

            MethodNode constructorMethodNode = new MethodNode(ACC_PUBLIC, "<init>", "()V", null, null);
            constructorMethodNode.instructions = new InsnList();
            constructorMethodNode.instructions.add(new VarInsnNode(ALOAD, 0));
            constructorMethodNode.instructions.add(new MethodInsnNode(INVOKESPECIAL, "java/lang/Object", "<init>", "()V"));
            constructorMethodNode.instructions.add(new InsnNode(RETURN));

            MethodNode acceptMethodNode = new MethodNode(ACC_PUBLIC, "accept", String.format("(Ljava/lang/Object)L%s;", packetInternalName), null, null);
            acceptMethodNode.instructions = new InsnList();
            acceptMethodNode.instructions.add(new TypeInsnNode(NEW, String.format("rip/hippo/parcel/generated/wrappers/%s", rawClassName)));
            acceptMethodNode.instructions.add(new InsnNode(DUP));
            acceptMethodNode.instructions.add(new VarInsnNode(ALOAD, 1));
            acceptMethodNode.instructions.add(new MethodInsnNode(INVOKESPECIAL, String.format("rip/hippo/parcel/generated/wrappers/%s", rawClassName), "<init>", "(Ljava/lang/Object;)V"));
            acceptMethodNode.instructions.add(new InsnNode(ARETURN));

            classNode.methods.add(constructorMethodNode);
            classNode.methods.add(acceptMethodNode);

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

        return null;
    }

}
