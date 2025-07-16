//package com.github.Debris.ommc.mixins;
//
//import com.github.Debris.ommc.event.tick.TestTicker;
//import net.minecraft.ChunkProviderUnderworld;
//import net.minecraft.IChunkProvider;
//import net.minecraft.World;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.Shadow;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//
//@Mixin(ChunkProviderUnderworld.class)
//public abstract class ChunkProviderUnderworldMixin implements IChunkProvider {
//
//    @Shadow
//    private World worldObj;
//
//    @Inject(method = "populate", at = @At("RETURN"))
//    private void test(IChunkProvider par1IChunkProvider, int x, int z, CallbackInfo ci) {
//        TestTicker.ChunkPos chunkPos = new TestTicker.ChunkPos(x, z);
//        if (!TestTicker.container.contains(chunkPos)) {
//            TestTicker.container.add(chunkPos);
//            TestTicker.analyzeChunk(this.worldObj, chunkPos);
//
//            if (TestTicker.container.size() % 1000 == 0) {
//                System.out.println("Analyze progress: " + TestTicker.container.size());
//            }
//
//            if (TestTicker.container.size() >= 2048 && !TestTicker.printed) {
//                TestTicker.printed = true;
//                TestTicker.print();
//            }
//
//        }
//    }
//}
