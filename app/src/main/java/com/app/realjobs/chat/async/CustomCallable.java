package com.app.realjobs.chat.async;

import java.util.concurrent.Callable;

public interface CustomCallable<R> extends Callable<R> {
    void setDataAfterLoading(R result);

    void setUiForLoading();
}