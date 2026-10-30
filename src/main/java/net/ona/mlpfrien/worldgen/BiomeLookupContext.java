package net.ona.mlpfrien.worldgen;

public final class BiomeLookupContext {
    private static final ThreadLocal<Boolean> VANILLA_LOOKUP = ThreadLocal.withInitial(() -> false);

    public static boolean isVanillaLookup() {return VANILLA_LOOKUP.get();}
    public static void beginVanillaLookup() {VANILLA_LOOKUP.set(true);}
    public static void endVanillaLookup() {VANILLA_LOOKUP.remove();}
}
