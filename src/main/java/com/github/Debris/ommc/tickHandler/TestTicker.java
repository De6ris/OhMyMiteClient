package com.github.Debris.ommc.tickHandler;

import fi.dy.masa.malilib.interfaces.IClientTickHandler;
import net.minecraft.Block;
import net.minecraft.EntityClientPlayerMP;
import net.minecraft.Minecraft;
import net.minecraft.World;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TestTicker implements IClientTickHandler {
    static final TestTicker Instance = new TestTicker();

    public static TestTicker getInstance() {
        return Instance;
    }

    public static boolean printed;

    static boolean debug;

    public static int[] data = new int[255];

    @Override
    public void onClientTick(Minecraft minecraft) {
        if (minecraft.theWorld == null) return;


        if (!debug) {
            ChunkPos chunkPos = new ChunkPos(114, 514);
            chunkPos.through(0, 16).forEach(blockPos -> {
                Block block = minecraft.theWorld.getBlock(blockPos.x, blockPos.y, blockPos.z);
                if (block == null) {
                    System.out.println("null block at" + blockPos);
                } else {
                    System.out.println(block.getLocalizedName());
                }
            });
            debug = true;
        }

        if (!minecraft.theWorld.isUnderworld()) return;

        EntityClientPlayerMP thePlayer = minecraft.thePlayer;
        ChunkPos chunkPos = new ChunkPos(thePlayer.getChunkPosX(), thePlayer.getChunkPosZ());


        chunkPos.around(5).forEach(x -> {
            if (!container.contains(x)) {
                analyzeChunk(minecraft.theWorld, chunkPos);
                container.add(x);
                if (container.size() % 100 == 0) {
                    System.out.println("Analyze progress: " + container.size());
                }
            }
        });
        if (container.size() >= 8192 && !printed) {
            print();
        }
    }

    public static void print() {
        System.out.println("has sampled chunks: " + container.size());

        for (int i = 0; i < 255; i++) {
            System.out.println("layer " + i + ": " + data[i]);
        }

        ArrayList<OreData> oreData = new ArrayList<>();

        for (int i = 0; i < 255; i++) {
            oreData.add(new OreData(i, (double) data[i] / container.size()));
        }

        oreData.sort(Comparator.comparingDouble(OreData::frequency));

        System.out.println("Sorted frequency: ");

        for (OreData oreDatum : oreData) {
            System.out.println("layer " + oreDatum.y + " with frequency: " + oreDatum.frequency);
        }

        printed = true;
    }

    public static final Set<ChunkPos> container = new HashSet<>();

    public record ChunkPos(int x, int z) {
        Stream<ChunkPos> around(int radius) {// 1->9, 2->25
            return IntStream.rangeClosed(-radius, radius).boxed()
                    .flatMap(m -> IntStream.rangeClosed(-radius, radius).boxed().map(n -> new ChunkPos(this.x + m, this.z + n)));
        }

        Stream<BlockPos> through(int minY, int maxY) {
            return IntStream.rangeClosed(minY, maxY).boxed()
                    .flatMap(m -> IntStream.rangeClosed(0, 255).boxed().map(n -> new BlockPos((this.x << 4) + (n >> 4), m, (this.z << 4) + (n & 15))));
        }
    }

    public record BlockPos(int x, int y, int z) {
    }

    public record OreData(int y, double frequency) {
    }

    public static void analyzeChunk(World world, ChunkPos chunkPos) {
        chunkPos.through(0, 255).forEach(blockPos -> {
            Block block = world.getBlock(blockPos.x, blockPos.y, blockPos.z);
            if (block == Block.oreAdamantium) {
                data[blockPos.y] += 1;
            }
        });
    }
}
