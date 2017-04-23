package me.ichun.mods.torched.common;

import me.ichun.mods.ichunutil.common.core.network.PacketChannel;
import me.ichun.mods.ichunutil.common.iChunUtil;
import me.ichun.mods.ichunutil.common.module.update.UpdateChecker;
import me.ichun.mods.torched.client.core.EventHandlerClient;
import me.ichun.mods.torched.common.core.EventHandlerServer;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import me.ichun.mods.torched.common.core.ProxyCommon;
import me.ichun.mods.torched.common.packet.PacketKeyEvent;

@Mod(modid = Torched.MOD_ID, name = Torched.MOD_NAME,
        version = Torched.VERSION,
        guiFactory = "me.ichun.mods.ichunutil.common.core.config.GenericModGuiFactory",
        dependencies = "required-after:ichunutil@[" + iChunUtil.VERSION_MAJOR +".2.0," + (iChunUtil.VERSION_MAJOR + 1) + ".0.0)",
        acceptableRemoteVersions = "[" + iChunUtil.VERSION_MAJOR +".0.0," + iChunUtil.VERSION_MAJOR + ".1.0)"
)
public class Torched
{
    public static final String MOD_NAME = "Torched";
    public static final String MOD_ID = "torched";
    public static final String VERSION = iChunUtil.VERSION_MAJOR + ".0.1";

    @Instance(Torched.MOD_ID)
    public static Torched instance;

    @SidedProxy(clientSide = "me.ichun.mods.torched.client.core.ProxyClient", serverSide = "me.ichun.mods.torched.common.core.ProxyCommon")
    public static ProxyCommon proxy;

    public static PacketChannel channel;

    public static EventHandlerClient eventHandlerClient;
    public static EventHandlerServer eventHandlerServer;

    public static Item itemTorchGun;
    public static Item itemTorchFirework;
    public static Item itemTorchLauncher;

    public static SoundEvent soundRPT;
    public static SoundEvent soundTube;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        proxy.preInitMod();

        channel = new PacketChannel(Torched.MOD_NAME, PacketKeyEvent.class);

        UpdateChecker.registerMod(new UpdateChecker.ModVersionInfo(MOD_NAME, iChunUtil.VERSION_OF_MC, VERSION, false));
    }
}
