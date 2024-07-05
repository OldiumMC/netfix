package dev.oldium.netfix.mixin.netty;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.nio.Buffer;

@Mixin(targets = "io.netty.util.internal.PlatformDependent0")
public class MixinPlatformDependent0 {
    /**
     * The field offset of {@link java.lang.reflect.AccessibleObject#override}.
     * It must be declared inline here, as we cannot access the field through reflection to use {@link Unsafe#objectFieldOffset(Field)}
     */
    @Unique
    private static final long OVERRIDE_FIELD_OFFSET = 12L;

    @Final
    @Mutable
    @Shadow(remap = false)
    private static Unsafe UNSAFE;

    @Final
    @Mutable
    @Shadow(remap = false)
    private static boolean UNALIGNED;

    @Final
    @Mutable
    @Shadow(remap = false)
    private static long ADDRESS_FIELD_OFFSET;

    /**
     * Fix various lookup problems for newer JDKs.
     */
    @Inject(method = "<clinit>", at = @At(value = "RETURN"))
    private static void fixUnsafeLookup(CallbackInfo ci) {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            UNSAFE = (Unsafe) theUnsafe.get(null);

            Class<?> bits = Class.forName("java.nio.Bits");
            Field unaligned = bits.getDeclaredField("UNALIGNED");
            // Hack for making fields accessible.
            // essentially: AccessibleObject#override = true;
            UNSAFE.putBoolean(unaligned, OVERRIDE_FIELD_OFFSET, true);
            UNALIGNED = (boolean) unaligned.get(null);

            Field address = Buffer.class.getDeclaredField("address");
            UNSAFE.putBoolean(address, OVERRIDE_FIELD_OFFSET, true);
            ADDRESS_FIELD_OFFSET = UNSAFE.objectFieldOffset(address);
        } catch (Exception ignored) {
        }
    }
}
