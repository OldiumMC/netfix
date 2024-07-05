package dev.oldium.netfix.mixin.patchy;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.net.SocketAddress;

@Mixin(Bootstrap.class)
public class MixinBootstrap {
    /**
     * @author Decencies
     * @reason Remove checks for server IPs.
     */
    @Overwrite(remap = false)
    ChannelFuture checkAddress(SocketAddress remoteAddress) {
        return null /* no-op */;
    }
}
