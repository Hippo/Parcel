package rip.hippo.parcel.wrapper;

import io.netty.channel.Channel;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;
import rip.hippo.parcel.plugin.ParcelPlugin;

import javax.management.ReflectionException;

import static org.objectweb.asm.Opcodes.*;

/**
 * @author Hippo
 * @version 1.0.0, 12/2/20
 * @since 1.0.0
 */
public enum PlayerWrapperFactory {
    ;

    private static final String VERSION = "v" + Bukkit.getServer().getClass().getName().replaceAll("[^((\\d+)_(\\d+)_R(\\d+))]", "");
    private static final StubClassLoader CLASS_LOADER = new StubClassLoader();

    public static PlayerWrapper createPlayerWrapper() {
        String playerWrapperInternal = Type.getDescriptor(PlayerWrapper.class);
        String craftPlayerInternal = String.format("org/bukkit/craftbukkit/%s/entity/CraftPlayer", VERSION);
        String entityPlayerInternal = String.format("net/minecraft/server/%s/EntityPlayer", VERSION);
        String playerConnectionInternal = String.format("net/minecraft/server/%s/PlayerConnection", VERSION);
        String networkManagerInternal = String.format("net/minecraft/server/%s/NetworkManager", VERSION);

        ClassNode classNode = new ClassNode();
        classNode.visit(V1_8, ACC_PUBLIC | ACC_FINAL, "rip/hipp/parcel/generated/GeneratedPlayerWrapper", null, "java/lang/Object", new String[] {playerWrapperInternal});

        MethodNode constructorMethodNode = new MethodNode(ACC_PUBLIC, "<init>", "()V", null, null);
        constructorMethodNode.instructions = new InsnList();
        constructorMethodNode.instructions.add(new VarInsnNode(ALOAD, 0));
        constructorMethodNode.instructions.add(new MethodInsnNode(INVOKESPECIAL, "java/lang/Object", "<init>", "()V"));
        constructorMethodNode.instructions.add(new InsnNode(RETURN));

        MethodNode getChannelMethodNode = new MethodNode(ACC_PUBLIC, "getChannel", String.format("(%s)%s", Type.getDescriptor(Player.class), Type.getDescriptor(Channel.class)), null, null);
        getChannelMethodNode.instructions = new InsnList();
        getChannelMethodNode.instructions.add(new VarInsnNode(ALOAD, 1));
        getChannelMethodNode.instructions.add(new TypeInsnNode(CHECKCAST, craftPlayerInternal));
        getChannelMethodNode.instructions.add(new MethodInsnNode(INVOKEVIRTUAL, craftPlayerInternal, "getHandle", String.format("()L%s;", entityPlayerInternal)));
        getChannelMethodNode.instructions.add(new FieldInsnNode(GETFIELD, entityPlayerInternal, "playerConnection", String.format("L%s;", playerConnectionInternal)));
        getChannelMethodNode.instructions.add(new FieldInsnNode(GETFIELD, playerConnectionInternal, "neworkManager", String.format("L%s;", networkManagerInternal)));
        getChannelMethodNode.instructions.add(new FieldInsnNode(GETFIELD, networkManagerInternal, "channel", Type.getDescriptor(Channel.class)));
        getChannelMethodNode.instructions.add(new InsnNode(ARETURN));

        classNode.methods.add(constructorMethodNode);
        classNode.methods.add(getChannelMethodNode);

        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        classNode.accept(classWriter);

        byte[] classBytes = classWriter.toByteArray();

        try {
            Class<?> dynamicClass = CLASS_LOADER.createClass(classNode.name.replace('/', '.'), classBytes);
            return (PlayerWrapper) dynamicClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static final class StubClassLoader extends ClassLoader {
        public StubClassLoader() {
            super(ParcelPlugin.class.getClassLoader());
        }

        public Class<?> createClass(String className, byte[] classBytes) {
            return super.defineClass(className, classBytes, 0, classBytes.length);
        }
    }
}