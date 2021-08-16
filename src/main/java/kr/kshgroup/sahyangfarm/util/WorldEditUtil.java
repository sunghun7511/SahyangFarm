package kr.kshgroup.sahyangfarm.util;

import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.session.ClipboardHolder;
import org.bukkit.Location;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public final class WorldEditUtil {
    public static Clipboard getClipboardFromFile(File file, BukkitWorld bukkitWorld) throws IOException {
        ClipboardFormat format = ClipboardFormat.findByFile(file);
        ClipboardReader reader = format.getReader(new FileInputStream(file));
        return reader.read(bukkitWorld.getWorldData());
    }

    public static void pasteClipboard(Clipboard clipboard, BukkitWorld bukkitWorld, Location location) throws WorldEditException {
        EditSession session = WorldEdit.getInstance().getEditSessionFactory().getEditSession(bukkitWorld, -1);
        Operation operation = new ClipboardHolder(clipboard, bukkitWorld.getWorldData())
                .createPaste(session, bukkitWorld.getWorldData())
                .to(BlockVector.toBlockPoint(
                        location.getBlockX(),
                        location.getBlockY(),
                        location.getBlockZ()
                ))
                .ignoreAirBlocks(true)
                .build();
        Operations.complete(operation);
    }
}
