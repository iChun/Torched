package me.ichun.mods.torched.common.packet;

import io.netty.buffer.ByteBuf;
import me.ichun.mods.ichunutil.common.core.network.AbstractPacket;
import me.ichun.mods.torched.common.Torched;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;

public class PacketKeyEvent extends AbstractPacket
{
    public boolean pressed;

    public PacketKeyEvent(){}

    public PacketKeyEvent(boolean press)
    {
        pressed = press;
    }

    @Override
    public void writeTo(ByteBuf buffer)
    {
        buffer.writeBoolean(pressed);
    }

    @Override
    public void readFrom(ByteBuf buffer)
    {
        pressed = buffer.readBoolean();
    }

    @Override
    public AbstractPacket execute(Side side, EntityPlayer player)
    {
        if(pressed)
        {
            Torched.eventHandlerServer.playerDelay.put(player.getName(), 0);
            Torched.eventHandlerServer.shootTorch(player);
        }
        else
        {
            Torched.eventHandlerServer.playerDelay.remove(player.getName());
        }
        return null;
    }

    @Override
    public Side receivingSide()
    {
        return Side.SERVER;
    }
}
