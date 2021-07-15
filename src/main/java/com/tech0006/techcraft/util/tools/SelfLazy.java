package com.tech0006.techcraft.util.tools;

import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullSupplier;

public abstract class SelfLazy<T> implements NonNullSupplier<T> {
    private final LazyOptional<T> lazy = LazyOptional.of(this);

    public final <R> LazyOptional<R> lazyCast() {
        return lazy.cast();
    }

    public final void invalidate() {
        lazy.invalidate();
    }
}
