package me.ichun.mods.torched.common.packet;

import me.ichun.mods.ichunutil.common.network.AbstractPacket;
import me.ichun.mods.torched.common.Torched;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class PacketKeyEvent extends AbstractPacket
{
    public boolean pressed;

    public PacketKeyEvent(){}

    public PacketKeyEvent(boolean press)
    {
        pressed = press;
    }

    @Override
    public void writeTo(PacketBuffer buffer)
    {
        buffer.writeBoolean(pressed);
    }

    @Override
    public void readFrom(PacketBuffer buffer)
    {
        pressed = buffer.readBoolean();
    }

    @Override
    public void process(NetworkEvent.Context context) //receivingSide = SERVER
    {
        context.enqueueWork(() -> {
            ServerPlayerEntity player = context.getSender();
            if(pressed)
            {
                Torched.eventHandlerServer.playerDelay.put(player.getName().getUnformattedComponentText(), 0);
                Torched.eventHandlerServer.shootTorch(player);
            }
            else
            {
                Torched.eventHandlerServer.playerDelay.remove(player.getName().getUnformattedComponentText());
            }

        });
    }
}
